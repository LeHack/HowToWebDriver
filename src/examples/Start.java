package examples;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;

import utils.Config;

public class Start {
    private static final Logger logger = Config.getLogger(Start.class);

    public static void main(String[] args) {
        new Start();
    }

    public Start() {
        Config cfg = new Config();

        // Look at util.Config.createInstance for details
        WebDriver drv = cfg.createInstance();
        drv.navigate().to(cfg.get("site_url"));

        logger.info("At the test site...");

        drv.close();
    }
}
