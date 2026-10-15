package com.faltauno.app.voz;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.util.Locale;

/**
 * Gestor del motor nativo TextToSpeech de Android configurado en español.
 */
public class GestorVoz implements TextToSpeech.OnInitListener {

    private static final String ETIQUETA = "GestorVoz";
    private TextToSpeech motorVoz;
    private boolean motorListo = false;

    // Constructor vacío por defecto.
    public GestorVoz() {
    }

    // Inicializa el servicio de voz de Android con el contexto de la aplicación.
    public void inicializar(Context contexto) {
        if (motorVoz == null) {
            motorVoz = new TextToSpeech(contexto.getApplicationContext(), this);
        }
    }

    // Callback de Android que configura el idioma español cuando el motor termina de cargar.
    @Override
    public void onInit(int estado) {
        if (estado == TextToSpeech.SUCCESS) {
            int resultado = motorVoz.setLanguage(new Locale("es", "ES"));
            if (resultado == TextToSpeech.LANG_MISSING_DATA || resultado == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e(ETIQUETA, "El idioma español no está disponible en este dispositivo.");
                motorListo = false;
            } else {
                motorListo = true;
                Log.i(ETIQUETA, "Motor de voz configurado en español con éxito.");
            }
        } else {
            Log.e(ETIQUETA, "Fallo al inicializar el motor TextToSpeech.");
            motorListo = false;
        }
    }

    // Detiene cualquier locución anterior y pronuncia el texto indicado en voz alta.
    public void hablar(String texto) {
        if (motorListo && motorVoz != null) {
            motorVoz.speak(texto, TextToSpeech.QUEUE_FLUSH, null, null);
        } else {
            Log.w(ETIQUETA, "El motor de voz aún no está listo para hablar.");
        }
    }

    // Cierra y libera los recursos del motor TextToSpeech al salir de la pantalla.
    public void liberar() {
        if (motorVoz != null) {
            motorVoz.stop();
            motorVoz.shutdown();
            motorVoz = null;
            motorListo = false;
        }
    }
}
