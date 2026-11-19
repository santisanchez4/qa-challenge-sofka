import http from 'k6/http';
import { check } from 'k6';

const BASE_URL = __ENV.BASE_URL || 'https://fakestoreapi.com';

// Equivalente al CURL entregado (--max-time 60 -> timeout 60s).
export function login(username, password) {
  const res = http.post(
    `${BASE_URL}/auth/login`,
    JSON.stringify({ username, password }),
    {
      headers: { 'Content-Type': 'application/json' },
      timeout: '60s',
      tags: { name: 'POST /auth/login' },
    }
  );

  check(res, {
    'status es 201': (r) => r.status === 201,
    'respuesta contiene token': (r) => {
      try {
        return typeof r.json('token') === 'string' && r.json('token').length > 0;
      } catch (e) {
        return false;
      }
    },
    'tiempo de respuesta <= 1.5s': (r) => r.timings.duration <= 1500,
  });

  return res;
}
