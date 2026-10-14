package co.edu.unicauca.saberpro.adapter;

/**
 * Servicio externo (Adaptee) que simula una plataforma de terceros.
 * Retorna los reactivos en un formato de cadena cruda, incompatible con el
 * modelo de dominio del Banco de Preguntas Saber PRO (simula, por ejemplo,
 * una respuesta JSON ya aplanada a texto por otro sistema).
 */
public class ExternalQuestionService {

    /**
     * Formato crudo devuelto por el sistema externo:
     * CTX:...|STMT:...|OPT1:texto;boolean|OPT2:texto;boolean|OPT3:texto;boolean|OPT4:texto;boolean|JUST:...|TOPIC:...
     */
    public String fetchRawQuestion(String externalId) {
        switch (externalId) {
            case "EXT-001":
                return "CTX:Un texto narrativo describe el viaje de un explorador por la Amazonía."
                        + "|STMT:¿Cuál es la idea principal del texto?"
                        + "|OPT1:El explorador documenta la flora y fauna local.;false"
                        + "|OPT2:El explorador relata su travesía y los retos del viaje.;true"
                        + "|OPT3:El explorador describe la gastronomía regional.;false"
                        + "|OPT4:El explorador narra una leyenda indígena.;false"
                        + "|JUST:El texto se centra en la travesía y los obstáculos enfrentados durante el viaje."
                        + "|TOPIC:Lectura Critica";
            case "EXT-002":
                return "CTX:Se presenta una gráfica de barras con la producción agrícola anual."
                        + "|STMT:¿Qué cultivo tuvo la mayor producción en el año analizado?"
                        + "|OPT1:Café;false"
                        + "|OPT2:Maíz;false"
                        + "|OPT3:Caña de azúcar;true"
                        + "|OPT4:Papa;false"
                        + "|JUST:La barra de caña de azúcar es la de mayor altura en la gráfica."
                        + "|TOPIC:Razonamiento Cuantitativo";
            default:
                throw new IllegalArgumentException("El sistema externo no tiene un reactivo con id: " + externalId);
        }
    }
}
