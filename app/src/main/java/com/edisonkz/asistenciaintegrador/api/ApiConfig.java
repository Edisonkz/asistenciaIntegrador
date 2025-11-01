package com.edisonkz.asistenciaintegrador.api;

public class ApiConfig {

    // Cambia aquí la IP cuando sea necesario
    private static String BASE_IP = "10.254.54.59";
    private static final String PORT = "8080";

    public static String getBaseUrl() {
        return "http://" + BASE_IP + ":" + PORT + "/";
    }

    // Si quieres cambiar la IP en tiempo de ejecución:
    public static void setBaseIp(String ip) {
        BASE_IP = ip;
    }
}
