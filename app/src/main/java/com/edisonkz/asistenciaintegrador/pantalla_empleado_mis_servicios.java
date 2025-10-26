package com.edisonkz.asistenciaintegrador;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.widget.LinearLayout;

import com.edisonkz.asistenciaintegrador.api.ApiServiceQr;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

        // Footer: redirigir a perfil al hacer click en el icono de usuario
        LinearLayout btnPerfil = findViewById(R.id.btn_perfil);
        btnPerfil.setOnClickListener(v -> {
            Intent intent = new Intent(this, pantalla_mi_perfil.class);
            startActivity(intent);
        });

        // Footer: mostrar QR al hacer click en el icono QR
        FloatingActionButton btnQr = findViewById(R.id.btn_qr);
        btnQr.setOnClickListener(v -> {
            // TODO: Reemplaza este valor por el id real del usuario logueado
            int idUsuario = 1; // <-- Usa aquí el id real del usuario autenticado
            String geolocalizacion = "0,0"; // o la real si tienes permisos
            String horarioInicio = "2024-06-01T08:00:00"; // puedes usar la hora actual
            String horarioFin = "2024-06-01T17:00:00";   // puedes calcular según lógica

            new ApiServiceQr().generarQrAsistencia(idUsuario, geolocalizacion, horarioInicio, horarioFin, new ApiServiceQr.QrCallback() {
                @Override
                public void onSuccess(String qrToken) {
                    Intent intent = new Intent(pantalla_empleado_mis_servicios.this, pantalla_qr_generado.class);
                    intent.putExtra("qr_token", qrToken);
                    startActivity(intent);
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(pantalla_empleado_mis_servicios.this, error, Toast.LENGTH_LONG).show();
                }
            });
        });
    }
}
