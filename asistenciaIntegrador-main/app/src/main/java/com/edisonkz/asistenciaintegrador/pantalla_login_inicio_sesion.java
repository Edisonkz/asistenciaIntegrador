package com.edisonkz.asistenciaintegrador;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;

public class pantalla_login_inicio_sesion extends AppCompatActivity {

    private TextInputEditText etDocumento, etPassword;
    private TextView tvOlvidePassword, btnLogin;

    // Credenciales predefinidas para los 3 roles (DNI/Contraseña)
    private static final String ADMIN_DNI = "12345678";
    private static final String ADMIN_PASS = "123";
    private static final String EMPLEADO_DNI = "87654321";
    private static final String EMPLEADO_PASS = "123";
    private static final String GUARDIA_DNI = "11223344";
    private static final String GUARDIA_PASS = "123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_login_inicio_sesion);

        // Inicializar vistas
        initViews();
        
        // Configurar listeners
        setupListeners();
    }

    private void initViews() {
        etDocumento = findViewById(R.id.et_documento);
        etPassword = findViewById(R.id.et_password);
        tvOlvidePassword = findViewById(R.id.tv_olvide_password);
        btnLogin = findViewById(R.id.btn_iniciar_sesion);
    }

    private void setupListeners() {
        // Listener para el botón de login
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLogin();
            }
        });

        // Listener para el enlace "¿Olvidaste tu contraseña?"
        tvOlvidePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Navegar a la pantalla de recuperar contraseña
                Intent intent = new Intent(pantalla_login_inicio_sesion.this, pantalla_login_recuperar.class);
                startActivity(intent);
            }
        });
    }

    private void validateLogin() {
        String dni = etDocumento.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // Validar campos vacíos
        if (dni.isEmpty()) {
            etDocumento.setError("Ingrese el DNI");
            etDocumento.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Ingrese la contraseña");
            etPassword.requestFocus();
            return;
        }

        // Validar credenciales y redirigir según el rol
        if (validateCredentials(dni, password)) {
            redirectUserByRole(dni);
        } else {
            Toast.makeText(this, "DNI o contraseña incorrectos", Toast.LENGTH_SHORT).show();
            // Limpiar campos
            etPassword.setText("");
            etPassword.requestFocus();
        }
    }

    private boolean validateCredentials(String dni, String password) {
        return (ADMIN_DNI.equals(dni) && ADMIN_PASS.equals(password)) ||
               (EMPLEADO_DNI.equals(dni) && EMPLEADO_PASS.equals(password)) ||
               (GUARDIA_DNI.equals(dni) && GUARDIA_PASS.equals(password));
    }

    private void redirectUserByRole(String dni) {
        Intent intent;
        String mensaje = "";

        switch (dni) {
            case ADMIN_DNI:
                // Admin va a MainActivity2 (pantalla admin)
                intent = new Intent(pantalla_login_inicio_sesion.this, MainActivity2.class);
                mensaje = "Bienvenido Administrador";
                break;
                
            case EMPLEADO_DNI:
                // Empleado va a Mis servicios
                intent = new Intent(pantalla_login_inicio_sesion.this, mis_servicios.class);
                mensaje = "Bienvenido Empleado";
                break;
                
            case GUARDIA_DNI:
                // Guardia va a MainActivity2 (pantalla guardia)
                intent = new Intent(pantalla_login_inicio_sesion.this, MainActivity2.class);
                mensaje = "Bienvenido Guardia";
                break;
                
            default:
                return;
        }

        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish(); // Cerrar pantalla de login
    }
}