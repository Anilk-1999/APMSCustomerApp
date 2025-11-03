package pageObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections4.Get;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;

public class MachineDowntimePage extends BasePage{ 

    public MachineDowntimePage(AndroidDriver driver) {
        super(driver);   
    }



    /** get all tabs dynamically */
    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'Tab')]")
    private List<WebElement> allTabs;

    /** All machine name elements under Favorite tab */
    @AndroidFindBy(xpath = "//android.view.View[contains(@content-desc,'#MCA')]")
    private List<WebElement> allMachines;

    /** Favorite tab */
    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Favorite 6 Tab 2 of 10']")
    private WebElement favoriteTab;

    @FindBy(xpath = "(//android.view.View[@clickable='true'])[1]")
    private WebElement topMachineName;

    @FindBy(xpath = "//android.view.View[@content-desc and @clickable='true']")
    private List<WebElement> machineListOnM2MachineDropdown;

    @FindBy(xpath = "//android.view.View[@content-desc=\"Downtime Summary of downtime today in the unit\"]/android.view.View")
    private WebElement downtimeSummaryCard;




    // ---------- STATUS CARD LOCATORS ----------
    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Running']/following-sibling::android.widget.TextView")
    private WebElement runningDurationCard;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Stopped']/following-sibling::android.widget.TextView")
    private WebElement stoppedDurationCard;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Waiting']/following-sibling::android.widget.TextView")
    private WebElement waitingDurationCard;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Idle']/following-sibling::android.widget.TextView")
    private WebElement idleDurationCard;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Product Setup']/following-sibling::android.widget.TextView")
    private WebElement productSetupDurationCard;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Data Loss']/following-sibling::android.widget.TextView")
    private WebElement dataLossDurationCard;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='Log Off']/following-sibling::android.widget.TextView")
    private WebElement logoffDurationCard;

    @AndroidFindBy(xpath = "//android.widget.TextView[@text='By Pass']/following-sibling::android.widget.TextView")
    private WebElement bypassDurationCard;

    @FindBy(xpath = "//android.widget.ImageView[contains(@content-desc,'Selected Range')]")
    private WebElement selectedRangeElement;

    @FindBy(xpath = "//android.widget.Button[@content-desc='OK']")
    private WebElement okButtonOnDatePicker;



    // Click on a specific tab by name in m1 screen

    public void clickOnTab(String expectedTabName) {
        boolean found = false;

        for (WebElement tab : allTabs) {
            String desc = tab.getAttribute("content-desc");
            if (desc != null && desc.toLowerCase().contains(expectedTabName.toLowerCase())) {
                tab.click();
                System.out.println("✅ Clicked on tab: " + desc);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new RuntimeException("❌ Expected tab not found: " + expectedTabName);
        }
    }

    /** Click on Favorite tab */
    public void clickFavoriteTab() {
        try {
            favoriteTab.click();
            System.out.println("✅ Clicked on Favorite tab successfully");
        } catch (Exception e) {
            System.out.println("❌ Failed to click Favorite tab: " + e.getMessage());
        }
    }

    // Find machine by name and click on it in m1 screen
    public void clickMachineByName(String machineName) {
        boolean found = false;

        for (int i = 0; i < 5; i++) {  // Limit scroll attempts to avoid infinite loop
            for (WebElement machine : allMachines) {
                String desc = machine.getAttribute("content-desc");
                if (desc != null && desc.contains(machineName)) {
                    System.out.println("✅ Found machine: " + desc);
                    machine.click();
                    found = true;
                    break;
                }
            }
            if (found) break;

            // Scroll if not found yet
            // scrollDown();
        }

        if (!found) {
            throw new RuntimeException("❌ Machine not found in Favorite list: " + machineName);
        }
    }


    //   Get the currently selected machine name displayed on top in M2 screen
    public String getCurrentMachineName() {
        try {
            String machineName = topMachineName.getAttribute("content-desc").trim();
            System.out.println("Current selected machine: " + machineName);
            return machineName;
        } catch (Exception e) {
            throw new RuntimeException("Unable to get current machine name. " + e.getMessage());
        }
    }

    //   Select the machine dynamically from dropdown based on user input in M2 screen
    public void machineChangeOver(String expectedMachineName) {
        try {
            topMachineName.click();
            System.out.println("Clicked on machine dropdown.");
            boolean found = false;
            for (WebElement machine : machineListOnM2MachineDropdown) {
                String desc = machine.getAttribute("content-desc").trim();
                if (desc.equalsIgnoreCase(expectedMachineName)) {
                    machine.click();
                    System.out.println("✅ Selected machine: " + desc);
                    found = true;
                    break;
                }
            }

            if (!found) {
                throw new RuntimeException("❌ Machine not found in dropdown: " + expectedMachineName);
            }

        } catch (Exception e) {
            throw new RuntimeException("Failed to select machine. " + e.getMessage());
        }
    }


    public void clickOnDowntimeSummaryCard() {
        try {
            downtimeSummaryCard.click();
            System.out.println("✅ Clicked on Downtime Summary card.");
        } catch (Exception e) {
            throw new RuntimeException("Failed to click on Downtime Summary card. " + e.getMessage());
        }

    }

     // ---------- METHODS TO GET CARD VALUES ----------
    public int getRunningCardDuration() {
        return parseDuration(runningDurationCard.getText());
    }

    public int getStoppedCardDuration() {
        return parseDuration(stoppedDurationCard.getText());
    }

    public int getWaitingCardDuration() {
        return parseDuration(waitingDurationCard.getText());
    }

    public int getIdleCardDuration() {
        return parseDuration(idleDurationCard.getText());
    }

    public int getProductSetupCardDuration() {
        return parseDuration(productSetupDurationCard.getText());
    }

    public int getDataLossCardDuration() {
        return parseDuration(dataLossDurationCard.getText());
    }

    public int getLogoffCardDuration() {
        return parseDuration(logoffDurationCard.getText());
    }

    public int getBypassCardDuration() {
        return parseDuration(bypassDurationCard.getText());
    }



    // ---------- PARSE DURATION STRING LIKE "1h 25m" INTO MINUTES ----------
    private int parseDuration(String durationText) {
        int totalMinutes = 0;
        if (durationText.contains("h")) {
            String hours = durationText.split("h")[0].trim();
            totalMinutes += Integer.parseInt(hours) * 60;
            if (durationText.contains("m")) {
                String mins = durationText.split("h")[1].replace("m", "").trim();
                totalMinutes += Integer.parseInt(mins);
            }
        } else if (durationText.contains("m")) {
            totalMinutes += Integer.parseInt(durationText.replace("m", "").trim());
        }
        return totalMinutes;
    }

    // ---------- SESSION DURATIONS (ASSUME ELEMENTS DYNAMIC) ----------
    @AndroidFindBy(xpath = "//android.widget.ScrollView//android.view.ViewGroup//android.widget.TextView[contains(@text,'Session Duration')]")
    private List<WebElement> allSessionDurations;

    @AndroidFindBy(xpath = "//android.widget.ScrollView//android.widget.TextView[@resource-id='sessionStatus']")
    private List<WebElement> allSessionStatuses;


    /** Get durations for a specific status */
    public int getTotalDurationForStatus(String status) {
        scroll.scrollUntilVisible("//android.widget.TextView[@text='" + status + "']");
        List<WebElement> sessionElements = driver.findElements(
                By.xpath("//android.widget.TextView[@text='" + status + "']/following-sibling::android.widget.TextView[contains(@text,'min')]")
        );

        int total = 0;
        for (WebElement ele : sessionElements) {
            total += parseDuration(ele.getText());
        }
        return total;
    }

    /** Compare each status session total vs card duration */
    public boolean verifyEachStatusSessionVsCard() {
        boolean allMatch = true;

        allMatch &= (getTotalDurationForStatus("Running") == getRunningCardDuration());
        allMatch &= (getTotalDurationForStatus("Stopped") == getStoppedCardDuration());
        allMatch &= (getTotalDurationForStatus("Waiting") == getWaitingCardDuration());
        allMatch &= (getTotalDurationForStatus("Idle") == getIdleCardDuration());
        allMatch &= (getTotalDurationForStatus("Product Setup") == getProductSetupCardDuration());
        allMatch &= (getTotalDurationForStatus("Data Loss") == getDataLossCardDuration());
        allMatch &= (getTotalDurationForStatus("Log Off") == getLogoffCardDuration());
        allMatch &= (getTotalDurationForStatus("By Pass") == getBypassCardDuration());

        return allMatch;
    }

    /** Compare total of all session durations vs sum of all cards */
    public boolean verifyTotalDurationsMatch() {
        int totalSessions = 0;
        for (WebElement ele : allSessionDurations) {
            totalSessions += parseDuration(ele.getText());
        }

        int totalCards = getRunningCardDuration() + getStoppedCardDuration() + getWaitingCardDuration() +
                getIdleCardDuration() + getProductSetupCardDuration() + getDataLossCardDuration() +
                getLogoffCardDuration() + getBypassCardDuration();

        return totalSessions == totalCards;
    }



    /** Validate session status sequence bottom to top */
     /**
     * ✅ Get all visible session statuses (bottom-to-top order)
     */
    public List<String> getAllSessionStatuses() {
        List<String> statuses = new ArrayList<>();
        for (WebElement status : allSessionStatuses) {
            if (status.isDisplayed()) {
                statuses.add(status.getText().trim());
            }
        }
        // sessions start from bottom → reverse list so we compare in correct order
        Collections.reverse(statuses);
        System.out.println("Captured session statuses (bottom → top): " + statuses);
        return statuses;
    }




    /**
     * ✅ Validate session transitions dynamically based on visible statuses.
     * Checks from bottom (oldest) → top (latest)
     */
    public boolean validateSessionStatusSequence() {
        List<String> statuses = getAllSessionStatuses();

        if (statuses.size() < 2) {
            System.out.println("Not enough sessions to validate transition.");
            return true;
        }

        for (int i = 0; i < statuses.size() - 1; i++) {
            String current = statuses.get(i);      // bottom/older session
            String next = statuses.get(i + 1);     // next (above it)

            if (!isValidTransition(current, next, statuses, i)) {
                System.out.println("❌ Invalid transition found: " + current + " → " + next);
                return false;
            } else {
                System.out.println("✅ Valid transition: " + current + " → " + next);
            }
        }

        System.out.println("✅ All session transitions are valid.");
        return true;
    }

    /**
     * ✅ Defines valid transitions between statuses.
     * Automatically validates the sequence based on conditions.
     */
    private boolean isValidTransition(String current, String next, List<String> allStatuses, int index) {
        switch (current) {
            case "Running":
                return next.matches("Stopped|Data Loss|By Pass");

            case "Stopped":
                return next.matches("Waiting|Product Setup|Data Loss|By Pass");

            case "Product Setup":
                return next.matches("Waiting|Data Loss|By Pass");

            case "Waiting":
                return next.matches("Idle|Running|Stopped|Product Setup|Data Loss|By Pass");

            case "Idle":
                return next.matches("Waiting|Product Setup|Maintenance|By Pass|Data Loss|Log Off");

            case "By Pass":
                // "By Pass" → should repeat previous status dynamically
                if (index > 0) {
                    String previousStatus = allStatuses.get(index - 1);
                    return next.equals(previousStatus);
                }
                return true;

            case "Log Off":
                return next.equals("Idle");

            default:
                return true;
        }
    }



    






    /** Validate session status sequence bottom to top */
    // public boolean verifySessionStatusSequence() {
    //     List<String> statuses = new ArrayList<>();
    //     for (WebElement status : allSessionStatuses) {
    //         statuses.add(status.getText().trim());
    //     }

    //     // Since bottom session is oldest, reverse list for bottom-to-top check
    //     for (int i = statuses.size() - 1; i > 0; i--) {
    //         String current = statuses.get(i);
    //         String next = statuses.get(i - 1);
    //         if (!isValidNextStatus(current, next)) {
    //             System.out.println("Invalid transition from " + current + " → " + next);
    //             return false;
    //         }
    //     }
    //     return true;
    // }

    /** Condition mapping for allowed transitions */
    // private boolean isValidNextStatus(String current, String next) {
    //     switch (current) {
    //         case "Running":
    //             return next.matches("Stopped|Data Loss|By Pass");
    //         case "Stopped":
    //             return next.matches("Waiting|Product Setup|Data Loss|By Pass");
    //         case "Product Setup":
    //             return next.matches("Waiting|Data Loss|By Pass");
    //         case "Waiting":
    //             return next.matches("Idle|Running|Stopped|Product Setup|Data Loss|By Pass");
    //         case "Idle":
    //             return next.matches("Waiting|Product Setup|Maintenance|By Pass|Data Loss|Log Off");
    //         case "By Pass":
    //             return true; // It should repeat the previous status dynamically
    //         case "Log Off":
    //             return next.equals("Idle");
    //         default:
    //             return true;
    //     }
    // }



}