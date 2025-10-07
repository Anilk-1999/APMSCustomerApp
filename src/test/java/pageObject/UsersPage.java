package pageObject;

import java.util.List;

import javax.management.relation.Role;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import utilities.ScrollUtils;
import utilities.WaitForElement;

public class UsersPage extends BasePage {

    public UsersPage(AndroidDriver driver) {
        super(driver);
    }


    @AndroidFindBy(xpath = "//android.view.View[@content-desc='MI-U']/preceding-sibling::android.widget.Button")
    private WebElement profileIcon;

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


      // User Name
    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='User Name *']")
    private WebElement userName;

    // Email ID
    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Email ID *']")
    private WebElement emailId;

    // Phone No
    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Phone No *']")
    private WebElement phoneNo;

    // Emergency No
    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Emergency No']")
    private WebElement emergencyNo;

    // Emp Code
    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Emp Code']")
    private WebElement empCode;

    // Blood Group (button with content-desc)
    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Blood Group *']")
    private WebElement bloodGroup;


    @AndroidFindBy(xpath = "//android.widget.Button")
    private List<WebElement> dropdownOptions;

    // Date of Birth
    @AndroidFindBy(xpath = "//android.view.View[@hint='Date of Birth']")
    private WebElement dob;

    // Date of Joining
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


    // Address Line 1
    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Address Line I']")
    private WebElement address1;

    // Address Line 2
    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Address Line II']")
    private WebElement address2;

    // Pin Code (assuming you have a field with hint 'Pin Code')
    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Pin Code']")
    private WebElement pinCode;

    // Role (assuming it is a dropdown/button with content-desc 'Role')
    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc=\"Roles *\"]")
    private WebElement roledropDown;

    @AndroidFindBy(accessibility = "Teams\nBy default, users are added to all teams if no team is selected.")
    private WebElement teamsInfo;

    @AndroidFindBy(xpath = "//android.view.View")
    private List<WebElement> teamsOptions;

    // Submit button
    @AndroidFindBy(xpath = "//android.widget.Button[@content-desc='Submit']")
    private WebElement submitButton;


    public String getAccountPreferenceHeader() {
        scroll=new ScrollUtils(driver);
        waitUtil=new WaitForElement(driver);
        waitUtil.waitForVisibility(accountPreferenceHeader);
        String getHeader = accountPreferenceHeader.getAttribute("content-desc");
        System.out.println("Account Preference Header: " + getHeader);
        return getHeader;

    }

    //clicks on the profile icon
    public void clickOnProfileIcon() {
        waitUtil.waitForVisibility(profileIcon);
        if (profileIcon.isDisplayed()) {
            profileIcon.click();
        }
    }


    //Clicks on the given header section if available
    public void clickOnSectionHeader(String expectedHeader) {
        waitUtil.waitForVisibilities(sectionHeadersExplorer);
        for (WebElement header : sectionHeadersExplorer) {
            String headerText = header.getAttribute("content-desc");
            if (headerText.equalsIgnoreCase(expectedHeader)) {
                waitUtil.waitForVisibility(header);
                header.click();
                System.out.println("Clicked on section header: " + headerText);
                return;
            }
        }
        throw new RuntimeException("Section header not found: " + expectedHeader);
    }

public String getSectionHeaderText(String expectedHeader) {
    // First, check visible headers
    waitUtil.waitForVisibilities(sectionHeadersExplorer);
    for (WebElement header : sectionHeadersExplorer) {
        if (header.isDisplayed() && header.getAttribute("content-desc").equalsIgnoreCase(expectedHeader)) {
            return header.getAttribute("content-desc");
        }
    }
    // If not found, scroll and try again
    // scrollToText(expectedHeader);

    // Re-locate headers after scroll
    waitUtil.waitForVisibilities(sectionHeadersExplorer);
    for (WebElement header : sectionHeadersExplorer) {
        if (header.isDisplayed() && header.getAttribute("content-desc").equalsIgnoreCase(expectedHeader)) {
            return header.getAttribute("content-desc");
        }
    }
    throw new RuntimeException("Section header not found: " + expectedHeader);
}


    //Clicks on the given feature if available
    public void clickOnFeature(String expectedFeature) {
        waitUtil.waitForVisibilities(allFeatures);
        for (WebElement feature : allFeatures) {
            String featureText = feature.getAttribute("content-desc");
            if (featureText.equalsIgnoreCase(expectedFeature)) {
                waitUtil.waitForVisibility(feature);
                feature.click();
                System.out.println("Clicked on feature: " + featureText);
                return;
            }
        }
        throw new RuntimeException("Feature not found: " + expectedFeature);
    }


    // Get full header text
    public String getFullListHeaderText() {
        return listPageHeader.getAttribute("content-desc");
    }

    // Get only the title part
    public String getListPageTitle() {
        waitUtil.waitForVisibility(searchButton);
        String header = getFullListHeaderText();
        String firstLine = header.split("\\r?\\n")[0]; // take only first line
        return firstLine.replaceAll("\\d", "").trim();
    }

    // Get only the number part
    public int getTotalListCount() {
        String header = getFullListHeaderText();
        String number = header.replaceAll("\\D+", ""); // keep only digits
        if (number.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(number);
    }


    public String getPageHeaderText() {
        System.out.println("get header text :-------"+addEditAndViewPageHeader.getAttribute("content-desc"));
         waitUtil.waitForVisibility(addEditAndViewPageHeader);
        return addEditAndViewPageHeader.getAttribute("content-desc");
    }

    public String getPopupHeaderText() {
        return popupAndBottomSheetHeader.getAttribute("content-desc");
    }

    // Clicks on the Add button if the header matches
    public void clickOnAddButton() {
            waitUtil.waitForVisibility(addButton);
            if (addButton.isEnabled()) {
                addButton.click();
            }
        }
   

     // Methods to fill the form
    public void enterUserName(String name) {
        waitUtil.waitForVisibility(userName);
        if (userName.isEnabled()) {
            userName.click();
            userName.clear();
            userName.sendKeys(name);
        }
    }

    public void enterEmail(String email) {
        waitUtil.waitForVisibility(emailId);
        if (emailId.isEnabled()) {
            emailId.click();
            emailId.clear();
            emailId.sendKeys(email);
        }
    }

    public void enterPhone(String phone) {
        waitUtil.waitForVisibility(phoneNo);
        if (phoneNo.isEnabled()) {
            phoneNo.click();
            phoneNo.clear();
            phoneNo.sendKeys(phone);
        }
    }

    public void enterEmergencyNo(String emergency) {
        waitUtil.waitForVisibility(emergencyNo);
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

public void getScreenSize()
{
    Dimension size = driver.manage().window().getSize();
    int screenWidth = size.width;
    int screenHeight = size.height;

    startX = screenWidth / 2;              // Middle of the screen (X-axis)
    startY = (int) (screenHeight * 0.8);   // Near bottom (80% height)
    endX   = screenWidth / 2;              // Keep X same (vertical scroll)
    endY   = (int) (screenHeight * 0.2);   // Near top (20% height)

System.out.println("startx :-----"+startX);
System.out.println("starty :-----"+startY);
System.out.println("endx :-----"+endX);
System.out.println("endy :-----"+endY);

}


    public void enterEmpCode(String code) {
        if (((AndroidDriver) driver).isKeyboardShown()) {
           ((AndroidDriver) driver).hideKeyboard();
        }
       waitUtil.waitForVisibility(empCode);
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
        waitUtil.waitForVisibilities(dropdownOptions);
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
        waitUtil.waitForVisibility(bloodGroup);
        if (bloodGroup.isEnabled()) {
            bloodGroup.click();
            selectOptionInDropdown(group);
        }
    
    }


    public void selectDate(String month, String year, String date)
    {
        waitUtil.waitForVisibility(yearDropdownButton);
        String selectedMonthYear = yearDropdownButton.getAttribute("content-desc");
        System.out.println("Selected Month/Year: " + selectedMonthYear);
        if (!selectedMonthYear.equals(month + " " + year)) {
            yearDropdownButton.click();
            waitUtil.waitForVisibilities(datesAndYearList);
            for (WebElement yearElement : datesAndYearList) {
                if (yearElement.getAttribute("content-desc").equals(year)) {
                    yearElement.click();
                    break;
                }
            }
        }
        if(!selectedMonthYear.startsWith(month)) {
            while (true) {
                selectedMonthYear = yearDropdownButton.getAttribute("content-desc");
                if (selectedMonthYear.startsWith(month)) {
                    break;
                }
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
        waitUtil.waitForVisibility(dob);
        if (dob.isEnabled()) {
            dob.click();
            selectDate(month, year, date);
            okButton.click();
        }
    }

    public void enterDOJ(String month, String year, String date) {
        waitUtil.waitForVisibility(doj);
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
        waitUtil.waitForVisibility(dob);
        if (dob.isEnabled()) {
            dob.click();
        }
    }

    public void clickOnDOJButton() {
        waitUtil.waitForVisibility(doj);
        if (doj.isEnabled()) {
            doj.click();
        }
    }

    public void clickOnCancelButton() {
        waitUtil.waitForVisibility(cancelButton);
        if (cancelButton.isEnabled()) {
            cancelButton.click();
        }
    }

    public void clickOnOkButton() {
        waitUtil.waitForVisibility(okButton);
        if (okButton.isEnabled()) {
            okButton.click();
        }
    }


    public void selectDateOfBirth()
    {
        clickOnDOBButton();
        clickOnOkButton();
    }

    public void selectDateOfJoining()
    {
        clickOnDOJButton();
        clickOnOkButton();
    }

    public void enterAddress1(String addr1) {
        if (((AndroidDriver) driver).isKeyboardShown()) {
           ((AndroidDriver) driver).hideKeyboard();
        }
        waitUtil.waitForVisibility(address1);
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
        scroll.scrollUntilVisible(address2);
        waitUtil.waitForVisibility(address2);
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
        scroll.scrollUntilVisible(pinCode);
        waitUtil.waitForVisibility(pinCode);
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
        scroll.scrollUntilVisible(roledropDown);
        waitUtil.waitForVisibility(roledropDown);
        if (roledropDown.isEnabled()) {
            roledropDown.click();
            System.out.println("role name :-------" + roleName);
            selectOptionInDropdown(roleName);
        }
    }

    public void teamSelection(String teamName)
    {
        if (((AndroidDriver) driver).isKeyboardShown()) {
           ((AndroidDriver) driver).hideKeyboard();
        }
        scroll.scrollUntilVisible(pinCode);
        waitUtil.waitForVisibility(teamsInfo);
        if(teamsInfo.isDisplayed()) {
            waitUtil.waitForVisibilities(teamsOptions);
            for (WebElement option : teamsOptions) {
                System.out.println("get team options : ---------"+option);
                if (option.getAttribute("content-desc").equalsIgnoreCase(teamName)) {
                    waitUtil.waitForVisibility(option);
                    option.click();
                    return;
                }
            }
        }
    }

    public void clickOnSubmitButton() {
        waitUtil.waitForVisibility(submitButton);
        if (submitButton.isEnabled()) {
            submitButton.click();
        }
    }


    public void getConfirmationMsg() throws InterruptedException {

   }

    




}
