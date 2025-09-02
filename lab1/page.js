import { By, until } from "selenium-webdriver";

export default class Page {
  constructor(driver, baseUrl) {
    this.driver = driver;
    this.baseUrl = baseUrl;
    // login items
    this.username = By.id("username");
    this.password = By.id("password");
    this.submit = By.css("input[type='submit']");
    this.loginForm = By.css("form");
    this.error = By.css("div[contains(@class, 'validation-summary-errors')]");
    // home items
    this.initialModal = By.css(".modal-content");
    this.logoutButton = By.linkText("Гарах");
  }

  async open() {
    await this.driver.get(this.baseUrl);
  }

  async login(user, pass) {
    const d = this.driver;
    await d.wait(until.elementLocated(this.username), 10000);
    await d.findElement(this.username).clear();
    await d.findElement(this.username).sendKeys(user);

    await d.findElement(this.password).clear();
    await d.findElement(this.password).sendKeys(pass);

    await d.findElement(this.submit).click();
  }

  async isErrorVisible() {
    const d = this.driver;
    try {
      const errEl = await d.wait(until.elementLocated(this.submit), 5000);
      await d.wait(until.elementIsVisible(errEl), 5000);
      return true;
    } catch {
      return false;
    }
  }

  async isLoggedIn() {
    const d = this.driver;
    try {
      const modal = await d.findElements(this.initialModal);
      return modal.length > 0;
    } catch {
      return false;
    }
  }

  async logoutIfPossible() {
    try {
      const btn = await this.driver.findElement(this.logoutButton);
      await btn.click();
    } catch {}
  }
}
