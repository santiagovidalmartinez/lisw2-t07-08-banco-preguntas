package co.edu.unicauca.saberpro.state;

import co.edu.unicauca.saberpro.domain.model.Context;

/**
 * Patrón State: define el protocolo de transiciones del ciclo de vida
 * de una pregunta, eliminando el uso de estructuras condicionales (if/switch)
 * en el Contexto. Cada estado concreto habilita solo las transiciones válidas;
 * las demás quedan bloqueadas por defecto (comportamiento por omisión en la interfaz).
 */
public interface IQuestionState {

    default void enviarARevision(Context context) {
        throw new IllegalStateException(
                "Transición inválida: no se puede enviar a revisión desde el estado " + getNombre());
    }

    default void iniciarRevision(Context context) {
        throw new IllegalStateException(
                "Transición inválida: no se puede iniciar revisión desde el estado " + getNombre());
    }

    default void aprobar(Context context) {
        throw new IllegalStateException(
                "Transición inválida: no se puede aprobar desde el estado " + getNombre());
    }

    default void solicitarCorrecciones(Context context) {
        throw new IllegalStateException(
                "Transición inválida: no se pueden solicitar correcciones desde el estado " + getNombre());
    }

    String getNombre();
}
