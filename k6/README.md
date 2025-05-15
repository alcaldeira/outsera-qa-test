
---
## 📄 `k6/README.md` – K6 + Grafana
```markdown
# Load Testing com K6 + Grafana

Teste de performance simulando 500 usuários simultâneos por 5 minutos, com dados enviados para InfluxDB e visualizados no Grafana.

## 📁 Estrutura

- `scripts/load-test.js`: Script principal do teste
- `docker-compose.yml`: Subida do Grafana + InfluxDB
- `results/`: Pasta opcional para armazenar JSONs ou exportações

## ▶️ Como rodar o teste

# Instalar as Dependencias
```bash
cd k6

docker-compose up -d
```

# Rodar o Teste
```bash
k6 run /scripts/load-test.js
k6 run /scripts/load-test.js --env VUS=500 --env STEP=100 --env TIME=60000
```
VUS = Quantidade máxima de usuários
STEP = Quantidade por etapa
TIME = Tempo por etapa

# Acessar Dash Grafana
```bash
docker-compose up -d
docker run -v "%cd%/scripts:/scripts" -i grafana/k6 run -o influxdb=http://host.docker.internal:8086/k6 /scripts/load-test.js
```

## ▶️ Para acessar o relatório

http://localhost:3000
Usuário: admin
Senha: admin

## Config Database (em caso de necessidade)
Caso não apareça relatório será necessário configurar o dataBase

1. Clicar em Dashboards
2. Import
3. Digite o Dashboard ID: 2587 e clique em Load
4. Digite influxdb no Data Source

OBS: Caso não existe o influxdb cadastrado será necessário cadastra segue orientação

1. Selecione influxdb
2. Em URL digite http://host.docker.internal:8086
3. Em Database digite k6
4. Selecione o HTTP Method GET