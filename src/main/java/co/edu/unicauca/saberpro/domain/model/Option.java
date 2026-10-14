package co.edu.unicauca.saberpro.domain.model;

/**
 * Representa una opción de respuesta para una pregunta de selección múltiple.
 */
public class Option {

    private final String texto;
    private final boolean correcta;

    public Option(String texto, boolean correcta) {
        this.texto = texto;
        this.correcta = correcta;
    }

    public String getTexto() {
        return texto;
    }

    public boolean isCorrecta() {
        return correcta;
    }

    @Override
    public String toString() {
        return texto + (correcta ? " [CORRECTA]" : "");
    }
}
