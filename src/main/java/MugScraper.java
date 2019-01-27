import data.Dao;
import model.Booking;
import model.Charges;
import model.Person;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MugScraper {


    private static final String PATH_TO_GECKODRIVER = "C:\\geckodriver.exe";
    private static final String PATH_TO_UBLOCK = "src/main/Resources/uBlock0@raymondhill.net.xpi";
    private static final String DESIRED_CLASS = "mugshots__inmate";
    private static final String URL = "https://www.journalstar.com/mugshots/?page={pageNum}";

    private static Map<String, Integer> bioMap = new HashMap<>();

    private static final String NAME = "NAME";

    private static final String RACE = "RACE";

    private static final String DOB = "DOB";

    private static final String BOOKING = "BOOKING";

    static {
        bioMap.put(NAME, 1);
        bioMap.put(RACE, 3);
        bioMap.put(DOB, 5);
        bioMap.put(BOOKING, 7);
    }

    public static void main(String[] args) {

        Dao d = new Dao();

        System.setProperty("webdriver.gecko.driver", PATH_TO_GECKODRIVER);
        FirefoxDriver driver = new FirefoxDriver();

        driver.installExtension(Paths.get(PATH_TO_UBLOCK));

        try {

            for (int i =1; i < 100; i++ ) {

                driver.get(URL.replaceAll("\\{pageNum\\}", Integer.toString(i)));
                List<WebElement> mugshots = null;
                try {
                    mugshots = (new WebDriverWait(driver, 15))
                            .until(ExpectedConditions.presenceOfAllElementsLocatedBy((By.className(DESIRED_CLASS))));
                } catch (TimeoutException e) {
                    return;
                }
                for (WebElement mugshot : mugshots) {

                    WebElement bio = mugshot.findElement(By.className("mugshots__bio"));
                    WebElement charges = mugshot.findElement(By.className("mugshots__charges"));

                    Booking booking = null;
                    try {
                        booking = fillBooking(bio.getText().split("\\n"), d);
                    } catch (ParseException e) {
                        System.err.println("Error parsing date with BIO text: " + bio.getText());
                    }
                    if (d.bookingExists(booking)) {
                        System.err.println("Booking already exists!");
                        return;
                    }
                    fillCharges(booking, charges, d);
                    d.saveEntity(booking);

                }

            }

        } finally {

            d.close();
            driver.quit();
        }
    }

    private static void fillCharges(Booking booking, WebElement charges, Dao d) {
        String[] splitCharge = charges.getText().split("\\n");
        if (splitCharge.length < 2) {
            return;
        }
        for (int i = 1; i<splitCharge.length; i++) {
            Charges c = new Charges(splitCharge[i]);
            if (!d.chargesExists(c)) {
                d.saveEntity(c);
            } else {
                c = d.getCharges(c);
            }
            booking.addCharge(c);
        }
    }

    private static Booking fillBooking(String[] bioInfo, Dao d) throws ParseException {
        String[] names = bioInfo[bioMap.get(NAME)].split(",");
        String firstName = names[1].trim();
        String lastName = names[0].trim();
        String[] raceSex = bioInfo[bioMap.get(RACE)].split("/");
        String race = raceSex[0];
        String sex = raceSex[1];
        String dob = bioInfo[bioMap.get(DOB)];
        String bookingTime = bioInfo[bioMap.get(BOOKING)];
        Person p = d.createPerson(firstName, lastName, race, sex, dob);
        if (d.personExists(p)) {
            p = d.getPerson(p);
        } else {
            d.saveEntity(p);
        }

        return d.createBooking(p, bookingTime);
    }
}
