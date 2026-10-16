package com.faltauno.app.voz;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import java.util.Locale;

public class GestorVoz implements TextToSpeech.OnInitListener {

    private TextToSpeech tts;
    private boolean listo = false;

    // Constructor vacio (por si se usa junto con inicializar)
    public GestorVoz() {
    }

    // Constructor directo con contexto (evita fugas con getApplicationContext)
    public GestorVoz(Context context) {
        inicializar(context);
    }

    // Metodo de inicializacion que usa Pol en MainActivity
    public void inicializar(Context context) {
        if (tts == null && context != null) {
            tts = new TextToSpeech(context.getApplicationContext(), this);
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int res = tts.setLanguage(new Locale("es", "ES"));
            if (res == TextToSpeech.LANG_NOT_SUPPORTED || res == TextToSpeech.LANG_MISSING_DATA) {
                Log.e("GestorVoz", "Idioma espanol no disponible en el movil");
            } else {
                listo = true;
            }
        } else {
            Log.e("GestorVoz", "Error iniciando TextToSpeech");
        }
    }

    // Reproduce el texto en voz alta cortando audios anteriores; devuelve false si la voz no está disponible
    public boolean hablar(String texto) {
        if (listo && tts != null && texto != null) {
            return tts.speak(texto, TextToSpeech.QUEUE_FLUSH, null, null) == TextToSpeech.SUCCESS;
        }
        return false;
    }

    // Libera recursos y limpia las referencias al salir
    public void liberar() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            tts = null;
            listo = false;
        }
    }
}