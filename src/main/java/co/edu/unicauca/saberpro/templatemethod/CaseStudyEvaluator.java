package co.edu.unicauca.saberpro.templatemethod;

import co.edu.unicauca.saberpro.domain.model.Context;

import java.util.List;

/**
 * Evaluador concreto para preguntas tipo Caso de Estudio: el usuario envía
 * una lista de palabras clave identificadas en su respuesta abierta; el
 * puntaje es proporcional a las palabras clave de la justificación oficial
 * que logró mencionar (evaluación semi-automática por coincidencia).
 */
public class CaseStudyEvaluator extends QuestionEvaluator {

    @Override
    protected boolean validarFormato(Context context, List<String> respuesta) {
        return respuesta != null && !respuesta.isEmpty();
    }

    @Override
    protected double calcularPuntaje(Context context, List<String> respuesta) {
        String justificacion = context.getQuestion().getJustificacion();
        if (justificacion == null || justificacion.isBlank()) {
            return 0.0;
        }
        String justificacionLower = justificacion.toLowerCase();
        long coincidencias = respuesta.stream()
                .filter(palabra -> palabra != null && !palabra.isBlank())
                .filter(palabra -> justificacionLower.contains(palabra.trim().toLowerCase()))
                .count();
        double puntaje = (double) coincidencias / respuesta.size();
        return Math.min(1.0, puntaje);
    }

    @Override
    protected String generarRetroalimentacion(Context context, double puntaje) {
        if (puntaje >= 0.8) {
            return "Excelente análisis del caso de estudio.";
        } else if (puntaje >= 0.4) {
            return "Análisis parcial. Revisa la justificación: " + context.getQuestion().getJustificacion();
        }
        return "El análisis no cubre los puntos clave. Justificación: " + context.getQuestion().getJustificacion();
    }
}
