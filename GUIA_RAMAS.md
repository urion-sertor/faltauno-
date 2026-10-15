# 🌿 Guía de Coordinación de Ramas de Git (Falta Uno!)

* **Equipo de 2.º DAM (Monlau):** Adrián Iturbe, Pol Cañizares, Enrique Flores y Oriol Torres.
* **Objetivo:** Trabajar en paralelo sin pisarnos el código ni sufrir conflictos de fusión (*merge conflicts*).

---

## 🎯 Regla de Oro: Arquitectura Modular y Asignación de Archivos

Cada compañero tiene asignada su propia **rama de Git**, sus **tareas de `tasks.md`** y sus **archivos exclusivos**. Nadie debe modificar los archivos de otro compañero sin avisar previamente.

| Integrante | Rama de Git | Archivos asignados | Tareas (`tasks.md`) |
| :--- | :--- | :--- | :--- |
| **Adrián Iturbe** | `feature/datos-partidas` | • `model/Partida.java`<br>• `data/GestorPartidas.java` | **T-1:** Persistencia local (`SharedPreferences`), lógica de datos y gestión de partidas. |
| **Pol Cañizares** | `feature/pantalla-principal` | • `ui/MainActivity.java`<br>• `res/layout/activity_main.xml` | **T-2 y T-3:** Diseño de pantalla principal, filtros gigantes por juego y conexión del listado. |
| **Enrique Flores** | `feature/tarjeta-y-voz` | • `ui/PartidasAdapter.java`<br>• `res/layout/item_partida.xml`<br>• `voz/GestorVoz.java` | **T-4 y T-5:** Tarjeta de partida, botón "Me apunto" con estado de plazas y lectura por voz nativa (`TextToSpeech`). |
| **Oriol Torres** | `feature/nueva-partida` | • `ui/NuevaPartidaActivity.java`<br>• `res/layout/activity_nueva_partida.xml` | **T-6 y T-7:** Formulario "Falta 1", validación con avisos `Toast` y publicación de partidas. |

> ⚠️ **Archivos compartidos de solo lectura:**  
> Los archivos `res/values/strings.xml`, `colors.xml`, `dimens.xml`, `themes.xml` y `AndroidManifest.xml` ya contienen **todos** los valores predefinidos necesarios para el MVP. **No los edites** salvo que todo el equipo esté de acuerdo.

---

## 🚀 Pasos para Empezar a Trabajar

### 1. Actualizar tu rama `main` local
Antes de crear tu rama, asegúrate de tener la última versión de la arquitectura base:

```bash
git checkout main
git pull origin main
```

---

### 2. Crear tu rama de trabajo
Cada miembro ejecuta el comando que le corresponde según su nombre:

* **Adrián Iturbe:**
  ```bash
  git checkout -b feature/datos-partidas
  ```

* **Pol Cañizares:**
  ```bash
  git checkout -b feature/pantalla-principal
  ```

* **Enrique Flores:**
  ```bash
  git checkout -b feature/tarjeta-y-voz
  ```

* **Oriol Torres:**
  ```bash
  git checkout -b feature/nueva-partida
  ```

---

### 3. Guardar cambios y subirlos a GitHub
Mientras programas tus archivos asignados, realiza commits periódicos con mensajes claros en español:

```bash
# 1. Comprueba qué archivos has tocado
git status

# 2. Añade tus cambios
git add .

# 3. Guarda el commit explicando qué has hecho
git commit -m "Implementada la funcionalidad de..."

# 4. Sube la rama a GitHub por primera vez
git push -u origin <nombre-de-tu-rama>
```
*(En las siguientes veces bastará con hacer `git push`).*

---

## 🔀 Integración y Fusión (*Merge*) al Terminar

Cuando termines y pruebes tu tarea en Android Studio:

1. **Abre un Pull Request (PR)** en GitHub desde tu rama hacia `main`.
2. **Pide a un compañero del equipo que revise tu código** (según la norma de la Constitución de revisar el trabajo entre todos).
3. Una vez aprobado, fusiona (*merge*) en GitHub.
4. Todos los demás compañeros deben actualizar su `main` local y traer los cambios a su rama activa:
   ```bash
   git checkout main
   git pull origin main
   git checkout <tu-rama>
   git merge main
   ```
   *¡Gracias a la separación de archivos, el merge se resolverá automáticamente al 100% sin ningún conflicto!*
