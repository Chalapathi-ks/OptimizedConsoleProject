# Comprehensive Wait Analysis Report
## MerchandizingTestcases Suite

**Generated:** February 20, 2025  
**Project:** /Users/unbxd/Downloads/ComparedSelfServeAutomationTest

---

## 1. Test Classes Used by MerchandizingTestcases.xml

The suite uses the following test classes:

| Test Class | Package |
|------------|---------|
| ABTest | UnbxdTests.testNG.consoleui.ABTest |
| FilterTest | UnbxdTests.testNG.consoleui.MerchConditionTest |
| PromotionStatusTest | UnbxdTests.testNG.consoleui.MerchConditionTest |
| SlotTest | UnbxdTests.testNG.consoleui.MerchConditionTest |
| SearchBannerTest | UnbxdTests.testNG.consoleui.MerchTest |
| SearchCEDBannerTest | UnbxdTests.testNG.consoleui.MerchTest |
| SearchCEDRedirectTest | UnbxdTests.testNG.consoleui.MerchTest |
| SearchRedirectTest | UnbxdTests.testNG.consoleui.MerchTest |
| PinningTest | UnbxdTests.testNG.consoleui.MerchConditionTest |
| PinTheProductFromConsoleTest | UnbxdTests.testNG.consoleui.MerchConditionTest |
| PromotedSuggestionsTest | UnbxdTests.testNG.consoleui.Autosuggest |
| BlacklistedSuggestionsTest | UnbxdTests.testNG.consoleui.Autosuggest |
| SegmentTest | UnbxdTests.testNG.consoleui.MerchTest |
| SortTest | UnbxdTests.testNG.consoleui.MerchConditionTest |
| SimilarQueryTest | UnbxdTests.testNG.consoleui.MerchConditionTest |
| FieldRuleFacetTest | UnbxdTests.testNG.consoleui.MerchTest |
| BoostTest | UnbxdTests.testNG.consoleui.MerchConditionTest |
| BrowseSortTest | UnbxdTests.testNG.consoleui.MerchConditionTest |
| BrowseSlotTest | UnbxdTests.testNG.consoleui.MerchConditionTest |
| BrowseFilterTest | UnbxdTests.testNG.consoleui.MerchConditionTest |
| BrowseBoostTest | UnbxdTests.testNG.consoleui.MerchConditionTest |
| BrowsePinningTest | UnbxdTests.testNG.consoleui.MerchConditionTest |
| BrowseBannerTest | UnbxdTests.testNG.consoleui.MerchTest |
| BrowseCEDBannerTest | UnbxdTests.testNG.consoleui.MerchTest |
| searchFreshness | UnbxdTests.testNG.consoleui.freshnessTest |
| browseFreshness | UnbxdTests.testNG.consoleui.freshnessTest |

**Base class for most merch tests:** `MerchandisingTest` (extends BaseTest)

---

## 2. Timeout Constant Values (UnbxdConstants.java)

| Constant | Value | Unit |
|----------|-------|------|
| SELENIUM_MAXTIMEOUT | 30000 | ms |
| SELENIUM_MINTIMEOUT | 3000 | ms |
| SELENIUM_PAGE_MAXTIMEOUT | 6000 | ms |
| SELENIUM_PAGE_MINTIMEOUT | 3000 | ms |
| WAIT_LOADER_SECONDS | 60 | seconds |
| WAIT_ELEMENT_APPEAR_SECONDS | 45 | seconds |
| WAIT_ELEMENT_LOAD_SECONDS | 30 | seconds |
| WAIT_ELEMENT_DISAPPEAR_SECONDS | 30 | seconds |

---

## 3. UiBase.java – Wait Method Definitions

| Method | Duration/Timeout | Notes |
|--------|------------------|-------|
| **ThreadWait()** (static) | 1000 ms | `Thread.sleep(1000)` |
| **threadWait()** (instance) | 1500 ms | `Thread.sleep(1500)` |
| **threadWaitForBackendVerification()** | 5000 ms | `Thread.sleep(5000)` |
| **awaitForPageToLoad()** | 10 seconds | `await().atMost(10)` – FluentAwait interprets values ≤1000 as seconds, >1000 as ms |
| **awaitForPageToLoadQuick()** | 5 seconds | `await().atMost(5)` |
| **awaitForElementPresence()** | 30 seconds | Uses `getMaxTimeout()` = 30000ms; FluentAwait divides by 1000 → 30s |
| **awaitForElementNotDisplayed()** | 30 seconds | Same as above |
| **awaitTillElementHasText()** | 30 seconds | Same as above |
| **waitForLoaderToDisAppear(locator, name)** | 60 seconds | `WAIT_LOADER_SECONDS` |
| **waitForLoaderToDisAppear(locator, name, retries, interval)** | `interval` seconds | Uses passed `interval` param |
| **waitForElementAppear(locator, name, retries, interval)** | `interval` seconds per retry | WebDriverWait with `interval` |
| **waitForElementToAppear(locator, name)** | 45 seconds | `WAIT_ELEMENT_APPEAR_SECONDS` |
| **waitForElementToBeClickable(locator, name)** | 15 seconds | Hardcoded |
| **waitForElementToBeClickable(locator, name, retries, interval)** | `interval` seconds | Uses passed param |
| **waitForElementToLoad(locator, eleName)** | 30 seconds | `WAIT_ELEMENT_LOAD_SECONDS` |
| **waitForElementToDisappear(element)** | 30 seconds | `WAIT_ELEMENT_DISAPPEAR_SECONDS` |
| **getMaxTimeout()** | 30000 | Returns SELENIUM_MAXTIMEOUT |
| **getMinTimeout()** | 3000 | Returns SELENIUM_MINTIMEOUT |

**Note on FluentAwait.atMost():** Values >1000 are treated as milliseconds and divided by 1000 for seconds. Values ≤1000 are treated as seconds directly.

---

## 4. Per-File Wait Catalog

### 4.1 MerchandisingTest.java

| Line | Type | Duration | Context |
|------|------|----------|---------|
| 46 | threadWait() | 1500 ms | setUp() after goTo |
| 57-58 | awaitForPageToLoad(), threadWait() | 10s + 1500ms | createPromotion() |
| 60-61 | threadWait() | 1500 ms | createPromotion – before delete |
| 65-66 | threadWait() | 1500 ms | createPromotion – before goToQueryBasedBanner |
| 71-72 | threadWait() | 1500 ms | createPromotion – before clickOnAddRule |
| 90-91 | threadWait() | 1500 ms | createPromotion – before fillQueryRuleData |
| 100-101 | awaitForPageToLoad(), threadWait() | 10s + 1500ms | createBrowsePromotion() |
| 137-141 | awaitForPageToLoad(), threadWait(), awaitForPageToLoad() | 10s + 1500ms + 10s | createGlobalRulePromotion() |
| 144 | threadWait() | 1500 ms | fillMerchandisingData() |
| 177 | threadWait() | 1500 ms | fillMerchandisingData – per group |
| 213, 246 | threadWait() | 1500 ms | fillPinSortMerchandisingData, fillupdatedPinSortMerchandisingData |
| 254, 341, 356 | threadWait() | 1500 ms | verifyMerchandisingGenericData, verifyMerchandisingData |
| 394 | UiBase.ThreadWait() | 1000 ms | verifyMerchandisingData – sort element fallback |
| 341 | threadWait() | 1500 ms | verifyMerchandisingData – pinned product verification |

**Total:** 1 ThreadWait (1s), ~20 threadWait (1.5s each), 5 awaitForPageToLoad (10s each)

---

### 4.2 MerchandisingActions.java

| Line | Type | Duration | Context |
|------|------|----------|---------|
| 24 | ThreadWait() | 1000 ms | publishCampaign() |
| 26 | awaitForElementPresence() | 30s | publishCampaign – publishButton |
| 27 | waitForLoaderToDisAppear() | 60s | publishCampaign – successMsgPopUp (.mask-loader) |
| 28 | ThreadWait() | 1000 ms | publishCampaign() |
| 29 | await() | **NO-OP** | publishCampaign – does nothing when standalone |
| 33-45 | ThreadWait(), await(), waitForLoaderToDisAppear(), ThreadWait() | 1s + 60s + 1s | publishGlobalRule() |
| 66, 78-79, 86 | ThreadWait() | 1000 ms | fillRowValues() |
| 104, 106 | threadWait() | 1500 ms | selectSimilarQueryData() |
| 112-117 | ThreadWait(), threadWait() | 1s + 1.5s | selectAISuggestedSimilarQueryData() |
| 125 | ThreadWait() | 1000 ms | listinPageAddMoreQueriesEditIcon() |
| 154 | threadWait() | 1500 ms | upcomingDateSelection() |
| 173, 194 | threadWait() | 1500 ms | fillSortOrPinRowValues() |
| 206-214 | ThreadWait() | 1000 ms | selectAttribute() – multiple |
| 232 | await() | **NO-OP** | selectSortAttribute() |
| 233 | Thread.sleep(3000) | 3000 ms | selectSortAttribute() |
| 235 | Thread.sleep(3000) | 3000 ms | selectSortAttribute() |
| 241-242 | ThreadWait() | 1000 ms | selectCondition() |
| 257-258 | ThreadWait() | 1000 ms | selectValue() |
| 282-286 | threadWait(), ThreadWait(), threadWait() | 1.5s + 1s + 1.5s | goToSectionInMerchandising() |
| 284 | ThreadWait() | 1000 ms | goToSectionInMerchandising() |
| 303 | awaitForPageToLoad() | 10s | nextPage() |
| 308-318 | ThreadWait(), threadWait(), ThreadWait(), ThreadWait() | 1s×4 + 1.5s | clickOnApplyButton() |
| 324 | ThreadWait() | 1000 ms | deleteConditionIfItsPresent() |
| 346, 354 | threadWait() | 1500 ms | selectGlobalActionType() |
| 374 | ThreadWait() | 1000 ms | pinProductInFirstPosition() |
| 381-384 | ThreadWait() | 1000 ms | selectSortAtrributeAndOrder() |
| 391 | ThreadWait() | 1000 ms | pinProductFromConsolePreview() |
| 329-331 | ThreadWait() | 1000 ms | getMerchandisingCondition() |
| 346 | ThreadWait() | 1000 ms | deleteMerchandizingCondition() |
| 352 | ThreadWait() | 1000 ms | verifySlotIconIsPresentAtGivenPosition() |
| 364-367 | threadWait(), ThreadWait(), threadWait() | 1.5s + 1s + 1.5s | goToLandingPage() |
| 374-378 | threadWait(), Thread.sleep(3000), threadWait()×2 | 1.5s + 3s + 1.5s×2 | goToSearch_browsePreview() |
| 384-385 | threadWait() | 1500 ms | ClickViewHideInsight() |
| 389 | ThreadWait() | 1000 ms | switchPreviewTab() |
| 416-419 | ThreadWait(), threadWait(), awaitForPageToLoad(), ThreadWait(), threadWait() | 1s + 1.5s + 10s + 1s + 1.5s | switchPreviewTab() |
| 419-421 | ThreadWait(), threadWait() | 1s + 1.5s | openPreviewAndSwitchTheTab() |
| 428-429 | ThreadWait(), threadWait() | 1s + 1.5s | openPreviewAndSwitchTheTab() |
| 434-436 | ThreadWait(), threadWait() | 1s + 1.5s | openPreviewAndSwitchTheTab() – retry loop |
| 441 | threadWait() | 1500 ms | dragAndDropPinningPosition() |
| 443 | ThreadWait() | 1000 ms | dragAndDropPinningPosition() |

**Total:** ~45 ThreadWait (1s), ~20 threadWait (1.5s), 3 Thread.sleep(3000), 3 waitForLoaderToDisAppear (60s), 1 awaitForPageToLoad (10s), 5 await() (NO-OP)

---

### 4.3 CommercePageActions.java

| Line | Type | Duration | Context |
|------|------|----------|---------|
| 26-27 | ThreadWait()×2 | 2000 ms | queryRuleByName() |
| 46-47 | ThreadWait(), awaitForElementPresence() | 1s + 30s | queryRuleByName1() |
| 54 | await() | **NO-OP** | queryRuleByName1() |
| 64-65 | ThreadWait()×2 | 2000 ms | deleteQueryRule() |
| 93, 97 | ThreadWait() | 1000 ms | deleteQueryRule() |
| 152 | threadWait() | 1500 ms | addAnotherCampaign() |
| 157, 165 | threadWait() | 1500 ms | getConditionTitle(), getConditionSize() |
| 172-173 | ThreadWait() | 1000 ms | getConditionSizeForConditionType() |
| 179-180 | ThreadWait() | 1000 ms | getSortPinConditionSize() |
| 187 | ThreadWait() | 1000 ms | selectModelWindow() |
| 251 | threadWait() | 1500 ms | editGlobalRule() |
| 254 | ThreadWait() | 1000 ms | fillQueryRuleData() |
| 266 | ThreadWait() | 1000 ms | fillQueryRuleData() – similar queries loop |
| 275 | await() | **NO-OP** | fillQueryRuleData() |
| 288-289 | ThreadWait(), threadWait() | 1s + 1.5s | fillPageName() |
| 298-299, 306-307, 314-315 | ThreadWait(), threadWait() | 1s + 1.5s | fillPageName() – multiple |
| 332 | ThreadWait() | 1000 ms | searchAndSelectDropdownOption() |

**Total:** ~15 ThreadWait (1s), ~6 threadWait (1.5s), 2 await() (NO-OP), multiple awaitForElementPresence (30s)

---

### 4.4 ABActions.java

| Line | Type | Duration | Context |
|------|------|----------|---------|
| 33 | threadWait() | 1500 ms | selectWinnerDecidedByabData() |
| 41 | ThreadWait() | 1000 ms | selectWinnerDecidedByabData() – dropdown wait loop |
| 75 | Thread.sleep(700) | 700 ms | waitAndScrollToPageBottom() |
| 88 | WebDriverWait | 10 seconds | scrollToAbSummaryStrategyBox() |
| 96 | Thread.sleep(300) | 300 ms | scrollToAbSummaryStrategyBox() |
| 104-105 | threadWait() | 1500 ms | selectPreferredOptionABData() |
| 107 | threadWait() | 1500 ms | selectPreferredOptionABData() |
| 148 | ThreadWait() | 1000 ms | fillABPercentage() |
| 150, 155, 159 | ThreadWait() | 1000 ms | fillABPercentage() |
| 171 | WebDriverWait | 10 seconds | scrollToBottomOfElement() |
| 178 | Thread.sleep(300) | 300 ms | scrollToBottomOfElement() |

**Total:** 4 ThreadWait (1s), 3 threadWait (1.5s), 2 Thread.sleep(700/300), 2 WebDriverWait (10s)

---

### 4.5 SegmentActions.java

| Line | Type | Duration | Context |
|------|------|----------|---------|
| 24-25 | ThreadWait(), threadWait() | 1s + 1.5s | fillLocation() |
| 45-48 | ThreadWait(), threadWait(), ThreadWait() | 1s×3 + 1.5s | selectTypeValues() |
| 56-60 | ThreadWait(), threadWait(), ThreadWait() | 1s×3 + 1.5s | selectDeviceType() |
| 85 | threadWait() | 1500 ms | fillSegmentName() |
| 91-96 | ThreadWait(), awaitForElementPresence(), ThreadWait() | 1s×2 + 30s | segmentRuleByName() |
| 101 | await() | **NO-OP** | segmentRuleByName() |
| 110-111 | awaitForPageToLoad(), threadWait() | 10s + 1.5s | createSegment() |
| 115-116 | ThreadWait(), threadWait() | 1s + 1.5s | createSegment() |
| 120 | ThreadWait() | 1000 ms | createSegment() |
| 130 | threadWait() | 1500 ms | deleteSegmentRule() |
| 143-144 | ThreadWait() | 1000 ms | deleteSegmentRule() |
| 154 | threadWait() | 1500 ms | selectSegmentActionType() |
| 183 | threadWait() | 1500 ms | fillCampaignData() |
| 193 | ThreadWait() | 1000 ms | addAndSaveCustomAttribute() |
| 208-209 | threadWait() | 1500 ms | addAndSaveCustomAttribute() |
| 230-231 | ThreadWait() | 1000 ms | selectAndEnterCustomAttribute() |
| 239 | ThreadWait() | 1000 ms | selectAndEnterCustomAttribute() |
| 259 | threadWait() | 1500 ms | selectAndEnterCustomValue() |
| 272 | threadWait() | 1500 ms | deleteCustomAttribute() |
| 286 | threadWait() | 1500 ms | deleteCustomAttribute() |

**Total:** ~12 ThreadWait (1s), ~15 threadWait (1.5s), 1 await() (NO-OP), 1 awaitForPageToLoad (10s)

---

### 4.6 freshnessAction.java

| Line | Type | Duration | Context |
|------|------|----------|---------|
| 21 | ThreadWait() | 1000 ms | selectAttribute() |
| 23 | Thread.sleep(3000) | 3000 ms | selectAttribute() |
| 26 | ThreadWait() | 1000 ms | selectAttribute() |
| 28 | Thread.sleep(3000) | 3000 ms | selectAttribute() |
| 30 | Thread.sleep(3000) | 3000 ms | selectAttribute() |
| 48 | ThreadWait() | 1000 ms | getFreshnessSummaryValue() |
| 51-52 | ThreadWait() | 1000 ms | getFreshnessSummaryValue() |
| 61-62 | ThreadWait(), threadWait() | 1s + 1.5s | getFreshnessSummaryValue() |
| 65-66 | threadWait() | 1500 ms | getFreshnessSummaryValue() |
| 76-77 | ThreadWait(), threadWait() | 1s + 1.5s | getFreshnessSummaryValue() |
| 95-96 | ThreadWait(), threadWait() | 1s + 1.5s | getFreshnessSummaryValue() |
| 96 | Thread.sleep(500) | 500 ms | getFreshnessSummaryValue() |
| 125 | ThreadWait() | 1000 ms | verifyFreshnessSummaryValue() |
| 143 | ThreadWait() | 1000 ms | verifyDateIsoForFirstFiveProducts() |

**Total:** 8 ThreadWait (1s), 6 threadWait (1.5s), 3 Thread.sleep(3000), 1 Thread.sleep(500)

---

### 4.7 VariantLockAction.java

| Line | Type | Duration | Context |
|------|------|----------|---------|
| 69-70 | ThreadWait(), Thread.sleep(800) | 1s + 800ms | scrollToBottomUntilVariantLockVisible() |
| 108-109 | ThreadWait(), Thread.sleep(800) | 1s + 800ms | scrollToBottomUntilVariantLockVisible() |
| 137-138 | ThreadWait(), Thread.sleep(1000) | 1s + 1000ms | scrollToBottomUntilVariantLockVisible() |
| 218 | ThreadWait() | 1000 ms | variantlock() |
| 220 | ThreadWait() | 1000 ms | variantlock() |
| 246-247 | ThreadWait(), Thread.sleep(500) | 1s + 500ms | scrollUntilPinningDropdownVisible() |
| 253 | ThreadWait() | 1000 ms | scrollUntilPinningDropdownVisible() |
| 279-280 | ThreadWait(), Thread.sleep(500) | 1s + 500ms | scrollUntilPinningDropdownVisible() |
| 281 | ThreadWait() | 1000 ms | scrollUntilPinningDropdownVisible() |
| 280 | Thread.sleep(600) | 600 ms | scrollUntilPinningDropdownVisible() |
| 344-345 | ThreadWait() | 1000 ms | waitAndScrollToPinningDropdown() |
| 363-364 | ThreadWait(), scrollToBottom (includes waits) | 1s + (see UiBase) | waitAndScrollToPinningDropdown() |
| 364-365 | ThreadWait() | 1000 ms | waitAndScrollToVariantStrategySummary() |
| 383-384 | ThreadWait() | 1000 ms | waitAndScrollToFooterButtonArea() |
| 322-323 | ThreadWait() | 1000 ms | selectVariantPinningProduct() |
| 328 | ThreadWait() | 1000 ms | selectVariantPinningProduct() |
| 345-346 | ThreadWait() | 1000 ms | waitAndScrollToPinningDropdownList() |
| 365-366 | ThreadWait() | 1000 ms | selectVariantPinningPosition() |
| 369-371 | ThreadWait()×3 | 3000 ms | selectVariantPinningPosition() |
| 386-387 | ThreadWait() | 1000 ms | getProductCount() |
| 398-399 | ThreadWait() | 1000 ms | getFirstProduct() |
| 413-414 | ThreadWait() | 1000 ms | getProductByIndex() |
| 458-459 | ThreadWait() | 1000 ms | clickVariantSwatch() |
| 471-472 | ThreadWait() | 1000 ms | clickVariantLockButton() |
| 447-448 | ThreadWait() | 1000 ms | navigateToNextVariant/PreviousVariant() |
| 483-484 | ThreadWait() | 1000 ms | waitForProductsToLoad() |
| 499-500 | ThreadWait() | 1000 ms | getUniqueIdFromSummary() |
| 528-529 | ThreadWait() | 1000 ms | getVariantIdFromSummary() |
| 632-633 | ThreadWait(), threadWait() | 1s + 1.5s | verifyRuleStatus() |
| 669-670 | ThreadWait(), threadWait() | 1s + 1.5s | verifyPendingSyncStatus() |
| 677-678 | threadWait(), ThreadWait() | 1.5s + 1s | verifyPendingSyncStatus() |
| 731-732 | ThreadWait(), threadWait() | 1s + 1.5s | clickSyncButton() |
| 741-742 | ThreadWait(), threadWait() | 1s + 1.5s | clickSyncButton() |
| 751-752 | ThreadWait(), threadWait() | 1s + 1.5s | clickSyncButton() |
| 756 | ThreadWait() | 1000 ms | clickSyncButton() |
| 771-772 | ThreadWait(), threadWait() | 1s + 1.5s | verifySyncInfoMessage() |
| 816-817 | ThreadWait(), threadWait() | 1s + 1.5s | refreshPage() |
| 826-827 | ThreadWait() | 1000 ms | refreshAndCheckSyncingStatus() |
| 841-842 | ThreadWait(), threadWait() | 1s + 1.5s | verifySyncingStatus() |
| 877-878 | ThreadWait(), threadWait() | 1s + 1.5s | waitSyncingNotToBeDisplayed() |
| 1264 | waitForLoaderToDisAppear() | Config-based | waitSyncingNotToBeDisplayed() |
| 919-920 | ThreadWait() | 1000 ms | verifyVariantLockingDisplayed() |
| 976-977 | ThreadWait() | 1000 ms | openVariantLockModal() |
| 988-989 | ThreadWait() | 1000 ms | clickVariantInModal() |
| 1008-1009 | ThreadWait() | 1000 ms | getVariantIdFromModal() |
| 1031-1032 | ThreadWait() | 1000 ms | applyVariantLockInModal() |
| 1036-1038 | threadWait(), ThreadWait() | 1.5s + 1s | applyVariantLockInModal() |
| 1048 | ThreadWait() | 1000 ms | verifyVariantIdAndLockInPreview() |
| 1081 | ThreadWait() | 1000 ms | selectAndLockFirstVariantFromModal() |
| 1110-1111 | ThreadWait(), threadWait() | 1s + 1.5s | verifyFirstProductVariantIdMatchesPreview() |
| 1131-1132 | ThreadWait() | 1000 ms | selectLockAndVerifyFirstVariant() |

**Total:** ~80+ ThreadWait (1s), ~15 threadWait (1.5s), 4 Thread.sleep(500-1000), 1 waitForLoaderToDisAppear (Config-based), multiple awaitTillElementDisplayed, awaitForPageToLoad

---

### 4.8 UiBase.java

| Line | Type | Duration | Context |
|------|------|----------|---------|
| 139-140 | ThreadWait()×2 | 2000 ms | selectDropDownValue() |
| 177 | threadWait() | 1500 ms | selectValueBYMatchingText() |
| 178 | Thread.sleep(2000) | 2000 ms | selectValueBYMatchingText() |
| 283 | threadWait() | 1500 ms | awaitTillElementDisplayed() – in loop |
| 334 | threadWait() | 1500 ms | unbxdInputBoxSearch() |
| 336 | threadWait() | 1500 ms | unbxdInputBoxSearch() |
| 367-384 | waitForLoaderToDisAppear() | 60s | Definition |
| 392-413 | waitForLoaderToDisAppear() | 60s (or interval) | Overload with retries |
| 415-434 | waitForElementAppear() | interval seconds | Definition |
| 438-458 | waitForElementToAppear() | 45s | Definition |
| 471-418 | waitForElementToBeClickable() | 15s or interval | Definition |
| 447 | Thread.sleep(1500) | 1500 ms | scrollToBottom() – in loop |
| 451 | waitForLoaderToDisAppear() | 1 retry, 5s | scrollToBottom() |
| 657-658 | ThreadWait() | 1000 ms | scrollToBottom() – modal guard loop |
| 471 | Thread.sleep(1500) | 1500 ms | scrollUntilVisible() |
| 524 | ThreadWait() | 1000 ms | retrieveText() |
| 531 | ThreadWait() | 1000 ms | retrieveText() – StaleElement catch |
| 702 | ThreadWait() | 1000 ms | waitForElementToDisappear() |
| 707 | ThreadWait() | 1000 ms | waitForElementToDisappear() – catch |
| 731 | ThreadWait() | 1000 ms | safeClick() – ElementClickIntercepted fallback |
| 746 | ThreadWait() | 1000 ms | safeClick() |
| 758 | ThreadWait() | 1000 ms | robustClick() |
| 504-506 | ThreadWait() | 1000 ms | Definition – static |

**Total:** Multiple ThreadWait (1s), threadWait (1.5s), Thread.sleep(1500/2000), waitForLoaderToDisAppear (60s)

---

### 4.9 MerchandisingRulesPage.java

**No direct wait calls.** Contains only element definitions and `getCampaignData()`.

---

### 4.10 CommerceSearchPage.java

| Line | Type | Duration | Context |
|------|------|----------|---------|
| 166 | awaitForPageToLoad() | 10 seconds | getUrl() |

**Total:** 1 awaitForPageToLoad (10s)

---

## 5. Summary Statistics

| File | Thread.sleep | ThreadWait (1s) | threadWait (1.5s) | awaitForPageToLoad | awaitForElementPresence | waitForLoaderToDisAppear | WebDriverWait | await() NO-OP |
|------|--------------|-----------------|------------------|--------------------|--------------------------|---------------------------|---------------|----------------|
| MerchandisingTest | 0 | 1 | ~20 | 5 | 2 | 0 | 0 | 0 |
| MerchandisingActions | 3×3000ms | ~45 | ~20 | 1 | many | 3 (60s) | 0 | 5 |
| CommercePageActions | 0 | ~15 | ~6 | 0 | many | 0 | 0 | 2 |
| ABActions | 4 (300-700ms) | 4 | 3 | 1 | 2 | 0 | 2 (10s) | 0 |
| SegmentActions | 0 | ~12 | ~15 | 1 | many | 0 | 0 | 1 |
| freshnessAction | 4 (500-3000ms) | 8 | 6 | 0 | 0 | 0 | 0 | 0 |
| VariantLockAction | 4 (500-1000ms) | ~80 | ~15 | many | 0 | 1 | 0 | 0 |
| UiBase | 3 (1500-2000ms) | many | many | 2 defs | 1 def | 2 defs | 4 defs | 0 |
| MerchandisingRulesPage | 0 | 0 | 0 | 0 | 0 | 0 | 0 | 0 |
| CommerceSearchPage | 0 | 0 | 0 | 1 | 0 | 0 | 0 | 0 |

---

## 6. Recommendations for Wait Reduction/Removal

### 6.1 High Priority – Remove or Replace

1. **Standalone `await()` calls** – These are **NO-OPs** (FluentAwait is created and discarded). Remove all 8+ instances in MerchandisingActions, CommercePageActions, SegmentActions.

2. **Thread.sleep(3000) in MerchandisingActions.selectSortAttribute()** (lines 233, 235) – Replace with `awaitForElementPresence()` or `waitForElementToBeClickable()` for the dropdown/selection.

3. **Thread.sleep(3000) in freshnessAction.selectAttribute()** (lines 23, 28, 30) – Replace with explicit waits for dropdown to load.

4. **Thread.sleep(3000) in MerchandisingActions.goToSearch_browsePreview()** (line 376) – Replace with `awaitForElementPresence(seach_browsepreview)` before click.

### 6.2 Medium Priority – Reduce Duration

1. **threadWait() (1500ms) vs ThreadWait() (1000ms)** – Standardize on one; 1000ms is often sufficient for UI updates.

2. **Multiple consecutive ThreadWait() + threadWait()** – e.g., MerchandisingActions lines 308-318 (4×ThreadWait + threadWait). Often one explicit wait (e.g., `waitForElementToBeClickable`) suffices.

3. **VariantLockAction** – Heavy use of ThreadWait() in scroll/visibility methods. Replace with `WebDriverWait` + `ExpectedConditions.visibilityOf()` or `elementToBeClickable()`.

4. **scrollToBottom() in UiBase** – Uses Thread.sleep(1500) in loop. Consider `WebDriverWait` for loader disappearance only.

### 6.3 Lower Priority – Consolidate

1. **waitForLoaderToDisAppear(60s)** – 60 seconds is conservative. If loader typically disappears in 5–10s, reduce to 15–30s or make configurable.

2. **awaitForElementPresence (30s)** – Used widely. Consider shorter timeouts (e.g., 15s) where elements load quickly.

3. **Duplicate waits** – e.g., `awaitForPageToLoad()` followed immediately by `threadWait()`. Often only one is needed.

### 6.4 Structural Improvements

1. **Introduce a configurable wait utility** – Centralize durations (e.g., `WAIT_UI_UPDATE_MS = 500`) for short post-action pauses.

2. **Prefer WebDriverWait over Thread.sleep** – Use `ExpectedConditions` for element visibility, clickability, and loader disappearance.

3. **Reduce retry loops with threadWait()** – e.g., `awaitTillElementDisplayed` uses a 12-iteration loop with threadWait(). Replace with a single `WebDriverWait` with appropriate timeout.
