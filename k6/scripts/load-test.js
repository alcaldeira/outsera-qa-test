
import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  stages: [
    { duration: '1m', target: 10 },
  //   { duration: '1m', target: 200 },
  //   { duration: '1m', target: 300 },
  //   { duration: '1m', target: 400 },
  //   { duration: '1m', target: 500 },
  //   { duration: '1m', target: 0 },
  ],
};

export default function () {
  const res = http.get('https://test-api.k6.io/public/crocodiles/');
  check(res, {
    'status is 200': (r) => r.status === 200,
  });
  sleep(1);
}
