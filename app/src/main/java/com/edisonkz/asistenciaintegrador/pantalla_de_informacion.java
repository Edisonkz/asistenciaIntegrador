package com.edisonkz.asistenciaintegrador;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class pantalla_de_informacion extends AppCompatActivity {

    private ViewPager2 viewPager2;
    private InformacionPagerAdapter adapter;
    private LinearLayout indicatorsContainer;
    private TextView tvSaltar;
    private List<InformacionFragment> fragments;
    private int currentPosition = 0;
    private Handler autoNavigationHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pantalla_de_informacion);
        
        initViews();
        setupViewPager();
        setupIndicators();
        setupClickListeners();
        
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void initViews() {
        viewPager2 = findViewById(R.id.viewPager2);
        indicatorsContainer = findViewById(R.id.ll_indicators);
        tvSaltar = findViewById(R.id.tv_saltar);
        autoNavigationHandler = new Handler(Looper.getMainLooper());
    }

    private void setupViewPager() {
        fragments = new ArrayList<>();
        
        // Página 1
        fragments.add(InformacionFragment.newInstance(
                "OLVÍDATE\nDE LAS FILAS",
                "Registra tu ingreso diario en segundos con\nreconocimiento facial y código QR dinámico",
                R.drawable.informacion_avatar1
        ));
        
        // Página 2
        fragments.add(InformacionFragment.newInstance(
                "PUNTUALIDAD\nSIN ESTRÉS",
                "Recibe alertas y notificaciones, mantén tu\nasistencia al día, sin papeleo ni errores",
                R.drawable.informacion_avatar2
        ));
        
        // Página 3
        fragments.add(InformacionFragment.newInstance(
                "SEGURIDAD Y\nCONFIANZA",
                "Tu identidad está protegida con la última\ntecnología en validación biométrica",
                R.drawable.informacion_avatar3
        ));

        adapter = new InformacionPagerAdapter(this, fragments);
        viewPager2.setAdapter(adapter);
        
        // Callback para detectar cambios de página
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                currentPosition = position;
                updateIndicators(position);
                
                // Si llegó a la última página, navegar automáticamente después de 2 segundos
                if (position == fragments.size() - 1) {
                    autoNavigationHandler.postDelayed(() -> {
                        finishIntroduction();
                    }, 2000); // 2 segundos de espera
                } else {
                    // Cancelar navegación automática si no está en la última página
                    autoNavigationHandler.removeCallbacksAndMessages(null);
                }
            }
        });
    }

    private void setupIndicators() {
        for (int i = 0; i < fragments.size(); i++) {
            View indicator = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(24, 24);
            params.setMargins(8, 0, 8, 0);
            indicator.setLayoutParams(params);
            indicator.setBackgroundResource(R.drawable.indicator_inactive);
            indicatorsContainer.addView(indicator);
        }
        
        // Activar el primer indicador
        if (indicatorsContainer.getChildCount() > 0) {
            indicatorsContainer.getChildAt(0).setBackgroundResource(R.drawable.indicator_active);
        }
    }

    private void updateIndicators(int position) {
        for (int i = 0; i < indicatorsContainer.getChildCount(); i++) {
            View indicator = indicatorsContainer.getChildAt(i);
            final int currentIndex = i; // Variable final para usar en lambda
            
            // Configurar el fondo inmediatamente
            if (i == position) {
                indicator.setBackgroundResource(R.drawable.indicator_active);
            } else {
                indicator.setBackgroundResource(R.drawable.indicator_inactive);
            }
            
            // Animación suave para el cambio de indicador
            indicator.animate()
                .scaleX(i == position ? 1.2f : 1.0f)
                .scaleY(i == position ? 1.2f : 1.0f)
                .alpha(i == position ? 1.0f : 0.6f)
                .setDuration(200)
                .start();
        }
    }

    private void setupClickListeners() {
        // Solo mantener el click del botón "Saltar"
        tvSaltar.setOnClickListener(v -> finishIntroduction());
    }

    private void finishIntroduction() {
        // Cancelar cualquier navegación automática pendiente
        if (autoNavigationHandler != null) {
            autoNavigationHandler.removeCallbacksAndMessages(null);
        }
        
        // Animación de fade out suave antes de navegar
        viewPager2.animate()
            .alpha(0.0f)
            .setDuration(300)
            .withEndAction(() -> {
                // Navegar a MainActivity con transición personalizada
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                
                // Transición suave entre actividades
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            })
            .start();
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Limpiar el handler para evitar memory leaks
        if (autoNavigationHandler != null) {
            autoNavigationHandler.removeCallbacksAndMessages(null);
        }
    }
}