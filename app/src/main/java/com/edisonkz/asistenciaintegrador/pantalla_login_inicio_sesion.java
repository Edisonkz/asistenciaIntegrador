package com.edisonkz.asistenciaintegrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class pantalla_login_inicio_sesion extends AppCompatActivity {

    private TextView tvOlvidePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_login_inicio_sesion);

        // Inicializar vistas
        initViews();
        
        // Configurar listeners
        setupListeners();
    }

    private void initViews() {
        tvOlvidePassword = findViewById(R.id.tv_olvide_password);
    }

    private void setupListeners() {
        // Listener para el enlace "¿Olvidaste tu contraseña?"
        tvOlvidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a la pantalla de recuperar contraseña
                Intent intent = new Intent(pantalla_login_inicio_sesion.this, pantalla_login_recuperar.class);
                startActivity(intent);
            }
        });
    }
}