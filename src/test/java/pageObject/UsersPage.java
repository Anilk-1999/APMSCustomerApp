package pageObject;

import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UsersPage extends BasePage {

    public UsersPage(AndroidDriver driver) {
        super(driver);
    }

    // profileIcon intentionally unused — clickOnProfileIcon() uses a direct driver.findElement
    // so the deep-nested XPath doesn't crash UiAutomator2.

    @AndroidFindBy(xpath = "//android.view.View[@content-desc=\"Account Preferences\"]")
    private WebElement accountPreferenceHeader;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc=\"Insights\"]")
    private WebElement insightSection;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc=\"Configurations\"]")
    private WebElement configurationSection;

    @AndroidFindBy(xpath = "//android.view.View")
    private List<WebElement> sectionHeadersExplorer;

    @AndroidFindBy(xpath = "//android.widget.ImageView")
    private List<WebElement> allFeatures;

    @AndroidFindBy(xpath = "(//android.view.View[contains(@content-desc,' ')])[1]")
    private WebElement addEditAndViewPageHeader;

    @AndroidFindBy(xpath = "(//android.view.View[contains(@content-desc,' ')])[1]")
    private WebElement listPageHeader;

    @AndroidFindBy(xpath = "(//android.view.View[contains(@content-desc,' ')])[0]")
    private WebElement popupAndBottomSheetHeader;

    @AndroidFindBy(accessibility = "Add")
    private WebElement addButton;

    @AndroidFindBy(accessibility = "Search")
    private WebElement searchButton;

    @AndroidFindBy(accessibility = "Sort")
    private WebElement sortButton;

    @AndroidFindBy(accessibility = "Filter")
    private WebElement filterButton;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='User Name *']")
    private WebElement userName;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Email ID *']")
    private WebElement emailId;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Phone No *']")
    private WebElement phoneNo;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Emergency No']")
    private WebElement emergencyNo;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Emp Code']")
    private WebElement empCode;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Blood Group *']")
    private WebElement bloodGroup;

    @AndroidFindBy(xpath = "//android.widget.Button")
    private List<WebElement> dropdownOptions;

    @AndroidFindBy(xpath = "//android.view.View[@hint='Date of Birth']")
    private WebElement dob;

    @AndroidFindBy(xpath = "//android.view.View[@hint='Date of Joining']")
    private WebElement doj;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Previous Month']")
    private WebElement previousMonthButton;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Next Month']")
    private WebElement nextMonthButton;

    @AndroidFindBy(xpath = "//android.widget.Button[contains(@content-desc,'Select year')]")
    private WebElement yearDropdownButton;

    @AndroidFindBy(xpath = "//android.widget.Button")
    private List<WebElement> datesAndYearList;

    @AndroidFindBy(accessibility = "Cancel")
    private WebElement cancelButton;

    @AndroidFindBy(accessibility = "OK")
    private WebElement okButton;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Address Line I']")
    private WebElement address1;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Address Line II']")
    private WebElement address2;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Pin Code']")
    private WebElement pinCode;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc=\"Roles *\"]")
    private WebElement roledropDown;

    @AndroidFindBy(xpath = "//android.view.View[@content-desc=\"Teams\n" +
            "The user will be listed in the dropdowns of the selected teams\"]")
    private WebElement teamsInfo;

    @AndroidFindBy(xpath = "//android.view.View")
    private List<WebElement> teamsOptions;

    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Submit']")
    private WebElement submitButton;

    @AndroidFindBy(xpath = "android.view.View")
    private List<WebElement> listRecords;

    // ── Actions ───────────────────────────────────────────────────────────────

    public String getAccountPreferenceHeader() {
        WebElement header = new WebDriverWait(driver, Duration.ofSeconds(15)).until(d -> {
            try {
                WebElement el = d.findElement(AppiumBy.xpath(
                        "//android.view.View[@content-desc='Account Preferences']"));
                return el.isDisplayed() ? el : null;
            } catch (Exception e) { return null; }
        });
        String getHeader = header.getAttribute("content-desc");
        System.out.println("Account Preference Header: " + getHeader);
        return getHeader;
    }

    public void clickOnProfileIcon() {
        // Use explicit wait so we only query the DOM once the element is actually present,
        // avoiding UiAutomator2 crashes caused by DOM traversal during Flutter animations.
        // 20 s — extra headroom for UiAutomator2 to restart after an instrumentation crash.
        //
        // XPath is tried FIRST because it scopes the search to the nav-bar area (View[1]),
        // which always contains the profile icon. The instance(0) approach fails on a
        // fully-loaded Dashboard because machine-card buttons appear before the nav-bar
        // in the DOM tree, making instance(0) point to a machine button instead.
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement icon;
        try {
            icon = wait.until(d -> {
                try {
                    return d.findElement(By.xpath(
                            "//android.widget.FrameLayout[@resource-id='android:id/content']" +
                            "/android.widget.FrameLayout/android.widget.FrameLayout" +
                            "/android.view.View/android.view.View/android.view.View" +
                            "/android.view.View/android.view.View[1]/android.widget.Button[1]"));
                } catch (Exception e) { return null; }
            });
        } catch (Exception e) {
            // Fallback: instance(0) works when the Dashboard is freshly loaded (before machine
            // card buttons appear ahead of the nav-bar in the DOM).
            icon = wait.until(d -> {
                try {
                    return d.findElement(AppiumBy.androidUIAutomator(
                            "new UiSelector().className(\"android.widget.Button\").instance(0)"));
                } catch (Exception ex) { return null; }
            });
        }
        icon.click();
    }

    public void clickOnSectionHeader(String expectedHeader) {
        // Section headers have content-desc like "Configurations, Collapsed" or "Configurations, Expanded".
        // Try UiScrollable first; fall back to direct find when content fits without scrolling.
        WebElement header = findSectionHeader(expectedHeader);
        waitForVisibility(header);
        String descBefore = header.getAttribute("content-desc");
        System.out.println("[UsersPage] Section header desc: " + descBefore);
        // Only click if collapsed — clicking an already-expanded section would collapse it,
        // causing subsequent clickOnFeature() calls to fail.
        if (descBefore != null && descBefore.contains("Expanded")) {
            System.out.println("[UsersPage] Section already expanded, skipping click: " + expectedHeader);
            return;
        }
        header.click();
        System.out.println("[UsersPage] Clicked on section header: " + expectedHeader);
        // Wait until content-desc changes — signals the section expand animation is done.
        final String capturedDesc = descBefore;
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(d -> {
                    try {
                        WebElement el = findSectionHeader(expectedHeader);
                        String desc = el.getAttribute("content-desc");
                        return desc != null && !desc.equals(capturedDesc);
                    } catch (Exception e) { return false; }
                });
    }

    public String getSectionHeaderText(String expectedHeader) {
        // Scroll to section header, strip ", Collapsed" / ", Expanded" suffix before returning.
        WebElement header = findSectionHeader(expectedHeader);
        waitForVisibility(header);
        String desc = header.getAttribute("content-desc");
        return desc.split(",")[0].trim();
    }

    private WebElement findSectionHeader(String expectedHeader) {
        try {
            return driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiScrollable(new UiSelector().scrollable(true))" +
                    ".scrollIntoView(new UiSelector().descriptionStartsWith(\"" + expectedHeader + "\"))"));
        } catch (Exception scrollEx) {
            // No scrollable container — content fits on screen without scrolling.
            System.out.println("[UsersPage] No scrollable container — trying direct find for: " + expectedHeader);
            return driver.findElement(AppiumBy.androidUIAutomator(
                    "new UiSelector().descriptionStartsWith(\"" + expectedHeader + "\")"));
        }
    }

    public void clickOnFeature(String expectedFeature) {
        // Scroll until the feature icon is visible, then click.
        // Wait 2 s between attempts — the Configurations section may still be rendering
        // after a back-navigation, and UiScrollable cannot find items not yet in the tree.
        WebElement feature = null;
        Exception lastEx = null;
        for (int attempt = 1; attempt <= 3; attempt++) {
            try {
                feature = driver.findElement(AppiumBy.androidUIAutomator(
                        "new UiScrollable(new UiSelector().scrollable(true))" +
                        ".scrollIntoView(new UiSelector().description(\"" + expectedFeature + "\"))"));
                break;
            } catch (Exception e) {
                lastEx = e;
                if (attempt < 3) {
                    try { Thread.sleep(2000); } catch (InterruptedException ex) { Thread.currentThread().interrupt(); }
                }
            }
        }
        if (feature == null) throw new RuntimeException("Feature not found after retry: " + expectedFeature, lastEx);
        waitForVisibility(feature);
        feature.click();
        System.out.println("Clicked on feature: " + expectedFeature);
    }

    public String getFullListHeaderText() {
        return listPageHeader.getAttribute("content-desc");
    }

    public String getListPageTitle() {
        // Brief pause lets the Flutter screen-transition animation begin before polling.
        // Without this, rapid findElement calls start before the screen is rendered,
        // stressing UiAutomator2 instrumentation and occasionally causing crashes.
        try { Thread.sleep(1500); } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
        // Poll at 1-second intervals (not the default 500 ms) to reduce load on UiAutomator2.
        new WebDriverWait(driver, Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(1))
                .until(d -> {
                    try {
                        return d.findElement(AppiumBy.accessibilityId("Search")).isDisplayed();
                    } catch (Exception e) { return false; }
                });
        String header = getFullListHeaderText();
        String firstLine = header.split("\\r?\\n")[0];
        return firstLine.replaceAll("\\d", "").trim();
    }

    public int getTotalListCount() {
        String header = getFullListHeaderText();
        String number = header.replaceAll("\\D+", "");
        if (number.isEmpty()) return 0;
        return Integer.parseInt(number);
    }

    public String getPageHeaderText() {
        System.out.println("get header text: " + addEditAndViewPageHeader.getAttribute("content-desc"));
        waitForVisibility(addEditAndViewPageHeader);
        return addEditAndViewPageHeader.getAttribute("content-desc");
    }

    public String getPopupHeaderText() {
        return popupAndBottomSheetHeader.getAttribute("content-desc");
    }

    public void clickOnAddButton() {
        waitForVisibility(addButton);
        if (addButton.isEnabled()) {
            addButton.click();
        }
    }

    public void enterUserName(String name) {
        waitForVisibility(userName);
        if (userName.isEnabled()) {
            userName.click();
            userName.clear();
            userName.sendKeys(name);
        }
    }

    public void enterEmail(String email) {
        waitForVisibility(emailId);
        if (emailId.isEnabled()) {
            emailId.click();
            emailId.clear();
            emailId.sendKeys(email);
        }
    }

    public void enterPhone(String phone) {
        waitForVisibility(phoneNo);
        if (phoneNo.isEnabled()) {
            phoneNo.click();
            phoneNo.clear();
            phoneNo.sendKeys(phone);
        }
    }

    public void enterEmergencyNo(String emergency) {
        waitForVisibility(emergencyNo);
        if (emergencyNo.isEnabled()) {
            emergencyNo.click();
            emergencyNo.clear();
            emergencyNo.sendKeys(emergency);
        }
    }

    int startX;
    int startY;
    int endX;
    int endY;

    public void getScreenSize() {
        Dimension size = driver.manage().window().getSize();
        int screenWidth  = size.width;
        int screenHeight = size.height;
        startX = screenWidth / 2;
        startY = (int) (screenHeight * 0.8);
        endX   = screenWidth / 2;
        endY   = (int) (screenHeight * 0.2);
        System.out.println("startx: " + startX + " starty: " + startY + " endx: " + endX + " endy: " + endY);
    }

    public void enterEmpCode(String code) {
        if (((AndroidDriver) driver).isKeyboardShown()) {
            ((AndroidDriver) driver).hideKeyboard();
        }
        waitForVisibility(empCode);
        if (empCode.isEnabled()) {
            empCode.click();
            if (((AndroidDriver) driver).isKeyboardShown()) {
                ((AndroidDriver) driver).hideKeyboard();
            }
            empCode.clear();
            empCode.sendKeys(code);
        }
    }

    public void selectOptionInDropdown(String optionText) {
        waitForVisibilities(dropdownOptions);
        for (WebElement option : dropdownOptions) {
            if (option.getAttribute("content-desc").equalsIgnoreCase(optionText)) {
                option.click();
                return;
            }
        }
        throw new RuntimeException("Dropdown option not found: " + optionText);
    }

    public void selectBloodGroup(String group) {
        if (((AndroidDriver) driver).isKeyboardShown()) {
            ((AndroidDriver) driver).hideKeyboard();
        }
        waitForVisibility(bloodGroup);
        if (bloodGroup.isEnabled()) {
            bloodGroup.click();
            selectOptionInDropdown(group);
        }
    }

    public void selectDate(String month, String year, String date) {
        waitForVisibility(yearDropdownButton);
        String selectedMonthYear = yearDropdownButton.getAttribute("content-desc");
        System.out.println("Selected Month/Year: " + selectedMonthYear);
        if (!selectedMonthYear.equals(month + " " + year)) {
            yearDropdownButton.click();
            waitForVisibilities(datesAndYearList);
            for (WebElement yearElement : datesAndYearList) {
                if (yearElement.getAttribute("content-desc").equals(year)) {
                    yearElement.click();
                    break;
                }
            }
        }
        if (!selectedMonthYear.startsWith(month)) {
            while (true) {
                selectedMonthYear = yearDropdownButton.getAttribute("content-desc");
                if (selectedMonthYear.startsWith(month)) break;
                nextMonthButton.click();
            }
        }
        for (WebElement dateElement : datesAndYearList) {
            if (dateElement.getAttribute("content-desc").equals(date)) {
                dateElement.click();
                break;
            }
        }
    }

    public void enterDOB(String month, String year, String date) {
        waitForVisibility(dob);
        if (dob.isEnabled()) {
            dob.click();
            selectDate(month, year, date);
            okButton.click();
        }
    }

    public void enterDOJ(String month, String year, String date) {
        waitForVisibility(doj);
        if (doj.isEnabled()) {
            doj.click();
            selectDate(month, year, date);
            okButton.click();
        }
    }

    public void clickOnDOBButton() {
        if (((AndroidDriver) driver).isKeyboardShown()) {
            ((AndroidDriver) driver).hideKeyboard();
        }
        waitForVisibility(dob);
        if (dob.isEnabled()) {
            dob.click();
        }
    }

    public void clickOnDOJButton() {
        waitForVisibility(doj);
        if (doj.isEnabled()) {
            doj.click();
        }
    }

    public void clickOnCancelButton() {
        waitForVisibility(cancelButton);
        if (cancelButton.isEnabled()) {
            cancelButton.click();
        }
    }

    public void clickOnOkButton() {
        waitForVisibility(okButton);
        if (okButton.isEnabled()) {
            okButton.click();
        }
    }

    public void selectDateOfBirth() {
        clickOnDOBButton();
        clickOnOkButton();
    }

    public void selectDateOfJoining() {
        clickOnDOJButton();
        clickOnOkButton();
    }

    public void enterAddress1(String addr1) {
        if (((AndroidDriver) driver).isKeyboardShown()) {
            ((AndroidDriver) driver).hideKeyboard();
        }
        waitForVisibility(address1);
        if (address1.isEnabled()) {
            address1.click();
            if (((AndroidDriver) driver).isKeyboardShown()) {
                ((AndroidDriver) driver).hideKeyboard();
            }
            address1.clear();
            address1.sendKeys(addr1);
        }
    }

    public void enterAddress2(String addr2) {
        if (((AndroidDriver) driver).isKeyboardShown()) {
            ((AndroidDriver) driver).hideKeyboard();
        }
        scrollUntilVisible(address2);
        waitForVisibility(address2);
        if (address2.isEnabled()) {
            address2.click();
            if (((AndroidDriver) driver).isKeyboardShown()) {
                ((AndroidDriver) driver).hideKeyboard();
            }
            address2.clear();
            address2.sendKeys(addr2);
        }
    }

    public void enterPinCode(String pin) {
        if (((AndroidDriver) driver).isKeyboardShown()) {
            ((AndroidDriver) driver).hideKeyboard();
        }
        scrollUntilVisible(pinCode);
        waitForVisibility(pinCode);
        if (pinCode.isEnabled()) {
            pinCode.click();
            if (((AndroidDriver) driver).isKeyboardShown()) {
                ((AndroidDriver) driver).hideKeyboard();
            }
            pinCode.clear();
            pinCode.sendKeys(pin);
        }
    }

    public void selectRole(String roleName) {
        scrollUntilVisible(roledropDown);
        waitForVisibility(roledropDown);
        if (roledropDown.isEnabled()) {
            roledropDown.click();
            System.out.println("role name: " + roleName);
            selectOptionInDropdown(roleName);
        }
    }

    public void teamSelection(String teamName) {
        if (((AndroidDriver) driver).isKeyboardShown()) {
            ((AndroidDriver) driver).hideKeyboard();
        }
        waitForVisibility(teamsInfo);
        if (teamsInfo.isDisplayed()) {
            waitForVisibilities(teamsOptions);
            for (WebElement option : teamsOptions) {
                System.out.println("get team options: " + option.getAttribute("content-desc"));
                if (option.getAttribute("content-desc").equalsIgnoreCase(teamName)) {
                    waitForVisibility(option);
                    option.click();
                    return;
                }
            }
        }
    }

    public void clickOnSubmitButton() {
        waitForVisibility(submitButton);
        if (submitButton.isEnabled()) {
            submitButton.click();
        }
    }

    public void getConfirmationMsg() throws InterruptedException {
        // confirmation is handled by the step definition assertion
    }

    public void clickOnsearchButton() {
        waitForVisibility(searchButton);
        if (searchButton.isEnabled()) {
            searchButton.click();
        }
    }

    public void clickOnSortButton() {
        waitForVisibility(sortButton);
        if (sortButton.isEnabled()) {
            sortButton.click();
        }
    }

    public void clickOnFilterButton() {
        waitForVisibility(filterButton);
        if (filterButton.isEnabled()) {
            filterButton.click();
        }
    }

    public void getListOfRecords() {
        waitForVisibilities(listRecords);
        for (WebElement listItem : listRecords) {
            System.out.println("get list of records: " + listItem.getAttribute("content-desc"));
        }
    }

    public void swipeRightToLeft() {
        for (WebElement listItem : listRecords) {
            int swipeStartX = (int) (listItem.getLocation().getX() + listItem.getSize().getWidth() * 0.9);
            int swipeEndX   = (int) (listItem.getLocation().getX() + listItem.getSize().getWidth() * 0.1);
            int yAxis       = listItem.getLocation().getY() + (listItem.getSize().getHeight() / 2);

            PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
            Sequence swipe = new Sequence(finger, 1);
            swipe.addAction(finger.createPointerMove(Duration.ZERO, PointerInput.Origin.viewport(), swipeStartX, yAxis));
            swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
            swipe.addAction(finger.createPointerMove(Duration.ofMillis(500), PointerInput.Origin.viewport(), swipeEndX, yAxis));
            swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
            driver.perform(Arrays.asList(swipe));
        }
    }
}
