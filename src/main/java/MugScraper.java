import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Paths;
import java.util.List;


public class MugScraper {

    private static final String PATH_TO_GECKODRIVER = "C:\\geckodriver.exe";
    private static final String PATH_TO_UBLOCK = "Resources/uBlock0@raymondhill.net.xpi";
    private static final String DESIRED_CLASS = "mugshots__inmate";

    public static void main(String[] args) {
        System.setProperty("webdriver.gecko.driver", PATH_TO_GECKODRIVER);
        FirefoxDriver driver = new FirefoxDriver();
        //WebDriverWait wait = new WebDriverWait(driver, 10);
        driver.installExtension(Paths.get(PATH_TO_UBLOCK));
        try {
            driver.get("https://www.journalstar.com/mugshots");
            List<WebElement> mugshots = (new WebDriverWait(driver, 15))
                    .until(ExpectedConditions.presenceOfAllElementsLocatedBy((By.className(DESIRED_CLASS))));
            for (WebElement mugshot : mugshots) {
                System.out.println(mugshot.getText());
            }
            //sleep(25000);
        } finally {


            driver.quit();
        }
    }
}
