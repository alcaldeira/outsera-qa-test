# Test info

- Name: Login Success
- Location: C:\Users\ander\workspace\outsera-test-qa\automation-frontend\tests\login.spec.ts:13:5

# Error details

```
Error: locator.waitFor: Test timeout of 30000ms exceeded.
Call log:
  - waiting for locator('[data-test="title"]') to be visible

    at LoginPage.assertLoginSuccess (C:\Users\ander\workspace\outsera-test-qa\automation-frontend\pages\LoginPage.js:25:32)
    at C:\Users\ander\workspace\outsera-test-qa\automation-frontend\tests\login.spec.ts:15:19
```

# Page snapshot

```yaml
- text: Swag Labs
- textbox "Username": standard_user
- textbox "Password": asa
- 'heading "Epic sadface: Username and password do not match any user in this service" [level=3]':
  - button
  - text: "Epic sadface: Username and password do not match any user in this service"
- button "Login"
- heading "Accepted usernames are:" [level=4]
- text: standard_user locked_out_user problem_user performance_glitch_user error_user visual_user
- heading "Password for all users:" [level=4]
- text: secret_sauce
```

# Test source

```ts
   1 | import { expect } from '@playwright/test';
   2 |
   3 | export class LoginPage {
   4 |     constructor(page) {
   5 |       this.page = page;
   6 |       this.usernameInput = page.locator('[data-test="username"]');
   7 |       this.passwordInput = page.locator('[data-test="password"]');
   8 |       this.loginButton = page.locator('[data-test="login-button"]');
   9 |       this.productsTitle = page.locator('[data-test="title"]');
  10 |       this.assertMessagemErrorLogin = page.locator('[data-test="error"]');
  11 |       
  12 |     }
  13 |   
  14 |     async goto() {
  15 |       await this.page.goto('https://www.saucedemo.com/');
  16 |     }
  17 |   
  18 |     async login(username,  password) {
  19 |       await this.usernameInput.fill(username);
  20 |       await this.passwordInput.fill(password);
  21 |       await this.loginButton.click();
  22 |     }
  23 |
  24 |     async assertLoginSuccess() {
> 25 |       await this.productsTitle.waitFor();
     |                                ^ Error: locator.waitFor: Test timeout of 30000ms exceeded.
  26 |       await expect(this.productsTitle, 'Products').toBeVisible();
  27 |     }
  28 |
  29 |     async assertLoginError(messagem) {
  30 |       await this.assertMessagemErrorLogin.waitFor();
  31 |       await expect(this.assertMessagemErrorLogin, messagem).toBeVisible();
  32 |     }
  33 |   }
  34 |   
```