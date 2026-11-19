function fn() {
  // Ambiente: mvn test -Dkarate.env=qa (por defecto: qa)
  var env = karate.env || 'qa';

  var config = {
    env: env,
    baseUrl: 'https://petstore.swagger.io/v2'
  };

  karate.configure('connectTimeout', 10000);
  karate.configure('readTimeout', 15000);
  // Request y response formateados en el log y en el reporte HTML
  karate.configure('logPrettyRequest', true);
  karate.configure('logPrettyResponse', true);

  return config;
}
