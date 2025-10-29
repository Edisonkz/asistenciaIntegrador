package com.edisonkz.asistenciaintegrador;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.edisonkz.asistenciaintegrador.api.ApiServiceUserLogin;
import com.edisonkz.asistenciaintegrador.model.Usuario;

public class pantalla_login_inicio_sesion extends AppCompatActivity {

    private TextInputEditText etDocumento, etPassword;
    private TextView tvOlvidePassword, btnLogin;
    private ApiServiceUserLogin apiService;
    private ProgressDialog progressDialog;
    private SwitchMaterial swModeLocal; // nuevo: switch para modo local

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_login_inicio_sesion);

        // Inicializar vistas y servicios
        initViews();
        apiService = new ApiServiceUserLogin();
        setupProgressDialog();
        setupListeners();
    }

    private void initViews() {
        etDocumento = findViewById(R.id.et_documento);
        etPassword = findViewById(R.id.et_password);
        tvOlvidePassword = findViewById(R.id.tv_olvide_password);
        btnLogin = findViewById(R.id.btn_iniciar_sesion);
        swModeLocal = findViewById(R.id.sw_mode_local); // inicializar switch
    }

    private void setupProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Iniciando sesión");
        progressDialog.setMessage("Verificando credenciales...");
        progressDialog.setCancelable(false);
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

        // Si está activado el modo local, autenticar localmente (sin tocar la BD)
        if (swModeLocal != null && swModeLocal.isChecked()) {
            progressDialog.show();
            // Simular pequeña demora y validar credenciales de prueba
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                progressDialog.dismiss();
                authenticateLocally(dni, password);
            }, 800);
            return;
        }

        // Mostrar progress dialog y Llamar a la API del backend con DNI
        progressDialog.show();
        apiService.login(dni, password, new ApiServiceUserLogin.LoginCallback() {
            @Override
            public void onSuccess(Usuario usuario) {
                runOnUiThread(() -> {
                    progressDialog.dismiss();
                    handleLoginSuccess(usuario);
                });
            }

            @Override
            public void onError(String error) {
                runOnUiThread(() -> {
                    progressDialog.dismiss();
                    Toast.makeText(pantalla_login_inicio_sesion.this,
                            "Error: " + error, Toast.LENGTH_LONG).show();
                    etPassword.setText("");
                    etPassword.requestFocus();
                });
            }
        });
    }

    // Nuevo: autenticación local de prueba (tres usuarios hardcodeados)
    private void authenticateLocally(String dni, String password) {
        // Credenciales de prueba (DNI, contraseña NUMÉRICA)
        // Empleado:      dni "12345678", password "1234"
        // Guardia:       dni "2222", password "2222"
        // Administrador: dni "87654321", password "9999"

        if (dni.equals("12345678") && password.equals("1234")) {
            // Empleado de prueba
            Usuario usuario = new Usuario(
                    "local_emp_1",
                    "Juan",
                    "Pérez",
                    "juan.local@local.test",
                    "empleado",
                    "Operario",
                    dni,
                    "999000111"
            );
            handleLoginSuccess(usuario);
        } else if (dni.equals("2222") && password.equals("2222")) {
            // Guardia de prueba
            Usuario usuario = new Usuario(
                    "local_guar_1",
                    "María",
                    "Gómez",
                    "maria.local@local.test",
                    "seguridad",
                    "Vigilante",
                    dni,
                    "999000222"
            );
            handleLoginSuccess(usuario);
        } else if (dni.equals("3333") && password.equals("3333")) {
            // Administrador de prueba
            Usuario usuario = new Usuario(
                    "local_admin_1",
                    "Admin",
                    "Local",
                    "admin.local@local.test",
                    "admin", // <-- Cambia a "admin" para que coincida con el switch
                    "Administrador",
                    dni,
                    "999000333"
            );
            handleLoginSuccess(usuario);
        } else {
            Toast.makeText(this, "Credenciales inválidas en modo local", Toast.LENGTH_LONG).show();
            etPassword.setText("");
            etPassword.requestFocus();
        }
    }

    private void handleLoginSuccess(Usuario usuario) {
        // Guardar los datos del usuario en SharedPreferences
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("usuario_id", usuario.getId());
        editor.putString("usuario_nombre", usuario.getNombre());
        editor.putString("usuario_apellido", usuario.getApellido());
        editor.putString("usuario_email", usuario.getEmail());
        editor.putString("usuario_rol", usuario.getRol());
        editor.putString("usuario_cargo", usuario.getCargo());
        editor.putString("usuario_dni", usuario.getDni());
        editor.putString("usuario_telefono", usuario.getTelefono());
        editor.apply();

        Intent intent;
        String mensaje = "Bienvenido " + usuario.getNombre();

        // Redirigir según el rol del usuario desde el backend
        switch (usuario.getRol().toLowerCase()) {
            case "admin":
            case "administrador":
                intent = new Intent(pantalla_login_inicio_sesion.this, pantalla_administrador_mis_servicios.class);
                break;

            case "empleado":
                intent = new Intent(pantalla_login_inicio_sesion.this, pantalla_empleado_mis_servicios.class);
                break;

            case "seguridad":
                intent = new Intent(pantalla_login_inicio_sesion.this, pantalla_guardia_inicio.class);
                break;

            default:
                intent = new Intent(pantalla_login_inicio_sesion.this, pantalla_empleado_mis_servicios.class);
                break;
        }

        // Pasar datos del usuario a la siguiente actividad
        intent.putExtra("usuario_id", usuario.getId());
        intent.putExtra("usuario_nombre", usuario.getNombre());
        intent.putExtra("usuario_email", usuario.getEmail());
        intent.putExtra("usuario_rol", usuario.getRol());
        intent.putExtra("usuario_cargo", usuario.getCargo());

        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }
}
