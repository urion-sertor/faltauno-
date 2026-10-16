package com.faltauno.app.ui;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faltauno.app.R;
import com.faltauno.app.data.GestorPartidas;
import com.faltauno.app.model.Partida;
import com.faltauno.app.voz.GestorVoz;
import com.google.android.material.button.MaterialButton;

import java.util.List;

/**
 * Pantalla principal que muestra el tablón de partidas del barrio y los filtros por juego.
 */
public class MainActivity extends AppCompatActivity implements PartidasAdapter.OnPartidaClickListener {

    private RecyclerView rvPartidas;
    private TextView tvSinPartidas;
    private PartidasAdapter adaptador;
    private GestorPartidas gestorPartidas;
    private GestorVoz gestorVoz;

    private MaterialButton btnFiltroTodos;
    private MaterialButton btnFiltroDomino;
    private MaterialButton btnFiltroCartas;
    private MaterialButton btnFiltroPetanca;
    private MaterialButton btnFiltroParchis;
    private MaterialButton btnFaltaUno;

    // Abre la pantalla "Falta 1" y, si se publica una partida, muestra el aviso verde al volver
    private final ActivityResultLauncher<Intent> abrirNuevaPartida = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            resultado -> {
                if (resultado.getResultCode() == RESULT_OK) {
                    Avisos.mostrarExito(rvPartidas, getString(R.string.aviso_partida_publicada), btnFaltaUno);
                }
            });

    // Juego elegido en los filtros; empieza en "Todos" (se lee de strings.xml en onCreate)
    private String juegoSeleccionado;

    // Método inicial del ciclo de vida donde se configuran las vistas y gestores.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        juegoSeleccionado = getString(R.string.juego_todos);
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
        tvSinPartidas = findViewById(R.id.tvSinPartidas);
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
        btnFiltroTodos.setOnClickListener(v -> filtrarPorJuego(getString(R.string.juego_todos)));
        btnFiltroDomino.setOnClickListener(v -> filtrarPorJuego(getString(R.string.juego_domino)));
        btnFiltroCartas.setOnClickListener(v -> filtrarPorJuego(getString(R.string.juego_cartas)));
        btnFiltroPetanca.setOnClickListener(v -> filtrarPorJuego(getString(R.string.juego_petanca)));
        btnFiltroParchis.setOnClickListener(v -> filtrarPorJuego(getString(R.string.juego_parchis)));
    }

    // Filtra la lista de partidas en pantalla y muestra un aviso si no hay ninguna de ese juego.
    private void filtrarPorJuego(String juego) {
        this.juegoSeleccionado = (juego != null) ? juego : getString(R.string.juego_todos);
        List<Partida> filtradas = gestorPartidas.obtenerPartidasPorJuego(this.juegoSeleccionado);
        if (adaptador != null) {
            adaptador.actualizarPartidas(filtradas);
        }
        marcarFiltroSeleccionado();

        // Alternar entre la lista o el mensaje de que no hay partidas
        boolean sinPartidas = (filtradas == null || filtradas.isEmpty());
        rvPartidas.setVisibility(sinPartidas ? View.GONE : View.VISIBLE);
        tvSinPartidas.setVisibility(sinPartidas ? View.VISIBLE : View.GONE);
    }

    // Resalta la casilla del juego elegido con un borde verde grueso; las demás quedan con un borde fino.
    private void marcarFiltroSeleccionado() {
        pintarBotonFiltro(btnFiltroTodos, getString(R.string.juego_todos));
        pintarBotonFiltro(btnFiltroDomino, getString(R.string.juego_domino));
        pintarBotonFiltro(btnFiltroCartas, getString(R.string.juego_cartas));
        pintarBotonFiltro(btnFiltroPetanca, getString(R.string.juego_petanca));
        pintarBotonFiltro(btnFiltroParchis, getString(R.string.juego_parchis));
    }

    // Cambia el borde de una casilla de filtro según si es el juego seleccionado o no.
    // Cada casilla mantiene su color pastel de Figma; la elegida se marca con borde verde grueso.
    private void pintarBotonFiltro(MaterialButton boton, String juego) {
        boolean seleccionado = juego.equals(juegoSeleccionado);
        int colorBorde = ContextCompat.getColor(this,
                seleccionado ? R.color.color_primario : R.color.color_borde);
        int anchoBorde = getResources().getDimensionPixelSize(
                seleccionado ? R.dimen.borde_seleccion : R.dimen.borde_fino);
        boton.setStrokeColor(ColorStateList.valueOf(colorBorde));
        boton.setStrokeWidth(anchoBorde);
        // El lector de pantalla dirá "seleccionado" en la casilla elegida
        boton.setSelected(seleccionado);
    }

    // Configura el botón inferior para abrir la pantalla de creación de aviso 'Falta 1'.
    private void configurarBotonFaltaUno() {
        btnFaltaUno.setOnClickListener(v -> {
            Intent intencion = new Intent(MainActivity.this, NuevaPartidaActivity.class);
            abrirNuevaPartida.launch(intencion);
        });
    }

    // Maneja la acción de apuntarse a una partida reduciendo una plaza y avisando al usuario.
    @Override
    public void onMeApuntoClick(Partida partida, int posicion) {
        boolean exito = gestorPartidas.apuntarseAPartida(partida.getId());
        if (exito) {
            Avisos.mostrarExito(rvPartidas, getString(R.string.aviso_apuntado, partida.getHora()), btnFaltaUno);
            filtrarPorJuego(juegoSeleccionado);
        } else {
            Avisos.mostrarError(rvPartidas, getString(R.string.aviso_partida_llena), btnFaltaUno);
        }
    }

    // Lee en voz alta la información de la partida usando el motor TextToSpeech nativo.
    @Override
    public void onAltavozClick(Partida partida) {
        if (gestorVoz == null) {
            return;
        }
        // "Queda 1 plaza libre" / "Quedan 2 plazas libres", o "Partida llena" si no queda ninguna
        String plazas = partida.estaLlena()
                ? getString(R.string.estado_partida_llena)
                : getResources().getQuantityString(R.plurals.locucion_plazas_libres,
                        partida.getPlazasLibres(), partida.getPlazasLibres());
        String texto = getString(R.string.formato_locucion_voz,
                partida.getJuego(),
                partida.getHora(),
                partida.getLugar(),
                plazas);
        if (!gestorVoz.hablar(texto)) {
            Avisos.mostrarError(rvPartidas, getString(R.string.aviso_error_voz), btnFaltaUno);
        }
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
