package com.edisonkz.asistenciaintegrador;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class pantalla_guardia_inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_guardia_inicio);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Redirigir a lista de asistencia al hacer click en "Ver Lista"
        findViewById(R.id.ver_lista_empleados).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(pantalla_guardia_inicio.this, pantalla_guardia_lista_de_asistencia.class);
                startActivity(intent);
            }
        });

        // Redirigir a perfil al hacer click en el icono de usuario en el footer
        LinearLayout btnPerfil = findViewById(R.id.btn_perfil);
        btnPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(this, pantalla_mi_perfil.class);
            startActivity(intent);
        });
    }
}
