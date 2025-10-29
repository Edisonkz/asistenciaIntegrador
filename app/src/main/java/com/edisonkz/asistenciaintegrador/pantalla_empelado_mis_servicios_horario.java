package com.edisonkz.asistenciaintegrador;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import android.content.SharedPreferences;
import android.widget.TextView;
import com.edisonkz.asistenciaintegrador.api.ApiServiceEmpleado;
import org.json.JSONArray;
import org.json.JSONObject;

public class pantalla_empelado_mis_servicios_horario extends AppCompatActivity {

    private Button btnSemana, btnMes, btnCambioTurno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_empelado_mis_servicios_horario);

        // Agregar botón de retroceso
        findViewById(R.id.btnBack).setOnClickListener(v -> onBackPressed());

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

        // Obtener el id del usuario logueado
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        String usuarioId = prefs.getString("usuario_id", null);

        if (usuarioId != null) {
            new ApiServiceEmpleado().obtenerHorarioSemanal(usuarioId, new ApiServiceEmpleado.HorarioCallback() {
                @Override
                public void onSuccess(JSONArray horario) {
                    runOnUiThread(() -> {
                        try {
                            String[] dias = {"lunes", "martes", "miercoles", "jueves", "viernes", "sabado", "domingo"};
                            for (int idx = 0; idx < dias.length; idx++) {
                                String diaBuscado = dias[idx];
                                JSONObject dia = null;
                                for (int j = 0; j < horario.length(); j++) {
                                    JSONObject obj = horario.getJSONObject(j);
                                    // Solución: compara sin tildes y en minúsculas
                                    String jsonDia = obj.optString("dia_semana", "").toLowerCase().replace("í", "i").replace("é", "e").replace("á", "a").replace("ó", "o").replace("ú", "u");
                                    String buscado = diaBuscado.toLowerCase();
                                    if (buscado.equals(jsonDia)) {
                                        dia = obj;
                                        break;
                                    }
                                }
                                int cardIdx = idx + 1;
                                int resDia = getResources().getIdentifier("texto_dia_" + cardIdx, "id", getPackageName());
                                int resHora = getResources().getIdentifier("texto_hora_" + cardIdx, "id", getPackageName());
                                int resEstado = getResources().getIdentifier("btn_estado_" + cardIdx, "id", getPackageName());

                                TextView tvDia = findViewById(resDia);
                                TextView tvHora = findViewById(resHora);
                                TextView tvEstado = findViewById(resEstado);

                                String labelDia = diaBuscado.substring(0, 1).toUpperCase() + diaBuscado.substring(1);
                                tvDia.setText(labelDia);

                                if (dia != null) {
                                    String horaInicio = dia.isNull("hora_inicio") ? "" : dia.optString("hora_inicio", "");
                                    String horaFin = dia.isNull("hora_fin") ? "" : dia.optString("hora_fin", "");
                                    String turno = dia.optString("turno", "");

                                    if ("libre".equalsIgnoreCase(turno)) {
                                        tvHora.setText("Libre");
                                        tvEstado.setText("Libre");
                                    } else if (!horaInicio.isEmpty() && !horaFin.isEmpty()) {
                                        String hora = horaInicio + " - " + horaFin + " (" + turno + ")";
                                        tvHora.setText(hora);
                                        tvEstado.setText("Asignado");
                                    } else {
                                        tvHora.setText(turno);
                                        tvEstado.setText("Asignado");
                                    }
                                } else {
                                    tvHora.setText("Sin horario");
                                    tvEstado.setText("Sin horario");
                                }
                            }
                        } catch (Exception e) {
                            // Puedes loguear el error si necesitas debug
                        }
                    });
                }

                @Override
                public void onError(String error) {
                    runOnUiThread(() -> {
                        // Puedes mostrar un Toast o mensaje de error
                    });
                }
            });
        }
    }
}
