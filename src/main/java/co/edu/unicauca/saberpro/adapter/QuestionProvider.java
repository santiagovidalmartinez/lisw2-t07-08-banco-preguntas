package co.edu.unicauca.saberpro.adapter;

import co.edu.unicauca.saberpro.domain.model.Question;

/**
 * Interfaz objetivo (Target) esperada por el Banco de Preguntas Saber PRO
 * para importar reactivos, sin conocer el formato interno de ningún
 * sistema externo concreto.
 */
public interface QuestionProvider {

    Question obtenerPregunta(String externalId);
}
