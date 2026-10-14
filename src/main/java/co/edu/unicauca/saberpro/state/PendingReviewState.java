package co.edu.unicauca.saberpro.state;

import co.edu.unicauca.saberpro.domain.model.Context;

/**
 * Pendiente de Revisión: la pregunta fue enviada a evaluación.
 * Bloquea modificaciones directas del autor y solo permite que
 * un revisor inicie formalmente la revisión por pares.
 */
public class PendingReviewState implements IQuestionState {

    @Override
    public void iniciarRevision(Context context) {
        context.setEstado(new ReviewState());
    }

    @Override
    public String getNombre() {
        return "PENDIENTE_DE_REVISION";
    }
}
