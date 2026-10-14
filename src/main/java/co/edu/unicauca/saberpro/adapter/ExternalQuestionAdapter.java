package co.edu.unicauca.saberpro.adapter;

import co.edu.unicauca.saberpro.builder.IQuestionBuilder;
import co.edu.unicauca.saberpro.builder.QuestionBuilder;
import co.edu.unicauca.saberpro.domain.model.Question;

/**
 * Patrón Adapter: adapta ExternalQuestionService (Adaptee, formato de
 * cadena cruda incompatible) a la interfaz QuestionProvider (Target)
 * esperada por el Banco de Preguntas Saber PRO. Internamente reutiliza
 * el patrón Builder para ensamblar la Question a partir del dato externo
 * ya traducido.
 */
public class ExternalQuestionAdapter implements QuestionProvider {

    private final ExternalQuestionService adaptee;

    public ExternalQuestionAdapter(ExternalQuestionService adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public Question obtenerPregunta(String externalId) {
        String raw = adaptee.fetchRawQuestion(externalId);
        return traducir(raw);
    }

    /**
     * Traduce el formato crudo del sistema externo al modelo de dominio,
     * delegando el ensamblaje final en el patrón Builder.
     */
    private Question traducir(String raw) {
        String[] campos = raw.split("\\|");
        String contexto = null;
        String enunciado = null;
        String justificacion = null;
        String clasificacion = null;

        IQuestionBuilder builder = new QuestionBuilder();
        builder.setTipo(Question.TipoPregunta.SELECCION_MULTIPLE);

        for (String campo : campos) {
            if (campo.startsWith("CTX:")) {
                contexto = campo.substring(4);
            } else if (campo.startsWith("STMT:")) {
                enunciado = campo.substring(5);
            } else if (campo.startsWith("JUST:")) {
                justificacion = campo.substring(5);
            } else if (campo.startsWith("TOPIC:")) {
                clasificacion = campo.substring(6);
            } else if (campo.startsWith("OPT")) {
                String contenido = campo.substring(campo.indexOf(':') + 1);
                String[] partes = contenido.split(";");
                String texto = partes[0];
                boolean esCorrecta = Boolean.parseBoolean(partes[1]);
                builder.addOpcion(texto, esCorrecta);
            }
        }

        builder.setContexto(contexto)
                .setEnunciado(enunciado)
                .setJustificacion(justificacion)
                .setClasificacion(clasificacion);

        return builder.build();
    }
}
