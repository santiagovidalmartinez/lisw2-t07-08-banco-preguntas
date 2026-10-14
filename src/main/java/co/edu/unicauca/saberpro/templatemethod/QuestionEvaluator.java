package co.edu.unicauca.saberpro.templatemethod;

import co.edu.unicauca.saberpro.domain.model.Context;

import java.util.List;

/**
 * Patrón Template Method: define el esqueleto invariante del algoritmo de
 * evaluación de una respuesta (1. recibir, 2. validar formato, 3. calcular
 * puntaje, 4. generar retroalimentación). El método plantilla evaluar() es
 * final: las subclases solo personalizan los pasos específicos de su tipo
 * de pregunta.
 */
public abstract class QuestionEvaluator {

    /** Método plantilla: fija el orden de los pasos del algoritmo. */
    public final ResultadoEvaluacion evaluar(Context context, List<String> respuestaUsuario) {
        List<String> respuesta = recibirRespuesta(respuestaUsuario);

        if (!validarFormato(context, respuesta)) {
            return new ResultadoEvaluacion(0.0, "Formato de respuesta inválido para este tipo de pregunta.");
        }

        double puntaje = calcularPuntaje(context, respuesta);
        String retroalimentacion = generarRetroalimentacion(context, puntaje);
        return new ResultadoEvaluacion(puntaje, retroalimentacion);
    }

    /** Paso 1 — Recibir respuesta. Comportamiento por defecto: la deja pasar tal cual. */
    protected List<String> recibirRespuesta(List<String> respuestaUsuario) {
        return respuestaUsuario;
    }

    /** Paso 2 — Validar formato. Obligatorio por subclase (hook abstracto). */
    protected abstract boolean validarFormato(Context context, List<String> respuesta);

    /** Paso 3 — Calcular puntaje específico del tipo de pregunta. Obligatorio por subclase. */
    protected abstract double calcularPuntaje(Context context, List<String> respuesta);

    /** Paso 4 — Generar retroalimentación. Comportamiento por defecto, sobrescribible. */
    protected String generarRetroalimentacion(Context context, double puntaje) {
        if (puntaje >= 1.0) {
            return "¡Respuesta correcta!";
        }
        return "Respuesta incorrecta. Justificación: " + context.getQuestion().getJustificacion();
    }
}
