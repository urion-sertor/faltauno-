package com.faltauno.app.voz;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import java.util.Locale;

public class GestorVoz implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    private boolean listo = false;

    // Constructor directo con el contexto
    public GestorVoz(Context context) {
        tts = new TextToSpeech(context, this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // Ponemos el idioma en espanol
            int res = tts.setLanguage(new Locale("es", "ES"));
            if (res == TextToSpeech.LANG_NOT_SUPPORTED || res == TextToSpeech.LANG_MISSING_DATA) {
                Log.e("GestorVoz", "Idioma no soportado");
            } else {
                listo = true;
            }
        } else {
            Log.e("GestorVoz", "Error al iniciar TTS");
        }
    }

    // Metodo para leer el texto que le pasemos
    public void hablar(String texto) {
        if (listo && tts != null) {
            tts.speak(texto, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    // Para apagarlo cuando salgamos de la app
    public void liberar() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}package com.faltauno.app.voz;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import java.util.Locale;

public class GestorVoz implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    private boolean listo = false;

    // Constructor directo con el contexto
    public GestorVoz(Context context) {
        tts = new TextToSpeech(context, this);
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // Ponemos el idioma en espanol
            int res = tts.setLanguage(new Locale("es", "ES"));
            if (res == TextToSpeech.LANG_NOT_SUPPORTED || res == TextToSpeech.LANG_MISSING_DATA) {
                Log.e("GestorVoz", "Idioma no soportado");
            } else {
                listo = true;
            }
        } else {
            Log.e("GestorVoz", "Error al iniciar TTS");
        }
    }

    // Metodo para leer el texto que le pasemos
    public void hablar(String texto) {
        if (listo && tts != null) {
            tts.speak(texto, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    // Para apagarlo cuando salgamos de la app
    public void liberar() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
    }
}