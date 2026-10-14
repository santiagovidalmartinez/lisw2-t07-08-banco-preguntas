package co.edu.unicauca.saberpro.templatemethod;

/**
 * Resultado producido por el algoritmo plantilla de QuestionEvaluator.
 */
public class ResultadoEvaluacion {

    private final double puntaje;
    private final String retroalimentacion;

    public ResultadoEvaluacion(double puntaje, String retroalimentacion) {
        this.puntaje = puntaje;
        this.retroalimentacion = retroalimentacion;
    }

    public double getPuntaje() {
        return puntaje;
    }

    public String getRetroalimentacion() {
        return retroalimentacion;
    }

    @Override
    public String toString() {
        return String.format("Puntaje: %.2f | %s", puntaje, retroalimentacion);
    }
}
