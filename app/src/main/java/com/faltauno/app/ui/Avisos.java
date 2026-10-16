package com.faltauno.app.ui;

import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.faltauno.app.R;
import com.google.android.material.snackbar.Snackbar;

/**
 * Muestra avisos grandes y legibles (Snackbar) con el mismo estilo en todas las pantallas.
 * Verde oscuro para las cosas que salen bien y verde casi negro para los errores o avisos.
 */
public final class Avisos {

    // Clase de ayuda: no se crean objetos, solo se usan sus métodos.
    private Avisos() {
    }

    // Muestra un aviso verde de que algo ha salido bien (por ejemplo, al apuntarse).
    public static void mostrarExito(View vista, String mensaje, View ancla) {
        mostrar(vista, mensaje, R.color.color_primario, ancla);
    }

    // Muestra un aviso oscuro de error o de algo que el usuario tiene que corregir.
    public static void mostrarError(View vista, String mensaje, View ancla) {
        mostrar(vista, mensaje, R.color.color_texto_principal, ancla);
    }

    // Crea el Snackbar con letra grande, texto blanco y el color de fondo indicado.
    private static void mostrar(View vista, String mensaje, int colorFondo, View ancla) {
        Snackbar aviso = Snackbar.make(vista, mensaje, Snackbar.LENGTH_LONG);
        aviso.setBackgroundTint(ContextCompat.getColor(vista.getContext(), colorFondo));
        aviso.setTextColor(ContextCompat.getColor(vista.getContext(), R.color.color_texto_blanco));

        // Letra grande (20sp) y hasta 4 líneas para que el mensaje se lea entero
        TextView texto = aviso.getView().findViewById(com.google.android.material.R.id.snackbar_text);
        texto.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                vista.getResources().getDimension(R.dimen.texto_subtitulo));
        texto.setMaxLines(4);

        // Si hay un botón importante abajo (por ejemplo "Falta 1"), el aviso sale encima y no lo tapa
        if (ancla != null) {
            aviso.setAnchorView(ancla);
        }
        aviso.show();
    }
}
