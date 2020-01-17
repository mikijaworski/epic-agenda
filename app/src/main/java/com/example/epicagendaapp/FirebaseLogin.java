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

public class FirebaseLogin extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private FirebaseAuth auth;
    private ProgressBar progressBar;
    private Button btnSignup, btnLogin, btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Obtener instancia de autenticación de Firebase
        auth = FirebaseAuth.getInstance();
        // Si la instancia es distinta de null
        if (auth.getCurrentUser() != null) {
            startActivity(new Intent(FirebaseLogin.this, MainActivity.class));
            finish();
        }
        // Establecer la vista ahora
        setContentView(R.layout.activity_firebase_login);
        // Obtener la referencia de los controles
        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        btnSignup = (Button) findViewById(R.id.btn_signup);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        // Obtener instancia de autenticación de Firebase
        auth = FirebaseAuth.getInstance();
        // Clic del botón registrar en la aplicación
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirebaseLogin.this, SignUpActivity.class));
            }
        });
        // Clic en el boton para resetear la contraseña del usuario
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirebaseLogin.this, ForgotPasswordActivity.class));
            }
        });
        // Clic para acceder
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener valores de los editText
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();
                // Validar si el logín a sido ingresado
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "¡Introducir la dirección de correo electrónico!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Validar si se ingreso la constraseña
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "¡Introducir la contraseña!", Toast.LENGTH_SHORT).show();
                    return;
                }
                // ProgressBar visible
                progressBar.setVisibility(View.VISIBLE);
                // Autenticar usuario existe
                auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(FirebaseLogin.this, new OnCompleteListener() {
                            @Override
                            public void onComplete(@NonNull Task task) {
                                // Si el inicio de sesión falla, muestre un mensaje al usuario. Si el inicio de sesión tiene éxito
                                // el Auth de estado de autenticación será notificado y la lógica para manejar el
                                // usuario registrado puede ser manejado en el Auth.
                                progressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()) {
                                    // Ocurrio un problema
                                    if (password.length() < 6) {
                                        inputPassword.setError(getString(R.string.minimum_password));
                                    } else {
                                        Toast.makeText(FirebaseLogin.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Intent intent = new Intent(FirebaseLogin.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
            }
        });
    }
}
