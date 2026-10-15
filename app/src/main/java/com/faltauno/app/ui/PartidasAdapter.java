package com.faltauno.app.ui;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.faltauno.app.R;
import com.faltauno.app.model.Partida;

import java.util.ArrayList;
import java.util.List;

/**
 * Adaptador de RecyclerView para mostrar las tarjetas de partidas de forma accesible.
 */
public class PartidasAdapter extends RecyclerView.Adapter<PartidasAdapter.PartidaViewHolder> {

    // Interfaz para comunicar las pulsaciones de botones a la actividad principal.
    public interface OnPartidaClickListener {
        void onMeApuntoClick(Partida partida, int posicion);
        void onAltavozClick(Partida partida);
    }

    private final Context contexto;
    private List<Partida> listaPartidas;
    private final OnPartidaClickListener listener;

    // Constructor que recibe el contexto, la lista de partidas y el manejador de clics.
    public PartidasAdapter(Context contexto, List<Partida> listaPartidas, OnPartidaClickListener listener) {
        this.contexto = contexto;
        this.listaPartidas = (listaPartidas != null) ? listaPartidas : new ArrayList<>();
        this.listener = listener;
    }

    // Actualiza la lista interna de partidas y refresca la vista del RecyclerView.
    public void actualizarPartidas(List<Partida> nuevasPartidas) {
        this.listaPartidas = (nuevasPartidas != null) ? nuevasPartidas : new ArrayList<>();
        notifyDataSetChanged();
    }

    // Infla el diseño XML de la tarjeta individual de partida.
    @NonNull
    @Override
    public PartidaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(contexto).inflate(R.layout.item_partida, parent, false);
        return new PartidaViewHolder(vista);
    }

    // Vincula los datos de la partida con los campos visuales de la tarjeta.
    @Override
    public void onBindViewHolder(@NonNull PartidaViewHolder holder, int position) {
        Partida partida = listaPartidas.get(position);

        holder.tvNombreJuego.setText(partida.getJuego());
        holder.tvHoraPartida.setText(contexto.getString(R.string.formato_hora, partida.getHora()));
        holder.tvLugarPartida.setText(contexto.getString(R.string.formato_lugar, partida.getLugar()));
        holder.tvPlazasLibres.setText(contexto.getString(R.string.formato_plazas_libres, partida.getPlazasLibres()));
        holder.tvCreadorPartida.setText(contexto.getString(R.string.formato_creador, partida.getCreador()));

        // Configuración visual según si quedan plazas o la partida está llena
        if (partida.estaLlena()) {
            holder.btnMeApunto.setText(contexto.getString(R.string.estado_partida_llena));
            holder.btnMeApunto.setEnabled(false);
            holder.btnMeApunto.setBackgroundTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(contexto, R.color.color_gris_lleno)));
        } else {
            holder.btnMeApunto.setText(contexto.getString(R.string.accion_me_apunto));
            holder.btnMeApunto.setEnabled(true);
            holder.btnMeApunto.setBackgroundTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(contexto, R.color.color_verde_apunto)));
        }

        // Listener para apuntarse a la partida
        holder.btnMeApunto.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMeApuntoClick(partida, holder.getAdapterPosition());
            }
        });

        // Listener para escuchar la información por voz
        holder.btnAltavoz.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAltavozClick(partida);
            }
        });
    }

    // Devuelve la cantidad total de partidas en la lista.
    @Override
    public int getItemCount() {
        return listaPartidas.size();
    }

    /**
     * ViewHolder que mantiene las referencias a los elementos visuales de la tarjeta.
     */
    public static class PartidaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreJuego;
        TextView tvHoraPartida;
        TextView tvLugarPartida;
        TextView tvPlazasLibres;
        TextView tvCreadorPartida;
        Button btnAltavoz;
        Button btnMeApunto;

        // Constructor del ViewHolder que vincula los IDs de las vistas.
        public PartidaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreJuego = itemView.findViewById(R.id.tvNombreJuego);
            tvHoraPartida = itemView.findViewById(R.id.tvHoraPartida);
            tvLugarPartida = itemView.findViewById(R.id.tvLugarPartida);
            tvPlazasLibres = itemView.findViewById(R.id.tvPlazasLibres);
            tvCreadorPartida = itemView.findViewById(R.id.tvCreadorPartida);
            btnAltavoz = itemView.findViewById(R.id.btnAltavoz);
            btnMeApunto = itemView.findViewById(R.id.btnMeApunto);
        }
    }
}
