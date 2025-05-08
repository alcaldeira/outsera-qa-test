import { expect } from '@playwright/test';

export class LoginPage {
    constructor(page) {
      this.page = page;
      this.usernameInput = page.locator('[data-test="username"]');
      this.passwordInput = page.locator('[data-test="password"]');
      this.loginButton = page.locator('[data-test="login-button"]');
      this.productsTitle = page.locator('[data-test="title"]');
      this.assertMessagemErrorLogin = page.locator('[data-test="error"]');
      
    }
  
    async goto() {
      await this.page.goto('https://www.saucedemo.com/');
    }
  
    async login(username,  password) {
      await this.usernameInput.fill(username);
      await this.passwordInput.fill(password);
      await this.loginButton.click();
    }

    async assertLoginSuccess() {
      await this.productsTitle.waitFor();
      await expect(this.productsTitle, 'Products').toBeVisible();
    }

    async assertLoginError(messagem) {
      await this.assertMessagemErrorLogin.waitFor();
      await expect(this.assertMessagemErrorLogin, messagem).toBeVisible();
    }
  }
  