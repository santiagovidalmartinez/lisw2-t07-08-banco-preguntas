package co.edu.unicauca.saberpro.state;

import co.edu.unicauca.saberpro.domain.model.Context;

/**
 * En Revisión: revisión por pares. Permite al revisor aprobar la pregunta
 * o devolverla a Borrador solicitando correcciones.
 */
public class ReviewState implements IQuestionState {

    @Override
    public void aprobar(Context context) {
        context.setEstado(new ApprovedState());
    }

    @Override
    public void solicitarCorrecciones(Context context) {
        context.setEstado(new DraftState());
    }

    @Override
    public String getNombre() {
        return "EN_REVISION";
    }
}
