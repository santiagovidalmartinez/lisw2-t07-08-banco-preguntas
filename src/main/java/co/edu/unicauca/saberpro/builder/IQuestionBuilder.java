package co.edu.unicauca.saberpro.builder;

import co.edu.unicauca.saberpro.domain.model.Question;

/**
 * Patrón Builder: define los pasos para ensamblar secuencialmente los
 * sub-objetos de una Question (Contexto, Enunciado, Opciones, Justificación,
 * Clasificación y Tipo). Interfaz fluida: cada paso retorna el propio builder.
 */
public interface IQuestionBuilder {

    IQuestionBuilder setContexto(String contexto);

    IQuestionBuilder setEnunciado(String enunciado);

    IQuestionBuilder addOpcion(String texto, boolean esCorrecta);

    IQuestionBuilder setJustificacion(String justificacion);

    IQuestionBuilder setClasificacion(String clasificacion);

    IQuestionBuilder setTipo(Question.TipoPregunta tipo);

    /**
     * Ensambla la Question final. Debe validar que existan exactamente
     * 4 opciones y exactamente 1 respuesta correcta.
     */
    Question build();
}
