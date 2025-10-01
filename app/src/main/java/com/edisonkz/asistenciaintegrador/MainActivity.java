package com.edisonkz.asistenciaintegrador;

import android.content.Intent;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Navegar a la pantalla de información después de un breve delay
        new android.os.Handler().postDelayed(() -> {
            Intent intent = new Intent(MainActivity.this, pantalla_de_informacion.class);
            startActivity(intent);
            finish();
        }, 500); // Medio segundo de delay
    }
}
