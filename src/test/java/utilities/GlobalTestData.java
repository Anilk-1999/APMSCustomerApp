package utilities;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Test-run-scoped shared data store.
 *
 * Records are created ONCE per module for the entire test run and reused
 * across all scenarios. This avoids the cost of creating a fresh record
 * in every scenario's Background step.
 *
 * Rules:
 *  - "has already created" steps check here first; create only when absent
 *  - Any step that changes a record's name MUST call set() to keep this in sync
 *  - ScenarioContext still holds the per-scenario working copy
 */
public class GlobalTestData {

    private static final Map<String, String> store = new ConcurrentHashMap<>();

    // ── Module keys ───────────────────────────────────────────────────────────
    public static final String ACTIVITY_NAME       = "global_activity_name";
    public static final String ACTIVITY_GROUP_NAME = "global_activity_group_name";
    public static final String HOLIDAY_NAME        = "global_holiday_name";
    public static final String MACHINE_NAME        = "global_machine_name";
    public static final String MACHINE_GROUP_NAME  = "global_machine_group_name";
    public static final String PRODUCT_GROUP_NAME  = "global_product_group_name";
    public static final String PRODUCT_GROUP_CODE  = "global_product_group_code";
    public static final String PRODUCT_NAME         = "global_product_name";
    public static final String PRODUCT_SETUP_NAME   = "global_product_setup_name";
    public static final String RAW_MATERIAL_NAME     = "global_raw_material_name";
    public static final String SCRAP_NAME            = "global_scrap_name";
    public static final String SPARE_NAME            = "global_spare_name";
    public static final String REASON_NAME           = "global_reason_name";
    public static final String OPERATOR_NAME         = "global_operator_name";
    public static final String UPDATED_OPERATOR_NAME = "global_updated_operator_name";
    public static final String USER_NAME             = "global_user_name";
    public static final String UPDATED_USER_NAME     = "global_updated_user_name";
    public static final String USER_EMAIL            = "global_user_email";
    public static final String CONFIRMED_USER_EMAIL  = "global_confirmed_user_email";

    // ── API ───────────────────────────────────────────────────────────────────
    public static String  get(String key)              { return store.get(key); }
    public static void    set(String key, String value) { if (value != null) store.put(key, value); }
    public static boolean has(String key)              { return store.containsKey(key) && store.get(key) != null; }
}
