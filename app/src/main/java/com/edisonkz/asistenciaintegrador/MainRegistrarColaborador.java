package com.edisonkz.asistenciaintegrador;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainRegistrarColaborador extends AppCompatActivity {

    private ImageButton btnBack;
    private EditText etNombreColab;
    private EditText etApellidosColab;
    private Spinner spinnerDoc;
    private EditText etNumDoc;
    private EditText etCorreoColab;
    private EditText etTelefonoColab;
    private Spinner spinnerCargo;
    private FrameLayout frameImagen1, frameImagen2, frameImagen3, frameImagen4, frameImagen5;
    private Button btnEnviar;

    private Uri[] imagenesSeleccionadas = new Uri[5];
    private int imagenActualIndex = -1;

    // Launcher para seleccionar imágenes
    private final ActivityResultLauncher<Intent> seleccionarImagen = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    Uri imagenUri = result.getData().getData();
                    if (imagenActualIndex >= 0 && imagenActualIndex < 5) {
                        imagenesSeleccionadas[imagenActualIndex] = imagenUri;
                        Toast.makeText(this, "Imagen " + (imagenActualIndex + 1) + " seleccionada", Toast.LENGTH_SHORT).show();
                        // Aquí podrías actualizar el FrameLayout con la imagen
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registrar_colaborador);

        initViews();
        setupSpinners();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        etNombreColab = findViewById(R.id.etNombreColab);
        etApellidosColab = findViewById(R.id.etApellidosColab);
        spinnerDoc = findViewById(R.id.spinnerDoc);
        etNumDoc = findViewById(R.id.etNumDoc);
        etCorreoColab = findViewById(R.id.etCorreoColab);
        etTelefonoColab = findViewById(R.id.etTelefonoColab);
        spinnerCargo = findViewById(R.id.spinnerCargo);
        frameImagen1 = findViewById(R.id.frameImagen1);
        frameImagen2 = findViewById(R.id.frameImagen2);
        frameImagen3 = findViewById(R.id.frameImagen3);
        frameImagen4 = findViewById(R.id.frameImagen4);
        frameImagen5 = findViewById(R.id.frameImagen5);
        btnEnviar = findViewById(R.id.btnEnviar);
    }

    private void setupSpinners() {
        // Spinner de Tipo de Documento
        String[] tiposDoc = {"DNI", "C.E.", "RUC"};
        ArrayAdapter<String> adapterDoc = createCustomAdapter(tiposDoc);
        spinnerDoc.setAdapter(adapterDoc);

        // Spinner de Cargo
        String[] cargos = {"Supervisor", "Operario", "Administrativo", "Técnico", "Guardía"};
        ArrayAdapter<String> adapterCargo = createCustomAdapter(cargos);
        spinnerCargo.setAdapter(adapterCargo);
    }

    private ArrayAdapter<String> createCustomAdapter(String[] items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                items
        ) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextSize(14f);
                return view;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = view.findViewById(android.R.id.text1);
                textView.setTextSize(14f);
                return view;
            }
        };
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }

    private void setupListeners() {
        // Botón regresar
        btnBack.setOnClickListener(v -> finish());

        // FrameLayouts para seleccionar imágenes
        frameImagen1.setOnClickListener(v -> abrirSelectorImagen(0));
        frameImagen2.setOnClickListener(v -> abrirSelectorImagen(1));
        frameImagen3.setOnClickListener(v -> abrirSelectorImagen(2));
        frameImagen4.setOnClickListener(v -> abrirSelectorImagen(3));
        frameImagen5.setOnClickListener(v -> abrirSelectorImagen(4));

        // Botón enviar
        btnEnviar.setOnClickListener(v -> registrarColaborador());
    }

    private void abrirSelectorImagen(int index) {
        imagenActualIndex = index;
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        seleccionarImagen.launch(intent);
    }

    private void registrarColaborador() {
        String nombre = etNombreColab.getText().toString().trim();
        String apellidos = etApellidosColab.getText().toString().trim();
        String tipoDoc = spinnerDoc.getSelectedItem().toString();
        String numDoc = etNumDoc.getText().toString().trim();
        String correo = etCorreoColab.getText().toString().trim();
        String telefono = etTelefonoColab.getText().toString().trim();
        String cargo = spinnerCargo.getSelectedItem().toString();

        // Validaciones
        if (nombre.isEmpty()) {
            Toast.makeText(this, "Ingrese el nombre del colaborador", Toast.LENGTH_SHORT).show();
            etNombreColab.requestFocus();
            return;
        }

        if (apellidos.isEmpty()) {
            Toast.makeText(this, "Ingrese los apellidos del colaborador", Toast.LENGTH_SHORT).show();
            etApellidosColab.requestFocus();
            return;
        }

        if (numDoc.isEmpty()) {
            Toast.makeText(this, "Ingrese el número de documento", Toast.LENGTH_SHORT).show();
            etNumDoc.requestFocus();
            return;
        }

        // Validación de DNI (8 dígitos para Perú)
        if (tipoDoc.equals("DNI") && numDoc.length() != 8) {
            Toast.makeText(this, "El DNI debe tener 8 dígitos", Toast.LENGTH_SHORT).show();
            etNumDoc.requestFocus();
            return;
        }

        if (correo.isEmpty()) {
            Toast.makeText(this, "Ingrese el correo electrónico", Toast.LENGTH_SHORT).show();
            etCorreoColab.requestFocus();
            return;
        }

        // Validación básica de correo
        if (!correo.contains("@") || !correo.contains(".")) {
            Toast.makeText(this, "Ingrese un correo electrónico válido", Toast.LENGTH_SHORT).show();
            etCorreoColab.requestFocus();
            return;
        }

        if (telefono.isEmpty()) {
            Toast.makeText(this, "Ingrese el número de teléfono", Toast.LENGTH_SHORT).show();
            etTelefonoColab.requestFocus();
            return;
        }

        // Validación de teléfono (9 dígitos para Perú)
        if (telefono.length() != 9) {
            Toast.makeText(this, "El teléfono debe tener 9 dígitos", Toast.LENGTH_SHORT).show();
            etTelefonoColab.requestFocus();
            return;
        }

        // Aquí implementas la lógica para registrar el colaborador
        // Puedes usar las imágenes de imagenesSeleccionadas[] para subir las fotos

        Toast.makeText(this, "Colaborador registrado exitosamente", Toast.LENGTH_LONG).show();

        // Opcional: Limpiar campos o cerrar activity
        limpiarCampos();
        // finish();
    }

    private void limpiarCampos() {
        etNombreColab.setText("");
        etApellidosColab.setText("");
        etNumDoc.setText("");
        etCorreoColab.setText("");
        etTelefonoColab.setText("");
        spinnerDoc.setSelection(0);
        spinnerCargo.setSelection(0);

        // Limpiar imágenes seleccionadas
        for (int i = 0; i < imagenesSeleccionadas.length; i++) {
            imagenesSeleccionadas[i] = null;
        }

        Toast.makeText(this, "Campos limpiados", Toast.LENGTH_SHORT).show();
    }
}