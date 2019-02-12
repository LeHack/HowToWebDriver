package examples;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.Config;

public class ChangeThings {
    private static final Logger logger = Logger.getLogger("FindThings");

    public static void main(String[] args) {
        new ChangeThings();
    }

    public ChangeThings() {
        Config cfg = new Config();

        // Look at util.Config.createInstance for details
        WebDriver drv = cfg.createInstance();
        drv.navigate().to(cfg.get("site_url"));
        logger.info("At the test site...");

        this.changeStuff(drv);

        drv.close();
    }

    public void changeStuff(WebDriver drv) {
        WebElement elem = drv.findElement(By.id("nameInput"));
        elem.clear();
        elem.sendKeys("Grafield");
        elem.sendKeys(Keys.ENTER);
        logger.info("The new kitty name is: " + elem.getAttribute("value"));

        elem = drv.findElement(By.xpath("//span[contains(@class, 'kittyName')]"));
        logger.info("Somebody says: " + elem.getText());

        WebElement button = drv.findElement(By.cssSelector(".kittyStart input"));
        logger.info("Showing kitty");
        button.click();

        logger.info("Hiding kitty");
        button.click();
    }
}
