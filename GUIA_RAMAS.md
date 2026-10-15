# 🌿 Guía de Coordinación y Reparto de Ramas (Falta Uno!)

* **Equipo de 2.º DAM (Monlau):** Adrián Iturbe, Pol Cañizares, Enrique Flores y Oriol Torres.
* **Objetivo de este documento:** Ser la guía definitiva para que cada integrante sepa **con total exactitud qué archivos debe programar**, qué carpetas pertenecen a su tarea y qué archivos tiene **estrictamente prohibido tocar** para evitar conflictos de fusión (*merge conflicts*) en Git.

---

## 🗺️ Mapa Completo de Archivos y Responsables

A continuación se muestra el árbol de archivos del proyecto con la etiqueta del responsable asignado:

```text
faltauno-/
├── .gitignore                                 [🔒 COMÚN - NO TOCAR]
├── build.gradle                               [🔒 COMÚN - NO TOCAR]
├── settings.gradle                            [🔒 COMÚN - NO TOCAR]
├── gradle.properties                          [🔒 COMÚN - NO TOCAR]
├── GUIA_RAMAS.md                              [📄 Documento de coordinación]
│
└── app/
    ├── build.gradle                           [🔒 COMÚN - NO TOCAR]
    ├── proguard-rules.pro                     [🔒 COMÚN - NO TOCAR]
    │
    └── src/main/
        ├── AndroidManifest.xml                [🔒 COMÚN - NO TOCAR]
        │
        ├── res/
        │   ├── drawable/
        │   │   └── ic_altavoz.xml             [🔒 COMÚN - Icono de voz]
        │   │
        │   ├── values/                        [🔒 ZONA COMÚN BLOQUEADA]
        │   │   ├── colors.xml                 [🔒 NO TOCAR - Colores accesibles]
        │   │   ├── dimens.xml                 [🔒 NO TOCAR - Botones 60dp, textos 18sp]
        │   │   ├── strings.xml                [🔒 NO TOCAR - Textos en español]
        │   │   └── themes.xml                 [🔒 NO TOCAR - Tema de alto contraste]
        │   │
        │   └── layout/
        │       ├── activity_main.xml          [👤 POL CAÑIZARES]
        │       ├── item_partida.xml           [👤 ADRIÁN ITURBE]
        │       └── activity_nueva_partida.xml [👤 ORIOL TORRES]
        │
        └── java/com/faltauno/app/
            ├── model/
            │   └── Partida.java               [👤 ENRIQUE FLORES (Core Backend)]
            │
            ├── data/
            │   └── GestorPartidas.java        [👤 ENRIQUE FLORES (Core Backend)]
            │
            ├── voz/
            │   └── GestorVoz.java             [👤 ADRIÁN ITURBE]
            │
            └── ui/
                ├── MainActivity.java          [👤 POL CAÑIZARES]
                ├── PartidasAdapter.java       [👤 ADRIÁN ITURBE]
                └── NuevaPartidaActivity.java  [👤 ORIOL TORRES]
```

---

## 🔒 Zona Bloqueada: Archivos Comunes (¡NO TOCAR!)

> ⚠️ **REGLA DE CONVIVENCIA EN GIT:**  
> Los siguientes archivos **YA ESTÁN COMPLETOS** con todas las definiciones necesarias para el MVP. Si dos personas editan estos archivos a la vez, se generarán conflictos de fusión (*merge conflicts*). **Nadie debe modificarlos individualmente**.
>
> * `app/src/main/res/values/strings.xml` (todos los textos, avisos Toast y etiquetas ya están definidos aquí).
> * `app/src/main/res/values/colors.xml` (paleta accesible con verde confirmación, gris deshabilitado y fondo claro).
> * `app/src/main/res/values/dimens.xml` (dimensiones obligatorias de accesibilidad para personas mayores).
> * `app/src/main/res/values/themes.xml` (estilo visual general).
> * `app/src/main/AndroidManifest.xml` (ambas actividades ya están pre-declaradas).
> * `build.gradle`, `app/build.gradle` y `settings.gradle`.
>
> *Si necesitas un nuevo texto o color, coméntalo primero con el equipo en clase para añadirlo de común acuerdo.*

---

## 📋 Fichas de Trabajo Individuales (Quién hace qué)

---

### 👤 1. Enrique Flores (Core Lógica / Backend con Claude Code)
* **Herramienta de apoyo:** Claude Code.
* **Rama de Git:** `feature/motor-persistencia`
* **Comando para crearla:** `git checkout -b feature/motor-persistencia`
* **Tareas asociadas (`tasks.md`):** **T-1** (Persistencia local real, lógica del motor de partidas y casos límite de `spec.md`).
* **Archivos asignados:**
  1. `app/src/main/java/com/faltauno/app/model/Partida.java`
  2. `app/src/main/java/com/faltauno/app/data/GestorPartidas.java`

#### 🎯 ¿Por qué es la parte más importante y técnica?
Es el **corazón y la columna vertebral de la aplicación**. Si el gestor de datos falla, ninguna pantalla muestra partidas ni se pueden guardar avisos. Con la ayuda de Claude Code, Enrique programará la persistencia robusta para que la app no dependa de servidores ni bases de datos complejas.

#### 🛠️ ¿Qué debe hacer Enrique en sus archivos?
* **En `Partida.java`:**
  - Asegurar el modelo con sus 6 atributos: `id` (int), `juego` (String), `lugar` (String), `hora` (String), `plazasLibres` (int) y `creador` (String).
  - Incluir constructores, getters/setters y el método de ayuda `estaLlena()`.
  - *(Opcional con Claude Code):* Métodos para convertir una partida a `JSONObject` y leer desde `JSONObject` si se requiere serialización JSON avanzada.
* **En `GestorPartidas.java`:**
  - **Carga inicial:** Mantener las 3 partidas por defecto (Dominó 17:00 en Plaza Mayor con 1 plaza, Petanca 18:00 en Parque Central con 2 plazas, Cartas 16:30 en Centro Cívico con 3 plazas).
  - **Persistencia real:** Implementar el guardado y recuperación en `SharedPreferences` (usando JSON nativo de Android: `JSONArray` / `JSONObject`) para que las partidas nuevas y las plazas no se pierdan al cerrar la app.
  - **Lógica de plazas:** Asegurar que `apuntarseAPartida(int idPartida)` reste 1 plaza si hay hueco, pero devuelva `false` y no permita bajar de 0 si ya está llena.
  - **Filtro:** Mantener `obtenerPartidasPorJuego(String juego)` devolviendo solo las partidas del juego pedido o todas si es "Todos".
  - **Regla de la Constitución:** Manejar los datos en memoria de manera limpia sin provocar fugas de memoria (*memory leaks*).
* 🚫 **Archivos que NO debe tocar:** Ningún archivo de la carpeta `ui/`, `voz/` ni ningún layout XML de `res/layout/`.

---

### 👤 2. Adrián Iturbe (Tarjeta Interactiva y Accesibilidad por Voz)
* **Rama de Git:** `feature/tarjeta-y-voz`
* **Comando para crearla:** `git checkout -b feature/tarjeta-y-voz`
* **Tareas asociadas (`tasks.md`):** **T-4 y T-5** (Tarjeta accesible, botón "Me apunto" con control de plazas y lectura TextToSpeech).
* **Archivos asignados:**
  1. `app/src/main/res/layout/item_partida.xml`
  2. `app/src/main/java/com/faltauno/app/ui/PartidasAdapter.java`
  3. `app/src/main/java/com/faltauno/app/voz/GestorVoz.java`

#### 🎯 ¿Qué debe hacer Adrián en sus archivos?
* **En `item_partida.xml`:**
  - Asegurar el diseño accesible de la tarjeta (`CardView`) con esquinas redondeadas, elevación y textos de mínimo 18sp.
  - Mantener el botón de altavoz gigante (mínimo 60x60dp) y el botón gigante "¡Me apunto!" (mínimo 60dp de alto).
* **En `PartidasAdapter.java`:**
  - Conectar los datos de la partida con los campos de la tarjeta.
  - Lógica visual:
    - Si `plazasLibres > 0`: botón con texto *"¡Me apunto!"*, color verde y habilitado.
    - Si `plazasLibres == 0`: botón con texto *"Partida llena"*, color gris y deshabilitado (`setEnabled(false)`).
  - Transmitir los clics de "Me apunto" y altavoz a la actividad principal.
* **En `GestorVoz.java`:**
  - Asegurar la inicialización del motor `TextToSpeech` nativo en español (`new Locale("es", "ES")`).
  - En `hablar(String texto)`: detener locuciones previas (`QUEUE_FLUSH`) y reproducir la locución accesible.
  - En `liberar()`: apagar el motor al salir de la pantalla para liberar memoria.
* 🚫 **Archivos que NO debe tocar:** `MainActivity.java` (de Pol), `NuevaPartidaActivity.java` (de Oriol) ni el motor de datos de Enrique.

---

### 👤 3. Pol Cañizares (Pantalla Principal y Filtros)
* **Rama de Git:** `feature/pantalla-principal`
* **Comando para crearla:** `git checkout -b feature/pantalla-principal`
* **Tareas asociadas (`tasks.md`):** **T-2 y T-3** (Diseño de pantalla principal y filtros de juegos).
* **Archivos asignados:**
  1. `app/src/main/res/layout/activity_main.xml`
  2. `app/src/main/java/com/faltauno/app/ui/MainActivity.java`

#### 🎯 ¿Qué debe hacer Pol en sus archivos?
* **En `activity_main.xml`:**
  - Cabecera grande accesible con textos de 24sp y alto contraste.
  - Fila de botones de filtro gigantes (mínimo 60dp de alto, letra 18sp) para "Todos", "Dominó", "Cartas", "Petanca" y "Parchís".
  - `RecyclerView` para listar las tarjetas de partidas.
  - Botón gigante inferior "¡Falta 1! (Crear aviso)" con altura mínima de 60dp y color destacado.
* **En `MainActivity.java`:**
  - Enlazar todas las vistas con `findViewById`.
  - Conectar los filtros con `gestorPartidas.obtenerPartidasPorJuego(juego)` para actualizar la lista en tiempo real.
  - Conectar el botón "Falta 1" para abrir `NuevaPartidaActivity` mediante un `Intent`.
  - Asegurar el refresco de la lista en `onResume()`.
* 🚫 **Archivos que NO debe tocar:** `PartidasAdapter.java` (de Adrián), `NuevaPartidaActivity.java` (de Oriol) ni el backend de Enrique.

---

### 👤 4. Oriol Torres (Pantalla "Falta 1" y Validación)
* **Rama de Git:** `feature/nueva-partida`
* **Comando para crearla:** `git checkout -b feature/nueva-partida`
* **Tareas asociadas (`tasks.md`):** **T-6 y T-7** (Formulario "Falta 1", validación con Toast y publicación).
* **Archivos asignados:**
  1. `app/src/main/res/layout/activity_nueva_partida.xml`
  2. `app/src/main/java/com/faltauno/app/ui/NuevaPartidaActivity.java`

#### 🎯 ¿Qué debe hacer Oriol en sus archivos?
* **En `activity_nueva_partida.xml`:**
  - Formulario con `ScrollView` accesible para pantallas grandes y pequeñas.
  - Entradas de texto gigantes (mínimo 60dp de alto, letra 18sp) para elegir juego (`Spinner`), hora, lugar y apodo (sin contraseñas ni correos).
  - Botón gigante verde "Publicar" (mínimo 60dp de alto y letra 22sp) y botón "Volver atrás".
* **En `NuevaPartidaActivity.java`:**
  - Rellenar el desplegable (`Spinner`) con los juegos del barrio.
  - Validación obligatoria con `Toast` (Requisito 6 de `spec.md`): si falta hora o lugar, mostrar aviso *"Por favor, escribe la hora y el lugar"*.
  - Si está completo, crear la partida, guardarla con `gestorPartidas.guardarPartida(...)`, mostrar `Toast` de éxito y cerrar la pantalla con `finish()`.
* 🚫 **Archivos que NO debe tocar:** `MainActivity.java` (de Pol), `PartidasAdapter.java` (de Adrián) ni el backend de Enrique.

---

## 🛠️ Flujo de Trabajo con Git Paso a Paso

### Paso 1: Partir siempre de `main` actualizado
Antes de crear tu rama o ponerte a programar, asegúrate de estar al día:

```bash
git checkout main
git pull origin main
```

### Paso 2: Crear tu rama personal de trabajo
Ejecuta únicamente el comando que te corresponde:

```bash
# Enrique (Core Backend):
git checkout -b feature/motor-persistencia

# Adrián (Tarjeta y Voz):
git checkout -b feature/tarjeta-y-voz

# Pol (Pantalla Principal):
git checkout -b feature/pantalla-principal

# Oriol (Nueva Partida):
git checkout -b feature/nueva-partida
```

### Paso 3: Programar y hacer commits regulares
Haz commits explicando lo que vas avanzando en tus archivos asignados:

```bash
git status
git add .
git commit -m "feat: implementada la logica asignada"
```

### Paso 4: Subir tu rama a GitHub
La primera vez que subas tu rama ejecuta:

```bash
git push -u origin <nombre-de-tu-rama>
```

### Paso 5: Integración final (*Pull Request* y *Merge*)
1. Entra en GitHub y abre un **Pull Request (PR)** desde tu rama hacia `main`.
2. Avisa al grupo para que otro compañero revise que tu código compila y cumple las normas de accesibilidad.
3. Haz el **Merge** en GitHub.
4. Los demás compañeros se descargan la novedad en su ordenador:
   ```bash
   git checkout main
   git pull origin main
   git checkout <tu-rama>
   git merge main
   ```
   *¡Como cada uno ha tocado archivos completamente diferentes, la fusión se hará en 1 segundo y con 0 conflictos!*
