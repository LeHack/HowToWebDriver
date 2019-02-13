package examples;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.InvalidElementStateException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import utils.Config;

public class BreakThings {
    private static final Logger logger = Config.getLogger(BreakThings.class);
    private WebDriver drv = null;

    public static void main(String[] args) {
        new BreakThings();
    }

    public BreakThings() {
        Config cfg = new Config();

        // Look at util.Config.createInstance for details
        WebDriver drv = cfg.createInstance();

        // store a local reference for usage with the shortcut method clickAt
        this.drv = drv;

        drv.navigate().to(cfg.get("site_url"));
        logger.info("At the test site...");

        this.breakStuff(drv);

        drv.close();
    }

    public void breakStuff(WebDriver drv) {
        try {
            drv.navigate().to("http://localhost:9999");
            logger.info("This code will never run");
        }
        catch (WebDriverException e) {
            if (e.getMessage().contains("Reached error page")) {
                logger.info("Oh dear, no such site found. Let's go back");
                drv.navigate().back();
            }
            else {
                // if it's not what we expect, make sure to rethrow it
                throw e;
            }
            /*
             * There could possibly be other cases to handle here
             * WebDriverException is a generic exception type
             * which also covers every other exception dealt with here
             * thus sometimes we have to resort to message checking
             */
        }

        // first let's see if Kitty is there
        try {
            WebElement kitty = drv.findElement(By.id("kitty"));
            logger.info("XXX This code will never run: " + kitty.getTagName());
        }
        catch (NoSuchElementException e) {
            logger.info("Kitty not found, as expected");
        }

        logger.info("Showing Kitty");
        // shortcuts are nice, they can give you
        clickAt(".kittyStart input");
        // instead of
        // drv.findElement(By.cssSelector(".kittyStart input")).click();

        // now let's look for Kitty again
        WebElement kitty = drv.findElement(By.id("kitty"));
        logger.info("Kitty found");

        logger.info("Hiding Kitty");
        clickAt(".kittyStart input");

        try {
            // try calling anything on kitty now
            kitty.getTagName();
            logger.info("XXX This code will never run");
        }
        catch (StaleElementReferenceException e) {
            logger.info("Kitty is not there anymore, hence failure, as expected");
        }

        logger.info("Showing Kitty again");
        clickAt(".kittyStart input");
        // refresh our object, so we can use it again
        kitty = drv.findElement(By.id("kitty"));

        clickAt(".kittyBowl");
        logger.info("Clicking on the bowl doesn't do much");

        // let's put the kitty right on top of the bowl
        Actions act = new Actions(drv);
        WebElement bowl = drv.findElement(By.cssSelector(".kittyBowl"));
        act.clickAndHold(kitty).moveToElement(bowl).release().perform();
        try {
            clickAt(".kittyBowl");
            logger.info("XXX This code will never run");
        }
        catch (ElementClickInterceptedException e) {
            logger.info("Cannot click the bowl, when Kitty is sitting on it. Quite expected.");
        }

        // finally let's go wild and hide Kitty without removing the HTML element
        logger.info("Now we will use JS magic to make Kitty invisible (and therefore invincible)");
        String jsCode =
                "let kitty = arguments[0];"
              + "kitty.style.display = 'none';";
        ((JavascriptExecutor)drv).executeScript(jsCode, kitty);

        try {
            kitty.click();
            logger.info("XXX This code will never run");
        }
        catch (InvalidElementStateException e) {
            logger.info("The kitty we knew is gone. We need to go look for him first.");
        }

        try {
            kitty = drv.findElement(By.id("kitty"));
            kitty.click();
            logger.info("XXX This code will also never run");
        }
        catch (ElementNotInteractableException e) {
            logger.info("Invisible Kitties are like Ninjas: definitely not clickable.");
        }

        logger.info("Aand we're done here");
    }

    private void clickAt(String cssPath) {
        // and the long ugly stuff gets hidden out of sight
        this.drv.findElement(By.cssSelector(cssPath)).click();
    }
}
