package examples;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import utils.Config;

public class ScriptThings {
    private static final Logger logger = Config.getLogger(ScriptThings.class);

    public static void main(String[] args) {
        new ScriptThings();
    }

    public ScriptThings() {
        Config cfg = new Config();

        // Look at util.Config.createInstance for details
        WebDriver drv = cfg.createInstance();

        drv.navigate().to(cfg.get("site_url"));
        logger.info("At the test site...");

        this.scriptStuff(drv);

        drv.close();
    }

    public void scriptStuff(WebDriver drv) {
        logger.info("Show kitty, using JS");
        ((JavascriptExecutor)drv).executeScript("Kitty.show()");

        // run a throw-away find just to confirm he is there (no need to keep the object)
        drv.findElement(By.id("kitty"));

        // set Kittys name
        WebElement input = drv.findElement(By.id("nameInput"));
        String jsCode = "arguments[0].value = arguments[1];";
        ((JavascriptExecutor)drv).executeScript(jsCode, input, "Jones");
        logger.info("Changed kittys name to Jones (Xenomorph does not like this).");

        // check Kittys name
        jsCode = "return arguments[0].value;";
        String name = (String)((JavascriptExecutor)drv).executeScript(jsCode, input);
        logger.info("Confirm that kittys name is now: " + name);

        // obviously we can choose whether we want to use WebDriver or JS to locate the element
        jsCode =
            "let kitty = document.getElementById('kitty');"
          + "kitty.style.display = 'none';";
        ((JavascriptExecutor)drv).executeScript(jsCode);
        logger.info("Jones has gone stealth. Sneaky.");
    }
}
