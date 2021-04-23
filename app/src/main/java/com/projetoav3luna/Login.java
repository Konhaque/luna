package com.projetoav3luna;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.projetoav3luna.db.Usuario;

public class Login extends AppCompatActivity {
    private EditText email;
    private EditText senha;
    private Button entrar;
    private Button registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        iniciarObjetos();
        setEntrar();
        setRegistrar();
    }

    private void iniciarObjetos(){
        email = (EditText) findViewById(R.id.email);
        senha = (EditText) findViewById(R.id.senha);
        entrar = (Button) findViewById(R.id.entrar);
        registrar = (Button) findViewById(R.id.registro);
    }

    private void setEntrar(){
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                LayoutInflater inflater = getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.activity_main,null));
                builder.setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
                entrar(dialog);
            }
        });
    }

    private void setRegistrar(){
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                LayoutInflater inflater = getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.activity_main,null));
                builder.setCancelable(false);
                AlertDialog dialog = builder.create();
                dialog.show();
                salvarNoBanco(dialog);
            }
        });
    }

    private void salvarNoBanco(final AlertDialog dialog) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email.getText().toString());
        usuario.setSenha(senha.getText().toString());
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Usuario");
        reference.child(reference.push().getKey()).setValue(usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                dialog.dismiss();
            }
        });
    }

    private void entrar(final AlertDialog dialog){
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Usuario");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("email").getValue().toString().equalsIgnoreCase(email.getText().toString())
                            && dataSnapshot.child("senha").getValue().toString().equals(senha.getText().toString())){
                        AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                        builder.setMessage("Sucesso!!");
                        builder.setNeutralButton("OKKK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            }
                        });
                        dialog.dismiss();
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}