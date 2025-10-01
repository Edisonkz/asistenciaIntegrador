package com.edisonkz.asistenciaintegrador;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class mis_servicios extends AppCompatActivity {

    private CardView cardHorario, cardComunicados, cardPreguntas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_mis_servicios);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar las CardViews
        cardHorario = findViewById(R.id.card_horario);
        cardComunicados = findViewById(R.id.card_comunicados);
        cardPreguntas = findViewById(R.id.card_preguntas_frecuentes);

        // Click listener para Horarios
        cardHorario.setOnClickListener(view -> {
            Intent intent = new Intent(mis_servicios.this, figure_10.class);
            startActivity(intent);
        });

        // Click listener para Comunicados
        cardComunicados.setOnClickListener(view -> {
            Intent intent = new Intent(mis_servicios.this, Comunicados.class);
            startActivity(intent);
        });

        // Click listener para Preguntas Frecuentes
        cardPreguntas.setOnClickListener(view -> {
            Intent intent = new Intent(mis_servicios.this, PreguntasFrecuentes.class);
            startActivity(intent);
        });
    }
}