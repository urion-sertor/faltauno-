package com.faltauno.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faltauno.app.R;
import com.faltauno.app.data.GestorPartidas;
import com.faltauno.app.model.Partida;
import com.faltauno.app.voz.GestorVoz;

import java.util.List;

/**
 * Pantalla principal que muestra el tablón de partidas del barrio y los filtros por juego.
 */
public class MainActivity extends AppCompatActivity implements PartidasAdapter.OnPartidaClickListener {

    private RecyclerView rvPartidas;
    private PartidasAdapter adaptador;
    private GestorPartidas gestorPartidas;
    private GestorVoz gestorVoz;

    private Button btnFiltroTodos;
    private Button btnFiltroDomino;
    private Button btnFiltroCartas;
    private Button btnFiltroPetanca;
    private Button btnFiltroParchis;
    private Button btnFaltaUno;

    private String juegoSeleccionado = "Todos";

    // Método inicial del ciclo de vida donde se configuran las vistas y gestores.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializarGestores();
        vincularVistas();
        configurarListaPartidas();
        configurarFiltros();
        configurarBotonFaltaUno();
    }

    // Inicializa el gestor de datos en memoria y el motor de voz en español.
    private void inicializarGestores() {
        gestorPartidas = GestorPartidas.obtenerInstancia(this);
        gestorVoz = new GestorVoz();
        gestorVoz.inicializar(this);
    }

    // Obtiene las referencias de todos los elementos visuales definidos en el XML.
    private void vincularVistas() {
        rvPartidas = findViewById(R.id.rvPartidas);
        btnFiltroTodos = findViewById(R.id.btnFiltroTodos);
        btnFiltroDomino = findViewById(R.id.btnFiltroDomino);
        btnFiltroCartas = findViewById(R.id.btnFiltroCartas);
        btnFiltroPetanca = findViewById(R.id.btnFiltroPetanca);
        btnFiltroParchis = findViewById(R.id.btnFiltroParchis);
        btnFaltaUno = findViewById(R.id.btnFaltaUno);
    }

    // Configura el RecyclerView con su administrador de diseño y adaptador inicial.
    private void configurarListaPartidas() {
        rvPartidas.setLayoutManager(new LinearLayoutManager(this));
        adaptador = new PartidasAdapter(this, gestorPartidas.obtenerPartidas(), this);
        rvPartidas.setAdapter(adaptador);
    }

    // Asigna las acciones a los botones gigantes de filtro de juegos.
    private void configurarFiltros() {
        btnFiltroTodos.setOnClickListener(v -> filtrarPorJuego("Todos"));
        btnFiltroDomino.setOnClickListener(v -> filtrarPorJuego("Dominó"));
        btnFiltroCartas.setOnClickListener(v -> filtrarPorJuego("Cartas"));
        btnFiltroPetanca.setOnClickListener(v -> filtrarPorJuego("Petanca"));
        btnFiltroParchis.setOnClickListener(v -> filtrarPorJuego("Parchís"));
    }

    // Filtra la lista de partidas en pantalla según el juego que el usuario haya seleccionado.
    private void filtrarPorJuego(String juego) {
        this.juegoSeleccionado = juego;
        List<Partida> filtradas = gestorPartidas.obtenerPartidasPorJuego(juego);
        adaptador.actualizarPartidas(filtradas);
    }

    // Configura el botón inferior para abrir la pantalla de creación de aviso 'Falta 1'.
    private void configurarBotonFaltaUno() {
        btnFaltaUno.setOnClickListener(v -> {
            Intent intencion = new Intent(MainActivity.this, NuevaPartidaActivity.class);
            startActivity(intencion);
        });
    }

    // Maneja la acción de apuntarse a una partida reduciendo una plaza y avisando al usuario.
    @Override
    public void onMeApuntoClick(Partida partida, int posicion) {
        boolean exito = gestorPartidas.apuntarseAPartida(partida.getId());
        if (exito) {
            String mensaje = getString(R.string.toast_apuntado_exito, partida.getHora(), partida.getLugar());
            Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
            filtrarPorJuego(juegoSeleccionado);
        } else {
            Toast.makeText(this, R.string.toast_partida_llena, Toast.LENGTH_SHORT).show();
        }
    }

    // Lee en voz alta la información de la partida usando el motor TextToSpeech nativo.
    @Override
    public void onAltavozClick(Partida partida) {
        String texto = getString(R.string.formato_locucion_voz,
                partida.getJuego(),
                partida.getHora(),
                partida.getLugar(),
                partida.getPlazasLibres());
        gestorVoz.hablar(texto);
    }

    // Refresca la lista de partidas cada vez que el usuario regresa a la pantalla principal.
    @Override
    protected void onResume() {
        super.onResume();
        filtrarPorJuego(juegoSeleccionado);
    }

    // Libera los recursos del motor de voz al cerrar completamente la actividad.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gestorVoz != null) {
            gestorVoz.liberar();
        }
    }
}
