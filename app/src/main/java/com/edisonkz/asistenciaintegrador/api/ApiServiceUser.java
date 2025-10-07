package com.edisonkz.asistenciaintegrador.api;

import android.util.Log;
import com.edisonkz.asistenciaintegrador.model.Usuario;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApiServiceUser {
    // URL actualizada según el backend
    private static final String BASE_URL = "http://192.168.0.100:8080/"; 
    
    private ExecutorService executor = Executors.newFixedThreadPool(3);

    public interface LoginCallback {
        void onSuccess(Usuario usuario);
        void onError(String error);
    }

    public void login(String dni, String password, LoginCallback callback) {
        executor.execute(() -> {
            try {
                // Usar el nuevo endpoint login-android
                String urlString = BASE_URL + "api/usuarios/login-android?dni=" + dni + "&password=" + password;
                Log.d("ApiService", "Connecting to: " + urlString);
                
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(15000); // Aumentar timeout
                connection.setReadTimeout(15000);
                
                Log.d("ApiService", "Attempting connection...");
                
                // Leer respuesta
                int responseCode = connection.getResponseCode();
                Log.d("ApiService", "Response code: " + responseCode);
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                // Procesar respuesta JSON
                JSONObject jsonResponse = new JSONObject(response.toString());
                boolean success = jsonResponse.optBoolean("success", false);

                if (success) {
                    JSONObject usuarioJson = jsonResponse.getJSONObject("usuario");
                    Usuario usuario = parseUsuario(usuarioJson);
                    callback.onSuccess(usuario);
                } else {
                    String message = jsonResponse.optString("message", "Error desconocido");
                    callback.onError(message);
                }

            } catch (Exception e) {
                Log.e("ApiService", "Error details: ", e);
                callback.onError("Error de conexión: " + e.getClass().getSimpleName() + " - " + e.getMessage());
            }
        });
    }

    private Usuario parseUsuario(JSONObject usuarioJson) throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(usuarioJson.optString("id"));
        usuario.setNombre(usuarioJson.optString("nombre"));
        usuario.setApellido(usuarioJson.optString("apellido"));
        usuario.setEmail(usuarioJson.optString("email"));
        usuario.setRol(usuarioJson.optString("rol"));
        usuario.setCargo(usuarioJson.optString("cargo"));
        usuario.setDni(usuarioJson.optString("dni"));
        usuario.setTelefono(usuarioJson.optString("telefono"));
        return usuario;
    }
}
