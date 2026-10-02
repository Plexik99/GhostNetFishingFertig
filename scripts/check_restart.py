#!/usr/bin/env python3
"""HTTP-Neustartprüfung in einem neuen temporären Verzeichnis, nur mit Testdaten."""
import argparse
import html
import http.cookiejar
import json
import re
import socket
import subprocess
import tempfile
import time
from datetime import datetime, timezone
from pathlib import Path
from urllib.error import URLError
from urllib.parse import urlencode
from urllib.request import HTTPCookieProcessor, ProxyHandler, build_opener


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    source = parser.add_mutually_exclusive_group(required=True)
    source.add_argument('--jar', type=Path, help='Mit mvn package erzeugte ausführbare JAR')
    source.add_argument('--classpath-file', type=Path, help='Alternative: vollständiger Java-Klassenpfad als Textdatei')
    parser.add_argument('--result', type=Path, help='Optionaler Pfad für das JSON-Ergebnis')
    args = parser.parse_args()
    if args.jar:
        jar = args.jar.resolve(strict=True)
        command = ['java', '-jar', str(jar)]
    else:
        command = ['java', '-cp', args.classpath_file.read_text().strip(),
                   'de.alex.ghostnet.GhostNetApplication']
    with socket.socket() as sock:
        sock.bind(('127.0.0.1', 0))
        port = sock.getsockname()[1]
    base = f'http://127.0.0.1:{port}'
    process = None
    logfile = None
    checks = []
    password = 'NurFuerNeustart123!'

    def session():
        return build_opener(ProxyHandler({}), HTTPCookieProcessor(http.cookiejar.CookieJar()))

    def request(client, path, data=None):
        body = None if data is None else urlencode(data).encode()
        with client.open(base + path, body, timeout=15) as response:
            return response.geturl(), response.read().decode('utf-8')

    def post(client, page, target, data):
        _, content = request(client, page)
        # Die Anwendung rendert Spring-Security-CSRF-Felder in ihren Formularen.
        token = re.search(r'name="_csrf"[^>]*value="([^"]+)"', content)
        if token is None:
            raise RuntimeError(f'Kein CSRF-Token auf {page}')
        return request(client, target, {**data, '_csrf': html.unescape(token.group(1))})

    def login(name):
        client = session()
        url, _ = post(client, '/anmelden', '/anmelden', {'username': name, 'password': password})
        assert url.endswith('/meine-bergungen'), f'Anmeldung fehlgeschlagen: {name}'
        return client

    def stop():
        nonlocal process, logfile
        if process is not None and process.poll() is None:
            process.terminate()
            try:
                process.wait(timeout=20)
            except subprocess.TimeoutExpired:
                process.kill()
                process.wait(timeout=10)
                raise RuntimeError('Kein sauberer Prozessstopp; Neustartnachweis abgebrochen')
        process = None
        if logfile:
            logfile.close()
            logfile = None

    with tempfile.TemporaryDirectory(prefix='ghostnet-neustart-') as directory:
        work = Path(directory)

        def start(number):
            nonlocal process, logfile
            logpath = work / f'app-{number}.log'
            logfile = logpath.open('w', encoding='utf-8')
            process = subprocess.Popen(command + [f'--server.port={port}', '--server.address=127.0.0.1',
                                                   '--logging.level.root=WARN'],
                                       cwd=work, stdout=logfile, stderr=subprocess.STDOUT)
            deadline = time.monotonic() + 60
            probe = session()
            while time.monotonic() < deadline:
                if process.poll() is not None:
                    raise RuntimeError('Start fehlgeschlagen: ' + logpath.read_text()[-3000:])
                try:
                    request(probe, '/')
                    return
                except URLError:
                    time.sleep(0.25)
            raise RuntimeError('Anwendung nach 60 Sekunden nicht erreichbar')

        try:
            start(1)
            public = session()
            for username, name in [('pruefung.a', 'Testperson A'), ('pruefung.b', 'Testperson B')]:
                url, _ = post(public, '/registrieren', '/registrieren', {
                    'benutzername': username, 'name': name, 'telefon': '00000',
                    'passwort': password, 'passwortWiederholung': password})
                assert url.endswith('/anmelden'), 'Registrierung fehlgeschlagen'
            a, b = login('pruefung.a'), login('pruefung.b')
            ids = []
            for _ in range(3):
                url, _ = post(public, '/netze/melden', '/netze/melden', {
                    'breitengrad': '54.18', 'laengengrad': '12.09',
                    'groesseInQuadratmetern': '20.50', 'anonym': 'true'})
                match = re.search(r'/netze/(\d+)$', url)
                assert match is not None, 'Netzmeldung fehlgeschlagen'
                ids.append(match.group(1))
            for net in ids[:2]:
                post(a, f'/netze/{net}', f'/netze/{net}/uebernehmen', {})
            post(a, f'/netze/{ids[0]}', f'/netze/{ids[0]}/abschliessen', {})
            post(b, f'/netze/{ids[1]}', f'/netze/{ids[1]}/verschollen', {})
            stop()
            assert (work / 'data/ghostnet.mv.db').is_file(), 'H2-Datei fehlt'
            start(2)
            a, b = login('pruefung.a'), login('pruefung.b')
            checks.append('Beide Konten können sich nach vollständigem Prozessneustart anmelden')
            for net, state in zip(ids, ['Geborgen', 'Verschollen', 'Gemeldet']):
                _, content = request(a, f'/netze/{net}')
                assert state in content and '54.180000' in content and '12.090000' in content
                checks.append(f'Netz {net}: Status {state} und Koordinaten erhalten')
            _, own = request(a, '/meine-bergungen')
            assert all(f'href="/netze/{net}"' in own for net in ids[:2])
            checks.append('Beide Zuordnungen bleiben unter Meine Bergungen erhalten')
            _, opened = request(a, '/netze')
            assert f'href="/netze/{ids[2]}"' in opened
            assert all(f'href="/netze/{net}"' not in opened for net in ids[:2])
            checks.append('Offene Liste enthält nur das weiterhin gemeldete Netz')
            result = {'success': True, 'utc': datetime.now(timezone.utc).isoformat(),
                      'checks': checks, 'scope': 'HTTP, zwei getrennte Java-Prozesse, H2-Datei; keine Browserprüfung'}
            encoded = json.dumps(result, ensure_ascii=False, indent=2)
            if args.result:
                args.result.write_text(encoded + '\n', encoding='utf-8')
            print(encoded)
        finally:
            stop()


if __name__ == '__main__':
    main()
