import { Page, expect } from '@playwright/test';

export class ProductsPage {
  private backpackAddButton;
  private removeItem;
  private cartIcon;
  private sortDropdown;
  private itemPrices;
  private cartBadge;

  constructor(private page: Page) {
    this.backpackAddButton = page.locator('[data-test="add-to-cart-sauce-labs-backpack"]');
    this.removeItem = page.locator('[data-test="shopping-cart-link"]');
    this.cartIcon = page.locator('.shopping_cart_link');
    this.sortDropdown = page.locator('[data-test="product-sort-container"]');
    this.itemPrices = page.locator('.inventory_item_price');
    this.cartBadge = page.locator('[data-test="remove-sauce-labs-backpack"]');
  }

  async addBackpackToCart() {
    await this.backpackAddButton.click();
  }

  async goToCart() {
    await this.cartIcon.click();
  }

  async sortByPriceHighToLow() {
    await this.sortDropdown.selectOption('Price (high to low)');
  }

  async removeItemFromCart() {
    await this.removeItem.click();
  }

  async assertRemoveCartItem() {
    await expect(this.page.locator('.cart_item')).toHaveCount(0);
  }

  async assertProductsSortedByHighToLow() {
    const pricesText = await this.itemPrices.allTextContents();
    const prices = pricesText.map(p => parseFloat(p.replace('$', '')));
    const sorted = [...prices].sort((a, b) => b - a);

    expect(prices).toEqual(sorted);
  }
}
