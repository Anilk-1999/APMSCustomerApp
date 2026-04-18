Feature: Manage Product Subscription in Scrap Item

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Product" section is displayed
    When User clicks on "Scraps" feature
    Then verify user navigates to "Scraps" list screen
    And User has already created a Scrap Item

  # =========================================================
  # 🔍 SEARCH + LONG PRESS + ACTION MENU FLOW
  # =========================================================

  Scenario: Open Product Subscription popup via Action Menu
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Scrap Name
    And User waits for search results to load
    Then system should display matching Scrap results
    And User verifies Scrap appears in list
    When User performs long press on Scrap record
    Then Action Menu bottom sheet should be displayed
    And Product Subscription option should be visible
    When User clicks on Product Subscription option
    Then Product Subscription popup should be displayed
    And "Add Products" label with "+" button should be visible
    And Product list section should be visible
    And Submit button should be visible
    And Close option should be available

  # =========================================================
  # 🧪 POSITIVE SCENARIOS
  # =========================================================

  Scenario: Add multiple products to Scrap
    When User opens Product Subscription popup
    And User clicks on "+" button under Add Products
    Then "Select Products" bottom sheet should be displayed
    When User selects multiple products
    And User clicks Submit button in bottom sheet
    Then selected products should be added to Product Subscription list
    When User clicks Submit button in Product Subscription popup
    Then Product Subscription should be saved successfully
    And updated products should be reflected in Scrap configuration

  Scenario: Add single product
    When User opens Product Subscription popup
    And User clicks on "+" button
    And User selects one product
    And User submits selection
    And User clicks Submit button
    Then product should be added successfully

  Scenario: Submit without selecting products (empty mapping allowed)
    When User opens Product Subscription popup
    And User clicks Submit button without selecting any product
    Then system should allow submission successfully

  # =========================================================
  # 🔁 DELETE FLOW
  # =========================================================

  Scenario: Delete product from subscription list
    When User opens Product Subscription popup
    And products are already added
    And User clicks delete button on a product
    Then product should be removed from list
    When User clicks Submit button
    Then updated product list should be saved successfully

  Scenario: Delete all products
    When User opens Product Subscription popup
    And User removes all products from list
    And User clicks Submit button
    Then system should allow empty product list

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Duplicate product selection
    When User opens Select Products bottom sheet
    And User selects already added product again
    Then system should prevent duplicate entry

  Scenario: Invalid product selection
    When User opens Select Products bottom sheet
    And product is inactive or unavailable
    Then system should restrict selection

  Scenario: Submit without changes
    When User opens Product Subscription popup
    And User clicks Submit without modification
    Then system should show "No changes detected" or allow safe submission

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid long press action
    When User performs long press multiple times quickly on Scrap record
    Then only one Action Menu should appear

  Scenario: Rapid multiple submit clicks
    When User clicks Submit button multiple times quickly
    Then system should prevent duplicate requests

  Scenario: Large product list handling
    When Select Products bottom sheet contains many records
    Then list should support scrolling and multi-selection
    And search functionality should work if available

  Scenario: Network failure during submission
    When User clicks Submit without internet connection
    Then system should display error message

  Scenario: Session timeout during operation
    When session expires during Product Subscription operation
    Then User should be redirected to login screen

  Scenario: Close popup without saving changes
    When User opens Product Subscription popup
    And User modifies product selection
    And User closes popup using back or outside click
    Then changes should not be saved

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify Product Subscription popup UI
    When User opens Product Subscription popup
    Then Add Products label should be visible
    And "+" button should be visible
    And Product list section should be visible
    And Submit button should be visible

  Scenario: Verify Select Products bottom sheet UI
    When User opens Select Products bottom sheet
    Then product list should be displayed
    And each product should have selectable checkbox
    And multi-selection should be enabled
    And Submit button should be visible

  Scenario: Verify delete button visibility
    When products are added
    Then each product should have delete option

  Scenario: Verify popup close behavior
    When User closes popup
    Then popup should be dismissed successfully
