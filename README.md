# allgeier-maven-restassured

API-Testprojekt mit REST Assured gegen die öffentliche Restful-Booker-API.

## Zweck im Assessment

Dieses Repository dient **P03 - schnittstellennahe Automatisierung** und zeigt, wie Kandidaten API-Tests analysieren, debuggen und gezielt erweitern.

## Technischer Stack

| Komponente | Version |
|---|---|
| REST Assured | 6.0.0 |
| JUnit Jupiter | 6.1.0 |
| Java | 25 |
| Maven | 3.9+ |
| Reporting | Maven Surefire |

## Zielsystem

**URL:** https://restful-booker.herokuapp.com

Restful Booker ist eine öffentlich verfügbare, ausdrücklich zum Testen freigegebene REST-API. Die Instanz setzt sich periodisch zurück.

## Voraussetzungen

- JDK 25+
- Maven 3.9+
- Internetzugang

## Ausführen

```bash
mvn clean verify
```

Die Ziel-URL und Zugangsdaten liegen in `src/test/resources/config.properties`.

## Reports

Surefire-Ergebnisse liegen nach dem Lauf unter:

```text
target/surefire-reports/
```

## Projektstruktur

```text
src/test/java/ch/allgeier/maven/restassured/tests/   REST-Assured-Tests
src/test/resources/                                  Testkonfiguration
pom.xml                                              Maven-Konfiguration
```

## Hinweise für das Assessment

Das Projekt enthält bewusst eingebaute Fehler. Kandidaten sollen das Fehlerbild analysieren, die Ursache finden und eine fachlich passende Korrektur umsetzen. Die Auflösung steht nicht in dieser README.
