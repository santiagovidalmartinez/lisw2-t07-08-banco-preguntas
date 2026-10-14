package co.edu.unicauca.saberpro.decorator;

import co.edu.unicauca.saberpro.domain.model.Context;

/**
 * Decorador concreto: adjunta una explicación técnica detallada
 * (tomada de la justificación de la pregunta) a la descripción,
 * sin alterar el puntaje base.
 */
public class FeedbackQuestion extends QuestionDecorator {

    private final Context context;

    public FeedbackQuestion(QuestionComponent componente, Context context) {
        super(componente);
        this.context = context;
    }

    @Override
    public String getDescripcion() {
        return componente.getDescripcion() + " | Retroalimentación: " + context.getQuestion().getJustificacion();
    }
}
