package co.edu.unicauca.saberpro.decorator;

/**
 * Decorador concreto: marca la pregunta como de alta dificultad y
 * añade un multiplicador de puntaje (1.5x) sobre el puntaje base.
 */
public class HighDifficultyQuestion extends QuestionDecorator {

    private static final double MULTIPLICADOR = 1.5;

    public HighDifficultyQuestion(QuestionComponent componente) {
        super(componente);
    }

    @Override
    public String getDescripcion() {
        return componente.getDescripcion() + " [Alta Dificultad]";
    }

    @Override
    public double getPuntajeBase() {
        return componente.getPuntajeBase() * MULTIPLICADOR;
    }
}
