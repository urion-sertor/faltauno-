package com.faltauno.app.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    // Horas válidas: "17", "17:00", "17.30", "17h", "17 h"...
    private static final Pattern FORMATO_HORA = Pattern.compile("^(\\d{1,2})(?:\\s*[:.hH]\\s*(\\d{2})?)?$");

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

    // Pasa la hora escrita ("17:00", "17.30", "17h" o "17") a minutos desde las 00:00, o -1 si no se entiende.
    public int calcularMinutosDelDia() {
        if (hora == null) {
            return -1;
        }
        Matcher partes = FORMATO_HORA.matcher(hora.trim());
        if (!partes.matches()) {
            return -1;
        }
        int horas = Integer.parseInt(partes.group(1));
        int minutos = (partes.group(2) != null) ? Integer.parseInt(partes.group(2)) : 0;
        if (horas > 23 || minutos > 59) {
            return -1;
        }
        return horas * 60 + minutos;
    }

    // Comprueba si la hora de la partida ya ha pasado. Si la hora no se entiende, no se oculta.
    public boolean yaHaPasado(int minutosAhora) {
        int minutosPartida = calcularMinutosDelDia();
        return minutosPartida >= 0 && minutosPartida < minutosAhora;
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
