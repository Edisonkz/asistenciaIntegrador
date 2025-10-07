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
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainConfigurarAlertas extends AppCompatActivity {

    private ImageButton btnBack;
    private EditText etTituloAlerta;
    private Spinner spinnerTipoAlerta;
    private EditText etDetalleAlerta;
    private FrameLayout frameImagen;
    private RadioButton rbColaborador;
    private RadioButton rbTodos;
    private EditText etColaborador;
    private Button btnEnviar;

    private Uri imagenSeleccionadaUri;

    // Launcher para seleccionar imagen
    private final ActivityResultLauncher<Intent> seleccionarImagen = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                    imagenSeleccionadaUri = result.getData().getData();
                    Toast.makeText(this, "Imagen seleccionada", Toast.LENGTH_SHORT).show();
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_administrador_configurar_alertas);

        initViews();
        setupSpinner();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        etTituloAlerta = findViewById(R.id.etTituloAlerta);
        spinnerTipoAlerta = findViewById(R.id.spinnerTipoAlerta);
        etDetalleAlerta = findViewById(R.id.etDetalleAlerta);
        frameImagen = findViewById(R.id.frameImagen);
        rbColaborador = findViewById(R.id.rbColaborador);
        rbTodos = findViewById(R.id.rbTodos);
        etColaborador = findViewById(R.id.etColaborador);
        btnEnviar = findViewById(R.id.btnEnviar);
    }

    private void setupSpinner() {
        // Datos del spinner
        String[] tiposAlerta = {"Comunicado", "Alerta", "Otros"};

        // Crear adapter personalizado para cambiar el tamaño del texto a 14sp
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_spinner_item,
                tiposAlerta
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
        spinnerTipoAlerta.setAdapter(adapter);
    }

    private void setupListeners() {
        // Botón regresar
        btnBack.setOnClickListener(v -> finish());

        // FrameLayout para seleccionar imagen
        frameImagen.setOnClickListener(v -> abrirSelectorImagen());

        // RadioButtons
        rbColaborador.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etColaborador.setVisibility(View.VISIBLE);
                rbTodos.setChecked(false);
            }
        });

        rbTodos.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                etColaborador.setVisibility(View.GONE);
                rbColaborador.setChecked(false);
            }
        });

        // Botón enviar
        btnEnviar.setOnClickListener(v -> enviarAlerta());
    }

    private void abrirSelectorImagen() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        seleccionarImagen.launch(intent);
    }

    private void enviarAlerta() {
        String titulo = etTituloAlerta.getText().toString().trim();
        String tipoAlerta = spinnerTipoAlerta.getSelectedItem().toString();
        String detalle = etDetalleAlerta.getText().toString().trim();

        // Validaciones
        if (titulo.isEmpty()) {
            Toast.makeText(this, "Ingrese un título", Toast.LENGTH_SHORT).show();
            return;
        }

        if (detalle.isEmpty()) {
            Toast.makeText(this, "Ingrese el detalle de la alerta", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!rbColaborador.isChecked() && !rbTodos.isChecked()) {
            Toast.makeText(this, "Seleccione a quién enviar la alerta", Toast.LENGTH_SHORT).show();
            return;
        }

        if (rbColaborador.isChecked() && etColaborador.getText().toString().trim().isEmpty()) {
            Toast.makeText(this, "Ingrese el número de documento", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "Alerta enviada exitosamente", Toast.LENGTH_SHORT).show();

        // Opcional: Limpiar campos o cerrar activity
        finish();
    }
}