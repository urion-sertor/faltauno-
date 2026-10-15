# Lista de Tareas: Falta Uno! (tasks.md)

* **Equipo:** Adrián Iturbe, Pol Cañizares, Enrique Flores, Oriol Torres
* **Curso:** 2.º DAM - Monlau

Aquí tenemos las tareas ordenadas para programar y probar el MVP en Android Studio con Java paso a paso.

---

### Tareas de Programación (Implementación en Android Studio con Java)

* **ID:** T-1
  * **Requisito al que responde:** Requisito 1 de `spec.md`
  * **Qué hay que hacer:** Crear la clase `Partida.java` y guardar 3 partidas de ejemplo en el almacenamiento local del móvil (`SharedPreferences` en Java).
  * **Dependencias:** Ninguna
  * **Criterio de finalización («hecho cuando...»):** Hecho cuando tengamos los datos de las partidas de prueba creados y se puedan leer sin errores en la app.

* **ID:** T-2
  * **Requisito al que responde:** Requisito 1 y diseño de `spec.md`
  * **Qué hay que hacer:** Diseñar en Android Studio el layout XML de la pantalla principal con los botones grandes de los juegos (dominó, cartas, petanca...).
  * **Dependencias:** Ninguna
  * **Criterio de finalización («hecho cuando...»):** Hecho cuando se vean los botones de los juegos en la pantalla ocupando buen espacio (mínimo 60x60 dp) y con texto grande.

* **ID:** T-3
  * **Requisito al que responde:** Requisito 1 de `spec.md`
  * **Qué hay que hacer:** Programar en Java la lista de partidas en pantalla para que muestre las partidas guardadas con su hora y lugar.
  * **Dependencias:** T-1, T-2
  * **Criterio de finalización («hecho cuando...»):** Hecho cuando al pulsar en "Dominó" se vean las partidas de hoy debajo.

* **ID:** T-4
  * **Requisito al que responde:** Requisito 2 de `spec.md`
  * **Qué hay que hacer:** Poner el botón del altavoz y conectarlo con la clase `TextToSpeech` en Java.
  * **Dependencias:** T-3
  * **Criterio de finalización («hecho cuando...»):** Hecho cuando al tocar el altavoz el móvil diga en voz alta el juego, la hora y el sitio.

* **ID:** T-5
  * **Requisito al que responde:** Requisitos 3 y 4 de `spec.md`
  * **Qué hay que hacer:** Programar en Java el botón "Me apunto" para que reste una plaza libre y se ponga en gris diciendo "Partida llena" si llega a 0.
  * **Dependencias:** T-1, T-3
  * **Criterio de finalización («hecho cuando...»):** Hecho cuando al pulsar el botón baje la plaza y se bloquee si no queda sitio.

* **ID:** T-6
  * **Requisito al que responde:** Requisito 5 de `spec.md`
  * **Qué hay que hacer:** Crear en Java y XML la pantalla sencilla para añadir una partida nueva al pulsar "Falta 1".
  * **Dependencias:** T-1, T-2
  * **Criterio de finalización («hecho cuando...»):** Hecho cuando se rellenen los datos, se pulse "Publicar" y la partida se añada a la lista.

* **ID:** T-7
  * **Requisito al que responde:** Requisito 6 de `spec.md`
  * **Qué hay que hacer:** Poner un aviso sencillo con `Toast` en Java si el usuario intenta publicar una partida sin poner la hora o el lugar.
  * **Dependencias:** T-6
  * **Criterio de finalización («hecho cuando...»):** Hecho cuando al dejar la hora vacía y pulsar "Publicar" salga un aviso claro en la pantalla.

---

### Tareas de Pruebas (Validación)

* **ID:** T-8
  * **Requisito al que responde:** Requisitos de diseño de `spec.md`
  * **Qué hay que hacer:** Ejecutar la app desde Android Studio en un móvil o tablet Android real por USB y comprobar que va fluida.
  * **Dependencias:** T-4, T-5, T-7
  * **Criterio de finalización («hecho cuando...»):** Hecho cuando la app arranque en el móvil real sin dar errores (cero cierres forzosos) y cargue en menos de 2 segundos.

* **ID:** T-9
  * **Requisito al que responde:** Estrategia de pruebas de `plan.md`
  * **Qué hay que hacer:** Dejarle la tablet Android a un familiar mayor para ver si consigue apuntarse a una partida de dominó sin ayuda.
  * **Dependencias:** T-8
  * **Criterio de finalización («hecho cuando...»):** Hecho cuando la persona mayor logre apuntarse sola a la primera.