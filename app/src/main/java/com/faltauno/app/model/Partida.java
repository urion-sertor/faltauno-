package com.faltauno.app.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Modelo que representa una partida de juego organizada en el barrio.
 */
public class Partida {

    // Nombres de los campos cuando la partida se guarda en formato JSON.
    private static final String CLAVE_ID = "id";
    private static final String CLAVE_JUEGO = "juego";
    private static final String CLAVE_LUGAR = "lugar";
    private static final String CLAVE_HORA = "hora";
    private static final String CLAVE_PLAZAS_LIBRES = "plazasLibres";
    private static final String CLAVE_CREADOR = "creador";

    private int id;
    private String juego;
    private String lugar;
    private String hora;
    private int plazasLibres;
    private String creador;

    // Constructor vacío por defecto para inicialización flexible.
    public Partida() {
    }

    // Constructor completo para crear una nueva partida con todos sus datos.
    public Partida(int id, String juego, String lugar, String hora, int plazasLibres, String creador) {
        this.id = id;
        this.juego = juego;
        this.lugar = lugar;
        this.hora = hora;
        setPlazasLibres(plazasLibres);
        this.creador = creador;
    }

    // Devuelve el identificador único de la partida.
    public int getId() {
        return id;
    }

    // Establece el identificador único de la partida.
    public void setId(int id) {
        this.id = id;
    }

    // Devuelve el nombre del juego (Dominó, Petanca, etc.).
    public String getJuego() {
        return juego;
    }

    // Establece el nombre del juego.
    public void setJuego(String juego) {
        this.juego = juego;
    }

    // Devuelve el lugar público donde se jugará la partida.
    public String getLugar() {
        return lugar;
    }

    // Establece el lugar público de la partida.
    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    // Devuelve la hora programada para jugar.
    public String getHora() {
        return hora;
    }

    // Establece la hora programada de la partida.
    public void setHora(String hora) {
        this.hora = hora;
    }

    // Devuelve el número de plazas que quedan libres actualmente.
    public int getPlazasLibres() {
        return plazasLibres;
    }

    // Establece las plazas libres sin permitir nunca un número negativo.
    public void setPlazasLibres(int plazasLibres) {
        this.plazasLibres = Math.max(0, plazasLibres);
    }

    // Devuelve el apodo de la persona que organizó la partida.
    public String getCreador() {
        return creador;
    }

    // Establece el apodo de la persona organizadora.
    public void setCreador(String creador) {
        this.creador = creador;
    }

    // Comprueba si la partida ya no tiene plazas libres disponibles.
    public boolean estaLlena() {
        return this.plazasLibres <= 0;
    }

    // Convierte la partida en un objeto JSON para poder guardarla en el móvil.
    public JSONObject aJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(CLAVE_ID, id);
        json.put(CLAVE_JUEGO, juego);
        json.put(CLAVE_LUGAR, lugar);
        json.put(CLAVE_HORA, hora);
        json.put(CLAVE_PLAZAS_LIBRES, plazasLibres);
        json.put(CLAVE_CREADOR, creador);
        return json;
    }

    // Crea una partida a partir de un objeto JSON guardado en el móvil.
    public static Partida desdeJson(JSONObject json) throws JSONException {
        return new Partida(
                json.getInt(CLAVE_ID),
                json.getString(CLAVE_JUEGO),
                json.getString(CLAVE_LUGAR),
                json.getString(CLAVE_HORA),
                json.getInt(CLAVE_PLAZAS_LIBRES),
                json.optString(CLAVE_CREADOR, "Vecino")
        );
    }
}
