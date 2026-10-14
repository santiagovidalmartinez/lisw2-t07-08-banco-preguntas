package co.edu.unicauca.saberpro.decorator;

/**
 * Componente del patrón Decorator: define la operación que tanto el
 * objeto concreto (BasicQuestion) como sus envoltorios (QuestionDecorator
 * y subclases) deben implementar.
 */
public interface QuestionComponent {

    String getDescripcion();

    double getPuntajeBase();
}
