package com.edisonkz.asistenciaintegrador;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class pantalla_empleado_mis_servicios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_empleado_mis_servicios);

        // Configurar click listeners para las tarjetas
        CardView cardHorarios = findViewById(R.id.card_horario);
        CardView cardComunicados = findViewById(R.id.card_comunicados);
        CardView cardPreguntas = findViewById(R.id.card_preguntas_frecuentes);

        cardHorarios.setOnClickListener(v -> {
            Intent intent = new Intent(this, pantalla_empelado_mis_servicios_horario.class);
            startActivity(intent);
        });

        cardComunicados.setOnClickListener(v -> {
            Intent intent = new Intent(this, pantalla_empleado_mis_servicios_comunicados.class);
            startActivity(intent);
        });

        cardPreguntas.setOnClickListener(v -> {
            Intent intent = new Intent(this, PreguntasFrecuentes.class);
            startActivity(intent);
        });
    }
}