# QA Automation & Performance Suite - Outsera

Este repositório contém um conjunto de automações de testes desenvolvidas para a prova técnica da Outsera, incluindo testes funcionais com Playwright e RestAssured, além de testes de performance com K6 integrados ao Grafana.

## 🧪 Estrutura dos Projetos

### ➤ `automation-frontend` - Testes E2E com Playwright
- Testes de login, adicionar/remover item e finalização de compra na [SauceDemo](https://www.saucedemo.com)
- Estrutura de Page Objects
- Report HTML ao final do teste
- Segurança aplicada via `.env` (exclusão de credenciais hardcoded)
- CI com GitHub Actions

### ➤ `automation-backend` - Testes de API com RestAssured
- Validação de endpoints públicos
- Verificação de status code, tempo de resposta e payload
- Estrutura em Java com Maven
- API pública utilizada https://dummyjson.com/

### ➤ `k6` - Testes de carga com K6
- Testes simulando até 500 usuários simultâneos
- Conectado ao Grafana com InfluxDB via Docker
- Dashboard com métricas de requisições, erros e tempo médio

---

## 🚀 Como Executar

### 📦 Playwright

```bash
cd automation-frontend
npm install
npx playwright test
```

### 📦 RestAssured

```bash
cd automation-backend
mvn test
```

### 📦 RestAssured

```bash
cd k6
docker-compose up -d
docker run -v "%cd%/scripts:/scripts" -i grafana/k6 run -o influxdb=http://host.docker.internal:8086/k6 /scripts/load-test.js
```

