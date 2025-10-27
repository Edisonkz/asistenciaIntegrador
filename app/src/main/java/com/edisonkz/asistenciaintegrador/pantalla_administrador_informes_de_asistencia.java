package com.edisonkz.asistenciaintegrador;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class pantalla_administrador_informes_de_asistencia extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_administrador_informes_de_asistencia);

        GridLayout grid = findViewById(R.id.grid_asistencia);
        String[] estados = {
                "NA","NA","P","P","P","P","NA",
                "P","P","P","P","P","P","NA",
                "P","T","P","P","P","P","NA",
                "P","P","T","P","P","P","NA",
                "NA","NA","NA","NA","NA","NA","NA"
        };

// Obtener el ancho disponible
        grid.post(() -> {
            int totalWidth = grid.getWidth();
            int cellWidth = totalWidth / 7; // 7 columnas

            for (String estado : estados) {
                TextView tv = new TextView(this);
                tv.setText(estado);
                tv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                tv.setTextSize(14f);
                tv.setGravity(android.view.Gravity.CENTER);

                // Configurar LayoutParams con ancho fijo
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = cellWidth;
                params.height = cellWidth; // Hacer las celdas cuadradas
                params.setMargins(2, 2, 2, 2); // Pequeño margen entre celdas
                tv.setLayoutParams(params);

                // Configurar background según el estado
                switch (estado) {
                    case "P":
                        tv.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_estado_asistio));
                        tv.setTextColor(Color.parseColor("#1A7459"));
                        break;
                    case "T":
                        tv.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_estado_tardanza));
                        tv.setTextColor(Color.parseColor("#9F7C07"));
                        break;
                    default: // "NA"
                        tv.setBackground(ContextCompat.getDrawable(this, R.drawable.bg_estado_falto));
                        tv.setTextColor(Color.DKGRAY);
                        break;
                }

                grid.addView(tv);
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}