package co.edu.unicauca.saberpro.state;

/**
 * Aprobada: pregunta validada pedagógicamente y habilitada para
 * publicación en simulacros. Estado terminal del ciclo modelado en este taller:
 * ninguna transición adicional está habilitada desde aquí, por lo que no
 * sobrescribe ningún método de IQuestionState (todas lanzan IllegalStateException
 * por defecto, cumpliendo la restricción de no usar condicionales).
 */
public class ApprovedState implements IQuestionState {

    @Override
    public String getNombre() {
        return "APROBADA";
    }
}
