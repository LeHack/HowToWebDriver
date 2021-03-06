package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.GeckoDriverService;

import io.github.bonigarcia.wdm.BrowserManager;
import io.github.bonigarcia.wdm.FirefoxDriverManager;

public final class Config {
    private final Properties cfg = new Properties();

    public Config() {
        try {
            LogManager.getLogManager().reset();
            LogManager.getLogManager().readConfiguration(new FileInputStream(new File("configs/logging.properties")));
            cfg.load(new FileInputStream("configs/settings.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("rawtypes")
    public static Logger getLogger(Class cls) {
        return Logger.getLogger(cls.getName());
    }

    public String get(String propName) {
        return cfg.getProperty(propName);
    }

    public WebDriver createInstance() {
        // Fetch current GeckoDriver
        BrowserManager gecko = FirefoxDriverManager.getInstance();
        gecko.version("0.24.0");
        gecko.setup();

        // init a Firefox binary
        FirefoxBinary ffBin = new FirefoxBinary();
        ffBin.addCommandLineOptions("--maximized");

        // setup Firefox
        FirefoxOptions ffOpt = new FirefoxOptions();
        // prepare and set a custom profile
        ffOpt.setCapability(FirefoxDriver.PROFILE, prepareFirefoxProfile());

        GeckoDriverService gds = new GeckoDriverService.Builder()
            .usingDriverExecutable(new File(gecko.getBinaryPath()))
            .usingFirefoxBinary(ffBin)
            .build();

        return new FirefoxDriver(gds, ffOpt);
    }

    private FirefoxProfile prepareFirefoxProfile() {
        FirefoxProfile ffprofile = new FirefoxProfile();

        // don't ask for updates
        ffprofile.setPreference("browser.startup.homepage_override.buildID", "");
        ffprofile.setPreference("browser.startup.homepage_override.mstone", "ignore");
        // don't ask for healthreport related stuff
        ffprofile.setPreference("datareporting.healthreport.service.enabled", false);
        ffprofile.setPreference("datareporting.healthreport.service.firstRun", false);
        ffprofile.setPreference("datareporting.healthreport.uploadEnabled", false);
        ffprofile.setPreference("datareporting.policy.dataSubmissionEnabled", false);
        ffprofile.setPreference("datareporting.policy.dataSubmissionPolicyResponseType", "accepted-info-bar-dismissed");
        // also silence update mechanisms
        ffprofile.setPreference("app.update.auto", false);
        ffprofile.setPreference("app.update.enabled", false);
        ffprofile.setPreference("app.update.silent", false);
        ffprofile.setPreference("app.update.checkInstallTime", false);
        ffprofile.setPreference("app.update.disabledForTesting", true);

        // disable the XSS protection
        ffprofile.setPreference("devtools.selfxss.count", 100);
        // make sure the console is enabled
        ffprofile.setPreference("devtools.chrome.enabled", true);
        // disable firefox logging
        System.setProperty("webdriver.firefox.logfile", "/dev/null");

        // disable downloading of the Cisco H.264 addon
        ffprofile.setPreference("media.gmp-provider.enabled", false);
        ffprofile.setPreference("media.gmp-gmpopenh264.provider.enabled", false);
        // keep the old behaviour of Ctrl+W
        ffprofile.setPreference("browser.tabs.closeWindowWithLastTab", true);
        // disable multi-thread windows rendering
        ffprofile.setPreference("browser.tabs.remote.autostart", false);
        ffprofile.setPreference("browser.tabs.remote.autostart.2", false);

        return ffprofile;
    }
}