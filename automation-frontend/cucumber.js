module.exports = {
  default: [
    '--require-module ts-node/register',
    '--require-module tsconfig-paths/register',
    '--require features/steps/**/*.ts',
    '--require features/support/**/*.ts',
    '--format json:reports/cucumber-report.json',
    'features/**/*.feature'
  ].join(' ')
};
