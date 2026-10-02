# Prüfnachweis vom 2. Oktober 2026

Die Prüfungen wurden in der Arbeitsumgebung des Assistenten mit Java 17 und Maven 3.9.11 ausgeführt. Sie sind keine Behauptung, dass die einreichende Person sie bereits selbst ausgeführt hat.

## Build und vorhandene Tests

Der Maven-Testlauf meldete **25 Tests, 0 Failures, 0 Errors, 0 Skipped**. Die unveränderte Testzusammenfassung steht in `integration-test-result.txt`. Anschließend wurde die ausführbare JAR mit dem Maven-Ziel `package` erfolgreich erzeugt. Die im ersten Offline-Paketierungsversuch fehlenden Plugin-Abhängigkeiten wurden aus Maven Central bezogen; der abschließende Paketierungsaufruf war erfolgreich. Weil die Tests im selben Quellstand bereits bestanden hatten, wurde diese reine Paketierung mit `-DskipTests` wiederholt.

Anwendungscode, Vorlagen, Ressourcen, POM und die vorhandene Integrationstestklasse wurden bei dieser Überarbeitung nicht geändert. Die Datei `source-sha256.json` hält die 30 geprüften Quelldateien einschließlich POM fest.

| Kennung im Bericht | Methode in GhostNetIntegrationTest | Erwartung, bestätigt |
|---|---|---|
| K1 | `anonymeMeldungSpeichertAuchBeiManipulationKeinenKontakt` | Anonyme Meldung speichert auch bei mitgesendetem Kontakt weder Name noch Telefon. |
| K2 | `serverLehntFalscheKoordinatenUndFlaechenAb` | 90.000001, 180.000001 und Fläche 0 werden abgewiesen; gültige Grenzwerte werden akzeptiert. |
| K3 | `einePersonKannMehrereNetzeUebernehmenAberNurEigeneAbschliessen` | A übernimmt zwei Netze; B darf weder erneut übernehmen noch den fremden Vorgang abschließen. |
| K4 | `jedeRegistriertePersonKannEinOffenesNetzAlsVerschollenMelden` | B meldet das A zugeordnete Netz als verschollen; Meldung, Zeitpunkt und alte Zuordnung werden erhalten. |
| K5 | `verschollenmeldungBrauchtAnmeldungUndCsrfToken` | Ohne Sitzung Anmeldung erforderlich; ohne CSRF HTTP 403; kein Statuswechsel. |
| K6 | `paralleleAenderungenKoennenEineZuordnungNichtUeberschreiben` | Genau eine von zwei konkurrierenden Datenbanktransaktionen speichert die Zuordnung. |

## Zusätzliche Neustartprüfung

Das Skript `scripts/check_restart.py` bestand zunächst mit einem vollständigen Klassenpfad und anschließend mit der tatsächlich gebauten JAR. Der folgende dokumentierte Aufruf wurde erfolgreich ausgeführt:

```sh
python3 scripts/check_restart.py --jar target/ghost-net-fishing-1.0.0.jar --result docs/restart-result.json
```

Die aktuelle Ergebnisdatei enthält den UTC-Zeitpunkt und sechs bestandene Prüfgruppen. Zwei frisch gestartete Java-Prozesse verwenden nacheinander dasselbe neue temporäre Verzeichnis. Zwei künstliche Konten und drei Netze werden angelegt. Nach dem Neustart werden Anmeldung, die Statuswerte Geborgen/Verschollen/Gemeldet, Koordinaten, Bergungszuordnungen und offene Liste erneut geprüft.

## Grenzen

MockMvc ist keine echte Browserprüfung. K6 testet konkurrierende Repository-Transaktionen, nicht einen vollständigen parallelen HTTP-Ablauf. Der Neustarttest prüft echte lokale HTTP-Antworten, aber keine Pixel, Barrierefreiheit oder Browserkompatibilität. Ein erfolgreicher Testlauf ist weder ein vollständiger Sicherheitsnachweis noch eine Lizenz- oder Plagiatsfreigabe.
