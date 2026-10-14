package co.edu.unicauca.saberpro;

import co.edu.unicauca.saberpro.adapter.ExternalQuestionAdapter;
import co.edu.unicauca.saberpro.adapter.ExternalQuestionService;
import co.edu.unicauca.saberpro.builder.IQuestionBuilder;
import co.edu.unicauca.saberpro.builder.QuestionBuilder;
import co.edu.unicauca.saberpro.domain.model.Context;
import co.edu.unicauca.saberpro.domain.model.Question;
import co.edu.unicauca.saberpro.facade.QuestionBankFacade;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de PARTE 1: Builder, Adapter y Facade.
 */
class QuestionBuilderTest {

    @Test
    void builder_construyeExitosamenteConCuatroOpcionesYUnaCorrecta() {
        IQuestionBuilder builder = new QuestionBuilder();
        Question pregunta = builder.setTipo(Question.TipoPregunta.SELECCION_MULTIPLE)
                .setContexto("Contexto de prueba")
                .setEnunciado("¿Cuál es la capital de Colombia?")
                .addOpcion("Medellín", false)
                .addOpcion("Bogotá", true)
                .addOpcion("Cali", false)
                .addOpcion("Cartagena", false)
                .setJustificacion("Bogotá es la capital.")
                .setClasificacion("Lectura Critica")
                .build();

        assertEquals(4, pregunta.getOpciones().size());
        assertEquals("Bogotá", pregunta.getOpcionCorrecta().getTexto());
        assertEquals(Question.TipoPregunta.SELECCION_MULTIPLE, pregunta.getTipo());
    }

    @Test
    void builder_fallaSiNoHayExactamenteCuatroOpciones() {
        IQuestionBuilder builder = new QuestionBuilder();
        builder.setEnunciado("Pregunta incompleta")
                .addOpcion("A", true)
                .addOpcion("B", false);

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void builder_fallaSiNoHayExactamenteUnaRespuestaCorrecta() {
        IQuestionBuilder builder = new QuestionBuilder();
        builder.setEnunciado("Pregunta con dos correctas")
                .addOpcion("A", true)
                .addOpcion("B", true)
                .addOpcion("C", false)
                .addOpcion("D", false);

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void builder_fallaSiNingunaOpcionEsCorrecta() {
        IQuestionBuilder builder = new QuestionBuilder();
        builder.setEnunciado("Pregunta sin correcta")
                .addOpcion("A", false)
                .addOpcion("B", false)
                .addOpcion("C", false)
                .addOpcion("D", false);

        assertThrows(IllegalStateException.class, builder::build);
    }

    @Test
    void adapter_traduceFormatoExternoAQuestionDelDominio() {
        ExternalQuestionAdapter adapter = new ExternalQuestionAdapter(new ExternalQuestionService());
        Question pregunta = adapter.obtenerPregunta("EXT-001");

        assertNotNull(pregunta);
        assertEquals(4, pregunta.getOpciones().size());
        assertNotNull(pregunta.getOpcionCorrecta());
        assertEquals("Lectura Critica", pregunta.getClasificacion());
    }

    @Test
    void adapter_lanzaExcepcionParaIdExternoInexistente() {
        ExternalQuestionAdapter adapter = new ExternalQuestionAdapter(new ExternalQuestionService());
        assertThrows(IllegalArgumentException.class, () -> adapter.obtenerPregunta("EXT-NO-EXISTE"));
    }

    @Test
    void facade_creaPreguntaYLaRegistraEnElBanco() {
        QuestionBankFacade facade = new QuestionBankFacade();
        Context contexto = facade.crearPregunta(
                "Contexto",
                "¿2+2?",
                new String[]{"3", "4", "5", "6"},
                1,
                "La suma de 2+2 es 4.",
                "Razonamiento Cuantitativo",
                Question.TipoPregunta.SELECCION_MULTIPLE,
                "autor1");

        assertEquals(1, facade.listarPreguntas().size());
        assertEquals("BORRADOR", contexto.getNombreEstado());
    }

    @Test
    void facade_importaPreguntaExternaYLaRegistraEnElBanco() {
        QuestionBankFacade facade = new QuestionBankFacade();
        Context contexto = facade.importarPreguntaExterna("EXT-002", "autor1");

        assertEquals(1, facade.listarPreguntas().size());
        assertEquals("Razonamiento Cuantitativo", contexto.getQuestion().getClasificacion());
    }
}
