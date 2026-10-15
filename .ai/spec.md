# Qué tiene que hacer la app (spec.md)

* **Proyecto:** Falta Uno! (Tema: La soledad de las personas mayores)
* **Equipo:** Adrián Iturbe, Pol Cañizares, Enrique Flores, Oriol Torres
* **Curso:** 2.º DAM - Monlau

---

## 1. Problema y a quién va dirigida
* **Problema:** En el barrio hay personas mayores solas en casa que quieren salir a jugar al dominó, a las cartas o a la petanca, pero no saben si habrá gente esperándoles.
* **Usuarios:** Abuelos y personas mayores. Necesitan botones gigantes, letras grandes y que no haya que escribir contraseñas.

---

## 2. Lo que queremos conseguir con el MVP
Una app sencilla para móvil y tablet Android (desarrollada en Android Studio) que sirva de tablón de anuncios del barrio:
* Ver qué partidas hay hoy en los parques o centros cívicos.
* Apuntarse con 1 solo toque en "Me apunto".
* Poner un aviso con "Falta 1" si a tu grupo le falta gente para jugar.

---

## 3. Lo que tiene y lo que NO tiene

### Lo que sí tiene:
* Ver las partidas de hoy con iconos grandes de cada juego.
* Botón grande "Me apunto" para unirse.
* Botón "Falta 1" para avisar de que falta gente.
* Botón de altavoz para escuchar la partida en voz alta (TextToSpeech).
* Poner solo un apodo (ej: "Paco") sin registrarse.

### Lo que dejamos fuera:
* Nada de chats ni mensajes privados.
* Nada de contraseñas ni correos electrónicos.
* Nada de mapas con GPS en tiempo real.
* Nada de dinero ni valoraciones con estrellas.

---

## 4. Requisitos de la app (con reglas EARS de clase)

Los numeramos del 1 al 6 de forma sencilla:

* **Requisito 1:** El sistema siempre deberá mostrar en la pantalla principal las partidas abiertas para hoy con letras grandes e iconos de cada juego.
* **Requisito 2:** **CUANDO** el usuario toque el botón del altavoz, el sistema deberá leer en voz alta el juego, la hora y el lugar usando la voz de Android.
* **Requisito 3:** **CUANDO** el usuario pulse "Me apunto", el sistema deberá apuntarlo a la partida y restar una plaza libre.
* **Requisito 4:** **MIENTRAS** una partida no tenga plazas disponibles, el sistema deberá impedir la inscripción y mostrar el botón en gris diciendo "Partida llena".
* **Requisito 5:** **CUANDO** el usuario pulse "Falta 1", el sistema deberá abrir una pantalla rápida para poner el juego, la hora y el lugar.
* **Requisito 6:** **SI** el usuario no rellena la hora o el lugar, **ENTONCES** el sistema deberá mostrar un aviso diciendo que faltan datos.

---

## 5. Cosas importantes de diseño
* **Botones:** Mínimo de 60x60 dp para que se pulsen bien con el dedo en pantallas táctiles de Android.
* **Texto:** Letra grande (mínimo tamaño 18 sp) y fondo claro para leer sin gafas.
* **Rapidez:** La app debe abrirse y cargar en menos de 2 segundos.
* **Facilidad:** Apuntarse cuesta solo 1 clic.

---

## 6. Ejemplo de prueba (Criterio de aceptación)
* **Dado que** hay una partida de dominó a las 17:00 en la plaza y queda 1 plaza,
* **Cuando** Paco pulsa "Me apunto",
* **Entonces** la partida cambia a "Partida llena", el botón se bloquea y a Paco le sale un mensaje verde: "¡Apuntado Paco, te esperan a las 17:00!".

---

## 7. Casos raros (Casos límite)
* **Si dos personas pulsan a la vez y solo quedaba 1 hueco:** Entra el primero y al segundo le sale un aviso diciendo que ya se ha llenado.
* **Si ya ha pasado la hora de la partida:** Se quita del tablón para no confundir.
* **Si no hay conexión:** La app sigue funcionando con las partidas guardadas en el móvil.

---

## 8. Dudas pendientes
* ¿Deberíamos poner un botón para borrarse si luego no puedes ir?
* ¿Qué pasa si llueve y la partida era en el parque?

---

## 9. Enlace al prototipo de Figma
* [Ver pantallas en Figma](https://www.figma.com/design/WCZAZMoVczL6dm7Y8frJ16/Proyecto?node-id=0-1&t=VA3Nup2OfO0z33No-1)
