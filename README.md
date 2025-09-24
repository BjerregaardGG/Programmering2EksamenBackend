# 🛠 SirenProject

Dette projekt er en 24 timers eksamensopgave. Projektet består af en **backend** og en **frontend**, der tilsammen udgør en komplet webapplikation til håndtering og visning af brand- og alarmsystemdata.

---

## 🌐 Live

Projektet er ikke offentlig hostet, men frontend kan køres lokalt via `index.html`.

---

## 📁 Projektstruktur

### 🖥 Backend

Backend-delen er udviklet i **Java** med **Spring Boot** og **Maven**. Den håndterer alle API-anmodninger og databaserelateret logik.

**Mapper og filer:**

- `src/main/java/...` - Java-klasser og Spring Boot controllers/services
- `pom.xml` - Maven-konfiguration
- `application.properties` - Database- og serverindstillinger

**Funktionalitet:**

- CRUD-operationer for brandalarmer og sirener
- REST API-endpoints via Spring Boot
- Validering og fejlbehandling
- Mulighed for udvidelse til flere sensorer og alarmsystemer

---

### 💻 Frontend

Frontend-delen er udviklet med **HTML, CSS og JavaScript**. Den kommunikerer med backend via AJAX/fetch.

**Mapper og filer:**

- `css/` - Stylesheets
- `js/` - JavaScript-funktioner
- `images/` - Billeder til UI
- HTML-sider:
  - `index.html` - Hjemmeside og oversigt
  - `fires.html` - Oversigt over aktive brande
  - `closed-fires.html` - Oversigt over lukkede brande
  - `add-fire.html` - Formular til at tilføje brand
  - `add-siren.html` - Formular til at tilføje sirene
  - `edit-siren.html` - Rediger sirene

**Funktionalitet:**

- Liste og visning af aktive/lukkede brande
- Tilføjelse, redigering og sletning af sirener
- Dynamisk opdatering via JavaScript
- Brugervenligt interface med CSS

---

## 🚀 Kom godt i gang

1. Klon både backend og frontend repositories til din lokale maskine.
2. Start backend-serveren (Spring Boot) med Maven eller din IDE.
3. Åbn frontend `index.html` i en browser.
4. Frontend vil kommunikere med backend via de definerede API-endpoints.

---

## 🛠 Teknologier

- **Backend:** Java, Spring Boot, Maven, REST API  
- **Frontend:** HTML, CSS, JavaScript

---

## 📄 Licens

Projektet er åbent for uddannelsesbrug. For anden brug, kontakt ejeren.
