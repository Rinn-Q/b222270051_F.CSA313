import "dotenv/config.js";
import { Builder } from "selenium-webdriver";
import edge from "selenium-webdriver/edge.js";
import { expect } from "chai";
import fs from "fs";
import path from "path";
import Page from "./page.js";

const BASE_URL = process.env.BASE_URL || "https://student.must.edu.mn";
const REAL_USER = process.env.MUST_USER;
const REAL_PASS = process.env.MUST_PASS;

describe("Selenium Лаб-1: Нэвтрэх урсгал", function () {
  this.timeout(60000);

  let driver;
  let login;

  before(async () => {
    const options = new edge.Options();
    if (!process.env.MOCHA_HEADFUL) options.addArguments("--headless=new");

    driver = await new Builder()
      .forBrowser("MicrosoftEdge")
      .setEdgeOptions(options)
      .build();

    login = new Page(driver, BASE_URL);
  });

  after(async () => {
    if (driver) await driver.quit();
  });

  const takeShot = async (name) => {
    const img = await driver.takeScreenshot();
    const file = path.join(
      "artifacts",
      "screenshots",
      `${Date.now()}-${name}.png`
    );
    fs.mkdirSync(path.dirname(file), { recursive: true });
    fs.writeFileSync(file, img, "base64");
    console.log(`📸 Screenshot: ${file}`);
  };

  it("Алхам 1-2: Веб ачаалж гарчгийг шалгах", async () => {
    await login.open();
  });

  it("Алхам 3: Буруу нэвтрэлт дээр алдааг харуулж байна (assert)", async () => {
    await login.open();
    await login.login("wrong_user", "wrong_pass");
    await driver.sleep(2000);
    const hasError = await login.isErrorVisible();
    if (!hasError) await takeShot("no-error-visible");
    expect(hasError, "Алдааны мессэж харагдах ёстой").to.equal(true);
  });

  it("Алхам 3-5: Зөв нэвтрэx → modal-content → logout", async function () {
    if (!REAL_USER || !REAL_PASS) this.skip();

    await login.open();
    await login.login(REAL_USER, REAL_PASS);

    const loggedIn = await login.isLoggedIn();
    if (!loggedIn) await takeShot("login-failed");
    expect(
      loggedIn,
      "Амжилттай нэвтэрсний modal-content харагдах ёстой"
    ).to.equal(true);

    await login.logoutIfPossible();
  });
});
