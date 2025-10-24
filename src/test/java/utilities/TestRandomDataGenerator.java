package utilities;

import java.util.List;
import java.util.Random;

import org.openqa.selenium.WebElement;

public class TestRandomDataGenerator {
    private static final Random random = new Random();

    public static String randomUserName() {
        return "User_" + random.nextInt(10000);
    }

    public static String randomEmail() {
        return "user" + random.nextInt(10000) + "@example.com";
    }

    public static String randomPhoneNumber() {
        return "9" + (100000000 + random.nextInt(900000000));
    }

    public static String randomEmpCode() {
        return "EMP" + (1000 + random.nextInt(9000));
    }

    public static String randomBloodGroup() {
        String[] groups = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        return groups[random.nextInt(groups.length)];
    }

    public static String randomAddress() {
        return "BTM Layout " + (1 + random.nextInt(5)) + " Stage";
    }

    public static String randomPinCode() {
        return String.valueOf(100000 + random.nextInt(900000));
    }

    public static String randomRole() {
        String[] roles = {"Admin", "Manager", "Super Admin", "Supervisor"};
        return roles[random.nextInt(roles.length)];
    }

    public static String randomTeam() {
        String[] teams = {"Maintenance", "Production Approval", "Quality Analyst"};
        return teams[random.nextInt(teams.length)];
    }

    public void selectRandomOptionFromDropdown(List<WebElement> dropdownOptions) {
        Random rand = new Random();  // Generate a random index
        int randomIndex = rand.nextInt(dropdownOptions.size());
        WebElement randomOption = dropdownOptions.get(randomIndex);// Get the random option
        randomOption.click();// Click it
        System.out.println("Randomly selected option: " + randomOption.getAttribute("text"));
    }

}

