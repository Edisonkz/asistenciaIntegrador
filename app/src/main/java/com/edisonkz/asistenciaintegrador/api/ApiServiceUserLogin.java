package com.edisonkz.asistenciaintegrador.api;

import android.util.Log;
import com.edisonkz.asistenciaintegrador.model.Usuario;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApiServiceUserLogin {

    private static final String BASE_URL = ApiConfig.getBaseUrl();

    private ExecutorService executor = Executors.newFixedThreadPool(3);

    public interface LoginCallback {

        void onSuccess(Usuario usuario);

        void onError(String error);
    }

    public void login(String dni, String password, LoginCallback callback) {
        executor.execute(() -> {
            try {
                // Usar el endpoint POST /api/usuarios/login con JSON en el body
                String urlString = BASE_URL + "api/usuarios/login";
                Log.d("ApiService", "Connecting to: " + urlString);

                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(15000);
                connection.setReadTimeout(15000);
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setDoOutput(true);

                // Crear el JSON del body
                JSONObject body = new JSONObject();
                body.put("dni", dni);
                body.put("password", password);

                // Enviar el body
                OutputStream os = connection.getOutputStream();
                os.write(body.toString().getBytes("UTF-8"));
                os.close();

                int responseCode = connection.getResponseCode();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        responseCode >= 400 ? connection.getErrorStream() : connection.getInputStream()
                ));

                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

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
        // Asegura que el id se obtenga correctamente, sea String o número
        usuario.setId(usuarioJson.get("id"));
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
