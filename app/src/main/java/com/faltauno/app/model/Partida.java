package com.faltauno.app.model;

/**
 * Modelo que representa una partida de juego organizada en el barrio.
 */
public class Partida {

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
        this.plazasLibres = plazasLibres;
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

    // Actualiza el número de plazas disponibles.
    public void setPlazasLibres(int plazasLibres) {
        this.plazasLibres = plazasLibres;
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
}
