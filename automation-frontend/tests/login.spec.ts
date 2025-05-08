import { test } from '@playwright/test';
import { LoginPage } from '../pages/LoginPage';

let loginPage: LoginPage;

test.beforeEach(async ({ page }) => {
  loginPage = new LoginPage(page);

  await loginPage.goto();

});

test('Login Success', async ({ page }) => {
  await loginPage.login('standard_user', 'secret_sauce');
  await loginPage.assertLoginSuccess();
});

// Esse teste está com falha de forma intencional
test('Intentional invalid password ', async ({ page }) => {
  await loginPage.login('standard_user', '1231');
  await loginPage.assertLoginSuccess();
});

test('Login password invalid', async ({ page }) => {
  const loginPage = new LoginPage(page);

  await loginPage.goto();
  await loginPage.login('locked_out_user', '1221');
  await loginPage.assertLoginError('Epic sadface: Username and password do not match any user in this service');
});

test('Check if it is possible to log in without filling in the email', async ({ page }) => {
  const loginPage = new LoginPage(page);

  await loginPage.goto();
  await loginPage.login('locked_out_user', '1221');
  await loginPage.assertLoginError('Epic sadface: Username and password do not match any user in this service');
});