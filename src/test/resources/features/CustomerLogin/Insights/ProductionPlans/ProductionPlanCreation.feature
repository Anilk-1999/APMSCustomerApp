Feature: Create Production Plan (Jobsheet) with Product, Template and Raw Material Management

  Background:
    When User clicks on profile icon
    Then verify that the "Account Preferences" screen is displayed
    When User clicks on "Insights" section
    And User clicks on "Plan"
    Then verify user navigates to "Production Plan" screen

  # =========================================================
  # 📱 UI VALIDATION - PLAN SECTIONS
  # =========================================================

  Scenario: Verify Production Plan sections visibility
    Then "New" section should be visible
    And "In Progress" section should be visible
    And "On Hold" section should be visible
    And "Template" section should be visible
    And "Completed" section should be visible
    And "Canceled" section should be visible

  # =========================================================
  # 🧪 POSITIVE SCENARIOS
  # =========================================================

  Scenario: Create Production Plan with mandatory fields only
    When User clicks on "+ Add" button
    And User selects Machine Name
    And User selects valid Schedule Start Date and Time
    And User selects valid Schedule End Date and Time
    And Schedule Start Date is less than End Date
    And User clicks "+" in Add Products
    Then Product selection bottom sheet should be displayed
    When User selects at least one product
    And User clicks Submit in product popup
    Then selected product should be added

    And User enters Target Quantity
    And User enters Serial Number
    And User clicks Submit button
    Then Production Plan should be created successfully
    And Plan should be visible in "New" section

  Scenario: Create Production Plan with all fields
    When User fills all mandatory fields
    And User enters Instruction
    And User adds product with all product-level fields
    And User enables Setup Approval toggle
    And User enables Production Approval toggle
    And User adds Raw Materials
    And User clicks Submit button
    Then Production Plan should be created successfully

  Scenario: Create Production Plan with template enabled
    When User enables "Save as Template" toggle
    And User enters Template Name
    And User enters Template Description
    And User fills all mandatory fields
    And User clicks Submit button
    Then Plan should be saved under "Template" section

  Scenario: Create Production Plan with multiple products
    When User adds multiple products
    And User enters required details for each product
    And User clicks Submit button
    Then Production Plan should be created successfully

  Scenario: Create Production Plan with new product creation
    When User opens Add Product popup
    And User clicks "+" to create new product
    And User enters mandatory product details
    And User clicks Submit
    Then new product should be created
    And product should be auto-selected

  Scenario: Create Production Plan with raw materials
    When User adds raw materials at product level
    And User selects multiple materials
    And User clicks Submit
    Then materials should be added successfully

  Scenario: Create Production Plan without optional fields
    When User skips Instruction
    And skips Template Description
    And skips Product Code
    And skips Product Group
    And User submits plan
    Then Production Plan should be created successfully

  # =========================================================
  # ❌ NEGATIVE SCENARIOS
  # =========================================================

  Scenario: Create without Machine Name
    When User does not select Machine Name
    And clicks Submit
    Then "Machine Name is required" should be displayed

  Scenario: Create without Schedule Start Date
    When User does not select Start Date
    Then validation error should be displayed

  Scenario: Create without Schedule End Date
    When User does not select End Date
    Then validation error should be displayed

  Scenario: Start Date greater than End Date
    When User selects Start Date greater than End Date
    Then system should display date validation error

  Scenario: Create without Product selection
    When User does not add any product
    And clicks Submit
    Then validation error should be displayed

  Scenario: Create without Target Quantity
    When User skips Target Quantity
    Then validation error should be displayed

  Scenario: Create without Serial Number
    When User skips Serial Number
    Then "Serial Number is required" should be displayed

  Scenario: Template enabled without Template Name
    When User enables template toggle
    And leaves Template Name empty
    Then validation error should be displayed

  Scenario: Product creation without mandatory fields
    When User tries to create product without Product Name
    Then validation error should be displayed

  Scenario: Product without UOM
    When User skips Product UOM
    Then validation error should be displayed

  Scenario: Product without Conversion Value
    When User skips Conversion Value
    Then validation error should be displayed

  Scenario: Product without Conversion UOM
    When User skips Conversion UOM
    Then validation error should be displayed

  Scenario: Duplicate product inside plan
    When User adds same product multiple times
    Then system should handle duplicate validation

  Scenario: Invalid input in fields
    When User enters invalid characters
    Then validation errors should be displayed

  Scenario: Partial product data entry
    When User enters incomplete product details
    Then system should restrict submission

  Scenario: Submit without internet
    When User clicks Submit without network
    Then error message should be displayed

  Scenario: Submit without entering any data
    When User opens Add Production Plan screen
    And clicks Submit without entering data
    Then all mandatory validations should trigger

  # =========================================================
  # ⚠️ EDGE CASE SCENARIOS
  # =========================================================

  Scenario: Rapid submit clicks
    When User clicks Submit button multiple times
    Then system should prevent duplicate plan creation

  Scenario: Large product list handling
    When product list is large
    Then bottom sheet should support scrolling and search

  Scenario: Close product popup without selection
    When User closes product popup without selecting
    Then no product should be added

  Scenario: Close raw material popup without selection
    When User closes raw material popup
    Then no material should be added

  Scenario: Raw material selected but not submitted
    When User selects materials but does not click Submit
    Then materials should not be added

  Scenario: Session timeout during creation
    When session expires during plan creation
    Then User should be redirected to login screen

  # =========================================================
  # 📱 UI VALIDATION SCENARIOS
  # =========================================================

  Scenario: Verify Add Production Plan UI
    Then all fields should be visible and aligned properly
    And "+ Add Product" button should be visible
    And Submit button should be enabled based on validation

  Scenario: Verify product bottom sheet UI
    When User opens product selection
    Then product list should be displayed
    And multi-select should be enabled

  Scenario: Verify raw material bottom sheet UI
    When User opens raw material selection
    Then material list should be displayed
    And multi-select should be enabled

 