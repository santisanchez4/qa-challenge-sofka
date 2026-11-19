import { SharedArray } from 'k6/data';
import exec from 'k6/execution';
import { textSummary } from 'https://jslib.k6.io/k6-summary/0.1.0/index.js';
import { getOptions } from '../config/options.js';
import { login } from '../helpers/auth.js';

const PROFILE = __ENV.PROFILE || 'load';
const RUN = __ENV.RUN || '1';

// Usuarios parametrizados desde CSV. SharedArray los carga una sola vez
// en memoria y los comparte entre todos los VUs.
const users = new SharedArray('users', () =>
  open('../data/users.csv')
    .trim()
    .split(/\r?\n/)
    .slice(1) // saltear encabezado user,passwd
    .map((line) => {
      const [user, passwd] = line.split(',');
      return { user: user.trim(), passwd: passwd.trim() };
    })
);

export const options = getOptions(PROFILE);

export default function () {
  // Rotacion secuencial: cada iteracion usa el siguiente usuario del CSV.
  const u = users[exec.scenario.iterationInTest % users.length];
  login(u.user, u.passwd);
}

export function handleSummary(data) {
  // Reportes agrupados por tipo de prueba: reports/<perfil>/<perfil>-runN-summary.*
  const name = `${PROFILE}-run${RUN}`;
  return {
    stdout: textSummary(data, { indent: ' ', enableColors: true }),
    [`reports/${PROFILE}/${name}-summary.txt`]: textSummary(data, { indent: ' ', enableColors: false }),
    [`reports/${PROFILE}/${name}-summary.json`]: JSON.stringify(data, null, 2),
  };
}
