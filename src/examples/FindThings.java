package examples;

import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.Config;

public class FindThings {
    private static final Logger logger = Config.getLogger(FindThings.class);

    public static void main(String[] args) {
        new FindThings();
    }

    public FindThings() {
        Config cfg = new Config();

        // Look at util.Config.createInstance for details
        WebDriver drv = cfg.createInstance();
        drv.navigate().to(cfg.get("site_url"));
        logger.info("At the test site...");

        this.lookForStuff(drv);

        drv.close();
    }

    public void lookForStuff(WebDriver drv) {
        List<WebElement> elems = drv.findElements(By.xpath("//div"));
        for (WebElement el : elems) {
            logger.info("I found class: " + el.getAttribute("className") + " and text: " + el.getText());
        }

        WebElement elem = drv.findElement(By.cssSelector("h1"));
        logger.info("h1 text is: " + elem.getText());

        elem = drv.findElement(By.cssSelector(".aThing"));
        logger.info(".aThing text is: " + elem.getText());

        elem = drv.findElement(By.cssSelector("div.kittyBowl"));
        logger.info("The kitty bowl picture url is: " + elem.getCssValue("background-image"));

        elem = drv.findElement(By.id("nameInput"));
        logger.info("The current kitty name is: " + elem.getAttribute("value"));
    }
}
