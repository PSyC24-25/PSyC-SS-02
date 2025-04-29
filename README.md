# PSyC-SS-02

## Pasos para ejecutar:

  1. Establecer la conexión con la base de datos, crear una base de datos "cine".
  2. En la carpeta raíz del proyecto (cine). Desde la consola ejecutar `mvn clean install`*.
  3. En la consola ejecutar `mvn spring-boot:run`.
  4. Abrir el navegador **"http://localhost:8080/"**.

*Esto ejecetura todos los tests, para compilar sin hacer los tests: `mvn clean install -DskipTests`.

## Tests: 

 1. Para ejecutar los tests: `mvn test`. (Se debe haber establecido la conexión con la base de datos)
 2. `mvn jacoco:report` para obtener el análisis de los test en **"target/site/jacoco/index.html"**
 3. Para ejecutar solo los tests de rendimiento: `mvn -Dtest=PerformanceTest test`. Resultados en: **"target/reports/perf-report.html"**
