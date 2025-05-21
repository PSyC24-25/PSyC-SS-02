# PSyC-SS-02 Aplicación de cine

## Descripción:
Esta aplicación permite la compra de entradas para las diferentes sesiones que tiene el cine. Los usuarios pueden visualizar la cartelera de películas y filtrarla por nombre o género, para poder efectuar los usuarios deben registrarse con un correo electrónico y también tendrán disponible una tienda para la compra de bebidas y comida.

## Documentación:
- Documentación creada con ([Doxygen](https://psyc24-25.github.io/PSyC-SS-02/)).
- Tests unitarios mediante ([JaCoCo](https://psyc24-25.github.io/PSyC-SS-02/jacoco)).

## Arquitectura:
Hemos creado esta aplicación mediante SpringBoot usando el patrón Modelo-Vista-Controlador en una estructura de 3 niveles (Controladores, servicios y repositorios).
- Modelo: Definición de entidades de la aplicación
- Vista: Frontend de la aplicación usando HTML y Thymeleaf
- Controlador: Maneja solicitudes HTTP de los usuarios
- Servicio: La capa lógica de la aplicación
- Respositorio: La capa que se encarga de la interacción con la base de datos

## Pasos para ejecutar:

  1. Establecer la conexión con la base de datos, crear una base de datos "cine".
  2. En la carpeta raíz del proyecto (cine). Desde la consola ejecutar `mvn clean install`*.
  3. En la consola ejecutar `mvn spring-boot:run`.
  4. Abrir el navegador **"http://localhost:8080/"**.

*Esto ejecetura todos los tests, para compilar sin hacer los tests: `mvn clean install -DskipTests`.

## Tests: 

 1. Para ejecutar los tests: `mvn test`. (Se debe haber establecido la conexión con la base de datos)
 2. Para ejecutar solo los tests de rendimiento: `mvn -Dtest=PerformanceTest test`. Resultados en: **"target/reports/perf-report.html"**
 3. Para ejecutar los tests de integración: `mvn -Pperformance integration-test`.
 4. `mvn jacoco:report` para obtener el análisis de los test en **"target/site/jacoco/index.html"**

## Crear documentación mediante Doxygen:
Para crear la documentación mediante Doxygen ejecutar `doxygen Doxyfile` en la carpeta raíz del programa. Esto generará toda la documentación dentro de la carpeta **"docs/html"**.
Las clases documentadas han sido:
* Entidades (Model)
* Capa de servicios
* Controlador
