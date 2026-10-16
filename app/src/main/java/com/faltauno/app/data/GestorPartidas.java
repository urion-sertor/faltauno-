package com.faltauno.app.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.faltauno.app.model.Partida;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestor encargado de almacenar y recuperar las partidas en la memoria del dispositivo.
 */
public class GestorPartidas {

    private static final String ETIQUETA = "GestorPartidas";
    private static final String NOMBRE_PREFERENCIAS = "FaltaUnoPrefs";
    private static final String CLAVE_PARTIDAS = "partidas";
    private static final String CLAVE_CONTADOR_ID = "contador_id";

    private static GestorPartidas instancia;
    private final List<Partida> listaPartidas;
    private int contadorId = 4;
    private final SharedPreferences preferencias;

    // Constructor privado que abre el almacenamiento del móvil y carga las partidas guardadas.
    private GestorPartidas(Context contexto) {
        // El gestor vive mientras la app esté abierta (singleton). Si guardáramos una Activity,
        // esa pantalla no se podría liberar nunca (fuga de memoria). Por eso usamos siempre
        // el contexto de la aplicación, que dura lo mismo que la app.
        Context contextoAplicacion = contexto.getApplicationContext();
        this.preferencias = contextoAplicacion.getSharedPreferences(NOMBRE_PREFERENCIAS, Context.MODE_PRIVATE);
        this.listaPartidas = new ArrayList<>();
        cargarPartidas();
    }

    // Obtiene la instancia única compartida del gestor de partidas en la app.
    public static synchronized GestorPartidas obtenerInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new GestorPartidas(contexto);
        }
        return instancia;
    }

    // Lee las partidas guardadas en el móvil o crea las de ejemplo si no hay ninguna.
    private void cargarPartidas() {
        listaPartidas.clear();
        String textoGuardado = preferencias.getString(CLAVE_PARTIDAS, null);

        if (textoGuardado == null) {
            cargarPartidasIniciales();
            guardarEnMovil();
            return;
        }

        try {
            JSONArray arrayPartidas = new JSONArray(textoGuardado);
            int idMaximo = 0;
            for (int i = 0; i < arrayPartidas.length(); i++) {
                Partida partida = Partida.desdeJson(arrayPartidas.getJSONObject(i));
                listaPartidas.add(partida);
                idMaximo = Math.max(idMaximo, partida.getId());
            }
            contadorId = Math.max(preferencias.getInt(CLAVE_CONTADOR_ID, idMaximo + 1), idMaximo + 1);
        } catch (JSONException e) {
            // Si los datos guardados están estropeados, empezamos de nuevo en vez de cerrar la app.
            Log.e(ETIQUETA, "Datos guardados no válidos, se cargan las partidas de ejemplo.", e);
            listaPartidas.clear();
            cargarPartidasIniciales();
            guardarEnMovil();
        }
    }

    // Carga las 3 partidas de ejemplo iniciales requeridas para el funcionamiento del MVP.
    private void cargarPartidasIniciales() {
        listaPartidas.clear();
        listaPartidas.add(new Partida(1, "Dominó", "Plaza Mayor", "17:00", 1, "Manolo"));
        listaPartidas.add(new Partida(2, "Petanca", "Parque Central", "18:00", 2, "Carmen"));
        listaPartidas.add(new Partida(3, "Cartas", "Centro Cívico", "16:30", 3, "Paco"));
        contadorId = 4;
    }

    // Guarda la lista completa de partidas y el contador de identificadores en el móvil.
    private void guardarEnMovil() {
        try {
            JSONArray arrayPartidas = new JSONArray();
            for (Partida partida : listaPartidas) {
                arrayPartidas.put(partida.aJson());
            }
            preferencias.edit()
                    .putString(CLAVE_PARTIDAS, arrayPartidas.toString())
                    .putInt(CLAVE_CONTADOR_ID, contadorId)
                    .apply();
        } catch (JSONException e) {
            Log.e(ETIQUETA, "No se han podido guardar las partidas.", e);
        }
    }

    // Devuelve la lista completa de todas las partidas registradas.
    public List<Partida> obtenerPartidas() {
        return new ArrayList<>(listaPartidas);
    }

    // Devuelve la lista de partidas filtradas según el nombre del juego seleccionado.
    public List<Partida> obtenerPartidasPorJuego(String juego) {
        if (juego == null || juego.equalsIgnoreCase("Todos")) {
            return obtenerPartidas();
        }
        List<Partida> filtradas = new ArrayList<>();
        for (Partida p : listaPartidas) {
            if (p.getJuego().equalsIgnoreCase(juego)) {
                filtradas.add(p);
            }
        }
        return filtradas;
    }

    // Guarda una nueva partida en la lista asignándole un identificador incremental.
    public void guardarPartida(Partida p) {
        p.setId(contadorId++);
        listaPartidas.add(0, p);
    }

    // Resta una plaza a la partida indicada y devuelve verdadero si la operación tuvo éxito.
    public boolean apuntarseAPartida(int idPartida) {
        for (Partida p : listaPartidas) {
            if (p.getId() == idPartida) {
                if (p.getPlazasLibres() > 0) {
                    p.setPlazasLibres(p.getPlazasLibres() - 1);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
