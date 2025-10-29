package com.edisonkz.asistenciaintegrador;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class pantalla_empleado_mis_servicios_preguntas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_empleado_mis_servicios_preguntas);

        findViewById(R.id.btnBack).setOnClickListener(v -> onBackPressed());
    }
}
