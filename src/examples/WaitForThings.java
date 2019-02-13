package examples;

import java.time.Duration;
import java.time.LocalTime;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import utils.Config;

public class WaitForThings {
    private static final Logger logger = Config.getLogger(WaitForThings.class);

    public static void main(String[] args) {
        new WaitForThings();
    }

    public WaitForThings() {
        Config cfg = new Config();

        // Look at util.Config.createInstance for details
        WebDriver drv = cfg.createInstance();
        drv.navigate().to(cfg.get("site_url"));
        logger.info("At the test site...");

        this.waitForStuff(drv);

        drv.close();
    }

    public void waitForStuff(WebDriver drv) {
        WebElement button = drv.findElement(By.cssSelector(".kittyStart input"));
        logger.info("Showing kitty");
        button.click();

        logger.info("Changing to doggy");
        Select select = new Select(drv.findElement(By.cssSelector("select")));
        select.selectByVisibleText("Doggy");

        try {
            WebElement doggy = drv.findElement(By.id("kitty"));
            doggy.click();
            logger.info("Nope, this won't work. Doggy is still loading...");
        }
        catch (ElementNotInteractableException e) {
            logger.info("We have to wait for the loading to go away first.");
        }

        // Ok, so check if the loader is visible every 250ms
        // if it's still visible after 10s, fail
        // Now go see the method and it's comment.
        boolean result = waitWhileElementVisible(drv, By.id("loading"), Duration.ofSeconds(10), Duration.ofMillis(250));
        // we should more likely put an assertTrue here
        if (result) {
            logger.info("Ok, doggy is loaded. Let him eat from kittys bowl. I'm sure Kitty won't mind.");
            WebElement doggy = drv.findElement(By.id("kitty"));
            WebElement bowl = drv.findElement(By.cssSelector(".kittyBowl"));
            (new Actions(drv)).clickAndHold(doggy).moveToElement(bowl, -120, -60).release().perform();
        }
        else {
            // always plan for disaster
            logger.info("If you see this, we most likely exceeded the max timeout. The page may be hanged on an error and will never complete loading.");
        }

        logger.info("Aaand we're done!");
    }

    /**
     * Now this is a very nice (Java 8 compatible) method to wait *WHILE* an element is visible on the page.
     * With some tinkering you can easily get variants that:
     *  - wait until an element is visible
     *  - wait while an element exists (without considering it's visibility - slightly faster)
     *  - wait until an element is gone
     *
     * Note: when dealing with page reloads, never assume that the old page will unload before your waiting method kicks in.
     * Instead:
     *  - first wait for some part of the UI to disappear (optional)
     *  - then wait for a loader indication to appear (WebApps usually have loader animations)
     *  - finally wait for that loader indication to hide/disappear
     */
    private boolean waitWhileElementVisible(WebDriver drv, By selector, Duration maxWait, Duration checkWait) {
        LocalTime endTime = LocalTime.now().plus(maxWait);

        boolean result = false;
        // loop until maxWait time is reached, unless of course we get stopped by a break from inside
        while (LocalTime.now().isBefore(endTime)) {
            try {
                if (!drv.findElement(selector).isDisplayed()) {
                    // stop if the element was found, but is not visible
                    result = true;
                    break;
                }
            }
            catch (NoSuchElementException e) {
                // also stop if the element was not found (and hence it cannot be visible)
                result = true;
                break;
            }
            // now if we didn't stop yet, wait the requested checkWait time before checking again
            try {
                Thread.sleep(checkWait.toMillis());
            } catch (InterruptedException e) {
                // if we get interrupted, we have to fail
                break;
            }
        }

        return result;
    }
}
