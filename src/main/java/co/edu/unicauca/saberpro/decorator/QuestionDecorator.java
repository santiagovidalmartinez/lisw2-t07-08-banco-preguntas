package co.edu.unicauca.saberpro.decorator;

/**
 * Decorador base abstracto: envuelve un QuestionComponent y delega en él
 * por defecto, permitiendo a las subclases concretas añadir
 * características dinámicas sin modificar BasicQuestion.
 */
public abstract class QuestionDecorator implements QuestionComponent {

    protected final QuestionComponent componente;

    protected QuestionDecorator(QuestionComponent componente) {
        this.componente = componente;
    }

    @Override
    public String getDescripcion() {
        return componente.getDescripcion();
    }

    @Override
    public double getPuntajeBase() {
        return componente.getPuntajeBase();
    }
}
