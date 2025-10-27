package com.edisonkz.asistenciaintegrador;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class pantalla_mi_perfil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_mi_perfil);

        // Obtener datos del usuario desde SharedPreferences
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        String nombre = prefs.getString("usuario_nombre", "");
        String apellido = prefs.getString("usuario_apellido", "");
        String dni = prefs.getString("usuario_dni", "");
        String email = prefs.getString("usuario_email", "");
        String telefono = prefs.getString("usuario_telefono", "");
        String cargo = prefs.getString("usuario_cargo", "");

        // Setear los datos en los TextView correspondientes
        TextView detalleNroDoc = findViewById(R.id.detalleNroDoc);
        TextView detalleNombre = findViewById(R.id.detalleNombre);
        TextView detalleNroTel = findViewById(R.id.detalleNroTel);
        TextView detalleCorreo = findViewById(R.id.detalleCorreo);
        TextView detalleDireccion = findViewById(R.id.detalleDireccion);

        detalleNroDoc.setText(dni);
        detalleNombre.setText((nombre + " " + apellido).trim());
        detalleNroTel.setText(telefono);
        detalleCorreo.setText(email);
        // detalleDireccion: puedes llenarlo si tienes ese dato, si no, déjalo como está
    }
}
