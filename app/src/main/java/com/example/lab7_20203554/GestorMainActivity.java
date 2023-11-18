package com.example.lab7_20203554;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.example.lab7_20203554.adapter.ListaCitasAdapter;
import com.example.lab7_20203554.databinding.ActivityGestorMainBinding;
import com.example.lab7_20203554.entity.Cita;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class GestorMainActivity extends AppCompatActivity {
    ActivityGestorMainBinding binding;
    private ArrayList<Cita> listaCitas = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ListaCitasAdapter adapter = new ListaCitasAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGestorMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.titulo.setText("Gestión de salón de belleza - Telebarber");

        db.collection("citas")
                .orderBy("hora", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Cita cita = document.toObject(Cita.class);
                            listaCitas.add(cita);
                            Log.d("msg-test", "cita: " + cita.getNombre());
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.d("msg-test", "error al cargar fotos");
                    }
                });

        adapter.setContext(GestorMainActivity.this);
        adapter.setListaCitas(listaCitas);

        binding.rvCitas.setAdapter(adapter);
        binding.rvCitas.setLayoutManager(new LinearLayoutManager(GestorMainActivity.this));
    }
}