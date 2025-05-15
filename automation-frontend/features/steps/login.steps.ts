
import { Given, When, Then } from '@cucumber/cucumber';
import { expect } from '@playwright/test';
import { LoginPage } from 'features/pages/LoginPage';
import { CustomWorld } from 'features/support/custom-world';
// import { page } from 'features/utils/hoocks';

let loginPage: LoginPage;

Given('the user is on the login page', async function (this: CustomWorld) {
  loginPage = new LoginPage(this.page);
  await loginPage.goto();
});

When('they login with username {string} and password {string}', async (username: string, password: string) => {
  await loginPage.login(username, password);
});

Then('they should see the products page', async () => {
  await loginPage.assertLoginSuccess();
});

Then('they should see the error message {string}', async (message: string) => {
  await loginPage.assertLoginError(message);
});
