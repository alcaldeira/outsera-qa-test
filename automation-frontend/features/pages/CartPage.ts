import { Page } from '@playwright/test';

export class CartPage {
  private checkoutButton;
  private inventoryItem;
  private shoppingCart;

  constructor(private page: Page) {
    this.inventoryItem = page.locator('[data-test="add-to-cart-sauce-labs-backpack"]');
    this.shoppingCart = page.locator('.shopping_cart_link');
    this.checkoutButton = page.locator('[data-test="checkout"]');
  }

  async proceedToCheckout() {
    await this.inventoryItem.click();
    await this.shoppingCart.click();
    await this.checkoutButton.click();
  }
}
