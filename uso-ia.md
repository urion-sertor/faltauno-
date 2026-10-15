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

## Reflexión del equipo sobre la IA (6 líneas)

Usar herramientas como OpenCode, Antigravity y Claude Code nos ha venido muy bien para no empezar con los documentos en blanco y avanzar más rápido.
El problema es que la IA muchas veces se flipa y se inventa cosas: pone palabras difíciles, cargos que nadie tiene o intenta guardar cosas donde no debe.
Por eso no nos hemos fiado de todo lo que decía y hemos revisado cada archivo a mano.
Al final fuimos nosotros los que decidimos hacer una app sencilla en Android Studio con almacenamiento local en el móvil, sin bases de datos complicadas en internet.
La IA te ayuda a arrancar, pero las decisiones del proyecto las tomamos siempre nosotros.
