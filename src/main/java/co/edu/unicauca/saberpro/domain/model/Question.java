package co.edu.unicauca.saberpro.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Entidad compleja Pregunta del Banco de Preguntas Saber PRO.
 * Se construye exclusivamente a través del patrón Builder
 * (co.edu.unicauca.saberpro.builder.QuestionBuilder).
 */
public class Question {

    public enum TipoPregunta {
        SELECCION_MULTIPLE,
        VERDADERO_FALSO,
        CASO_ESTUDIO
    }

    private final String id;
    private final String contexto;
    private final String enunciado;
    private final List<Option> opciones;
    private final String justificacion;
    private final String clasificacion; // competencia Saber PRO
    private final TipoPregunta tipo;

    public Question(String contexto,
                     String enunciado,
                     List<Option> opciones,
                     String justificacion,
                     String clasificacion,
                     TipoPregunta tipo) {
        this.id = UUID.randomUUID().toString().substring(0, 8);
        this.contexto = contexto;
        this.enunciado = enunciado;
        this.opciones = new ArrayList<>(opciones);
        this.justificacion = justificacion;
        this.clasificacion = clasificacion;
        this.tipo = tipo;
    }

    public String getId() {
        return id;
    }

    public String getContexto() {
        return contexto;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public List<Option> getOpciones() {
        return new ArrayList<>(opciones);
    }

    public String getJustificacion() {
        return justificacion;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public TipoPregunta getTipo() {
        return tipo;
    }

    public Option getOpcionCorrecta() {
        return opciones.stream().filter(Option::isCorrecta).findFirst().orElse(null);
    }

    @Override
    public String toString() {
        return String.format("[%s] (%s - %s) %s", id, tipo, clasificacion, enunciado);
    }
}
