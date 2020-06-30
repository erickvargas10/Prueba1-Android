package com.example.prueba;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class Register extends AppCompatActivity {
    EditText autNombre, autEmail, autClave, autNum;
    Button btn1;
    ListView listV;
    FirebaseAuth fAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        autNombre=findViewById(R.id.autNombre);
        autClave=findViewById(R.id.autClave);
        autEmail=findViewById(R.id.autEmail);
        autNum=findViewById(R.id.autNum);
        btn1=findViewById(R.id.btn);
        listV=findViewById(R.id.listV);

        fAuth=FirebaseAuth.getInstance();
        if (fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }


        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String nombre =autNombre.getText().toString().trim();
                String email =autEmail.getText().toString().trim();
                String password =autClave.getText().toString().trim();


                Persona p = new Persona();
                p.setUid(UUID.randomUUID().toString());
                p.setNombre(nombre);
                databaseReference.child("Persona").child(p.getUid()).setValue(p);



                if(TextUtils.isEmpty(email)){

                    autEmail.setError("Por favor ingrese su correo");
                    return;
                }


                if(TextUtils.isEmpty(password)){

                    autClave.setError("Por favor ingrese su clave");
                    return;
                }

                if (password.length()<8){
                    autClave.setError("La clave debe ser más larga");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this, "usuario creado exitosamente", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(Register.this, "No se pudo crear el usuario", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });
    }


}