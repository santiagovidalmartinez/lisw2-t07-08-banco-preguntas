package co.edu.unicauca.saberpro.domain.model;

import co.edu.unicauca.saberpro.state.DraftState;
import co.edu.unicauca.saberpro.state.IQuestionState;

/**
 * Contexto del patrón State. Mantiene la referencia al estado actual del
 * ciclo de vida de una Question y delega en él cada operación de transición,
 * sin preguntar nunca "en qué estado estoy" mediante if/switch.
 */
public class Context {

    private final Question question;
    private final String autor;
    private IQuestionState estadoActual;

    public Context(Question question, String autor) {
        this.question = question;
        this.autor = autor;
        this.estadoActual = new DraftState();
    }

    public void setEstado(IQuestionState nuevoEstado) {
        this.estadoActual = nuevoEstado;
    }

    public IQuestionState getEstado() {
        return estadoActual;
    }

    public String getNombreEstado() {
        return estadoActual.getNombre();
    }

    public Question getQuestion() {
        return question;
    }

    public String getAutor() {
        return autor;
    }

    public void enviarARevision() {
        estadoActual.enviarARevision(this);
    }

    public void iniciarRevision() {
        estadoActual.iniciarRevision(this);
    }

    public void aprobar() {
        estadoActual.aprobar(this);
    }

    public void solicitarCorrecciones() {
        estadoActual.solicitarCorrecciones(this);
    }

    @Override
    public String toString() {
        return question.getId() + " (" + getNombreEstado() + ") - " + question.getEnunciado();
    }
}
