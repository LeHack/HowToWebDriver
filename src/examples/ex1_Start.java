package examples;

import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;

import utils.Config;

public class ex1_Start {
    private static final Logger logger = Config.getLogger(ex1_Start.class);

    public static void main(String[] args) {
        new ex1_Start();
    }

    public ex1_Start() {
        Config cfg = new Config();

        // Look at util.Config.createInstance for details
        WebDriver drv = cfg.createInstance();
        drv.navigate().to(cfg.get("site_url"));

        logger.info("At the test site...");

        drv.close();
    }
}
