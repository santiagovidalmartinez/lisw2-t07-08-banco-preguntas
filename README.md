# Taller Integrado 07-08: Patrones de Diseño Creacionales, Estructurales y de Comportamiento

**Curso:** Ingeniería de Software II — Universidad del Cauca
**Proyecto base:** Banco de Preguntas Saber PRO
**Autor:** Santiago Vidal Martínez

## Patrones implementados

| Parte | Patrón | Clases clave |
|---|---|---|
| 1 (presencial) | Builder | `builder/IQuestionBuilder`, `builder/QuestionBuilder`, `builder/QuestionDirector` |
| 1 (presencial) | Adapter | `adapter/ExternalQuestionService`, `adapter/QuestionProvider`, `adapter/ExternalQuestionAdapter` |
| 1 (presencial) | Facade | `facade/QuestionBankFacade` |
| 2 (autónoma) | State | `state/IQuestionState`, `DraftState`, `PendingReviewState`, `ReviewState`, `ApprovedState` |
| 2 (autónoma) | Template Method | `templatemethod/QuestionEvaluator`, `MultipleChoiceEvaluator`, `TrueFalseEvaluator`, `CaseStudyEvaluator` |
| 2 (autónoma) | Decorator | `decorator/QuestionComponent`, `BasicQuestion`, `QuestionDecorator`, `HighDifficultyQuestion`, `FeedbackQuestion` |

## Cómo compilar y ejecutar

### Opción A — Con Maven (si tienes acceso a internet / Maven Central)

```bash
mvn compile
mvn test
mvn exec:java -Dexec.mainClass="co.edu.unicauca.saberpro.ApplicationMain"
```

### Opción B — Sin Maven (javac directo, recomendado si el equipo no tiene internet)

Desde la raíz del proyecto (`lisw2_taller07_08_g1/`):

**Windows PowerShell:**
```powershell
mkdir target\classes -Force
Get-ChildItem -Recurse -Filter *.java src\main | ForEach-Object { $_.FullName } > sources.txt
javac -encoding UTF-8 -d target\classes (Get-Content sources.txt)
java -cp target\classes co.edu.unicauca.saberpro.ApplicationMain
```

**Linux / macOS:**
```bash
mkdir -p target/classes
find src/main -name "*.java" > sources.txt
javac -encoding UTF-8 -d target/classes @sources.txt
java -cp target/classes co.edu.unicauca.saberpro.ApplicationMain
```

### Ejecutar las pruebas JUnit 5 sin Maven

1. Descarga `junit-platform-console-standalone` (o instálalo con tu gestor de paquetes si tienes internet: `apt-get install junit5` en Linux).
2. Compila el código de pruebas:
   ```bash
   javac -encoding UTF-8 -cp "target/classes:junit-platform-console-standalone.jar" -d target/test-classes $(find src/test -name "*.java")
   ```
3. Ejecuta las pruebas:
   ```bash
   java -jar junit-platform-console-standalone.jar execute -cp target/classes -cp target/test-classes --scan-classpath --details=tree
   ```

**Resultado verificado en este entorno: 23/23 pruebas pasando (0 fallos).**

## Estructura del proyecto

```
lisw2_taller07_08_g1/
├── pom.xml
├── README.md
└── src/
    ├── main/java/co/edu/unicauca/saberpro/
    │   ├── ApplicationMain.java
    │   ├── builder/        (Builder)
    │   ├── facade/         (Facade)
    │   ├── adapter/        (Adapter)
    │   ├── state/          (State)
    │   ├── templatemethod/ (Template Method)
    │   ├── decorator/      (Decorator)
    │   └── domain/model/   (Question, Option, Context)
    └── test/java/co/edu/unicauca/saberpro/
        ├── QuestionBuilderTest.java    (Builder + Adapter + Facade)
        ├── QuestionStateTest.java      (State)
        └── QuestionEvaluatorTest.java  (Template Method + Decorator)
```

## Notas de diseño

- **Builder:** `QuestionBuilder.build()` valida estrictamente 4 opciones y exactamente 1 respuesta correcta antes de ensamblar la `Question`; `QuestionDirector` encapsula la receta estándar de construcción.
- **Adapter:** `ExternalQuestionAdapter` traduce el formato crudo (tipo clave-valor delimitado) del `ExternalQuestionService` (Adaptee) a la interfaz `QuestionProvider` (Target), reutilizando internamente el Builder para ensamblar la `Question` resultante.
- **Facade:** `QuestionBankFacade` es el único punto de entrada para la capa de presentación; coordina Builder, Adapter y el repositorio en memoria sin exponer sus detalles internos.
- **State:** el ciclo de vida (`Borrador → Pendiente de Revisión → En Revisión → Aprobada`, con retorno a Borrador si se solicitan correcciones) se modela completamente con polimorfismo: `IQuestionState` define transiciones por defecto que lanzan `IllegalStateException`, y cada estado concreto solo sobrescribe las transiciones que le son válidas. **No existe ningún `if/switch` sobre el estado actual.**
- **Template Method:** `QuestionEvaluator.evaluar()` es `final` y fija el algoritmo (recibir → validar formato → calcular puntaje → generar retroalimentación); las subclases concretas solo personalizan los pasos específicos de su tipo de pregunta.
- **Decorator:** `QuestionDecorator` permite envolver dinámicamente una pregunta (`BasicQuestion`) con `HighDifficultyQuestion` (multiplicador de puntaje) y `FeedbackQuestion` (retroalimentación detallada), combinables en cadena sin modificar la clase base.
