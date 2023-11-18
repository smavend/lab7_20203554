package com.example.lab7_20203554;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.lab7_20203554.databinding.ActivityMainBinding;
import com.example.lab7_20203554.entity.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String userUid;
    Usuario usuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.button.setOnClickListener(view -> {
            binding.button.setEnabled(false);
            String correo = binding.textCorreo.getText().toString();
            String contrasena = binding.textContrase.getText().toString();
            if (binding.textCorreo.getText() == null || binding.textContrase.getText() == null){
                Snackbar.make(binding.getRoot(), "Debe ingresar todos los campos", Snackbar.LENGTH_SHORT).show();
            }
            else {
                if (correo.trim().equals("") || contrasena.equals("")){
                    Toast.makeText(this, "Debe ingresar todos los campos", Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.signInWithEmailAndPassword(correo, contrasena)
                            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // Sign in success, update UI with the signed-in user's information
                                        obtenerUserData();
                                    } else {
                                        // If sign in fails, display a message to the user.
                                        Snackbar.make(binding.getRoot(), "Las credenciales son incorrectas.", Snackbar.LENGTH_SHORT)
                                                .show();
                                        Log.d("msg-test", "credenciales incorrectas");
                                    }
                                }
                            });
                }
            }
            binding.button.setEnabled(true);
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {

            userUid = currentUser.getUid();

            try (FileInputStream fileInputStream = openFileInput("userData");
                 FileReader fileReader = new FileReader(fileInputStream.getFD());
                 BufferedReader bufferedReader = new BufferedReader(fileReader)){

                String jsonData = bufferedReader.readLine();
                Gson gson = new Gson();
                Usuario usuarioLogged = gson.fromJson(jsonData, Usuario.class);

                redirigirSegunRol(usuarioLogged);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private void obtenerUserData() {
            userUid = mAuth.getCurrentUser().getUid();
            db.collection("usuarios").document(userUid)
                    .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()){
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d("msg-test", "busqueda ok");
                            usuario = document.toObject(Usuario.class);
                            if (usuario.getEstado().equals("activo")){

                                guardarUserEnMemoria();
                                redirigirSegunRol(usuario);

                            } else {
                                Snackbar.make(binding.getRoot(), "El usuario no es activo", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Snackbar.make(binding.getRoot(), "El usuario no existe", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Snackbar.make(binding.getRoot(), "Error en búsqueda", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    private void redirigirSegunRol(Usuario usuario) {
        Intent intent = null;
        String rol = usuario.getRol();
        switch (rol) {
            case "gestor":
                intent = new Intent(MainActivity.this, GestorMainActivity.class);
                break;
            case "cliente":
                intent = new Intent(MainActivity.this, ClienteMainActivity.class);
                break;
        }
        startActivity(intent);
        finish();
    }

    private void guardarUserEnMemoria() {
        Gson gson = new Gson();
        String usuarioJson = gson.toJson(usuario);
        try (FileOutputStream fileOutputStream = openFileOutput("userData", Context.MODE_PRIVATE);
             FileWriter fileWriter = new FileWriter(fileOutputStream.getFD())) {
            fileWriter.write(usuarioJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}