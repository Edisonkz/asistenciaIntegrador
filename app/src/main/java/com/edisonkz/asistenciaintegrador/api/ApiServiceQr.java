package com.edisonkz.asistenciaintegrador.api;

import android.os.Handler;
import android.os.Looper;
import org.json.JSONObject;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApiServiceQr {

    private static final String BASE_URL = ApiConfig.getBaseUrl();
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public interface QrCallback {

        void onSuccess(String qrToken);

        void onError(String error);
    }

    public void generarQrAsistencia(int idUsuario, String geolocalizacion, String horarioInicio, String horarioFin, QrCallback callback) {
        executor.execute(() -> {
            try {
                String urlString = BASE_URL + "api/empleado/generar-qr-asistencia";
                URL url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                conn.setConnectTimeout(10000);
                conn.setReadTimeout(10000);
                conn.setDoOutput(true);

                JSONObject json = new JSONObject();
                json.put("idUsuario", idUsuario);
                json.put("geolocalizacion", geolocalizacion);
                json.put("horarioInicio", horarioInicio);
                json.put("horarioFin", horarioFin);

                OutputStream os = conn.getOutputStream();
                os.write(json.toString().getBytes("UTF-8"));
                os.close();

                int responseCode = conn.getResponseCode();
                InputStream is = (responseCode >= 200 && responseCode < 300) ? conn.getInputStream() : conn.getErrorStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                if (jsonResponse.optBoolean("success", false)) {
                    String qrToken = jsonResponse.optString("qr_token");
                    new Handler(Looper.getMainLooper()).post(() -> callback.onSuccess(qrToken));
                } else {
                    String msg = jsonResponse.optString("message", "Error al generar QR");
                    new Handler(Looper.getMainLooper()).post(() -> callback.onError(msg));
                }
            } catch (Exception e) {
                new Handler(Looper.getMainLooper()).post(() -> callback.onError("Error: " + e.getMessage()));
            }
        });
    }
}
