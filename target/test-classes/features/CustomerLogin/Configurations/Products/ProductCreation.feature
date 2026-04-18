Feature: Create Product via Configuration Module

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Configurations" section
    Then verify the "Product" section is displayed
    When User clicks on "Products" feature
    Then verify user navigates to "Products" list screen

  # =========================================================
  # 🔁 NAVIGATION FLOW
  # =========================================================

  Scenario: Navigate to Product list screen
    Then Product list should be displayed
    And Add "+" button should be visible

  # =========================================================
  # ➕ OPEN ADD PRODUCT POPUP
  # =========================================================

  Scenario: Open Add Product popup
    When User clicks on "+" Add button in Product list screen
    Then "Add New Product" popup should be displayed
    And Product Name field should be visible
    And Product Code field should be visible
    And Product Group dropdown should be visible
    And Description field should be visible
    And Product UOM dropdown should be visible
    And Conversion Value field should be visible
    And Conversion UOM dropdown should be visible
    And Raw Materials section with "+" button should be visible
    And Machine Subscription section with "+" button should be visible
    And Upload PDF option should be visible
    And Save button should be visible
    And Close "X" button should be visible in popup header

  # =========================================================
  # 🧪 POSITIVE SCENARIOS
  # =========================================================

  Scenario: Create Product with all fields
    When User clicks on "+" Add button in Product list screen
    And User enters valid Product Name
    And User enters valid Product Code
    And User selects Product Group from dropdown
    And User enters Description
    And User selects Product UOM
    And User enters valid Conversion Value
    And User selects Conversion UOM
    And User clicks Raw Materials "+" button
    Then Raw Material List bottom sheet should be displayed
    When User selects multiple raw materials
    And User clicks Submit button in raw material bottom sheet
    Then selected raw materials should be added to product
    When User clicks Machine Subscription "+" button
    Then Machine List bottom sheet should be displayed
    When User selects multiple machines
    And User clicks Submit button in machine selection bottom sheet
    Then selected machines should be added to product
    When User uploads valid PDF file
    And User clicks Save button
    Then Product should be created successfully
    And newly created Product should be visible in Product list screen

  Scenario: Create Product with mandatory fields only
    When User clicks on "+" Add button in Product list screen
    And User enters valid Product Name
    And User selects Product UOM
    And User enters valid Conversion Value
    And User selects Conversion UOM
    And User clicks Save button
    Then Product should be created successfully

  Scenario: Create Product without optional fields
    When User clicks on "+" Add button in Product list screen
    And User fills only mandatory Product fields
    And User clicks Save button
    Then Product should be created successfully

  Scenario: Create Product with Product Group and no file upload
    When User clicks on "+" Add button in Product list screen
    And User enters valid Product Name
    And User enters valid Product Code
    And User selects Product Group from dropdown
    And User selects Product UOM
    And User enters valid Conversion Value
    And User selects Conversion UOM
    And User clicks Save button
    Then Product should be created successfully

  Scenario: Create Product with trimmed Product Name
    When User clicks on "+" Add button in Product list screen
    And User enters Product Name with leading and trailing spaces
    And User selects Product UOM
    And User enters valid Conversion Value
    And User selects Conversion UOM
    And User clicks Save button
    Then system should trim spaces and create Product successfully

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Create Product without Product Name
    When User clicks on "+" Add button in Product list screen
    And User leaves Product Name empty
    And User clicks Save button
    Then "Product Name is required" should be displayed

  Scenario: Create Product without Product UOM
    When User clicks on "+" Add button in Product list screen
    And User enters valid Product Name
    And User does not select Product UOM
    And User clicks Save button
    Then "Product UOM is required" should be displayed

  Scenario: Create Product without Conversion Value
    When User clicks on "+" Add button in Product list screen
    And User enters valid Product Name
    And User selects Product UOM
    And User leaves Conversion Value empty
    And User clicks Save button
    Then "Conversion Value is required" should be displayed

  Scenario: Create Product without Conversion UOM
    When User clicks on "+" Add" button in Product list screen
    And User enters valid Product Name
    And User selects Product UOM
    And User enters valid Conversion Value
    And User does not select Conversion UOM
    And User clicks Save button
    Then "Conversion UOM is required" should be displayed

  Scenario: Create Product with invalid Conversion Value
    When User clicks on "+" Add button in Product list screen
    And User enters valid Product Name
    And User selects Product UOM
    And User enters non-numeric or negative Conversion Value
    Then validation error should be displayed

  Scenario: Create Product with duplicate Product Code
    When User clicks on "+" Add button in Product list screen
    And User enters valid Product Name
    And User enters existing Product Code
    And User selects Product UOM
    And User enters valid Conversion Value
    And User selects Conversion UOM
    And User clicks Save button
    Then duplicate validation error should be displayed for Product Code

  Scenario: Create Product with only spaces in Product Name
    When User clicks on "+" Add button in Product list screen
    And User enters only spaces in Product Name field
    And User clicks Save button
    Then "Product Name is required" should be displayed

  Scenario: Create Product with invalid file upload
    When User clicks on "+" Add button in Product list screen
    And User fills mandatory Product fields
    And User uploads non-PDF file
    Then system should show "Invalid file format" error

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid multiple clicks on Save button
    When User creates Product with valid data
    And User clicks Save button multiple times quickly
    Then system should prevent duplicate Product creation

  Scenario: Large file upload handling
    When User uploads large PDF file
    Then system should validate file size limit

  Scenario: Large dataset in Raw Material bottom sheet
    When Raw Material bottom sheet contains large number of items
    Then list should support scrolling and search

  Scenario: Large dataset in Machine selection bottom sheet
    When Machine selection bottom sheet contains large number of items
    Then list should support scrolling and search

  Scenario: Network failure during creation
    When User clicks Save button without internet connection
    Then system should display network error message

  Scenario: Session timeout during creation
    When session expires while creating Product
    Then User should be redirected to login screen

  Scenario: Close popup without saving
    When User opens Add New Product popup
    And User clicks on Close "X" button
    Then popup should be closed without saving Product data

  Scenario: Reopen popup after close
    When User closes Add New Product popup
    And User reopens Add New Product popup
    Then all fields should be reset

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify Add Product popup UI
    When User clicks on "+" Add button in Product list screen
    Then all Product fields should be properly aligned
    And labels should be clearly visible
    And Save button should be enabled only when mandatory fields are filled

  Scenario: Verify bottom sheet UI for Raw Materials
    When User opens Raw Materials selection
    Then Raw Material list should display with multi-select options

  Scenario: Verify bottom sheet UI for Machine Subscription
    When User opens Machine selection
    Then Machine list should display with multi-select options

  Scenario: Verify Close button functionality
    When User clicks on Close "X" button
    Then popup should be closed
    And User should return to Product list screen

  Scenario: Verify selected raw materials display
    When User selects raw materials and submits selection
    Then selected raw materials should be displayed in Product popup

  Scenario: Verify selected machines display
    When User selects machines and submits selection
    Then selected machines should be displayed in Product popup

  # =========================================================
  # 🔍 FIELD VALIDATION SCENARIOS
  # =========================================================

  Scenario Outline: Validate Product Name field
    When User clicks on "+" Add button in Product list screen
    And User selects Product UOM
    And User enters valid Conversion Value
    And User selects Conversion UOM
    And User clicks Save button
    Then "<error>" should be displayed

    Examples:
      | input | error                         |
      |       | Product Name is required      |
      

  Scenario Outline: Validate Conversion Value field
    When User clicks on "+" Add button in Product list screen
    And User enters valid Product Name
    And User selects Product UOM
    And User enters "<input>" in Conversion Value field
    And User clicks Save button
    Then "<error>" should be displayed

    Examples:
      | input | error                               |
      |       | Conversion Value is required        |
      | -1    | Invalid Conversion Value            |
      | abc   | Only numeric value is allowed       |
      | 0     | Conversion Value must be greater than 0 |

  Scenario Outline: Validate Product Code field
    When User clicks on "+" Add button in Product list screen
    And User enters valid Product Name
    And User selects Product UOM
    And User enters valid Conversion Value
    And User selects Conversion UOM
    And User clicks Save button
    Then "<error>" should be displayed

    Examples:
      | input | error                          |

  Scenario: Validate Description field
    When User clicks on "+" Add button in Product list screen
    And User enters long Description text
    Then system should allow optional input without error
