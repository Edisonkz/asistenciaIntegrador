package com.edisonkz.asistenciaintegrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
    private Button btnSiguiente;
    private TextView tvSaltar;
    private List<InformacionFragment> fragments;
    private int currentPosition = 0;

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
        btnSiguiente = findViewById(R.id.btn_siguiente);
        tvSaltar = findViewById(R.id.tv_saltar);
    }

    private void setupViewPager() {
        fragments = new ArrayList<>();
        
        // Página 1
        fragments.add(InformacionFragment.newInstance(
                "OLVÍDATE\nDE LAS FILAS",
                "Registra tu ingreso diario en segundos con\nreconocimiento facial y código QR dinámico",
                R.drawable.informacion_icon1
        ));
        
        // Página 2
        fragments.add(InformacionFragment.newInstance(
                "PUNTUALIDAD\nSIN ESTRÉS",
                "Recibe alertas y notificaciones, mantén tu\nasistencia al día, sin papeleo ni errores",
                R.drawable.informacion_icon2
        ));
        
        // Página 3
        fragments.add(InformacionFragment.newInstance(
                "SEGURIDAD Y\nCONFIANZA",
                "Tu identidad está protegida con la última\ntecnología en validación biométrica",
                R.drawable.informacion_icon3
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
                updateButtonText(position);
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
            if (i == position) {
                indicator.setBackgroundResource(R.drawable.indicator_active);
            } else {
                indicator.setBackgroundResource(R.drawable.indicator_inactive);
            }
        }
    }

    private void updateButtonText(int position) {
        if (position == fragments.size() - 1) {
            btnSiguiente.setText("Comenzar");
        } else {
            btnSiguiente.setText("Siguiente");
        }
    }

    private void setupClickListeners() {
        btnSiguiente.setOnClickListener(v -> {
            if (currentPosition < fragments.size() - 1) {
                viewPager2.setCurrentItem(currentPosition + 1);
            } else {
                // Finalizar introducción y ir a la siguiente actividad
                finishIntroduction();
            }
        });

        tvSaltar.setOnClickListener(v -> finishIntroduction());
    }

    private void finishIntroduction() {
        // Navegar a la siguiente actividad (por ejemplo, MainActivity)
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}