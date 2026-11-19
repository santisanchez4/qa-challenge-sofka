// Perfiles de carga. Se elige con: k6 run -e PROFILE=<baseline|load|stress> tests/login-load.js
// Se usan executors de tasa de llegada (modelo abierto): k6 garantiza las TPS
// aunque el servidor se vuelva lento, en lugar de que la carga baje sola.

const scenarios = {
  // Linea base: carga baja para conocer el comportamiento "sin presion".
  baseline: {
    executor: 'constant-arrival-rate',
    rate: 2,
    timeUnit: '1s',
    duration: '1m',
    preAllocatedVUs: 5,
    maxVUs: 10,
  },

  // Prueba de carga pedida: 20 TPS sostenidas.
  load: {
    executor: 'ramping-arrival-rate',
    startRate: 0,
    timeUnit: '1s',
    preAllocatedVUs: 30,
    maxVUs: 100,
    stages: [
      { target: 20, duration: '30s' }, // ramp-up
      { target: 20, duration: '3m' },  // estado estable a 20 TPS
      { target: 0, duration: '10s' },  // ramp-down
    ],
  },

  // Estres liviano por escalones: busca donde empieza a degradarse.
  stress: {
    executor: 'ramping-arrival-rate',
    startRate: 0,
    timeUnit: '1s',
    preAllocatedVUs: 50,
    maxVUs: 200,
    stages: [
      { target: 20, duration: '30s' },
      { target: 20, duration: '1m' },
      { target: 30, duration: '30s' },
      { target: 30, duration: '1m' },
      { target: 40, duration: '30s' },
      { target: 40, duration: '1m' },
      { target: 0, duration: '10s' },
    ],
  },
};

// Criterios de aceptacion del ejercicio, como thresholds (pass/fail automatico).
export const thresholds = {
  // Tiempo de respuesta maximo 1,5 s: el 95% de las peticiones debe cumplirlo.
  http_req_duration: ['p(95)<1500'],
  // Tasa de error menor al 3%.
  http_req_failed: ['rate<0.03'],
  // Al menos el 97% de las validaciones funcionales deben pasar.
  checks: ['rate>0.97'],
  // Si k6 no pudo lanzar iteraciones por falta de VUs, no se alcanzaron las TPS.
  dropped_iterations: ['count==0'],
};

export function getOptions(profile) {
  const scenario = scenarios[profile];
  if (!scenario) {
    throw new Error(`PROFILE invalido: "${profile}". Usar: ${Object.keys(scenarios).join(', ')}`);
  }
  return {
    scenarios: { [profile]: scenario },
    thresholds,
    summaryTrendStats: ['avg', 'min', 'med', 'max', 'p(90)', 'p(95)', 'p(99)'],
  };
}
