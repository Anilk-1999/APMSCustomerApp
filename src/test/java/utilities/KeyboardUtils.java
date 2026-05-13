package utilities;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * Keyboard operations — safe wrappers that never throw on no-op.
 *
 * Flutter fields:
 *  - clear() may leave residual text → clearField() uses backspace fallback
 *  - isKeyboardShown() is reliable on Android/UiAutomator2
 */
public class KeyboardUtils {

    private final AndroidDriver driver;

    public KeyboardUtils(AndroidDriver driver) {
        this.driver = driver;
    }

    // ═══════════════════════════════════════════════════════
    //  KEYBOARD STATE
    // ═══════════════════════════════════════════════════════

    public boolean isKeyboardVisible() {
        try { return driver.isKeyboardShown(); }
        catch (Exception e) { return false; }
    }

    // ═══════════════════════════════════════════════════════
    //  KEYBOARD DISMISS
    // ═══════════════════════════════════════════════════════

    /** Hides the keyboard. No-op if already hidden. */
    public void hideKeyboardSafely() {
        try { driver.hideKeyboard(); }
        catch (Exception ignored) {}
    }

    /** Hides keyboard only if currently visible — avoids an unnecessary Appium call. */
    public void hideKeyboardIfVisible() {
        if (isKeyboardVisible()) hideKeyboardSafely();
    }

    // ═══════════════════════════════════════════════════════
    //  FIELD CLEARING
    // ═══════════════════════════════════════════════════════

    /**
     * Robustly clears a text field:
     *  1. click() to focus
     *  2. clear() to remove content
     *  3. Backspace over any text that clear() left behind (Flutter quirk)
     */
    public void clearField(WebElement field) {
        try {
            field.click();
            field.clear();
            String remaining = field.getAttribute("text");
            if (remaining != null && !remaining.isEmpty()) {
                for (int i = 0; i < remaining.length() + 2; i++) {
                    field.sendKeys(Keys.BACK_SPACE);
                }
            }
        } catch (Exception ignored) {}
    }

    /**
     * Clears a field and then types the given text.
     * Equivalent to clearField() + field.sendKeys(text).
     */
    public void clearAndType(WebElement field, String text) {
        clearField(field);
        try { field.sendKeys(text); } catch (Exception ignored) {}
    }
}
