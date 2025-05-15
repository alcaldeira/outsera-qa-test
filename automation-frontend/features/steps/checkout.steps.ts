import { Given, When, Then } from '@cucumber/cucumber';
import { expect } from '@playwright/test';
import { LoginPage } from 'features/pages/LoginPage';
import { CheckoutPage } from 'features/pages/CheckoutPage';
import { CartPage } from 'features/pages/CartPage';
import { CustomWorld } from 'features/support/custom-world';

Given('the user is logged in', async function (this: CustomWorld) {
  this.loginPage = new LoginPage(this.page);
  await this.loginPage.goto();
  await this.loginPage.login('standard_user', 'secret_sauce');
  await this.page.pause()
});

When('the user adds an item to the cart', async function (this: CustomWorld) {
  const cartPage = new CartPage(this.page);
  
  await cartPage.proceedToCheckout();
});

When(
  'proceeds to checkout with first name {string}, last name {string}, and postal code {string}',
  async function (this: CustomWorld, first: string, last: string, zip: string) {
    this.checkoutPage = new CheckoutPage(this.page);
    await this.checkoutPage.fillInfo(first, last, zip);
    await this.checkoutPage.finishOrder();
  }
);

Then('the checkout should be completed successfully', async function (this: CustomWorld) {
  await expect(this.page.locator('.complete-header')).toHaveText('Thank you for your order!');
  await this.page.pause()
});