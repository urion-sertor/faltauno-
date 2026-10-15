# Plan Técnico: Falta Uno! (plan.md)

* **Equipo:** Adrián Iturbe, Pol Cañizares, Enrique Flores, Oriol Torres
* **Curso:** 2.º DAM - Monlau

Explicamos cómo vamos a hacer la app en Android Studio usando Java para que nos dé tiempo a terminarla.

---

## 1. Tecnologías que vamos a usar y por qué
* **Android Studio con Java:**
  * **Por qué:** En 2.º DAM sabemos programar en Java. Usaremos Java con pantallas en XML, que es lo que entendemos bien y no nos da problemas.
  * **Voz:** Usamos la clase nativa de Android `TextToSpeech` en Java, que ya viene en el sistema para leer texto en voz alta sin instalar librerías raras.

---

## 2. Partes principales de la app (Componentes)
* **Pantalla Principal (MainActivity.java):** Muestra los botones de los juegos (dominó, cartas, petanca, etc.) y la lista de partidas de hoy.
* **Tarjeta de Partida (diseño en XML):** El recuadro de cada partida con la hora, el parque, el botón del altavoz y el botón grande de "Me apunto".
* **Ventana "Falta 1" (NuevaPartidaActivity.java o Diálogo):** Pantalla sencilla con campos de texto para poner una partida nueva (elegir juego, hora y sitio).
* **Módulo de voz (TextToSpeech en Java):** El código básico para hacer hablar al móvil cuando tocan el altavoz.

---

## 3. Datos de las partidas y dónde se guardan
Cada partida guarda estos datos simples en una clase `Partida.java`:
* `id`: Número de partida (1, 2, 3...)
* `juego`: Nombre del juego (ej: "Dominó")
* `lugar`: Sitio público (ej: "Plaza Mayor")
* `hora`: Hora (ej: "17:00")
* `plazasLibres`: Plazas que quedan libres
* `creador`: Apodo de quien avisa (ej: "Manolo")

**Dónde se guardan:**
* En el almacenamiento local del móvil Android (`SharedPreferences` o una lista guardada en local).
* **Por qué:** No nos da tiempo a montar un servidor con base de datos en internet. Guardándolo dentro del propio móvil en Java la app va súper rápida, no se cuelga si no hay wifi en clase y es 100% viable para el MVP.

---

## 4. Relación con las pantallas de Figma
Diseño acordado en Figma: [Ver pantallas en Figma](https://www.figma.com/design/WCZAZMoVczL6dm7Y8frJ16/Proyecto?node-id=0-1&t=VA3Nup2OfO0z33No-1)

* **Pantalla 1 de Figma:** Es nuestra **MainActivity** con los botones de juegos y el listado (cumple el Requisito 1 de `spec.md`).
* **Pantalla 2 de Figma:** Es el diseño de la **Tarjeta de Partida** con el botón "Me apunto" y el altavoz (cumple los Requisitos 2, 3 y 4 de `spec.md`).
* **Pantalla 3 de Figma:** Es la pantalla **"Falta 1"** para crear partida (cumple los Requisitos 5 y 6 de `spec.md`).

---

## 5. Cómo vamos a probar la app (Estrategia de pruebas)
1. **Prueba en emulador y móvil real:** Ejecutar la app con el botón Play (Run) de Android Studio y probarla en un móvil o tablet Android conectado por cable USB.
2. **Prueba de botones:** Comprobar que al pulsar "Me apunto" baja una plaza y se bloquea cuando no quedan huecos.
3. **Prueba de voz:** Tocar el altavoz y comprobar que el motor `TextToSpeech` lee bien en español.
4. **Prueba con un abuelo:** Dejarle la tablet Android a un familiar mayor para ver si se apunta solo a una partida sin ayuda.

---

## 6. Viabilidad
El equipo ha revisado este plan y vemos que es totalmente viable hacerlo en Android Studio con Java básico dentro del plazo de clase, ya que nos centramos en lo imprescindible.
