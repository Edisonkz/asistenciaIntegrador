package com.edisonkz.asistenciaintegrador;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class pantalla_qr_generado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_qr_generado);

        String qrToken = getIntent().getStringExtra("qr_token");
        ImageView qrImage = findViewById(R.id.iv_qr);

        // Obtener datos reales del usuario desde SharedPreferences
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        String nombre = prefs.getString("usuario_nombre", "");
        String apellido = prefs.getString("usuario_apellido", "");
        String dni = prefs.getString("usuario_dni", "");

        // Setear nombre y documento reales
        TextView tvNombre = findViewById(R.id.tvNombreQr);
        TextView tvDoc = findViewById(R.id.tvDocQr);
        tvNombre.setText((nombre + " " + apellido).trim());
        tvDoc.setText("N° Doc: " + dni);

        // Setear fecha y hora actual
        TextView tvFechaHora = findViewById(R.id.tvFechaHoraQr);
        String fechaHora = getFechaHoraActual();
        tvFechaHora.setText(fechaHora);

        // Generar el QR
        if (qrToken != null && !qrToken.isEmpty()) {
            try {
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.encodeBitmap(qrToken, BarcodeFormat.QR_CODE, 400, 400);
                qrImage.setImageBitmap(bitmap);
            } catch (WriterException e) {
                Toast.makeText(this, "Error generando QR", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "No se recibió el token QR", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para obtener fecha y hora actual en formato "Fecha: dd/MM/yyyy   Hora: HH:mm:ss"
    private String getFechaHoraActual() {
        Date now = new Date();
        SimpleDateFormat sdfFecha = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat sdfHora = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        return "Fecha: " + sdfFecha.format(now) + "   Hora: " + sdfHora.format(now);
    }
}
