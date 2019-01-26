import model.Suspect;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Paths;
import java.util.*;


public class MugScraper {

    private static final String PATH_TO_GECKODRIVER = "C:\\geckodriver.exe";
    private static final String PATH_TO_UBLOCK = "src/main/Resources/uBlock0@raymondhill.net.xpi";
    private static final String DESIRED_CLASS = "mugshots__inmate";
    public static final String URL = "https://www.journalstar.com/mugshots/?page={pageNum}";

    private static Map<String, Integer> bioMap = new HashMap<>();

    public static final String NAME = "NAME";

    public static final String RACE = "RACE";

    public static final String DOB = "DOB";

    public static final String BOOKING = "BOOKING";

    static {
        bioMap.put(NAME, 1);
        bioMap.put(RACE, 3);
        bioMap.put(DOB, 5);
        bioMap.put(BOOKING, 7);
    }

    public static void main(String[] args) {

        Set<Suspect> suspects = new HashSet<>();
        System.setProperty("webdriver.gecko.driver", PATH_TO_GECKODRIVER);
        FirefoxDriver driver = new FirefoxDriver();

        driver.installExtension(Paths.get(PATH_TO_UBLOCK));

        try {

            for (int i =1; i < 100; i++ ) {

                driver.get(URL.replaceAll("\\{pageNum\\}", Integer.toString(i)));
                List<WebElement> mugshots = (new WebDriverWait(driver, 15))
                        .until(ExpectedConditions.presenceOfAllElementsLocatedBy((By.className(DESIRED_CLASS))));
                for (WebElement mugshot : mugshots) {

                    WebElement bio = mugshot.findElement(By.className("mugshots__bio"));
                    WebElement charges = mugshot.findElement(By.className("mugshots__charges"));

                    Suspect suspect = fillBio(bio.getText().split("\\n"));
                    fillCharges(suspect, charges);
                    System.out.println(suspect.toString());
//                System.out.println(charges.getText());
//                //String[] lastNameFirstName = unparsed[1].split(",");
//                suspects.add(s);
                }

            }
            //sleep(25000);
        } finally {


            driver.quit();
        }
    }

    private static void fillCharges(Suspect suspect, WebElement charges) {
        String[] splitCharge = charges.getText().split("\\n");
        if (splitCharge.length < 2) {
            return;
        }
        for (int i = 1; i<splitCharge.length; i++) {
            suspect.addCharges(splitCharge[i]);
        }
    }

    private static Suspect fillBio(String[] bioInfo) {
        Suspect s = new Suspect();
        String[] names = bioInfo[bioMap.get(NAME)].split(",");
        String firstName = names[1].trim();
        String lastName = names[0].trim();
        String[] raceSex = bioInfo[bioMap.get(RACE)].split("/");
        String race = raceSex[0];
        String sex = raceSex[1];
        String dob = bioInfo[bioMap.get(DOB)];
        String[] bookInfo = bioInfo[bioMap.get(BOOKING)].split("@");
        String bookTime = bookInfo[1].trim();
        String bookDate = bookInfo[0].trim();
        s.setFirstName(firstName);
        s.setLastName(lastName);
        s.setRace(race);
        s.setSex(sex);
        s.setDateOfBirth(dob);
        s.setBookingTime(bookTime);
        s.setBookingDate(bookDate);
        return s;
    }
}
