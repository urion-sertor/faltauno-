package com.faltauno.app.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.faltauno.app.model.Partida;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Gestor encargado de almacenar y recuperar las partidas en la memoria del dispositivo.
 * Todas las partidas son "de hoy": al cambiar de día se borran y las que ya han pasado se ocultan.
 */
public class GestorPartidas {

    private static final String ETIQUETA = "GestorPartidas";
    private static final String NOMBRE_PREFERENCIAS = "FaltaUnoPrefs";
    private static final String CLAVE_PARTIDAS = "partidas";
    private static final String CLAVE_CONTADOR_ID = "contador_id";
    private static final String CLAVE_FECHA_TABLON = "fecha_tablon";

    private static GestorPartidas instancia;
    private final List<Partida> listaPartidas;
    private int contadorId = 1;
    private String fechaTablon;
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
        actualizarTablon();
    }

    // Obtiene la instancia única compartida del gestor de partidas en la app.
    public static synchronized GestorPartidas obtenerInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new GestorPartidas(contexto);
        }
        return instancia;
    }

    // Lee del móvil las partidas guardadas, el contador de identificadores y el día del tablón.
    private void cargarPartidas() {
        listaPartidas.clear();
        fechaTablon = preferencias.getString(CLAVE_FECHA_TABLON, null);
        String textoGuardado = preferencias.getString(CLAVE_PARTIDAS, null);
        if (textoGuardado == null) {
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
            Log.e(ETIQUETA, "Datos guardados no válidos, se empieza con el tablón vacío.", e);
            listaPartidas.clear();
        }
    }

    // Borra las partidas de días anteriores y, si no queda ninguna visible, crea las de ejemplo.
    private void actualizarTablon() {
        boolean hayCambios = false;
        String hoy = obtenerFechaDeHoy();

        if (!hoy.equals(fechaTablon)) {
            listaPartidas.clear();
            fechaTablon = hoy;
            hayCambios = true;
        }

        if (obtenerPartidasVisibles().isEmpty()) {
            cargarPartidasIniciales();
            hayCambios = true;
        }

        if (hayCambios) {
            guardarEnMovil();
        }
    }

    // Añade las 3 partidas de ejemplo del MVP con horas a partir de ahora para que la demo nunca salga vacía.
    private void cargarPartidasIniciales() {
        listaPartidas.add(new Partida(contadorId++, "Dominó", "Plaza Mayor", calcularHoraDentroDe(1), 1, "Manolo"));
        listaPartidas.add(new Partida(contadorId++, "Petanca", "Parque Central", calcularHoraDentroDe(2), 2, "Carmen"));
        listaPartidas.add(new Partida(contadorId++, "Cartas", "Centro Cívico", calcularHoraDentroDe(3), 3, "Paco"));
    }

    // Devuelve la hora en punto dentro de las horas indicadas ("17:00"), o "23:59" si ya sería mañana.
    private String calcularHoraDentroDe(int horas) {
        Calendar calendario = Calendar.getInstance();
        int diaDeHoy = calendario.get(Calendar.DAY_OF_YEAR);
        calendario.add(Calendar.HOUR_OF_DAY, horas);
        if (calendario.get(Calendar.DAY_OF_YEAR) != diaDeHoy) {
            return "23:59";
        }
        return String.format(Locale.US, "%02d:00", calendario.get(Calendar.HOUR_OF_DAY));
    }

    // Devuelve la fecha de hoy en formato "2026-10-16" para saber si ha cambiado el día.
    private String obtenerFechaDeHoy() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.US).format(new Date());
    }

    // Devuelve los minutos que han pasado desde las 00:00 de hoy.
    private int obtenerMinutosAhora() {
        Calendar ahora = Calendar.getInstance();
        return ahora.get(Calendar.HOUR_OF_DAY) * 60 + ahora.get(Calendar.MINUTE);
    }

    // Devuelve solo las partidas cuya hora todavía no ha pasado.
    private List<Partida> obtenerPartidasVisibles() {
        int minutosAhora = obtenerMinutosAhora();
        List<Partida> visibles = new ArrayList<>();
        for (Partida p : listaPartidas) {
            if (!p.yaHaPasado(minutosAhora)) {
                visibles.add(p);
            }
        }
        return visibles;
    }

    // Guarda la lista completa de partidas, el contador de identificadores y el día en el móvil.
    private void guardarEnMovil() {
        try {
            JSONArray arrayPartidas = new JSONArray();
            for (Partida partida : listaPartidas) {
                arrayPartidas.put(partida.aJson());
            }
            preferencias.edit()
                    .putString(CLAVE_PARTIDAS, arrayPartidas.toString())
                    .putInt(CLAVE_CONTADOR_ID, contadorId)
                    .putString(CLAVE_FECHA_TABLON, fechaTablon)
                    .apply();
        } catch (JSONException e) {
            Log.e(ETIQUETA, "No se han podido guardar las partidas.", e);
        }
    }

    // Devuelve las partidas de hoy que todavía no han pasado.
    public synchronized List<Partida> obtenerPartidas() {
        actualizarTablon();
        return obtenerPartidasVisibles();
    }

    // Devuelve la lista de partidas filtradas según el nombre del juego seleccionado.
    public synchronized List<Partida> obtenerPartidasPorJuego(String juego) {
        List<Partida> visibles = obtenerPartidas();
        if (juego == null || juego.trim().isEmpty() || juego.trim().equalsIgnoreCase("Todos")) {
            return visibles;
        }
        List<Partida> filtradas = new ArrayList<>();
        for (Partida p : visibles) {
            if (juego.trim().equalsIgnoreCase(p.getJuego())) {
                filtradas.add(p);
            }
        }
        return filtradas;
    }

    // Guarda una nueva partida al principio de la lista con un identificador nuevo y la deja en el móvil.
    public synchronized void guardarPartida(Partida p) {
        if (p == null) {
            return;
        }
        p.setId(contadorId++);
        listaPartidas.add(0, p);
        guardarEnMovil();
    }

    // Indica si una hora escrita ya ha pasado hoy, para que el formulario "Falta 1" (NuevaPartidaActivity)
    // lo compruebe antes de publicar: una partida con la hora pasada se guardaría pero no se vería en el tablón.
    // Si la hora no se entiende (por ejemplo "tarde") devuelve false, igual que el tablón, que no la oculta.
    public boolean esHoraPasada(String hora) {
        int minutosPartida = Partida.convertirHoraAMinutos(hora);
        return minutosPartida >= 0 && minutosPartida < obtenerMinutosAhora();
    }

    // Resta una plaza a la partida indicada y devuelve verdadero si la operación tuvo éxito.
    // Es synchronized: si dos personas pulsan a la vez, entra la primera y la segunda recibe false.
    public synchronized boolean apuntarseAPartida(int idPartida) {
        for (Partida p : listaPartidas) {
            if (p.getId() == idPartida) {
                if (p.estaLlena()) {
                    return false;
                }
                p.setPlazasLibres(p.getPlazasLibres() - 1);
                guardarEnMovil();
                return true;
            }
        }
        return false;
    }
}
