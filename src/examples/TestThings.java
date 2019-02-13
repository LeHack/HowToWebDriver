package examples;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.AfterMethod;

import utils.Config;

/**
 * You need to have TestNG plugin installed in Eclipse to run this.
 * Try Help -> Install new software and sitename: http://beust.com/eclipse
 * Then use Run As -> TestNG Test
 *
 * Note: make sure to checkout the getScreenshot() and cleanup() methods
 */
public class TestThings {
    private static final Logger logger = Config.getLogger(TestThings.class);
    private final WebDriver drv;

    public TestThings() {
        Config cfg = new Config();

        // Look at util.Config.createInstance for details
        this.drv = cfg.createInstance();
        this.drv.navigate().to(cfg.get("site_url"));
        logger.info("At the test site...");
    }

    @Test
    public void testWelcome() {
        WebElement elem = this.drv.findElement(By.cssSelector("h1"));
        Assert.assertEquals(elem.getText(), "Welcome", "Correct greeting");
    }

    @Test
    public void testShowingKitty() {
        /*
         * we don't know the order of the tests, so we must assume
         * a Schrödinger cat scenario and handle both cases
         */
        List<WebElement> kittyCheck = this.drv.findElements(By.id("kitty"));
        if (!kittyCheck.isEmpty()) {
            // if Kitty is already there, hide him
            ((JavascriptExecutor)this.drv).executeScript("Kitty.hide()");
        }

        // now do the actual test
        try {
            WebElement button = drv.findElement(By.cssSelector(".kittyStart input"));
            button.click();
        }
        catch (NoSuchElementException e) {
            Assert.fail("Kitty showing button not found");
        }
        WebElement kitty = this.drv.findElement(By.id("kitty"));
        Assert.assertTrue(kitty.getCssValue("background-image").contains("kitty.png"), "Looks like Kitty");
    }

    @Test
    public void testHidingKitty() {
        /*
         * Again:
         * we don't know the order of the tests, so we must assume
         * a Schrödinger cat scenario and handle both cases
         */
        List<WebElement> kittyCheck = this.drv.findElements(By.id("kitty"));
        if (kittyCheck.isEmpty()) {
            // if Kitty is not already there, show him
            ((JavascriptExecutor)this.drv).executeScript("Kitty.show()");
        }

        // now do the actual test
        try {
            WebElement button = drv.findElement(By.cssSelector(".kittyStart input"));
            button.click();
        }
        catch (NoSuchElementException e) {
            Assert.fail("Kitty hiding button not found");
        }

        try {
            this.drv.findElement(By.id("kitty"));
            Assert.fail("Not good, Kitty was found on site.");
        }
        catch (NoSuchElementException e) {
            Assert.assertTrue(true, "Kitty was not found, as expected.");
        }
    }

    @Test
    public void testWhichFails() {
        Assert.fail("Just because");
    }

    @AfterClass
    protected void cleanup() {
        // make sure we close WebDriver after TestNG finishes
        this.drv.close();
    }

    @AfterMethod(alwaysRun=true)
    protected void getScreenshot(ITestResult result) {
        if (result.isSuccess())
            return; // skip, if the test passed

        // only failed tests will run this part
        File scrFile = ((TakesScreenshot) this.drv).getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(scrFile, new File("screenshot.jpg"), true);
        } catch (IOException e) {
            logger.warning("Cannot create screenshot file.");
        }
        /*
         *  Now go see the screenshot.jpg in your main project directory.
         *  Note that only a portion of the page may be visible as
         *  Selenium tries to detect the overall dimensions of the web page.
         *  Read https://stackoverflow.com/a/44086163 for details.
         */
    }
}
