package com.edisonkz.asistenciaintegrador;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class pantalla_carga extends AppCompatActivity {

    // Duración de la pantalla de carga (en milisegundos)
    private static final int SPLASH_DURATION = 3000; // 3 segundos
    
    private TextView tvLoading;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_carga);
        
        initViews();
        startLoadingProcess();
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    
    private void initViews() {
        tvLoading = findViewById(R.id.tv_loading);
        handler = new Handler(Looper.getMainLooper());
    }
    
    private void startLoadingProcess() {
        // Simular proceso de carga con diferentes mensajes
        handler.postDelayed(() -> tvLoading.setText("Iniciando aplicación..."), 500);
        handler.postDelayed(() -> tvLoading.setText("Cargando recursos..."), 1200);
        handler.postDelayed(() -> tvLoading.setText("Preparando interfaz..."), 1800);
        handler.postDelayed(() -> tvLoading.setText("¡Casi listo!"), 2400);
        
        // Navegar a la pantalla de información después del tiempo especificado
        handler.postDelayed(this::navigateToInformation, SPLASH_DURATION);
    }
    
    private void navigateToInformation() {
        Intent intent = new Intent(pantalla_carga.this, pantalla_de_informacion.class);
        startActivity(intent);
        finish(); // Cerrar la activity actual para que el usuario no pueda volver
        
        // Animación de transición suave
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Limpiar el handler para evitar memory leaks
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
    }
}