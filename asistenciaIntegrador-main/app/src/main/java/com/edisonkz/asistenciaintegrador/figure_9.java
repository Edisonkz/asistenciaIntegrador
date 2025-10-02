package com.edisonkz.asistenciaintegrador;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class figure_9 extends AppCompatActivity {

    private CardView cardH, cardC, cardPF;
    private Button btn_next_H, btn_next_C, btn_next_PF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_figure9);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Referencias a views
        cardH = findViewById(R.id.card_horario);
        cardC = findViewById(R.id.card_comunid);
        cardPF = findViewById(R.id.card_preg_frec);
        btn_next_H = findViewById(R.id.button_cont_horario);
        btn_next_C = findViewById(R.id.button_cont_comunidad);
        btn_next_PF = findViewById(R.id.button_cont_preg_frec);

        // Ocultamos todos los botones al inicio
        btn_next_H.setVisibility(View.GONE);
        btn_next_C.setVisibility(View.GONE);
        btn_next_PF.setVisibility(View.GONE);

        // Listener común para las cards
        View.OnClickListener cardClickListener = v -> {
            // Ocultamos todos los botones primero
            btn_next_H.setVisibility(View.GONE);
            btn_next_C.setVisibility(View.GONE);
            btn_next_PF.setVisibility(View.GONE);

            // Mostramos solo el botón correspondiente a la card clickeada
            if (v.getId() == R.id.card_horario) {
                btn_next_H.setVisibility(View.VISIBLE);
            } else if (v.getId() == R.id.card_comunid) {
                btn_next_C.setVisibility(View.VISIBLE);
            } else if (v.getId() == R.id.card_preg_frec) {
                btn_next_PF.setVisibility(View.VISIBLE);
            }
        };

        // Asignamos el listener a las cards
        cardH.setOnClickListener(cardClickListener);
        cardC.setOnClickListener(cardClickListener);
        cardPF.setOnClickListener(cardClickListener);
    }
}