package com.example.lab7_20203554;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.util.Log;

import com.example.lab7_20203554.adapter.ListaCitasAdapter;
import com.example.lab7_20203554.adapter.ListaSalonesAdapter;
import com.example.lab7_20203554.databinding.ActivityClienteMainBinding;
import com.example.lab7_20203554.databinding.ActivityGestorMainBinding;
import com.example.lab7_20203554.entity.Cita;
import com.example.lab7_20203554.entity.Salon;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class ClienteMainActivity extends AppCompatActivity {
    ActivityClienteMainBinding binding;
    private ArrayList<Salon> listaSalon = new ArrayList<>();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ListaSalonesAdapter adapter = new ListaSalonesAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityClienteMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        db.collection("salones")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Salon salon = document.toObject(Salon.class);
                            listaSalon.add(salon);
                            Log.d("msg-test", "cita: " + salon.getNombre());
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Log.d("msg-test", "error al cargar fotos");
                    }
                });

        adapter.setContext(ClienteMainActivity.this);
        adapter.setListaSalones(listaSalon);

        binding.rvSalones.setAdapter(adapter);
        binding.rvSalones.setLayoutManager(new LinearLayoutManager(ClienteMainActivity.this));
    }
}