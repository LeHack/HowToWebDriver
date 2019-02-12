package examples;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import utils.Config;

public class MoveThings {
    private static final Logger logger = Config.getLogger(MoveThings.class);

    public static void main(String[] args) {
        new MoveThings();
    }

    public MoveThings() {
        Config cfg = new Config();

        // Look at util.Config.createInstance for details
        WebDriver drv = cfg.createInstance();
        drv.navigate().to(cfg.get("site_url"));
        logger.info("At the test site...");

        this.moveStuff(drv);

        drv.close();
    }

    public void moveStuff(WebDriver drv) {
        Actions act = new Actions(drv);

        logger.info("Showing kitty");
        WebElement button = drv.findElement(By.cssSelector(".kittyStart input"));
        act.click(button).perform();

        logger.info("Moving kitty");
        WebElement kitty = drv.findElement(By.id("kitty"));
        act.moveToElement(kitty).clickAndHold().moveByOffset(200, 0).release().perform();

        logger.info("Slowly moving kitty");
        for (int i = 0; i < 10; i++) {
            act.moveToElement(kitty).clickAndHold().moveByOffset(20, 0).release().perform();
        }

        logger.info("Feeding kitty");
        WebElement bowl = drv.findElement(By.cssSelector(".kittyBowl"));
        act.clickAndHold(kitty).moveToElement(bowl, -120, -60).release().perform();
        
        logger.info("Bon Appétit Kitty ");
    }
}
