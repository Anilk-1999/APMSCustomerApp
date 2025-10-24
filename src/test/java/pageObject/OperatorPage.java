package pageObject;

import org.openqa.selenium.WebElement;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import utilities.ScrollUtils;

public class OperatorPage extends BasePage {

 public OperatorPage(AndroidDriver driver) {
        super(driver);
    }

    @AndroidFindBy(xpath = "//android.view.View[@content-desc='Add New Operator']/preceding-sibling::android.widget.Button")
    private WebElement backButton;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Email ID']")
    private WebElement emailIdOptionField;

   @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Operator Name *']")
   private WebElement operatorName;

   @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Operator Code']")
   private WebElement operatorCode;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='City']")
    private WebElement cityTextField;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='State']")
    private WebElement stateTextField;

    @AndroidFindBy(xpath = "//android.widget.EditText[@hint='Country']")
    private WebElement countryTextField;


// Implement actions methods for the elements
   public void enterOperatorName(String name) {
        scroll = new ScrollUtils(driver);
        waitUtil.waitForVisibility(operatorName);
        operatorName.click();
        operatorName.clear();
        operatorName.sendKeys(name);
   }

   public void enterOptionalEmail(String email) {
        waitUtil.waitForVisibility(emailIdOptionField);
        if (emailIdOptionField.isEnabled()) {
            emailIdOptionField.click();
            emailIdOptionField.clear();
            emailIdOptionField.sendKeys(email);
        }
    }


   
   public void enterOperatorCode(String code) {
       waitUtil.waitForVisibility(operatorCode);
       scroll.scrollUntilVisible(operatorCode);
       waitUtil.waitForVisibility(operatorCode);
       operatorCode.click();
       if (((AndroidDriver) driver).isKeyboardShown()) {
            ((AndroidDriver) driver).hideKeyboard();
            }
       operatorCode.clear();
       operatorCode.sendKeys(code);
   }

    public void enterCity(String city) {
        scroll.scrollUntilVisible(cityTextField);
        waitUtil.waitForVisibility(cityTextField);
        cityTextField.click();
        if (((AndroidDriver) driver).isKeyboardShown()) {
            ((AndroidDriver) driver).hideKeyboard();
            }
        cityTextField.clear();
        cityTextField.sendKeys(city);
    }

    public void enterState(String state) {
        scroll.scrollUntilVisible(stateTextField);
        waitUtil.waitForVisibility(stateTextField);
        stateTextField.click();
        if (((AndroidDriver) driver).isKeyboardShown()) {
            ((AndroidDriver) driver).hideKeyboard();
        }
        stateTextField.clear();
        stateTextField.sendKeys(state);
    }

    public void enterCountry(String country) {
        scroll.scrollUntilVisible(countryTextField);
        waitUtil.waitForVisibility(countryTextField);
        countryTextField.click();
        if (((AndroidDriver) driver).isKeyboardShown()) {
            ((AndroidDriver) driver).hideKeyboard();
        }
        countryTextField.clear();
        countryTextField.sendKeys(country);
    }

    public void clickBackButton() {
        waitUtil.waitForVisibility(backButton);
        if(backButton.isDisplayed()) {
          backButton.click();
        }
       
    }


}
