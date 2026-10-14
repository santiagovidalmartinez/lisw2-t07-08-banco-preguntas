package co.edu.unicauca.saberpro.templatemethod;

import co.edu.unicauca.saberpro.domain.model.Context;
import co.edu.unicauca.saberpro.domain.model.Option;

import java.util.List;

/**
 * Evaluador concreto para preguntas de Verdadero/Falso.
 * El usuario envía "true" o "false" (como texto) y se compara contra
 * la opción marcada como correcta en la pregunta.
 */
public class TrueFalseEvaluator extends QuestionEvaluator {

    @Override
    protected boolean validarFormato(Context context, List<String> respuesta) {
        if (respuesta == null || respuesta.size() != 1) {
            return false;
        }
        String valor = respuesta.get(0).trim().toLowerCase();
        return valor.equals("true") || valor.equals("false");
    }

    @Override
    protected double calcularPuntaje(Context context, List<String> respuesta) {
        Option correcta = context.getQuestion().getOpcionCorrecta();
        if (correcta == null) {
            return 0.0;
        }
        boolean valorUsuario = Boolean.parseBoolean(respuesta.get(0).trim());
        boolean valorCorrecto = Boolean.parseBoolean(correcta.getTexto().trim());
        return valorUsuario == valorCorrecto ? 1.0 : 0.0;
    }
}
