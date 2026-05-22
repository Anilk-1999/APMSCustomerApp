package utilities;

import java.util.Random;

/**
 * Centralised random test-data factory.
 * Replaces TestRandomDataGenerator + RandomElements (merged here).
 */
public class DataGenerator {

    private static final Random RNG = new Random();

    // ── Activities ────────────────────────────────────────────────────────────

    public static String randomActivityName()      { return "Activity_"  + fourDigit(); }
    public static String randomActivityGroupName() { return "ActGroup_"  + fourDigit(); }

    // ── Holidays ──────────────────────────────────────────────────────────────

    public static String randomHolidayName() { return "Holiday_" + fourDigit(); }
    /** Returns a day number between 15–28 — safe range for any calendar month. */
    public static int randomFutureDay()      { return 15 + RNG.nextInt(14); }

    // ── Machines / Machine Groups ─────────────────────────────────────────────

    public static String randomMachineName()       { return "Machine_"   + fourDigit(); }
    public static String randomMachineCode()       { return "MCH"        + fourDigit(); }
    public static String randomMachineGroupName()  { return "MchGrp_"   + fourDigit(); }

    // ── Products / Product Groups ─────────────────────────────────────────────

    public static String randomProductGroupName()  { return "PrdGrp_"   + fourDigit(); }
    public static String randomProductGroupCode()  { return "PGRP"      + fourDigit(); }
    public static String randomProductName()       { return "Product_"  + fourDigit(); }
    public static String randomProductCode()       { return "PRD"       + fourDigit(); }
    public static String randomProductSetupName()  { return "PSType_"   + fourDigit(); }

    // ── Raw Materials / Scraps ────────────────────────────────────────────────

    public static String randomRawMaterialName()   { return "RawMat_"   + fourDigit(); }
    public static String randomScrapName()         { return "Scrap_"    + fourDigit(); }

    // ── Spares ───────────────────────────────────────────────────────────────

    public static String randomSpareName()         { return "Spare_"    + fourDigit(); }
    public static String randomSpareCode()         { return "SPR"       + fourDigit(); }

    // ── Reasons / Reason Groups ───────────────────────────────────────────────

    public static String randomReasonName()        { return "Reason_"   + fourDigit(); }
    public static String randomReasonGroupName()   { return "RsnGrp_"  + fourDigit(); }

    // ── Users / Operators ─────────────────────────────────────────────────────

    public static String randomUserName()          { return "User_"     + fourDigit(); }
    public static String randomOperatorName()      { return "Oper_"     + fourDigit(); }
    public static String randomOperatorCode()      { return "OPR"       + fourDigit(); }
    public static String randomEmpCode()           { return "EMP"       + fourDigit(); }
    public static String randomEmail()             { return "user"      + fourDigit() + "@example.com"; }
    public static String randomPhone()             { return "9"         + nineDigit(); }
    public static String randomPinCode()           { return String.valueOf(100000 + RNG.nextInt(899999)); }
    public static String randomAddress()           { return "BTM Layout " + (1 + RNG.nextInt(9)) + " Stage"; }

    public static String randomBloodGroup() {
        String[] groups = {"A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        return groups[RNG.nextInt(groups.length)];
    }

    public static String randomRole() {
        String[] roles = {"Admin", "Manager", "Super Admin", "Supervisor"};
        return roles[RNG.nextInt(roles.length)];
    }

    // ── Stock ─────────────────────────────────────────────────────────────────

    /** Returns a value like "12345.67" — valid for Current Stock field (max 6 digits, 2 decimals). */
    public static String randomStockValue() {
        int wholePart    = RNG.nextInt(999999);          // 0 – 999999
        int decimalPart  = RNG.nextInt(100);             // 00 – 99
        return String.format("%d.%02d", wholePart, decimalPart);
    }

    // ── Descriptions ─────────────────────────────────────────────────────────

    public static String randomDescription() { return "Desc_" + fourDigit(); }

    // ── Internals ─────────────────────────────────────────────────────────────

    private static String fourDigit()  { return String.format("%04d", RNG.nextInt(9999)); }
    private static String nineDigit()  { return String.format("%09d", RNG.nextInt(999999999)); }
}
