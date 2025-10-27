CREATE OR REPLACE FUNCTION obtener_horario_usuario(p_id_usuario integer)
RETURNS TABLE(
    dia_semana character varying,
    hora_inicio character varying,
    hora_fin character varying,
    turno character varying
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        h.dia_semana,
        h.hora_inicio,
        h.hora_fin,
        h.turno
    FROM public.horario_de_asistencia h
    WHERE h.id_usuario = p_id_usuario
    ORDER BY
        CASE h.dia_semana
            WHEN 'lunes' THEN 1
            WHEN 'martes' THEN 2
            WHEN 'miercoles' THEN 3
            WHEN 'jueves' THEN 4
            WHEN 'viernes' THEN 5
            WHEN 'sabado' THEN 6
            WHEN 'domingo' THEN 7
            ELSE 8
        END;
END;
$$ LANGUAGE plpgsql;

SELECT * FROM obtener_horario_usuario(4);
