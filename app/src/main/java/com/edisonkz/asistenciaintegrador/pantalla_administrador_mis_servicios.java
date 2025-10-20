package com.edisonkz.asistenciaintegrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class pantalla_administrador_mis_servicios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_administrador_mis_servicios);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setupCardListeners();
        setupFooterListeners();
    }

    private void setupCardListeners() {
        CardView cardColaboradores = findViewById(R.id.card_colaboradores);
        CardView cardHorarios = findViewById(R.id.card_horarios);
        CardView cardAlertas = findViewById(R.id.card_alertas);
        CardView cardReportes = findViewById(R.id.card_reportes);

        if (cardColaboradores != null) {
            cardColaboradores.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent(this, MainRegistrarColaborador.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "No se pudo abrir la pantalla de colaboradores", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (cardHorarios != null) {
            cardHorarios.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent(this, activity_pantalla_administrador_asignar_horarios.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "No se pudo abrir la pantalla de horarios", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (cardAlertas != null) {
            cardAlertas.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent(this, MainConfigurarAlertas.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "No se pudo abrir la pantalla de alertas", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (cardReportes != null) {
            cardReportes.setOnClickListener(v -> {
                Toast.makeText(this, "Módulo de reportes próximamente", Toast.LENGTH_SHORT).show();
            });
        }
    }

    private void setupFooterListeners() {
        View btnHome = findViewById(R.id.btn_home);
        if (btnHome != null) {
            btnHome.setOnClickListener(v -> {
                // Puedes regresar al inicio o recargar esta pantalla
            });
        }

        FloatingActionButton btnAddUser = findViewById(R.id.btn_add_user);
        if (btnAddUser != null) {
            btnAddUser.setOnClickListener(v -> {
                try {
                    Intent intent = new Intent(this, MainRegistrarColaborador.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, "No se pudo abrir la pantalla de agregar colaborador", Toast.LENGTH_SHORT).show();
                }
            });
        }

        View btnPerfil = findViewById(R.id.btn_perfil);
        if (btnPerfil != null) {
            btnPerfil.setOnClickListener(v -> {
                // Implementa la redirección a perfil si tienes esa pantalla
            });
        }
    }
}