package co.edu.unicauca.saberpro;

import co.edu.unicauca.saberpro.builder.QuestionBuilder;
import co.edu.unicauca.saberpro.decorator.BasicQuestion;
import co.edu.unicauca.saberpro.decorator.FeedbackQuestion;
import co.edu.unicauca.saberpro.decorator.HighDifficultyQuestion;
import co.edu.unicauca.saberpro.decorator.QuestionComponent;
import co.edu.unicauca.saberpro.domain.model.Context;
import co.edu.unicauca.saberpro.domain.model.Question;
import co.edu.unicauca.saberpro.templatemethod.CaseStudyEvaluator;
import co.edu.unicauca.saberpro.templatemethod.MultipleChoiceEvaluator;
import co.edu.unicauca.saberpro.templatemethod.QuestionEvaluator;
import co.edu.unicauca.saberpro.templatemethod.ResultadoEvaluacion;
import co.edu.unicauca.saberpro.templatemethod.TrueFalseEvaluator;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de PARTE 2: Template Method y Decorator.
 */
class QuestionEvaluatorTest {

    private Context crearContextoSeleccionMultiple() {
        Question pregunta = new QuestionBuilder()
                .setEnunciado("¿Capital de Colombia?")
                .addOpcion("Medellín", false)
                .addOpcion("Bogotá", true)
                .addOpcion("Cali", false)
                .addOpcion("Cartagena", false)
                .setJustificacion("Bogotá es la capital de Colombia.")
                .setClasificacion("Lectura Critica")
                .build();
        return new Context(pregunta, "autor1");
    }

    @Test
    void templateMethod_multipleChoice_respuestaCorrectaDaPuntajeMaximo() {
        Context contexto = crearContextoSeleccionMultiple();
        QuestionEvaluator evaluador = new MultipleChoiceEvaluator();

        ResultadoEvaluacion resultado = evaluador.evaluar(contexto, List.of("Bogotá"));

        assertEquals(1.0, resultado.getPuntaje());
        assertTrue(resultado.getRetroalimentacion().toLowerCase().contains("correcta"));
    }

    @Test
    void templateMethod_multipleChoice_respuestaIncorrectaDaPuntajeCero() {
        Context contexto = crearContextoSeleccionMultiple();
        QuestionEvaluator evaluador = new MultipleChoiceEvaluator();

        ResultadoEvaluacion resultado = evaluador.evaluar(contexto, List.of("Cali"));

        assertEquals(0.0, resultado.getPuntaje());
    }

    @Test
    void templateMethod_multipleChoice_formatoInvalidoDaPuntajeCero() {
        Context contexto = crearContextoSeleccionMultiple();
        QuestionEvaluator evaluador = new MultipleChoiceEvaluator();

        ResultadoEvaluacion resultado = evaluador.evaluar(contexto, List.of());

        assertEquals(0.0, resultado.getPuntaje());
        assertTrue(resultado.getRetroalimentacion().toLowerCase().contains("formato"));
    }

    @Test
    void templateMethod_trueFalse_evaluaCorrectamente() {
        Question pregunta = new QuestionBuilder()
                .setEnunciado("¿El sol es una estrella? (V/F)")
                .addOpcion("true", true)
                .addOpcion("false", false)
                .addOpcion("N/A1", false)
                .addOpcion("N/A2", false)
                .setJustificacion("El sol es efectivamente una estrella.")
                .setClasificacion("Razonamiento Cuantitativo")
                .build();
        Context contexto = new Context(pregunta, "autor1");

        QuestionEvaluator evaluador = new TrueFalseEvaluator();
        ResultadoEvaluacion correcto = evaluador.evaluar(contexto, List.of("true"));
        ResultadoEvaluacion incorrecto = evaluador.evaluar(contexto, List.of("false"));

        assertEquals(1.0, correcto.getPuntaje());
        assertEquals(0.0, incorrecto.getPuntaje());
    }

    @Test
    void templateMethod_caseStudy_otorgaPuntajeParcialSegunCoincidencias() {
        Question pregunta = new QuestionBuilder()
                .setEnunciado("Analice el caso presentado.")
                .addOpcion("op1", true)
                .addOpcion("op2", false)
                .addOpcion("op3", false)
                .addOpcion("op4", false)
                .setJustificacion("El caso trata sobre sostenibilidad ambiental y energía renovable.")
                .setClasificacion("Comunicación Escrita")
                .build();
        Context contexto = new Context(pregunta, "autor1");

        QuestionEvaluator evaluador = new CaseStudyEvaluator();
        ResultadoEvaluacion resultado = evaluador.evaluar(contexto, List.of("sostenibilidad", "energía", "marketing"));

        assertTrue(resultado.getPuntaje() > 0.0 && resultado.getPuntaje() < 1.0);
    }

    @Test
    void decorator_basicQuestion_tienePuntajeBaseUno() {
        Context contexto = crearContextoSeleccionMultiple();
        QuestionComponent base = new BasicQuestion(contexto);

        assertEquals(1.0, base.getPuntajeBase());
    }

    @Test
    void decorator_highDifficulty_multiplicaElPuntajeBase() {
        Context contexto = crearContextoSeleccionMultiple();
        QuestionComponent base = new BasicQuestion(contexto);
        QuestionComponent dificil = new HighDifficultyQuestion(base);

        assertEquals(1.5, dificil.getPuntajeBase());
        assertTrue(dificil.getDescripcion().contains("Alta Dificultad"));
    }

    @Test
    void decorator_feedbackQuestion_agregaRetroalimentacionSinAlterarPuntaje() {
        Context contexto = crearContextoSeleccionMultiple();
        QuestionComponent base = new BasicQuestion(contexto);
        QuestionComponent conFeedback = new FeedbackQuestion(base, contexto);

        assertEquals(base.getPuntajeBase(), conFeedback.getPuntajeBase());
        assertTrue(conFeedback.getDescripcion().contains("Retroalimentación"));
    }

    @Test
    void decorator_combinaMultiplesDecoradoresEnCadena() {
        Context contexto = crearContextoSeleccionMultiple();
        QuestionComponent compuesto = new FeedbackQuestion(new HighDifficultyQuestion(new BasicQuestion(contexto)), contexto);

        assertEquals(1.5, compuesto.getPuntajeBase());
        assertTrue(compuesto.getDescripcion().contains("Alta Dificultad"));
        assertTrue(compuesto.getDescripcion().contains("Retroalimentación"));
    }
}
