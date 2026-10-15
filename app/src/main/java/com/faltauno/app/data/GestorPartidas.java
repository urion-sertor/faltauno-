package com.faltauno.app.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.faltauno.app.model.Partida;

import java.util.ArrayList;
import java.util.List;

/**
 * Gestor encargado de almacenar y recuperar las partidas en la memoria del dispositivo.
 */
public class GestorPartidas {

    private static GestorPartidas instancia;
    private final List<Partida> listaPartidas;
    private int contadorId = 4;
    private final SharedPreferences preferencias;

    // Constructor privado que inicializa la persistencia y carga las partidas iniciales de prueba.
    private GestorPartidas(Context contexto) {
        this.preferencias = contexto.getApplicationContext().getSharedPreferences("FaltaUnoPrefs", Context.MODE_PRIVATE);
        this.listaPartidas = new ArrayList<>();
        cargarPartidasIniciales();
    }

    // Obtiene la instancia única compartida del gestor de partidas en la app.
    public static synchronized GestorPartidas obtenerInstancia(Context contexto) {
        if (instancia == null) {
            instancia = new GestorPartidas(contexto);
        }
        return instancia;
    }

    // Carga las 3 partidas de ejemplo iniciales requeridas para el funcionamiento del MVP.
    private void cargarPartidasIniciales() {
        listaPartidas.clear();
        listaPartidas.add(new Partida(1, "Dominó", "Plaza Mayor", "17:00", 1, "Manolo"));
        listaPartidas.add(new Partida(2, "Petanca", "Parque Central", "18:00", 2, "Carmen"));
        listaPartidas.add(new Partida(3, "Cartas", "Centro Cívico", "16:30", 3, "Paco"));
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
