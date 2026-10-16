package com.faltauno.app.ui;

import android.annotation.SuppressLint;
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
 * Adaptador de RecyclerView que muestra cada partida en una tarjeta accesible.
 */
public class PartidasAdapter extends RecyclerView.Adapter<PartidasAdapter.PartidaViewHolder> {

    // Listener para avisar a la Activity de los clicks
    public interface OnPartidaClickListener {
        void onMeApuntoClick(Partida partida, int posicion);
        void onAltavozClick(Partida partida);
    }

    private Context context;
    private List<Partida> listaPartidas;
    private OnPartidaClickListener listener;

    // Constructor que recibe el contexto, la lista de partidas y quién escucha los clics.
    public PartidasAdapter(Context context, List<Partida> listaPartidas, OnPartidaClickListener listener) {
        this.context = context;
        this.listaPartidas = (listaPartidas != null) ? listaPartidas : new ArrayList<>();
        this.listener = listener;
    }

    // Para recargar la lista cuando haya cambios o filtros.
    // Cada filtro cambia la lista entera, así que avisar de todo el cambio es lo más sencillo.
    @SuppressLint("NotifyDataSetChanged")
    public void actualizarPartidas(List<Partida> nuevasPartidas) {
        this.listaPartidas = (nuevasPartidas != null) ? nuevasPartidas : new ArrayList<>();
        notifyDataSetChanged();
    }

    // Crea la vista de una tarjeta a partir del diseño item_partida.xml.
    @NonNull
    @Override
    public PartidaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_partida, parent, false);
        return new PartidaViewHolder(v);
    }

    // Rellena la tarjeta con los datos de la partida y prepara sus botones.
    @Override
    public void onBindViewHolder(@NonNull PartidaViewHolder holder, int position) {
        Partida p = listaPartidas.get(position);

        // Cargamos los datos de la partida
        holder.tvNombreJuego.setText(p.getJuego());
        holder.tvHoraPartida.setText(context.getString(R.string.formato_hora, p.getHora()));
        holder.tvLugarPartida.setText(context.getString(R.string.formato_lugar, p.getLugar()));
        holder.tvPlazasLibres.setText(context.getString(R.string.formato_plazas_libres, p.getPlazasLibres()));
        holder.tvCreadorPartida.setText(context.getString(R.string.formato_creador, p.getCreador()));

        // Control visual del boton de apuntarse segun las plazas
        if (p.estaLlena()) {
            holder.btnMeApunto.setText(context.getString(R.string.estado_partida_llena));
            holder.btnMeApunto.setEnabled(false);
            holder.btnMeApunto.setBackgroundTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(context, R.color.color_gris_lleno)));
            // Texto blanco también desactivado: si no, Android lo pone gris claro y no se lee
            holder.btnMeApunto.setTextColor(ContextCompat.getColor(context, R.color.color_texto_blanco));
        } else {
            holder.btnMeApunto.setText(context.getString(R.string.accion_me_apunto));
            holder.btnMeApunto.setEnabled(true);
            holder.btnMeApunto.setBackgroundTintList(ColorStateList.valueOf(
                    ContextCompat.getColor(context, R.color.color_verde_apunto)));
        }

        // Accion al pulsar me apunto
        holder.btnMeApunto.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMeApuntoClick(p, holder.getAdapterPosition());
            }
        });

        // Accion al pulsar el boton de voz
        holder.btnAltavoz.setOnClickListener(v -> {
            if (listener != null) {
                listener.onAltavozClick(p);
            }
        });
    }

    // Devuelve cuántas partidas hay en la lista.
    @Override
    public int getItemCount() {
        return listaPartidas.size();
    }

    // ViewHolder con los componentes de cada tarjeta
    public static class PartidaViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreJuego, tvHoraPartida, tvLugarPartida, tvPlazasLibres, tvCreadorPartida;
        Button btnAltavoz, btnMeApunto;

        // Busca en la tarjeta cada texto y botón por su id.
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
