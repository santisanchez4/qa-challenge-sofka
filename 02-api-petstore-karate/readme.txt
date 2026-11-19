=====================================================================
 PRUEBA TECNICA V9 - EJERCICIO 2: PRUEBAS DE API REST CON KARATE
 Autor: Santiago Sánchez
=====================================================================

1. OBJETIVO
-----------
Automatizar el ciclo de vida completo de un usuario en la API PetStore
(https://petstore.swagger.io), identificando entradas, salidas,
validaciones y variables en cada caso:

  CASO 1  Crear un usuario                     POST   /v2/user
  CASO 2  Buscar el usuario creado             GET    /v2/user/{username}
  CASO 3  Actualizar el nombre y el correo     PUT    /v2/user/{username}
  CASO 4  Buscar el usuario actualizado        GET    /v2/user/{username}
  CASO 5  Eliminar el usuario                  DELETE /v2/user/{username}

Adicionales (valor agregado):
  - Verificar que el usuario eliminado ya no existe (404).
  - Escenario negativo: buscar un usuario inexistente (404).


2. TECNOLOGIAS Y VERSIONES
--------------------------
  - Java JDK 17 o superior (probado con Eclipse Temurin JDK 25)
  - Apache Maven 3.9 o superior (probado con 3.10.1)
  - Karate 1.5.2 (io.karatelabs:karate-junit5), se descarga con Maven
  - JUnit 5 (incluido en karate-junit5)
  - Sistema operativo: Windows 11 (64 bits)

Opcional: VS Code con la extension "Karate Runner" (Kirk Slota) para
ejecutar los .feature desde el editor.


3. ESTRUCTURA DEL PROYECTO
--------------------------
02-api-petstore-karate/
  pom.xml                                  Dependencias y configuracion Maven
  src/test/java/
    karate-config.js                       Configuracion global (baseUrl, timeouts)
    logback-test.xml                       Nivel de logs
    petstore/
      PetStoreRunner.java                  Runner JUnit 5
      user/
        user-crud.feature                  Escenarios de prueba (Gherkin)
        data/user.json                     Plantilla del usuario (entrada)
  reports/                                 Copia del reporte HTML de la ultima ejecucion
  readme.txt                               Este archivo
  conclusiones.txt                         Hallazgos y conclusiones


4. INSTALACION DE PRERREQUISITOS
--------------------------------
Paso 1. Instalar Java JDK 17+:
    https://adoptium.net/temurin/releases/?version=17
    (en el instalador marcar "Set JAVA_HOME" y "Add to PATH")

Paso 2. Instalar Maven:
    https://maven.apache.org/download.cgi  (Binary zip archive)
    Descomprimir y agregar la carpeta bin al PATH.

Paso 3. Verificar en una terminal nueva:
    java -version
    mvn -v


5. EJECUCION (PASO A PASO)
--------------------------
Paso 1. Abrir una terminal en la carpeta del ejercicio:
    cd 02-api-petstore-karate

Paso 2. Ejecutar todas las pruebas:
    mvn clean test

  La primera vez Maven descarga las dependencias (puede tardar 1-2 minutos).

Paso 3 (opcional). Ejecutar solo un grupo de escenarios por tag:
    mvn clean test "-Dkarate.options=--tags @crud"
    mvn clean test "-Dkarate.options=--tags @negative"

Resultado esperado en consola:
    scenarios:  2 | passed:  2 | failed:  0
    Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
    BUILD SUCCESS


6. REPORTES
-----------
Karate genera un reporte HTML en cada ejecucion:
    target/karate-reports/karate-summary.html

Abrirlo en el navegador (Windows):
    start target/karate-reports/karate-summary.html

El reporte muestra cada paso con el request enviado (entrada), el
response recibido (salida), las validaciones y los tiempos.
Haciendo clic en el feature "petstore/user/user-crud" se ve el detalle.

La carpeta target/ se regenera en cada ejecucion y no se versiona; en
reports/ se incluye una copia del reporte de la ultima ejecucion como
evidencia.
