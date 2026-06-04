# Assessment-Lösung: allgeier-maven-restassured

> **ACHTUNG:** Diese Datei ist ausschliesslich für Prüfer bestimmt und darf nicht an Kandidaten weitergegeben werden.

## Eingebaute Fehler

### Bug 1 – Falscher erwarteter HTTP-Statuscode (testAuthentication)

**Datei:** `src/test/java/ch/allgeier/maven/restassured/tests/BookingTest.java`
**Methode:** `testAuthentication()`
**Code:** `assertEquals(201, response.getStatusCode(), "Erwartet HTTP 201 für POST /auth")`

**Fehlerbild:**
```
AssertionError: Erwartet HTTP 201 für POST /auth ==> expected: <201> but was: <200>
```

**Ursache:** Der Endpunkt POST /auth gibt HTTP 200 zurück, nicht 201 (201 ist für Ressourcen-Erstellung reserviert).

**Korrektur:** `assertEquals(201, ...)` → `assertEquals(200, ...)`

**Erwartetes Verhalten nach Korrektur:** `testAuthentication` besteht; `testGetBookingList` schlägt wegen Bug 2 weiter fehl.

---

### Bug 2 – Falscher JSON-Feldname bei Listenauswertung (testGetBookingList)

**Datei:** `src/test/java/ch/allgeier/maven/restassured/tests/BookingTest.java`
**Methode:** `testGetBookingList()`
**Code:** `response.jsonPath().getString("[0].id")`

**Fehlerbild:**
```
AssertionError: Buchungsliste soll 'id'-Feld enthalten, war: null
expected: not <null>
```

**Ursache:** Die API gibt das Feld als `bookingid` zurück, nicht als `id`. Der JSON-Pfad `[0].id` findet kein Element → getString() gibt null zurück.

**Korrektur:** `getString("[0].id")` → `getString("[0].bookingid")`

**Erwartetes Verhalten nach Korrektur:** Alle drei Tests bestehen.
