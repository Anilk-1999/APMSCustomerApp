Feature: View Product Details from Product List

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Product" section is displayed
    When User clicks on "Products" feature
    Then verify user navigates to "Products" list screen
    And User has already created a Product

  # =========================================================
  # 🔍 SEARCH + VIEW FLOW
  # =========================================================

  Scenario: Search and open Product View popup
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Name
    And User waits for search results to load
    Then system should display matching product results
    And User verifies product appears in list
    When User clicks on the product record
    Then "View Product" popup should be displayed

    # ✅ Mandatory fields (always present)
    And Product ID should be visible
    And Product Name should be visible
    And Product UOM should be visible
    And Conversion Value should be visible
    And Conversion UOM should be visible

    # ⚠️ Optional fields (conditional validation)
    And Product Code should be displayed if exists
    And Product Group should be displayed if exists
    And Description should be displayed if exists
    And Raw Materials should be displayed if added
    And Machine Subscription should be displayed if added
    And Uploaded PDF should be displayed if available

    And Close "X" button should be visible
    And all fields should be displayed in read-only mode

  # =========================================================
  # 👁️ POSITIVE SCENARIOS
  # =========================================================

  Scenario: Verify Product view data accuracy
    When User opens Product View popup for newly created product
    Then system should display all product details correctly
    And mandatory fields should always have values
    And optional fields should be displayed only when data exists

  Scenario: Verify popup close functionality
    When User opens Product View popup
    And User clicks on "X" button
    Then popup should be closed
    And User should return to Products list screen

  Scenario: Verify Raw Materials display
    When Product has raw materials mapped
    And User opens Product View popup
    Then all mapped raw materials should be displayed correctly

  Scenario: Verify Machine Subscription display
    When Product has machine subscriptions mapped
    And User opens Product View popup
    Then all mapped machines should be displayed correctly

  Scenario: Verify uploaded PDF display
    When Product has uploaded PDF
    And User opens Product View popup
    Then uploaded PDF should be visible and accessible

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Verify fields are not editable
    When User opens Product View popup
    And User tries to edit Product Name
    Then Product Name field should not be editable

  Scenario: Verify no action buttons in view popup
    When User opens Product View popup
    Then Save button should not be visible
    And Edit option should not be visible

  Scenario: View deleted Product
    When Product is deleted from backend
    And User searches same product
    Then system should display "Product not found"

  Scenario: Unauthorized access to Product view
    When User without permission tries to view Product
    Then system should show "Access Denied"

  Scenario: Attempt modification via keyboard
    When User opens Product View popup
    And User tries to modify field values using keyboard input
    Then field values should remain unchanged

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Large data handling in view popup
    When Product contains large description or large lists
    And User opens Product View popup
    Then User should be able to scroll popup content properly

  Scenario: Rapid click on product record
    When User clicks multiple times quickly on product record
    Then only one View Product popup should open

  Scenario: Network failure during view
    When User clicks product record without internet connection
    Then system should display network error message

  Scenario: Session timeout during view
    When session expires while opening Product View popup
    Then User should be redirected to login screen

  Scenario: Reopen view popup multiple times
    When User opens and closes Product View popup multiple times
    Then Product details should load correctly each time

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify View Product popup UI
    When User opens Product View popup
    Then all fields should be properly aligned
    And labels should be clearly visible
    And values should be readable
    And "X" button should be positioned correctly

  Scenario: Verify read-only UI behavior
    When User opens Product View popup
    Then all fields should appear disabled or read-only
    And no editable input cursor should be visible

  Scenario: Verify scroll functionality
    When User opens Product View popup
    Then User should be able to scroll entire popup content

  Scenario: Verify UI consistency with Edit popup
    When User opens Product View popup
    Then View popup layout should match Edit Product popup structure
    And all fields should be disabled

  Scenario: Verify popup overlay behavior
    When User opens Product View popup
    Then background screen should not be interactive until popup is closed

  # =========================================================
  # 🔁 SEARCH VARIATION COVERAGE
  # =========================================================

  Scenario Outline: Validate product search behavior
    When User enters "<searchInput>" in search field
    Then system should return "<expectedResult>"

    Examples:
      | searchInput        | expectedResult   |
      | exact name         | product found    |
      | partial name       | relevant results |
      | uppercase          | product found    |
      | invalid name       | no results       |
      | special chars      | safe handling    |

  # =========================================================
  # 🔍 READ-ONLY FIELD VALIDATION
  # =========================================================

  Scenario Outline: Validate read-only behavior of Product fields
    When User opens Product View popup
    Then "<fieldName>" should be displayed in read-only mode

    Examples:
      | fieldName         |
      | Product Name      |
      | Product Code      |
      | Product UOM       |
      | Conversion Value  |
      | Conversion UOM    |
      | Description       |