# Instrucciones para la IA (AGENTS.md)

Hola IA. Vas a ayudarnos con la app **Falta Uno!**. Somos un grupo de 4 estudiantes novatos de 2.º DAM (Monlau): **Adrián Iturbe, Pol Cañizares, Enrique Flores y Oriol Torres**. Por favor, no nos compliques la vida.

---

## 1. De qué va la app
Es una app nativa para móvil y tablet Android pensada para que los abuelos del barrio no estén solos y puedan quedar a jugar al dominó, cartas, petanca o parchís en parques y centros cívicos.
* Botones gigantes (mínimo 60x60 dp).
* Letra grande (mínimo 18 sp) y fondo claro con alto contraste.
* Cero registros, correos ni contraseñas.
* Apuntarse a una partida cuesta solo 1 clic.

---

## 2. Los 5 documentos que tienes que mirar
Antes de tocar nada, revisa estos 5 archivos para que todo cuadre:

1. **`constitution.md`:** Nuestras normas básicas (accesibilidad obligatoria para mayores, proteger datos y revisar lo que tú hagas).
2. **`AGENTS.md`:** Esta misma hoja con tus instrucciones.
3. **`spec.md`:** Los 6 requisitos de la app con reglas EARS, casos límite y el enlace a nuestro Figma.
4. **`plan.md`:** La propuesta técnica (Android Studio con Java y relación con Figma).
5. **`tasks.md`:** La lista de tareas (T-1 a T-9) para saber qué toca hacer y cuándo está terminada («hecho cuando...»).

---

## 3. Reglas para escribir el código 
* **Tecnología:** Desarrollamos en **Android Studio únicamente con Java y XML**.
* **Persistencia:** Guardamos los datos en local (`SharedPreferences` o lista simple) y usamos `TextToSpeech` de Android en Java para la lectura por voz.
* **Nombres fáciles en español:** Nombres claros para variables y métodos (ejemplo: `listaPartidas`, `apuntarseAPartida`, `leerEnVozAlta`, `guardarPartida`).
* **Código de novatos:** Usa código de Java tradicional, fácil de entender, con `findViewById` y sin librerías complicadas ni cosas raras de arquitectura.
* **Comentarios:** Una frase corta arriba de cada método explicando qué hace.
* **Figma:** Siempre siguiendo el diseño de nuestro prototipo de Figma indicado en `spec.md`.

---

## 4. Comprobaciones antes de dar por bueno algo
* Que esté escrito en **Java**
* Que cumpla el requisito exacto de `spec.md` (del 1 al 6) y la tarea de `tasks.md`.
* Que los botones sigan siendo gigantes para pantallas táctiles de Android.
* Que el código no provoque cierres forzosos (crashes).
* Que no rompas nada de lo que ya funcionaba.

---

## 5. Comandos del proyecto (Android Studio)
* **Arrancar y probar la app:** Pulsar el botón verde de Play (Run 'app') en Android Studio con un emulador o un móvil conectado por cable USB.
* **Comando para compilar desde terminal:** `./gradlew assembleDebug`
* **Comandos de tests automáticos:** `[PENDIENTE: ./gradlew test]`
* **Generar APK para instalar en el móvil:** `[PENDIENTE: por definir en la fase final]`

---

## 6. Si tienes dudas
* **Pregunta:** Si no sabes cómo hacer una pantalla o botón en Android Studio con Java, no te lo inventes. Pregúntanos al equipo.
* **Elige lo fácil:** Si algo se puede hacer con Java de toda la vida, no uses patrones difíciles de profesionales.
* **Explícanoslo paso a paso:** Dinos qué archivo `.java` o `.xml` abrir y dónde pegar el código, sin palabras difíciles.
