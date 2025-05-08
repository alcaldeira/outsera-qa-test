const fs = require('fs');
const path = require('path');

const reportPath = path.join(__dirname, '../../playwright-report/report.json');
const summaryPath = process.env.GITHUB_STEP_SUMMARY;

if (!fs.existsSync(reportPath)) {
  console.error('No Playwright report found.');
  process.exit(1);
}

const report = JSON.parse(fs.readFileSync(reportPath, 'utf-8'));

let passed = 0;
let failed = 0;
let skipped = 0;

for (const suite of report.suites) {
  for (const test of suite.specs) {
    for (const result of test.tests) {
      if (result.status === 'passed') passed++;
      else if (result.status === 'failed') failed++;
      else skipped++;
    }
  }
}

const summary = `
## 🧪 Playwright Test Summary

- ✅ Passed: **${passed}**
- ❌ Failed: **${failed}**
- ⏭️ Skipped: **${skipped}**
`;

fs.appendFileSync(summaryPath, summary);
