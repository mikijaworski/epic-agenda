package com.example.epicagendaapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.content.Intent;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.FirebaseAuth;

public class SignUpActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btnSignIn, btnSignUp, btnResetPassword;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        // Obtener instancia de autenticación de Firebase
        auth = FirebaseAuth.getInstance();
        // Obtener referencias de objectos
        btnSignIn = (Button) findViewById(R.id.sign_in_button);
        btnSignUp = (Button) findViewById(R.id.sign_up_button);
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnResetPassword = findViewById(R.id.btn_reset_password);
        // Clic para ir a la clase de resetear contraseña
        btnResetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, ForgotPasswordActivity.class));
            }
        });
        // Clic para ir a la clase de login
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        // Click para registrarse
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener valores de los editText
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();
                // Validar si se ingreso el correo electronico
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "¡Introducir la dirección de correo electrónico!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Validar si se ingreso la contraseña
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "¡Introducir la contraseña!", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Contraseña demasiado corta, ingrese un mínimo de 6 caracteres.", Toast.LENGTH_SHORT).show();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                // Crear el usuario
                auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                Toast.makeText(SignUpActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                // Mostrar mensaje si la Authenticacion fallo
                                if (!task.isSuccessful()) {
                                    Toast.makeText(SignUpActivity.this, "Autenticación fallida" + task.getException(),
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    startActivity(new Intent(SignUpActivity.this, FirebaseMain.class));
                                    finish();
                                }
                            }
                        });

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}

