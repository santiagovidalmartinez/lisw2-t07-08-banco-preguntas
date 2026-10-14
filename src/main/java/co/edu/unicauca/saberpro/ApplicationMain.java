package co.edu.unicauca.saberpro;

import co.edu.unicauca.saberpro.decorator.BasicQuestion;
import co.edu.unicauca.saberpro.decorator.FeedbackQuestion;
import co.edu.unicauca.saberpro.decorator.HighDifficultyQuestion;
import co.edu.unicauca.saberpro.decorator.QuestionComponent;
import co.edu.unicauca.saberpro.domain.model.Context;
import co.edu.unicauca.saberpro.domain.model.Question;
import co.edu.unicauca.saberpro.facade.QuestionBankFacade;
import co.edu.unicauca.saberpro.templatemethod.MultipleChoiceEvaluator;
import co.edu.unicauca.saberpro.templatemethod.QuestionEvaluator;
import co.edu.unicauca.saberpro.templatemethod.ResultadoEvaluacion;

import java.util.List;

/**
 * Cliente de demostración del Taller Integrado 07-08.
 * Parte 1 (presencial): Builder + Adapter + Facade.
 * Parte 2 (autónoma): State + Template Method + Decorator.
 */
public class ApplicationMain {

    public static void main(String[] args) {
        System.out.println("=== BANCO DE PREGUNTAS SABER PRO — TALLER INTEGRADO 07-08 ===\n");

        QuestionBankFacade facade = new QuestionBankFacade();

        // ---------- PARTE 1: Builder + Facade ----------
        System.out.println(">>> PARTE 1: Construcción con Builder a través de la Facade");
        Context pregunta1 = facade.crearPregunta(
                "Un estudio analiza el consumo de energía renovable en Colombia.",
                "¿Cuál es la fuente de energía renovable con mayor crecimiento según el estudio?",
                new String[]{"Energía solar", "Energía eólica", "Energía geotérmica", "Biomasa"},
                0,
                "El estudio reporta que la energía solar tuvo el mayor crecimiento porcentual anual.",
                "Razonamiento Cuantitativo",
                Question.TipoPregunta.SELECCION_MULTIPLE,
                "autor1"
        );
        System.out.println("Pregunta creada: " + pregunta1);

        // ---------- PARTE 1: Adapter ----------
        System.out.println("\n>>> PARTE 1: Importación externa con Adapter a través de la Facade");
        Context pregunta2 = facade.importarPreguntaExterna("EXT-001", "autor1");
        System.out.println("Pregunta importada: " + pregunta2);

        System.out.println("\n>>> Banco de preguntas actual (" + facade.listarPreguntas().size() + " preguntas):");
        for (Context c : facade.listarPreguntas()) {
            System.out.println("  - " + c);
        }

        // ---------- PARTE 2: State ----------
        System.out.println("\n>>> PARTE 2: Ciclo de vida con State");
        System.out.println("Estado inicial: " + pregunta1.getNombreEstado());
        facade.enviarARevision(pregunta1.getQuestion().getId());
        System.out.println("Tras enviarARevision(): " + pregunta1.getNombreEstado());
        facade.iniciarRevision(pregunta1.getQuestion().getId());
        System.out.println("Tras iniciarRevision(): " + pregunta1.getNombreEstado());
        facade.aprobar(pregunta1.getQuestion().getId());
        System.out.println("Tras aprobar(): " + pregunta1.getNombreEstado());

        try {
            pregunta1.enviarARevision(); // transición inválida desde APROBADA
        } catch (IllegalStateException e) {
            System.out.println("Transición inválida bloqueada correctamente: " + e.getMessage());
        }

        // ---------- PARTE 2: Template Method ----------
        System.out.println("\n>>> PARTE 2: Evaluación con Template Method");
        QuestionEvaluator evaluador = new MultipleChoiceEvaluator();
        ResultadoEvaluacion resultadoOk = evaluador.evaluar(pregunta1, List.of("Energía solar"));
        System.out.println("Respuesta correcta -> " + resultadoOk);
        ResultadoEvaluacion resultadoFail = evaluador.evaluar(pregunta1, List.of("Biomasa"));
        System.out.println("Respuesta incorrecta -> " + resultadoFail);

        // ---------- PARTE 2: Decorator ----------
        System.out.println("\n>>> PARTE 2: Características dinámicas con Decorator");
        QuestionComponent base = new BasicQuestion(pregunta1);
        QuestionComponent dificil = new HighDifficultyQuestion(base);
        QuestionComponent dificilConFeedback = new FeedbackQuestion(dificil, pregunta1);

        System.out.println("Básica       -> " + base.getDescripcion() + " | Puntaje: " + base.getPuntajeBase());
        System.out.println("Alta dific.  -> " + dificil.getDescripcion() + " | Puntaje: " + dificil.getPuntajeBase());
        System.out.println("Con feedback -> " + dificilConFeedback.getDescripcion() + " | Puntaje: " + dificilConFeedback.getPuntajeBase());

        System.out.println("\n=== FIN DE LA DEMOSTRACIÓN ===");
    }
}
