package com.faltauno.app.ui;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.faltauno.app.R;
import com.faltauno.app.data.GestorPartidas;
import com.faltauno.app.model.Partida;

/**
 * Pantalla que permite a un usuario publicar un aviso de que 'Falta 1' para una partida.
 */
public class NuevaPartidaActivity extends AppCompatActivity {

    private Spinner spJuegos;
    private EditText etHoraPartida;
    private EditText etLugarPartida;
    private EditText etCreadorPartida;
    private Button btnPublicarPartida;
    private Button btnVolverAtras;

    private GestorPartidas gestorPartidas;

    // Método inicial del ciclo de vida donde se configuran el formulario y las acciones.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_partida);

        gestorPartidas = GestorPartidas.obtenerInstancia(this);
        vincularVistas();
        configurarDesplegableJuegos();
        configurarBotonPublicar();
        configurarBotonVolver();
    }

    // Obtiene las referencias de los campos de texto, desplegables y botones del XML.
    private void vincularVistas() {
        spJuegos = findViewById(R.id.spJuegos);
        etHoraPartida = findViewById(R.id.etHoraPartida);
        etLugarPartida = findViewById(R.id.etLugarPartida);
        etCreadorPartida = findViewById(R.id.etCreadorPartida);
        btnPublicarPartida = findViewById(R.id.btnPublicarPartida);
        btnVolverAtras = findViewById(R.id.btnVolverAtras);
    }

    // Llena el selector desplegable con las opciones de juegos populares del barrio.
    private void configurarDesplegableJuegos() {
        String[] listaJuegos = {
                getString(R.string.juego_domino),
                getString(R.string.juego_cartas),
                getString(R.string.juego_petanca),
                getString(R.string.juego_parchis)
        };
        ArrayAdapter<String> adaptadorJuegos = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                listaJuegos
        );
        adaptadorJuegos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spJuegos.setAdapter(adaptadorJuegos);
    }

    // Configura el botón de publicación con validación simple de campos obligatorios.
    private void configurarBotonPublicar() {
        btnPublicarPartida.setOnClickListener(v -> {
            String juego = (spJuegos.getSelectedItem() != null)
                    ? spJuegos.getSelectedItem().toString()
                    : getString(R.string.juego_domino);
            String hora = etHoraPartida.getText().toString().trim();
            String lugar = etLugarPartida.getText().toString().trim();
            String creador = etCreadorPartida.getText().toString().trim();

            if (hora.isEmpty() || lugar.isEmpty()) {
                Toast.makeText(this, R.string.toast_faltan_campos, Toast.LENGTH_SHORT).show();
                return;
            }

            // La hora tiene que entenderse (por ejemplo "17:00") para poder mostrarla en el tablón
            if (Partida.convertirHoraAMinutos(hora) < 0) {
                Toast.makeText(this, R.string.toast_hora_no_valida, Toast.LENGTH_LONG).show();
                return;
            }

            // Si la hora ya ha pasado, la partida no se vería en el tablón: avisamos y no publicamos
            if (gestorPartidas.esHoraPasada(hora)) {
                Toast.makeText(this, R.string.toast_hora_pasada, Toast.LENGTH_LONG).show();
                return;
            }

            if (creador.isEmpty()) {
                creador = "Vecino";
            }

            // Una partida recién publicada avisa que falta 1 jugador
            Partida nuevaPartida = new Partida(0, juego, lugar, hora, 1, creador);
            gestorPartidas.guardarPartida(nuevaPartida);

            Toast.makeText(this, R.string.toast_partida_publicada, Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    // Configura el botón de volver para regresar a la pantalla principal sin guardar cambios.
    private void configurarBotonVolver() {
        btnVolverAtras.setOnClickListener(v -> finish());
    }
}
