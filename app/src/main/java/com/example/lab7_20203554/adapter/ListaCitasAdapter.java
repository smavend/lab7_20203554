package com.example.lab7_20203554.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lab7_20203554.R;
import com.example.lab7_20203554.entity.Cita;

import java.util.List;

public class ListaCitasAdapter extends RecyclerView.Adapter<ListaCitasAdapter.CitasViewHolder>{
    private List<Cita> listaCitas;
    private Context context;

    @NonNull
    @Override
    public CitasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_rv, parent, false);
        return new CitasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CitasViewHolder holder, int position) {
        Cita c = listaCitas.get(position);
        holder.cita = c;

        TextView textView = holder.itemView.findViewById(R.id.textView2);
        textView.setText(c.getHora()+" - "+c.getNombre()+" - "+c.getUsuario());
    }

    @Override
    public int getItemCount() {
        return listaCitas.size();
    }

    public class CitasViewHolder extends RecyclerView.ViewHolder{
        Cita cita;
        public CitasViewHolder(@NonNull View itemview){
            super(itemview);
        }
    }

    public List<Cita> getListaCitas() {
        return listaCitas;
    }

    public void setListaCitas(List<Cita> listaCitas) {
        this.listaCitas = listaCitas;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
