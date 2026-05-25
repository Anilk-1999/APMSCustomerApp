package utilities;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Run-scoped store for the latest entity name per module.
 *
 * Records are created ONCE per test run and reused across all scenarios.
 * After any successful update, the caller must call setLatestName() so
 * subsequent scenarios always search by the current name.
 *
 * Usage:
 *   GlobalEntityStore.setLatestName(GlobalEntityStore.ACTIVITY_GROUP, name);
 *   GlobalEntityStore.getLatestName(GlobalEntityStore.ACTIVITY_GROUP);
 *
 * Rules:
 *   - Background steps check here first; create only when absent.
 *   - Update success steps MUST call setLatestName() to keep store in sync.
 *   - Activity module is excluded — it manages its own data lifecycle.
 */
public class GlobalEntityStore {

    private static final Map<String, String> latestNames = new ConcurrentHashMap<>();

    // ── Entity type keys ─────────────────────────────────────────────────────
    public static final String ACTIVITY_GROUP = "ACTIVITY_GROUP";
    public static final String HOLIDAY        = "HOLIDAY";
    public static final String MACHINE        = "MACHINE";
    public static final String MACHINE_GROUP  = "MACHINE_GROUP";
    public static final String PRODUCT_GROUP  = "PRODUCT_GROUP";
    public static final String PRODUCT        = "PRODUCT";
    public static final String RAW_MATERIAL   = "RAW_MATERIAL";
    public static final String SCRAP          = "SCRAP";
    public static final String SPARE          = "SPARE";
    public static final String REASON         = "REASON";
    public static final String OPERATOR            = "OPERATOR";
    public static final String USER                = "USER";
    public static final String PRODUCT_SETUP_TYPE  = "PRODUCT_SETUP_TYPE";

    // ── API ──────────────────────────────────────────────────────────────────

    public static void setLatestName(String entityType, String name) {
        if (name != null && !name.isEmpty()) latestNames.put(entityType, name);
    }

    public static String getLatestName(String entityType) {
        return latestNames.get(entityType);
    }

    public static boolean hasLatestName(String entityType) {
        String v = latestNames.get(entityType);
        return v != null && !v.isEmpty();
    }

    public static void clear(String entityType) {
        latestNames.remove(entityType);
    }
}
