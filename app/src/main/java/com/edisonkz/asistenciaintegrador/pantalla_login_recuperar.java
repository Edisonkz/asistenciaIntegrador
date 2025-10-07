package com.edisonkz.asistenciaintegrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;

public class pantalla_login_recuperar extends AppCompatActivity {

    private TextInputEditText etEmailRecover;
    private TextView btnContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_login_recuperar);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar vistas
        initViews();

        // Configurar listeners
        setupListeners();
    }

    private void initViews() {
        etEmailRecover = findViewById(R.id.et_email_recover);
        btnContinuar = findViewById(R.id.btn_continuar);
    }

    private void setupListeners() {
        // Listener para el botón continuar
        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndContinue();
            }
        });
    }

    private void validateAndContinue() {
        String email = etEmailRecover.getText().toString().trim();

        // Validar email vacío
        if (email.isEmpty()) {
            etEmailRecover.setError("Ingrese el correo electrónico");
            etEmailRecover.requestFocus();
            return;
        }

        // Validar formato de email básico
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmailRecover.setError("Ingrese un correo válido");
            etEmailRecover.requestFocus();
            return;
        }

        // Mostrar mensaje de éxito y redirigir
        Toast.makeText(this, "Enviando correo de recuperación...", Toast.LENGTH_SHORT).show();

        // Redirigir a pantalla de verificar correo
        Intent intent = new Intent(pantalla_login_recuperar.this, pantalla_login_verificar_correo.class);
        intent.putExtra("email", email); // Pasar el email a la siguiente pantalla
        startActivity(intent);
        finish();
    }
}