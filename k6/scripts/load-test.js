import http from 'k6/http';
import { check, sleep } from 'k6';

// Padrões
const totalVus = __ENV.VUS ? parseInt(__ENV.VUS) : 500;
const stepSize = __ENV.STEP ? parseInt(__ENV.STEP) : 100;
const timePerStepMs = __ENV.TIME ? parseInt(__ENV.TIME) : 60000; // default 1 minuto = 60000ms

function formatDuration(ms) {
  const seconds = Math.floor(ms / 1000) % 60;
  const minutes = Math.floor(ms / 60000);
  return `${minutes}m${seconds}s`;
}

function buildStages(total, step, timeMs) {
  const stages = [];
  for (let i = step; i <= total; i += step) {
    stages.push({
      duration: formatDuration(timeMs),
      target: i,
    });
  }
  return stages;
}

export const options = {
  stages: buildStages(totalVus, stepSize, timePerStepMs),
  thresholds: {
    http_req_duration: ['p(95)<500'],
    http_req_failed: ['rate<0.01'],
  },
};

export default function () {
  const res = http.get('https://test-api.k6.io/public/crocodiles/');
  check(res, {
    'status is 200': (r) => r.status === 200,
    'resposta em < 500ms': (r) => r.timings.duration < 500,
  });
  sleep(1);
}
