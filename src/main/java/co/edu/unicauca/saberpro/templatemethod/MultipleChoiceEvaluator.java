package co.edu.unicauca.saberpro.templatemethod;

import co.edu.unicauca.saberpro.domain.model.Context;
import co.edu.unicauca.saberpro.domain.model.Option;

import java.util.List;

/**
 * Evaluador concreto para preguntas de Selección Múltiple:
 * el usuario envía exactamente el texto de UNA opción.
 */
public class MultipleChoiceEvaluator extends QuestionEvaluator {

    @Override
    protected boolean validarFormato(Context context, List<String> respuesta) {
        return respuesta != null && respuesta.size() == 1 && respuesta.get(0) != null && !respuesta.get(0).isBlank();
    }

    @Override
    protected double calcularPuntaje(Context context, List<String> respuesta) {
        Option correcta = context.getQuestion().getOpcionCorrecta();
        if (correcta == null) {
            return 0.0;
        }
        String elegida = respuesta.get(0).trim();
        return correcta.getTexto().trim().equalsIgnoreCase(elegida) ? 1.0 : 0.0;
    }
}
