# Ghost Net Fishing

Java-Prototyp zur IU-Fallstudie IPWA02-01, Aufgabe 3. Umgesetzt sind die Backlog-Punkte 1, 2, 3, 4 und 7: Netze melden, offene Netze anzeigen, Bergungen übernehmen und abschließen sowie Netze als verschollen melden.

## Voraussetzungen und Start

- JDK 17, geprüft mit Java 17.
- Apache Maven 3.9.x, geprüft mit Maven 3.9.11. Ein Maven Wrapper ist nicht enthalten.
- Für den ersten Build Internetzugriff auf Maven Central.

Im Projektordner mit der `pom.xml`:

```sh
java -version
mvn -version
mvn test
mvn spring-boot:run
```

Danach <http://127.0.0.1:8080> öffnen. Beenden im Terminal mit Strg+C. Die Anwendung bindet standardmäßig nur an die lokale Adresse. Es gibt keine vorgegebenen Konten. Meldungen sind ohne Anmeldung möglich; für Bergungen und Verschollenmeldungen ein Testkonto anlegen.

Alternativ kann ein ausführbares Paket erstellt werden:

```sh
mvn package
java -jar target/ghost-net-fishing-1.0.0.jar
```

### Eclipse

JDK 17 als JRE/JDK der IDE auswählen. Über **File → Import → Maven → Existing Maven Projects** den Ordner mit `pom.xml` importieren. Maven-Abhängigkeiten laden lassen. `de.alex.ghostnet.GhostNetApplication` als Java Application starten. In der Run Configuration das Arbeitsverzeichnis auf den Projektordner setzen. Für Tests `pom.xml` als Maven Build mit dem Ziel `test` ausführen.

## Datenbank und Arbeitsverzeichnis

H2 speichert unter `./data/ghostnet.mv.db`, relativ zum **Arbeitsverzeichnis des Java-Prozesses**. Bei einem Neustart dasselbe Arbeitsverzeichnis verwenden. Ein anderes Arbeitsverzeichnis erzeugt eine andere Datenbank. Das ZIP enthält keine Konten oder Datenbankdatei. Die integrierte H2-Konsole ist deaktiviert.

Für einen leeren Teststand die Anwendung beenden und den lokalen Ordner `data` vorher sichern und anschließend umbenennen. Keine Datenbankdatei einer laufenden Anwendung kopieren oder bearbeiten. Laufzeitdaten und Buildausgaben gehören nicht in Git.

## Prüfungen

`mvn test` führt die vorhandenen 25 Fälle in `GhostNetIntegrationTest` aus. Sie verwenden eine H2-Datenbank im Speicher; die lokale Dateidatenbank wird dabei nicht verwendet. Der Nebenläufigkeitstest prüft zwei Datenbanktransaktionen, keine parallelen HTTP-Anfragen.

Zusätzlich ist eine wiederholbare HTTP-Neustartprüfung enthalten. Sie benötigt Python 3.10 oder neuer, erstellt ausschließlich künstliche Daten in einem neuen temporären Verzeichnis, beendet und startet den Java-Prozess und prüft Konten, Status, Koordinaten, Zuordnungen und Listen erneut:

```sh
mvn package
python3 scripts/check_restart.py --jar target/ghost-net-fishing-1.0.0.jar
```

Unter Windows kann `python` anstelle von `python3` erforderlich sein. Das Skript löscht am Ende nur sein selbst angelegtes temporäres Verzeichnis. Es greift nicht auf die eigene Projekt-Datenbank zu. Ausgewählte Prüfkriterien und die zuletzt ausgeführten Ergebnisse stehen in `docs/PRUEFNACHWEIS.md` und `docs/restart-result.json`.

## Projektstruktur

- `src/main/java/de/alex/ghostnet`: Controller, Services, Repositorys, Formulare, Entitäten und Sicherheitskonfiguration.
- `src/main/resources/templates`: Thymeleaf-Ansichten; gemeinsame Navigation in `teile.html`.
- `src/main/resources/static`: eigenes CSS, JavaScript und SVG.
- `src/test/java/de/alex/ghostnet`: Integrationstests.
- `THIRD_PARTY.md`: Herkunft und Lizenzhinweise der Abhängigkeiten.

## Grenzen des Prototyps

Identität, telefonische Erreichbarkeit und tatsächliche Netzfunde werden nicht verifiziert. Jedes angemeldete Konto kann ein offenes Netz als verschollen melden; diese Aktion kann nicht zurückgenommen werden. Es gibt weder Karte noch Administration, Korrekturverfahren oder vollständige Barrierefreiheits- und Browserprüfung. Der lokale Prototyp ist keine freigegebene Produktivlösung.

## Herkunft und Stand

Repository: <https://github.com/Plexik99/GhostNetFishingFertig>. Die 29 Dateien unter `src` und die `pom.xml` entsprechen dem fachlich geprüften Stand `c1dd658f0132cc3733d40eaabf9a17c6dc4f7abf`; der spätere Commit `bb7e6878733f5b735ed1df293efec6bbd8c16feb` änderte nur die README. Die Überarbeitung vom 2. Oktober 2026 ergänzt Dokumentation, Ignore-Regeln und das Neustart-Prüfskript. Anwendungscode und vorhandene Integrationstests wurden dabei nicht verändert.

Anwendungscode, Tests und Begleittexte wurden mit KI-Unterstützung erstellt beziehungsweise überarbeitet. Die Abgabe und die Verwendung dieser Unterstützung müssen mit den für die Prüfungsleistung geltenden IU-Vorgaben übereinstimmen. Dieses Repository enthält keine nachträglich erfundene Erklärung einer vollständig eigenständigen Erstellung.
