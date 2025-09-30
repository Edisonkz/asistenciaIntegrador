package com.edisonkz.asistenciaintegrador;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class InformacionFragment extends Fragment {

    private static final String ARG_TITLE = "title";
    private static final String ARG_SUBTITLE = "subtitle";
    private static final String ARG_ICON_RES = "icon_res";

    private String title;
    private String subtitle;
    private int iconRes;

    public static InformacionFragment newInstance(String title, String subtitle, int iconRes) {
        InformacionFragment fragment = new InformacionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        args.putString(ARG_SUBTITLE, subtitle);
        args.putInt(ARG_ICON_RES, iconRes);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
            subtitle = getArguments().getString(ARG_SUBTITLE);
            iconRes = getArguments().getInt(ARG_ICON_RES);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        
        // Seleccionar layout según el tipo de pantalla
        int layoutId = R.layout.activity_pantalla_carga_contenedor;
        if (iconRes == R.drawable.informacion_avatar3) {
            layoutId = R.layout.activity_pantalla_carga_informacion3;
        }
        
        View view = inflater.inflate(layoutId, container, false);

        ImageView ivIcon = view.findViewById(R.id.iv_icon);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvSubtitle = view.findViewById(R.id.tv_subtitle);

        // Configurar contenido
        if (iconRes != 0) {
            ivIcon.setImageResource(iconRes);
        }
        
        tvTitle.setText(title);
        tvSubtitle.setText(subtitle);

        return view;
    }
}