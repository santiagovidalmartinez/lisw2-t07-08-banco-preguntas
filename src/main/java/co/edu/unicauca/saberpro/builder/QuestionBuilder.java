package co.edu.unicauca.saberpro.builder;

import co.edu.unicauca.saberpro.domain.model.Option;
import co.edu.unicauca.saberpro.domain.model.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementación concreta del patrón Builder para la entidad Pregunta.
 * Construcción fluida por pasos: setContexto().setEnunciado().addOpcion()...build()
 */
public class QuestionBuilder implements IQuestionBuilder {

    private String contexto;
    private String enunciado;
    private final List<Option> opciones = new ArrayList<>();
    private String justificacion;
    private String clasificacion;
    private Question.TipoPregunta tipo = Question.TipoPregunta.SELECCION_MULTIPLE;

    @Override
    public IQuestionBuilder setContexto(String contexto) {
        this.contexto = contexto;
        return this;
    }

    @Override
    public IQuestionBuilder setEnunciado(String enunciado) {
        this.enunciado = enunciado;
        return this;
    }

    @Override
    public IQuestionBuilder addOpcion(String texto, boolean esCorrecta) {
        this.opciones.add(new Option(texto, esCorrecta));
        return this;
    }

    @Override
    public IQuestionBuilder setJustificacion(String justificacion) {
        this.justificacion = justificacion;
        return this;
    }

    @Override
    public IQuestionBuilder setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
        return this;
    }

    @Override
    public IQuestionBuilder setTipo(Question.TipoPregunta tipo) {
        this.tipo = tipo;
        return this;
    }

    @Override
    public Question build() {
        if (enunciado == null || enunciado.isBlank()) {
            throw new IllegalStateException("No se puede construir la pregunta: falta el enunciado.");
        }
        if (opciones.size() != 4) {
            throw new IllegalStateException(
                    "No se puede construir la pregunta: se requieren exactamente 4 opciones, se recibieron " + opciones.size() + ".");
        }
        long correctas = opciones.stream().filter(Option::isCorrecta).count();
        if (correctas != 1) {
            throw new IllegalStateException(
                    "No se puede construir la pregunta: se requiere exactamente 1 respuesta correcta, se recibieron " + correctas + ".");
        }
        Question pregunta = new Question(contexto, enunciado, opciones, justificacion, clasificacion, tipo);
        reset();
        return pregunta;
    }

    private void reset() {
        this.contexto = null;
        this.enunciado = null;
        this.opciones.clear();
        this.justificacion = null;
        this.clasificacion = null;
        this.tipo = Question.TipoPregunta.SELECCION_MULTIPLE;
    }
}
