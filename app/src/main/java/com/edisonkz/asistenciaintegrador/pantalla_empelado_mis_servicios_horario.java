package com.edisonkz.asistenciaintegrador;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class pantalla_empelado_mis_servicios_horario extends AppCompatActivity {
    
    private Button btnSemana, btnMes, btnCambioTurno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_empelado_mis_servicios_horario);
        
        // Agregar botón de retroceso
        findViewById(R.id.back_button).setOnClickListener(v -> onBackPressed());
        
        // Inicializar botones
        btnSemana = findViewById(R.id.btn_semana);
        btnMes = findViewById(R.id.btn_mes);
        btnCambioTurno = findViewById(R.id.btn_cambio_turno);

        // Click listener para botón de semana
        btnSemana.setOnClickListener(v -> {
            btnSemana.setBackgroundResource(R.drawable.btn_rounded_blue);
            btnSemana.setTextColor(getResources().getColor(android.R.color.white));
            btnMes.setBackgroundResource(R.drawable.btn_rounded_gray);
            btnMes.setTextColor(getResources().getColor(android.R.color.darker_gray));
        });

        // Click listener para botón de mes
        btnMes.setOnClickListener(v -> {
            btnMes.setBackgroundResource(R.drawable.btn_rounded_blue);
            btnMes.setTextColor(getResources().getColor(android.R.color.white));
            btnSemana.setBackgroundResource(R.drawable.btn_rounded_gray);
            btnSemana.setTextColor(getResources().getColor(android.R.color.darker_gray));
        });
    }
}
