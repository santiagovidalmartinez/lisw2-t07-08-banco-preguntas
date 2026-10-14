package co.edu.unicauca.saberpro;

import co.edu.unicauca.saberpro.builder.QuestionBuilder;
import co.edu.unicauca.saberpro.domain.model.Context;
import co.edu.unicauca.saberpro.domain.model.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas de PARTE 2: ciclo de vida con patrón State.
 */
class QuestionStateTest {

    private Context contexto;

    @BeforeEach
    void setUp() {
        Question pregunta = new QuestionBuilder()
                .setEnunciado("¿Pregunta de prueba?")
                .addOpcion("A", true)
                .addOpcion("B", false)
                .addOpcion("C", false)
                .addOpcion("D", false)
                .setJustificacion("A es correcta.")
                .setClasificacion("Lectura Critica")
                .build();
        contexto = new Context(pregunta, "autor1");
    }

    @Test
    void estadoInicialEsBorrador() {
        assertEquals("BORRADOR", contexto.getNombreEstado());
    }

    @Test
    void transicionCompletaDelCicloDeVidaHastaAprobada() {
        contexto.enviarARevision();
        assertEquals("PENDIENTE_DE_REVISION", contexto.getNombreEstado());

        contexto.iniciarRevision();
        assertEquals("EN_REVISION", contexto.getNombreEstado());

        contexto.aprobar();
        assertEquals("APROBADA", contexto.getNombreEstado());
    }

    @Test
    void revisionPuedeDevolverLaPreguntaABorradorConCorrecciones() {
        contexto.enviarARevision();
        contexto.iniciarRevision();
        contexto.solicitarCorrecciones();

        assertEquals("BORRADOR", contexto.getNombreEstado());
    }

    @Test
    void noSePuedeAprobarDirectamenteDesdeBorrador() {
        assertThrows(IllegalStateException.class, () -> contexto.aprobar());
    }

    @Test
    void noSePuedeEnviarARevisionDesdeEstadoAprobado() {
        contexto.enviarARevision();
        contexto.iniciarRevision();
        contexto.aprobar();

        assertThrows(IllegalStateException.class, () -> contexto.enviarARevision());
    }

    @Test
    void noSePuedeIniciarRevisionDirectamenteDesdeBorrador() {
        assertThrows(IllegalStateException.class, () -> contexto.iniciarRevision());
    }
}
