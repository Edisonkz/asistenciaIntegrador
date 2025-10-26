package com.edisonkz.asistenciaintegrador;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class Comunicados extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_servicios_empleado_comunicados);

        findViewById(R.id.back_button).setOnClickListener(v -> onBackPressed());
    }
}
