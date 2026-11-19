=====================================================================
 PERFORMANCE - EJERCICIO 1: PRUEBA DE CARGA DEL SERVICIO DE LOGIN
 Autor: Santiago
=====================================================================
 
1. OBJETIVO
-----------
Prueba de carga sobre el servicio de login de Fake Store API
(POST https://fakestoreapi.com/auth/login), con usuarios parametrizados
desde un archivo CSV.
 
Criterios de aceptacion:
  - Alcanzar al menos 20 TPS.
  - Tiempo de respuesta maximo de 1,5 segundos.
  - Tasa de error menor al 3% del total de peticiones.
 
 
2. TECNOLOGIAS Y VERSIONES
--------------------------
  - k6 v2.4.0 (Grafana k6)          https://github.com/grafana/k6/releases
  - Sistema operativo: Windows 11 (64 bits), PowerShell 5.1
  - Libreria remota k6-summary 0.1.0 (jslib.k6.io), se descarga sola
    al ejecutar. Requiere conexion a internet.
 
No se necesita Node.js, npm ni otra dependencia: k6 es un unico binario.
 
 
3. ESTRUCTURA DEL PROYECTO
--------------------------
03-performance-k6-login/
  config/options.js     Perfiles de carga (baseline, load, stress) y thresholds
  data/users.csv        Usuarios parametrizados (user,passwd)
  helpers/auth.js       Peticion de login reutilizable + validaciones (checks)
  tests/login-load.js   Script principal (carga del CSV, ejecucion, reportes)
  reports/baseline/     Resultados de las 3 corridas de linea base
  reports/load/         Resultados de las 3 corridas de carga (20 TPS)
  reports/stress/       Resultado de la corrida de estres
  readme.txt            Este archivo
  conclusiones.txt      Hallazgos y conclusiones
 
 
4. INSTALACION DE k6
--------------------
Windows (opcion A, winget):
    winget install k6 --source winget
 
Windows (opcion B, instalador):
    Descargar k6-v2.4.0-windows-amd64.msi desde
    https://github.com/grafana/k6/releases/tag/v2.4.0 e instalarlo.
 
macOS:   brew install k6
Linux:   ver https://grafana.com/docs/k6/latest/set-up/install-k6/
 
Verificar:
    k6 version
    -> k6.exe v2.4.0 (...)
 
 
5. PERFILES DE PRUEBA
---------------------
Se usa un modelo abierto (executors de tasa de llegada): k6 lanza
iteraciones a una tasa fija sin importar cuanto tarde el servidor, por lo
que las TPS se garantizan por diseno.
 
  PERFIL     EXECUTOR               CARGA                           DURACION
  baseline   constant-arrival-rate  2 TPS                           1 min
  load       ramping-arrival-rate   0->20 TPS (30s), 20 TPS (3min),  3m40s
                                    20->0 (10s)
  stress     ramping-arrival-rate   escalones 20 -> 30 -> 40 TPS     4m40s
                                    (1 min cada uno, rampas de 30s)
 
Thresholds (criterio pass/fail automatico, en config/options.js):
  - http_req_duration   p(95) < 1500 ms
  - http_req_failed     rate  < 3%
  - checks              rate  > 97%
  - dropped_iterations  count == 0  (confirma que se alcanzaron las TPS)
 
Checks por peticion (en helpers/auth.js):
  - status es 201
  - la respuesta contiene token
  - tiempo de respuesta <= 1,5 s
 
 
6. EJECUCION (PASO A PASO)
--------------------------
Paso 1. Abrir una terminal en la carpeta del ejercicio:
    cd 03-performance-k6-login
 
Paso 2. (Opcional) Habilitar el reporte HTML del dashboard de k6.
  PowerShell:
    $env:K6_WEB_DASHBOARD="true"
    $env:K6_WEB_DASHBOARD_EXPORT="reports/load/load-run1.html"
  Linux / macOS (bash):
    export K6_WEB_DASHBOARD=true
    export K6_WEB_DASHBOARD_EXPORT=reports/load/load-run1.html
 
Paso 3. Ejecutar la prueba eligiendo perfil y numero de corrida:
    k6 run -e PROFILE=load -e RUN=1 tests/login-load.js
 
  Otros perfiles:
    k6 run -e PROFILE=baseline -e RUN=1 tests/login-load.js
    k6 run -e PROFILE=stress   -e RUN=1 tests/login-load.js
 
Paso 4 (opcional). Ejecutar las 3 corridas de un perfil seguidas (PowerShell):
    $env:K6_WEB_DASHBOARD="true"
    1..3 | ForEach-Object { $env:K6_WEB_DASHBOARD_EXPORT="reports/load/load-run$_.html"; k6 run -e PROFILE=load -e RUN=$_ tests/login-load.js }
 
Variables disponibles:
  PROFILE   baseline | load | stress    (por defecto: load)
  RUN       numero de corrida           (por defecto: 1)
  BASE_URL  URL base del servicio       (por defecto: https://fakestoreapi.com)
 
 
7. RESULTADOS Y REPORTES
------------------------
Por cada corrida se generan en reports/<perfil>/:
  <perfil>-runN.html           Dashboard grafico (abrir con el navegador)
  <perfil>-runN-summary.txt    Resumen de metricas en texto
  <perfil>-runN-summary.json   Datos completos en JSON
 
Codigo de salida de k6:
  0   todos los thresholds se cumplieron
  99  al menos un threshold fallo
 
Los hallazgos y conclusiones estan en conclusiones.txt.
 