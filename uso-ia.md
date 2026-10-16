# Cómo usamos la IA (uso-ia.md)

* **Equipo:** Adrián Iturbe, Pol Cañizares, Enrique Flores, Oriol Torres
* **Curso:** 2.º DAM - Monlau


---

## Caso 1: Quitar los cargos inventados en `plan.md`

* **Tarea y objetivo:** Queríamos poner a los integrantes del equipo en `plan.md`, pero sin ponernos cargos raros. Corresponde al documento **`plan.md`**.
* **Herramienta o agente:** OpenCode con Claude Code. Modelo LLM: Claude 3.5 Sonnet.
* **Modo de trabajo:** Modo edición (para cambiar el archivo directamente).
* **Contexto y prompts:** 
  * La IA se inventó puestos de trabajo ("Product", "UX/UI", "Frontend", "Backend") que nadie le había pedido.
  * **Nuestro prompt para corregirla:**  
    `la parte de "equipo y roles" sobra y no sé dé dónde sacaste esa información`
* **Resultado y revisión humana:**  
  La IA nos puso puestos como si fuéramos una empresa grande. Como somos cuatro compañeros de clase en 2.º DAM y todos vamos a programar en Android Studio juntos, le dijimos que lo borrara. La IA borró esa parte y dejamos solo nuestros nombres.
* **Evidencia:**  
  `![Revisión de roles](evidencias-ia/equipo_roles.png)`

---

## Caso 2: Controlar dónde guarda los archivos la IA

* **Tarea y objetivo:** Pedirle a la IA que nos creara el archivo `.md` en nuestro ordenador. Corresponde al inicio de la fase SDD y a **`constitution.md`**.
* **Herramienta o agente:** Antigravity (asistente con permisos para ejecutar comandos en el ordenador). Modelo LLM: Claude / Gemini (asistente interactivo).
* **Modo de trabajo:** Modo agente.
* **Contexto y prompts:**  
  * Le pedimos que nos creara el archivo con:  
    `creamelo el archivo`
  * Luego nos tocó corregirla con este prompt:  
    `nooo pero crea el .md en la carpeta descargas`
* **Resultado y revisión humana:**  
  La IA nos pidió permiso en pantalla para mirar rutas del ordenador (`Get-Location; Test-Path`). Vimos que se estaba liando e intentaba poner el archivo en el escritorio o en carpetas raras. Tuvimos que frenarla y decirle claramente que lo guardara en la carpeta `Descargas` para tenerlo todo junto.
* **Evidencia:**  
  `![Permiso de rutas en Antigravity](evidencias-ia/comprobando_rutas.png)`

---

## Caso 3: Ordenar las ramas sin perder el trabajo de nadie

* **Tarea y objetivo:** Al empezar la fase de código, Pol había subido su trabajo a una rama que no estaba en el reparto (`feature/pantalla-principal`) y su rama `PolCanizares` estaba vacía. Queríamos dejar solo las ramas que tocaban sin perder nada. Corresponde a **`GUIA_RAMAS.md`**.
* **Herramienta o agente:** Claude Code (terminal en Windows). Modelo LLM: Claude Opus 5.5.
* **Modo de trabajo:** Modo agente (ejecuta `git` y `gh` en el ordenador, pidiendo permiso).
* **Contexto y prompts:**
  * Enrique le puso reglas claras antes de tocar nada:
    `ANTES de tocar nada, haz una copia de seguridad de esa rama [...] NO borres ninguna rama remota, NO hagas force push [...] Proponme el plan y espera.`
  * Cuando la IA propuso mover el commit de Pol, Enrique la frenó:
    `de momento NO toques nada de Pol ni borres ninguna rama remota [...] Pásame los comandos exactos para que se los envíe a Pol y lo haga él.`
* **Resultado y revisión humana:**
  La IA hizo una etiqueta `backup/feature/pantalla-principal` y un archivo `.bundle` de copia, comparó la rama con `main` y avisó de una contradicción: la guía hablaba de ramas `feature/...`, pero el equipo usaba una rama por persona. La decisión la tomamos en grupo: Pol movió su trabajo él mismo con los comandos que le pasamos. Más tarde la IA comprobó que en `main` se había perdido el aviso "No hay partidas", y solo borramos la rama cuando ese aviso estuvo recuperado y Enrique lo confirmó.
* **Evidencia:**
  Pull Requests [#2](https://github.com/urion-sertor/faltauno-/pull/2) y [#5](https://github.com/urion-sertor/faltauno-/pull/5), y `GUIA_RAMAS.md` actualizado con el flujo real.

---

## Caso 4: Programar el motor de datos (T-1) por fases

* **Tarea y objetivo:** Programar la parte de Enrique: `Partida.java` y `GestorPartidas.java` (guardado en `SharedPreferences`, plazas, filtro y casos límite). Corresponde a **`tasks.md` (T-1)** y a los requisitos 1, 3 y 4 y los casos límite de **`spec.md`**.
* **Herramienta o agente:** Claude Code (terminal en Windows). Modelo LLM: Claude Opus 5.5.
* **Modo de trabajo:** Modo agente, en 5 fases con un commit y un push a la rama `EnriqueFlores` en cada una.
* **Contexto y prompts:**
  * Antes de programar, Enrique pidió que cada requisito tuviera su origen en el SDD:
    `dime en qué archivo y sección del SDD [...] aparece cada requisito de T-1`
  * Y corrigió dos decisiones de la IA:
    `Partidas pasadas: prefiero la regla estricta para todas, sin excepciones. Para que la demo nunca salga vacía, crea las 3 partidas de ejemplo con horas relativas a la hora actual`
    `guarda siempre context.getApplicationContext() para evitar fugas de memoria. Coméntalo en el código.`
* **Resultado y revisión humana:**
  La IA enseñó una tabla con dónde salía cada requisito y avisó de que `GUIA_RAMAS.md` atribuía a la constitución una regla ("sin fugas de memoria") que la constitución no tiene. Propuso no ocultar nunca las partidas de ejemplo, y Enrique lo cambió por la regla estricta con horas relativas. Cada fase se compiló antes de subirla, y la lectura de horas se probó con casos como "17:00", "17h", "tarde" o vacío. Enrique revisó cada fase con una explicación corta para poder defenderla en clase.
* **Evidencia:**
  Pull Request [#3](https://github.com/urion-sertor/faltauno-/pull/3) con los 5 commits por fases.

---

## Caso 5: Juntar las ramas y revisar la app completa

* **Tarea y objetivo:** Comprobar que las cuatro partes encajaban, fusionarlas en `main` y hacer una revisión final (accesibilidad, recursos sin usar, cumplimiento del SDD).
* **Herramienta o agente:** Claude Code (terminal en Windows). Modelo LLM: Claude Opus 5.5.
* **Modo de trabajo:** Modo agente, con una rama temporal de prueba (sin subir) y después la rama `fix/integracion`.
* **Contexto y prompts:**
  * `Prueba de integración LOCAL: crea una rama temporal desde main, fusiona en ella [...] intenta compilar y dime qué conflictos y errores salen y de quién es cada uno. NO subas esa rama`
  * `Antes de cambiar nada, dame la lista de problemas encontrados y qué propones para cada uno. Espera mi "sí".`
* **Resultado y revisión humana:**
  La prueba encontró dos errores que impedían compilar: la clase `GestorVoz` estaba pegada dos veces y su constructor no encajaba con `MainActivity`. Se los pasamos a Adrián y Pol en un mensaje por persona. En la revisión final la IA propuso 11 cambios y Enrique los aprobó o los cambió. Por ejemplo, decidió cambiar los `Toast` por avisos grandes (`Snackbar`) y dejar los iconos de cada juego para más adelante. La IA también se equivocó: el primer intento de crear una Pull Request falló por un comando mal encadenado (comprobó que no se había creado nada y lo repitió), y en el emulador pulsó "atrás" de más y salió de la app. Los dos fallos quedaron explicados en la conversación.
* **Evidencia:**
  Pull Requests [#1](https://github.com/urion-sertor/faltauno-/pull/1), [#4](https://github.com/urion-sertor/faltauno-/pull/4) y [#5](https://github.com/urion-sertor/faltauno-/pull/5).

---

## Caso 6: Llevar el diseño de Figma a la app y preparar la presentación

* **Tarea y objetivo:** Que la app se pareciera al prototipo de Figma (colores, logo, casillas de juego, tarjetas y formulario) y preparar la presentación final. Corresponde a la relación con Figma de **`plan.md`** y a las normas de accesibilidad de **`constitution.md`**.
* **Herramienta o agente:** Claude Code (terminal en Windows). Modelo LLM: Claude Opus 5.5.
* **Modo de trabajo:** Modo agente: lee las imágenes exportadas de Figma, compila, instala la app en el emulador y hace capturas para comparar.
* **Contexto y prompts:**
  * `Saca los colores exactos de las imágenes [...] Ojo con el contraste: textos secundarios más oscuros que en el Figma, y texto oscuro (nunca blanco) sobre amarillo y sobre los fondos pastel.`
  * `Instálalo en el emulador del móvil con los datos limpios y enséñame capturas al lado de las del Figma.`
* **Resultado y revisión humana:**
  La IA midió los colores en los píxeles de las imágenes y calculó el contraste de cada combinación. Oscureció el gris de los textos secundarios (de 5,7:1 a 8:1) y el borde de los campos. Propuso poner las casillas de juego en una fila en vez de la cuadrícula de Figma, porque si no la lista no cabía en el móvil, y Enrique lo aceptó al ver las capturas. Al compararlas, la IA vio que las pistas de los campos parecían texto ya escrito y las aclaró. Enrique revisó las capturas lado a lado antes de dar el "sí" a la Pull Request.
* **Evidencia:**
  `![Figma y app: pantalla principal](evidencias-ia/figma_vs_app_inicio.png)`
  `![Figma y app: formulario](evidencias-ia/figma_vs_app_formulario.png)`
  Pull Request [#7](https://github.com/urion-sertor/faltauno-/pull/7).

---

## Reflexión del equipo sobre la IA (6 líneas)

Usar herramientas como OpenCode, Antigravity y Claude Code nos ha venido muy bien para no empezar con los documentos en blanco y avanzar más rápido.
El problema es que la IA muchas veces se flipa y se inventa cosas: pone palabras difíciles, cargos que nadie tiene o intenta guardar cosas donde no debe.
Por eso no nos hemos fiado de todo lo que decía y hemos revisado cada archivo a mano.
Al final fuimos nosotros los que decidimos hacer una app sencilla en Android Studio con almacenamiento local en el móvil, sin bases de datos complicadas en internet.
La IA te ayuda a arrancar, pero las decisiones del proyecto las tomamos siempre nosotros.

### Nota de Enrique sobre la fase de código

Con Claude Code la IA ya no solo escribe texto: ejecuta comandos de Git, compila y prueba la app en el emulador. Por eso le pusimos límites desde el principio: nada de force push, no tocar ramas de compañeros ni fusionar en `main` sin un "sí", y copia de seguridad antes de borrar. Las decisiones (qué regla seguir con las partidas pasadas, qué cambiar del Figma, cuándo fusionar) las tomamos nosotros, y cada cambio se compiló y se probó antes de subirlo.
