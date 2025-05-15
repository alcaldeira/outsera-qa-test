import { Page } from '@playwright/test';
import { IWorldOptions, setWorldConstructor, World } from '@cucumber/cucumber';
import { LoginPage } from 'features/pages/LoginPage';
import { CheckoutPage } from 'features/pages/CheckoutPage';

export class CustomWorld extends World {
  page!: Page;
  loginPage!: LoginPage;
  checkoutPage!: CheckoutPage;
}

setWorldConstructor(CustomWorld);