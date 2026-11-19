=====================================================================
 PRUEBA TECNICA V9 - EJERCICIO 1: PRUEBA E2E DE COMPRA CON SERENITY BDD
 Autor: Santiago Sánchez
=====================================================================

1. OBJETIVO
-----------
Automatizar el flujo de compra de punta a punta (E2E) en la tienda
http://opencart.abstracta.us/ :

  1. Agregar dos productos al carrito (MacBook e iPhone)
  2. Visualizar el carrito
  3. Completar el checkout como invitado ("Guest Checkout")
  4. Finalizar la compra hasta la confirmacion "Your order has been placed!"


2. TECNOLOGIAS Y VERSIONES
--------------------------
  - Java JDK 17 o superior (probado con Eclipse Temurin JDK 25)
  - Apache Maven 3.9 o superior (probado con 3.10.1)
  - Serenity BDD 5.3.8 (serenity-core, serenity-cucumber, serenity-screenplay)
  - Cucumber 7.34.2 (escenarios en Gherkin, en español)
  - JUnit 6.0.3 (JUnit Platform Suite como runner)
  - Selenium WebDriver 4 (incluido en Serenity)
  - Google Chrome (version estable actual). El ChromeDriver correspondiente
    lo descarga automaticamente Selenium Manager.
  - Sistema operativo: Windows 11 (64 bits)

Dependencias declaradas en pom.xml; Maven las descarga solas.


3. PATRON DE DISEÑO: SCREENPLAY
-------------------------------
Se uso el patron Screenplay, el recomendado por Serenity BDD. En lugar de
Page Objects con metodos, se modela a un ACTOR (el cliente) que realiza
TAREAS y responde PREGUNTAS sobre lo que ve en pantalla.

  Feature (Gherkin)   ->  QUE se prueba, en lenguaje de negocio
  Step definitions    ->  traducen cada paso del feature a tareas del actor
  Tasks               ->  COMO se realiza cada accion de negocio
  Questions           ->  QUE se verifica en pantalla
  UI (Targets)        ->  DONDE estan los elementos (selectores)
  Models              ->  datos de prueba (cliente)


4. ESTRUCTURA DEL PROYECTO
--------------------------
01-e2e-opencart-serenity/
  pom.xml
  src/test/resources/
    features/compra/compra_invitado.feature   Escenario en Gherkin
    serenity.conf                             Navegador, URL base, esperas
    junit-platform.properties                 Configuracion de Cucumber
    logback-test.xml                          Nivel de logs
  src/test/java/opencart/
    CucumberTestSuite.java                    Runner (JUnit Platform Suite)
    stepdefinitions/CompraStepDefinitions.java
    tasks/        AgregarAlCarrito, VerCarrito, CompletarCheckoutInvitado,
                  ConfirmarOrden
    questions/    ProductosEnCarrito, MensajeConfirmacion
    ui/           PaginaInicio, PaginaProducto, PaginaCarrito,
                  PaginaCheckout, PaginaConfirmacion
    models/       Cliente
  reports/                                    Copia del reporte de la ultima ejecucion
  readme.txt                                  Este archivo
  conclusiones.txt                            Hallazgos y conclusiones


5. INSTALACION DE PRERREQUISITOS
--------------------------------
Paso 1. Instalar Java JDK 17+:
    https://adoptium.net/temurin/releases/?version=17
    (en el instalador marcar "Set JAVA_HOME" y "Add to PATH")

Paso 2. Instalar Maven:
    https://maven.apache.org/download.cgi  (Binary zip archive)
    Descomprimir y agregar la carpeta bin al PATH.

Paso 3. Tener Google Chrome instalado (version estable).

Paso 4. Verificar en una terminal nueva:
    java -version
    mvn -v


6. EJECUCION (PASO A PASO)
--------------------------
Paso 1. Abrir una terminal en la carpeta del ejercicio:
    cd 01-e2e-opencart-serenity

Paso 2. Ejecutar la prueba (se abre Chrome y se ve el flujo):
    mvn clean verify

  La primera vez Maven descarga las dependencias (puede tardar unos
  minutos). Duracion de la prueba: ~25 segundos.

Paso 3 (opcional). Ejecutar sin abrir la ventana del navegador:
    mvn clean verify "-Dheadless.mode=true"

Resultado esperado en consola:
    Tests run: 1, Failures: 0, Errors: 0, Skipped: 0
    BUILD SUCCESS

Nota: se usa "mvn verify" (no "mvn test") porque las pruebas E2E corren
en la fase integration-test con el plugin failsafe, y el reporte de
Serenity se genera despues, en la fase post-integration-test.


7. REPORTES
-----------
Serenity genera un reporte HTML con cada paso, su duracion y una captura
de pantalla de cada accion:
    target/site/serenity/index.html            Reporte completo
    target/site/serenity/serenity-summary.html Resumen en una pagina

Abrirlo en el navegador (Windows):
    start target/site/serenity/index.html

La carpeta target/ se regenera en cada ejecucion y no se versiona; en
reports/ se incluye una copia del reporte de la ultima ejecucion como
evidencia.
