Feature: Update Product via Swipe Action from Product List

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Product" section is displayed
    When User clicks on "Products" feature
    Then verify user navigates to "Products" list screen
    And User has already created a Product

  # =========================================================
  # 🔍 SEARCH + SWIPE + EDIT FLOW
  # =========================================================

  Scenario: Search and open Edit Product popup
    When User clicks on search icon
    And User taps on search input field
    And User clears existing text in search field
    And User enters newly created Product Name
    And User waits for search results to load
    Then system should display matching product results
    And User verifies product appears in list
    When User swipes product record from right to left
    Then Edit option should be visible
    When User clicks on Edit button
    Then "Edit Product" popup should be displayed
    And Product Name field should be pre-filled
    And Product UOM dropdown should be pre-filled
    And Conversion Value field should be pre-filled
    And Conversion UOM dropdown should be pre-filled
    And optional fields should be displayed if data exists
    And Raw Materials section should display items only if previously added
    And Machine Subscription section should display items only if previously added
    And uploaded PDF should be visible only if previously uploaded
    And Save button should be visible
    And Close "X" button should be visible

  # =========================================================
  # 🧪 POSITIVE SCENARIOS
  # =========================================================

  Scenario: Update Product with valid mandatory fields
    When User searches for newly created Product Name
    And User swipes product record from right to left
    And User clicks on Edit button
    And User updates Product Name
    And User updates Product UOM
    And User updates Conversion Value
    And User updates Conversion UOM
    And User clicks Save button
    Then Product should be updated successfully
    And updated Product data should be reflected in Products list screen

  Scenario: Update Product including optional fields
    When User searches for newly created Product Name
    And User swipes product record from right to left
    And User clicks on Edit button
    And User updates Product Code
    And User updates Product Group
    And User updates Description
    And User adds raw materials if required
    And User adds machine subscriptions if required
    And User uploads valid PDF file if required
    And User clicks Save button
    Then Product should be updated successfully

  Scenario: Partial update of Product
    When User searches for newly created Product Name
    And User swipes product record from right to left
    And User clicks on Edit button
    And User updates only Description
    And User clicks Save button
    Then Product should be updated successfully

  Scenario: Update Product with mandatory fields only and clear optional data
    When User searches for newly created Product Name
    And User swipes product record from right to left
    And User clicks on Edit button
    And User keeps mandatory fields valid
    And User clears optional fields where allowed
    And User clicks Save button
    Then Product should be updated successfully

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Update Product without Product Name
    When User searches for newly created Product Name
    And User swipes product record from right to left
    And User clicks on Edit button
    And User clears Product Name
    And User clicks Save button
    Then "Product Name is required" should be displayed

  Scenario: Update Product without Product UOM
    When User searches for newly created Product Name
    And User swipes product record from right to left
    And User clicks on Edit button
    And User removes Product UOM
    And User clicks Save button
    Then "Product UOM is required" should be displayed

  Scenario: Update Product without Conversion Value
    When User searches for newly created Product Name
    And User swipes product record from right to left
    And User clicks on Edit button
    And User clears Conversion Value
    And User clicks Save button
    Then "Conversion Value is required" should be displayed

  Scenario: Update Product without Conversion UOM
    When User searches for newly created Product Name
    And User swipes product record from right to left
    And User clicks on Edit button
    And User removes Conversion UOM
    And User clicks Save button
    Then "Conversion UOM is required" should be displayed

  Scenario: Update Product with invalid Conversion Value
    When User searches for newly created Product Name
    And User swipes product record from right to left
    And User clicks on Edit button
    And User enters invalid Conversion Value
    And User clicks Save button
    Then validation error should be displayed

  Scenario: Update Product with duplicate Product Code
    When User searches for newly created Product Name
    And User swipes product record from right to left
    And User clicks on Edit button
    And User enters existing Product Code
    And User clicks Save button
    Then duplicate validation error should be displayed for Product Code

  Scenario: Submit without changes
    When User searches for newly created Product Name
    And User swipes product record from right to left
    And User clicks on Edit button
    And User clicks Save button without modification
    Then system should show "No changes detected"

  # =========================================================
  # 🔁 DELETE FLOW INSIDE EDIT POPUP
  # =========================================================

  Scenario: Delete Raw Material if exists
    When User searches for newly created Product Name
    And User swipes product record from right to left
    And User clicks on Edit button
    And Raw Materials are already added
    And User removes a Raw Material
    Then Raw Material should be removed successfully

  Scenario: Delete Machine Subscription if exists
    When User searches for newly created Product Name
    And User swipes product record from right to left
    And User clicks on Edit button
    And Machines are already added
    And User removes a Machine
    Then Machine Subscription should be removed successfully

  Scenario: Delete uploaded PDF if exists
    When User searches for newly created Product Name
    And User swipes product record from right to left
    And User clicks on Edit button
    And PDF is already uploaded
    And User removes the PDF
    Then PDF should be removed successfully

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid Save clicks
    When User searches for newly created Product Name
    And User swipes product record from right to left
    And User clicks on Edit button
    And User clicks Save button multiple times quickly
    Then system should prevent duplicate updates

  Scenario: Network failure during update
    When User searches for newly created Product Name
    And User swipes product record from right to left
    And User clicks on Edit button
    And User clicks Save button without internet connection
    Then system should display network error message

  Scenario: Session timeout during update
    When User searches for newly created Product Name
    And User.swipes product record from right to left
    And User clicks on Edit button
    And session expires during editing
    Then User should be redirected to login screen

  Scenario: Close popup without saving updated changes
    When User searches for newly created Product Name
    And User swipes product record from right to left
    And User clicks on Edit button
    And User modifies Product details
    And User clicks on Close "X" button
    Then popup should be closed without saving updated changes

  Scenario: Reopen popup after close without save
    When User searches for newly created Product Name
    And User swipes product record from right to left
    And User clicks on Edit button
    And User updates Product Name
    And User clicks on Close "X" button
    And User reopens Edit Product popup
    Then previously saved Product data should be displayed

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify Edit Product popup UI
    When User searches for newly created Product Name
    And User swipes product record from right to left
    And User clicks on Edit button
    Then mandatory fields should be clearly visible
    And optional fields should be displayed properly
    And Save button should be visible
    And Close "X" button should be visible

  Scenario: Verify conditional display of optional fields
    When User searches for newly created Product Name
    And User swipes product record from right to left
    And User clicks on Edit button
    Then optional fields should appear only if data exists

  Scenario: Verify selected raw materials display
    When User searches for newly created Product Name
    And User swipes product record from right to left
    And User clicks on Edit button
    Then selected raw materials should be displayed if previously added

  Scenario: Verify selected machine subscriptions display
    When User searches for newly created Product Name
    And User swipes product record from right to left
    And User clicks on Edit button
    Then selected machine subscriptions should be displayed if previously added

  Scenario: Verify uploaded PDF display
    When User searches for newly created Product Name
    And User swipes product record from right to left
    And User clicks on Edit button
    Then uploaded PDF should be displayed if previously uploaded

  


  