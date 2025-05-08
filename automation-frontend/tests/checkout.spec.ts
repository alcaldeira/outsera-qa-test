import { test, expect } from '@playwright/test';
import { LoginPage } from '../pages/LoginPage';
import { ProductsPage } from '../pages/ProductsPage';
import { CartPage } from '../pages/CartPage';
import { CheckoutPage } from '../pages/CheckoutPage';
import { CheckoutCompletePage } from '../pages/CheckoutCompletePage';

let loginPage: LoginPage;
let productsPage: ProductsPage;
let cartPage: CartPage;
let checkoutPage: CheckoutPage;
let completePage: CheckoutCompletePage;

test.beforeEach(async ({ page }) => {
  loginPage = new LoginPage(page);
  productsPage = new ProductsPage(page);
  cartPage = new CartPage(page);
  checkoutPage = new CheckoutPage(page);
  completePage = new CheckoutCompletePage(page);

  await loginPage.goto();

});

test('Check purchase of search', async () => {
  await loginPage.login('standard_user', 'secret_sauce');
  await productsPage.addBackpackToCart();
  await productsPage.goToCart();
  await cartPage.proceedToCheckout();
  await checkoutPage.fillInfo('Anderson', 'Caldeira', '01001-000');
  await checkoutPage.finishOrder();
  await completePage.expectOrderSuccess();
});

test('Check remove item to cart', async () => {
  await loginPage.login('standard_user', 'secret_sauce');
  await productsPage.addBackpackToCart();
  await productsPage.removeItemFromCart();
});

test('Order products from highest to lowest price', async () => {
  await loginPage.login('standard_user', 'secret_sauce');
  await productsPage.sortByPriceHighToLow();
  await productsPage.assertProductsSortedByHighToLow();
});