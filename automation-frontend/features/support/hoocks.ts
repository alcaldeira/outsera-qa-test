import { setDefaultTimeout, Before, After, BeforeAll, AfterAll } from '@cucumber/cucumber';
import { chromium, Browser } from '@playwright/test';
import { CustomWorld } from './custom-world';
import fs from 'fs';
import path from 'path';

let browser: Browser;

setDefaultTimeout(60 * 1000);

BeforeAll(async () => {
  const isHeadless = process.env.HEADLESS !== 'false';
  const isDebug = process.env.DEBUG === 'true';

  if (process.env.PWDEBUG === '1') {
    browser = await chromium.launch({
      headless: false,
      args: ['--remote-debugging-port=9222']
    });
  } else {
    browser = await chromium.launch({
      headless: isHeadless,
      slowMo: isDebug ? 500 : 0,
      devtools: isDebug
    });
}
});

AfterAll(async () => {
  await browser.close();
});

Before(async function (this: CustomWorld) {
  this.page = await browser.newPage();
});

After(async function (this: CustomWorld, scenario) {
  if (scenario.result?.status === 'FAILED' && this.page) {
    const screenshot = await this.page.screenshot();
    const screenshotDir = path.resolve('reports/screenshots');
    const screenshotName = scenario.pickle.name.replace(/[^a-zA-Z0-9]/g, '_') + '.png';
    const screenshotPath = path.join(screenshotDir, screenshotName);

    fs.mkdirSync(screenshotDir, { recursive: true });
    fs.writeFileSync(screenshotPath, screenshot);
    this.attach(screenshot, 'image/png');
  }

  await this.page.close();
});
