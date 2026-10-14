package co.edu.unicauca.saberpro.builder;

import co.edu.unicauca.saberpro.domain.model.Question;

/**
 * Director del patrón Builder. Encapsula la receta de construcción estándar
 * de una pregunta de Selección Múltiple del Banco Saber PRO, desacoplando
 * al cliente del orden exacto de llamadas sobre el builder.
 */
public class QuestionDirector {

    private final IQuestionBuilder builder;

    public QuestionDirector(IQuestionBuilder builder) {
        this.builder = builder;
    }

    public Question construirPreguntaSeleccionMultiple(String contexto,
                                                         String enunciado,
                                                         String[] opciones,
                                                         int indiceCorrecta,
                                                         String justificacion,
                                                         String clasificacion) {
        builder.setTipo(Question.TipoPregunta.SELECCION_MULTIPLE)
                .setContexto(contexto)
                .setEnunciado(enunciado)
                .setJustificacion(justificacion)
                .setClasificacion(clasificacion);

        for (int i = 0; i < opciones.length; i++) {
            builder.addOpcion(opciones[i], i == indiceCorrecta);
        }
        return builder.build();
    }
}
