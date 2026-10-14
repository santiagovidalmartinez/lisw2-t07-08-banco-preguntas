package co.edu.unicauca.saberpro.facade;

import co.edu.unicauca.saberpro.adapter.ExternalQuestionAdapter;
import co.edu.unicauca.saberpro.adapter.ExternalQuestionService;
import co.edu.unicauca.saberpro.adapter.QuestionProvider;
import co.edu.unicauca.saberpro.builder.IQuestionBuilder;
import co.edu.unicauca.saberpro.builder.QuestionBuilder;
import co.edu.unicauca.saberpro.domain.model.Context;
import co.edu.unicauca.saberpro.domain.model.Question;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Patrón Facade: expone una interfaz unificada y simplificada hacia la
 * capa de presentación (GUI o Main), ocultando la coordinación entre el
 * subsistema de construcción (Builder), el repositorio en memoria, el
 * validador implícito del Builder y el importador externo (Adapter).
 */
public class QuestionBankFacade {

    private final List<Context> repositorio = new ArrayList<>();
    private final QuestionProvider importadorExterno;

    public QuestionBankFacade() {
        this.importadorExterno = new ExternalQuestionAdapter(new ExternalQuestionService());
    }

    /** Permite inyectar un importador alterno (útil para pruebas). */
    public QuestionBankFacade(QuestionProvider importadorExterno) {
        this.importadorExterno = importadorExterno;
    }

    /**
     * Crea y registra una pregunta nueva usando el patrón Builder,
     * sin que el cliente tenga que conocer QuestionBuilder directamente.
     */
    public Context crearPregunta(String contexto,
                                  String enunciado,
                                  String[] opciones,
                                  int indiceCorrecta,
                                  String justificacion,
                                  String clasificacion,
                                  Question.TipoPregunta tipo,
                                  String autor) {
        IQuestionBuilder builder = new QuestionBuilder();
        builder.setTipo(tipo)
                .setContexto(contexto)
                .setEnunciado(enunciado)
                .setJustificacion(justificacion)
                .setClasificacion(clasificacion);
        for (int i = 0; i < opciones.length; i++) {
            builder.addOpcion(opciones[i], i == indiceCorrecta);
        }
        Question pregunta = builder.build();
        return registrar(pregunta, autor);
    }

    /** Importa un reactivo externo mediante el Adapter y lo registra en el banco. */
    public Context importarPreguntaExterna(String externalId, String autor) {
        Question pregunta = importadorExterno.obtenerPregunta(externalId);
        return registrar(pregunta, autor);
    }

    public List<Context> listarPreguntas() {
        return Collections.unmodifiableList(repositorio);
    }

    public Optional<Context> buscarPorId(String id) {
        return repositorio.stream().filter(c -> c.getQuestion().getId().equals(id)).findFirst();
    }

    public void enviarARevision(String id) {
        buscarPorId(id).ifPresent(Context::enviarARevision);
    }

    public void iniciarRevision(String id) {
        buscarPorId(id).ifPresent(Context::iniciarRevision);
    }

    public void aprobar(String id) {
        buscarPorId(id).ifPresent(Context::aprobar);
    }

    public void solicitarCorrecciones(String id) {
        buscarPorId(id).ifPresent(Context::solicitarCorrecciones);
    }

    private Context registrar(Question pregunta, String autor) {
        Context contexto = new Context(pregunta, autor);
        repositorio.add(contexto);
        return contexto;
    }
}
