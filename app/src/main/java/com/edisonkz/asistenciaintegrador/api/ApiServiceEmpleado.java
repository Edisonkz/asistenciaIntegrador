package com.edisonkz.asistenciaintegrador.api;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApiServiceEmpleado {

    private static final String BASE_URL = ApiConfig.getBaseUrl();

    private ExecutorService executor = Executors.newFixedThreadPool(3);

    public interface HorarioCallback {

        void onSuccess(JSONArray horario);

        void onError(String error);
    }

    public void obtenerHorarioSemanal(String usuarioId, HorarioCallback callback) {
        executor.execute(() -> {
            try {
                String urlString = BASE_URL + "api/empleado/horario-semanal?idUsuario=" + usuarioId;
                Log.d("ApiServiceEmpleado", "Connecting to: " + urlString);

                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(15000);

                int responseCode = connection.getResponseCode();
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                boolean success = jsonResponse.optBoolean("success", false);

                if (success) {
                    JSONArray horario = jsonResponse.getJSONArray("horario");
                    callback.onSuccess(horario);
                } else {
                    String message = jsonResponse.optString("message", "Error desconocido");
                    callback.onError(message);
                }
            } catch (Exception e) {
                Log.e("ApiServiceEmpleado", "Error: ", e);
                callback.onError("Error de conexión: " + e.getMessage());
            }
        });
    }
}
