package co.edu.unicauca.saberpro.state;

import co.edu.unicauca.saberpro.domain.model.Context;

/**
 * Borrador: estado inicial. Permite edición completa de enunciado y opciones
 * (gestionada fuera del patrón State) y transiciona a Pendiente de Revisión.
 */
public class DraftState implements IQuestionState {

    @Override
    public void enviarARevision(Context context) {
        context.setEstado(new PendingReviewState());
    }

    @Override
    public String getNombre() {
        return "BORRADOR";
    }
}
