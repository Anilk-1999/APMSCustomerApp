package utilities;

import java.util.HashMap;
import java.util.Map;

/**
 * Shares test state between Step Definition classes within one scenario.
 * PicoContainer injects the same instance across all step classes in a scenario.
 *
 * Usage:
 *   context.set(ContextKey.ACTIVITY_NAME, "Activity_1234");
 *   context.getString(ContextKey.ACTIVITY_NAME);
 */
public class ScenarioContext {

    private final Map<String, Object> store = new HashMap<>();

    // ── Keys (avoid raw string literals in step defs) ─────────────────────────

    public static final String ACTIVITY_NAME          = "ACTIVITY_NAME";
    public static final String ACTIVITY_GROUP_NAME    = "ACTIVITY_GROUP_NAME";
    public static final String HOLIDAY_NAME           = "HOLIDAY_NAME";
    public static final String MACHINE_NAME           = "MACHINE_NAME";
    public static final String MACHINE_GROUP_NAME     = "MACHINE_GROUP_NAME";
    public static final String PRODUCT_GROUP_NAME     = "PRODUCT_GROUP_NAME";
    public static final String PRODUCT_GROUP_CODE     = "PRODUCT_GROUP_CODE";
    public static final String PRODUCT_NAME           = "PRODUCT_NAME";
    public static final String PRODUCT_SETUP_NAME     = "PRODUCT_SETUP_NAME";
    public static final String RAW_MATERIAL_NAME      = "RAW_MATERIAL_NAME";
    public static final String SCRAP_NAME             = "SCRAP_NAME";
    public static final String SPARE_NAME             = "SPARE_NAME";
    public static final String SPARE_CODE             = "SPARE_CODE";
    public static final String REASON_NAME            = "REASON_NAME";
    public static final String REASON_GROUP_NAME      = "REASON_GROUP_NAME";
    public static final String USER_NAME              = "USER_NAME";
    public static final String UPDATED_USER_NAME      = "UPDATED_USER_NAME";
    public static final String OPERATOR_NAME          = "OPERATOR_NAME";
    public static final String UPDATED_OPERATOR_NAME  = "UPDATED_OPERATOR_NAME";
    public static final String CURRENT_STATUS         = "CURRENT_STATUS";
    public static final String PENDING_ACTIVITY_NAME  = "PENDING_ACTIVITY_NAME";
    public static final String VIEW_TOGGLE_STATE        = "VIEW_TOGGLE_STATE";
    public static final String MACHINE_COUNT_BEFORE     = "MACHINE_COUNT_BEFORE";

    // ── API ───────────────────────────────────────────────────────────────────

    public void set(String key, Object value) {
        store.put(key, value);
    }

    public Object get(String key) {
        return store.get(key);
    }

    public String getString(String key) {
        Object value = store.get(key);
        return value != null ? String.valueOf(value) : null;
    }

    public boolean contains(String key) {
        return store.containsKey(key);
    }

    public void clear() {
        store.clear();
    }
}
