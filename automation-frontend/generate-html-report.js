const reporter = require('multiple-cucumber-html-reporter');

reporter.generate({
  jsonDir: './reports',
  reportPath: './reports/html',
  displayDuration: true,
  openReportInBrowser: false,
  pageTitle: 'Relatório de Testes Automatizados',
  metadata: {
    browser: {
      name: 'chrome',
      version: '119'
    },
    device: 'Local QA',
    platform: {
      name: 'windows',
      version: '11'
    }
  },
  customData: {
    title: 'Dados da Execução',
    data: [
      { label: 'Projeto', value: 'playwright-saucedemo' },
      { label: 'Executado em', value: new Date().toLocaleString('pt-BR') }
    ]
  }
});
