package co.edu.unicauca.saberpro.decorator;

import co.edu.unicauca.saberpro.domain.model.Context;

/**
 * Componente concreto: envuelve una pregunta del banco con su puntaje
 * base (1.0 punto), sin ninguna característica dinámica adicional.
 */
public class BasicQuestion implements QuestionComponent {

    private static final double PUNTAJE_BASE = 1.0;

    private final Context context;

    public BasicQuestion(Context context) {
        this.context = context;
    }

    @Override
    public String getDescripcion() {
        return "Pregunta: " + context.getQuestion().getEnunciado();
    }

    @Override
    public double getPuntajeBase() {
        return PUNTAJE_BASE;
    }
}
