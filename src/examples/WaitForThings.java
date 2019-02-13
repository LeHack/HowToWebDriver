package examples;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

        Select select = new Select(drv.findElement(By.cssSelector("select")));
        select.selectByVisibleText("Doggy");

        // TODO
        
        logger.info("We've got a doggy now!");
    }
}
