package com.edisonkz.asistenciaintegrador.model;

public class Usuario {

    private String id;
    private String nombre;
    private String apellido;
    private String email;
    private String rol;
    private String cargo;
    private String dni;
    private String telefono;

    // Constructores
    public Usuario() {
    }

    public Usuario(String id, String nombre, String apellido, String email,
            String rol, String cargo, String dni, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.rol = rol;
        this.cargo = cargo;
        this.dni = dni;
        this.telefono = telefono;
    }

    // Getters y Setters
    public String getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = String.valueOf(id);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
}
