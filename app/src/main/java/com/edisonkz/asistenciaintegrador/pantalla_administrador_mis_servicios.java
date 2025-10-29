package com.edisonkz.asistenciaintegrador;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class pantalla_administrador_mis_servicios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_administrador_mis_servicios);

        // Redirecciones de los servicios
        FrameLayout areaColaboradores = findViewById(R.id.accionGuardia1);
        FrameLayout areaHorarios = findViewById(R.id.accionGuardia2);
        FrameLayout areaAlertas = findViewById(R.id.accionGuardia3);
        FrameLayout areaReportes = findViewById(R.id.accionGuardia4);

        areaColaboradores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Toast.makeText(pantalla_administrador_mis_servicios.this, "Abriendo lista de colaboradores...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(pantalla_administrador_mis_servicios.this, pantalla_administrador_lista_colaboradores.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(pantalla_administrador_mis_servicios.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        areaHorarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(pantalla_administrador_mis_servicios.this, pantalla_administrador_asignar_horarios.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(pantalla_administrador_mis_servicios.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        areaAlertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(pantalla_administrador_mis_servicios.this, pantalla_administrador_configurar_alertas.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(pantalla_administrador_mis_servicios.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        areaReportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(pantalla_administrador_mis_servicios.this, pantalla_administrador_informes_de_asistencia.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(pantalla_administrador_mis_servicios.this, "Error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

        // Footer: redirigir a perfil al hacer click en el icono de usuario
        LinearLayout btnPerfil = findViewById(R.id.btn_perfil);
        btnPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(this, pantalla_mi_perfil.class);
            startActivity(intent);
        });

    }

}
