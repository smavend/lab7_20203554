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
import com.example.lab7_20203554.entity.Salon;

import java.util.List;

public class ListaSalonesAdapter extends RecyclerView.Adapter<ListaSalonesAdapter.SalonViewHolder>{
    private List<Salon> listaSalones;
    private Context context;

    @NonNull
    @Override
    public SalonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_salones, parent, false);
        return new SalonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SalonViewHolder holder, int position) {
        Salon s = listaSalones.get(position);
        holder.salon = s;

        TextView textView = holder.itemView.findViewById(R.id.textView5);
        textView.setText(s.getNombre());
    }

    @Override
    public int getItemCount() {
        return listaSalones.size();
    }

    public class SalonViewHolder extends RecyclerView.ViewHolder{
        Salon salon;
        public SalonViewHolder(@NonNull View itemView){
            super(itemView);
        }
    }

    public List<Salon> getListaSalones() {
        return listaSalones;
    }

    public void setListaSalones(List<Salon> listaSalones) {
        this.listaSalones = listaSalones;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
