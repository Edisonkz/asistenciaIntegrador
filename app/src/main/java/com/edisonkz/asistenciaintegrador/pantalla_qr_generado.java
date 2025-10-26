package com.edisonkz.asistenciaintegrador;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class pantalla_qr_generado extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_qr_generado);

        String qrToken = getIntent().getStringExtra("qr_token");
        ImageView qrImage = findViewById(R.id.iv_qr);

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
}
