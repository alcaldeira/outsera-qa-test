import { expect } from '@playwright/test';

export class CheckoutCompletePage {
    constructor(page) {
      this.page = page;
      this.successMessage = page.locator('[data-test="complete-header"]');
    }
  
    async expectOrderSuccess() {
      await this.successMessage.waitFor();
      await expect(this.successMessage, 'Thank you for your order!').toBeVisible();
    }
  }
  