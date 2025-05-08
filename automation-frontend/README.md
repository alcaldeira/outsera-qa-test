
---
## 📄 `automation-frontend/README.md` – Playwright
```markdown

# Playwright Tests - Frontend

Este projeto realiza testes automatizados E2E na aplicação [SauceDemo](https://www.saucedemo.com).

## ✅ Funcionalidades testadas

- Login com credenciais válidas
- Adicionar produto ao carrinho
- Remover produto do carrinho
- Finalizar compra
- Ordenação de produtos por preço

## 📁 Estrutura

- `pages/`: Page Objects
- `tests/`: Casos de teste
- `utils/`: Utilitários
- `playwright.config.js`: Configuração base

## ▶️ Como executar

```bash
npm install
npx playwright test
```

## ▶️ Execução no gitAction
Para executar o teste de E2E dentro do gitAction 
Acesse o link https://github.com/alcaldeira/outsera-qa-test/actions
Executa a action [Playwright Tests - Frontend]