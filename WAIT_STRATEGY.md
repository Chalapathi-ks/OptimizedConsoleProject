# Wait & timing strategy (remote / Grid)

Tests often fail on remote/Grid with **wait**, **timing**, **element not interactable**, **not found**, or **assertion false**. This doc summarizes what was done and what to do next.

## Centralized changes

- **UiBase.AWAIT_PRESENCE_TIMEOUT_SEC**  
  Set to **25** seconds so all `awaitForElementPresence`-based flows get more time on remote.

- **MerchandisingActions**  
  All **10s** `WebDriverWait` usages for dropdowns/lists (attribute, sort, condition, timezone, window handles) increased to **20s** to avoid “Timed out after 10 seconds” on remote.

- **Similar-query modal**  
  Before clicking Apply in similar-query flows:
  - Wait for `.similar-queries-modal` overlay (opacity 0.6) to stop blocking: `waitForSimilarQueriesModalNotBlocking()`.
  - Use **25s** `waitForElementToBeClickable` for Apply and **clickUsingJS(applyChanges)** in both `selectSimilarQueryData` and `selectAISuggestedSimilarQueryData`.

- **Banner tabs**  
  - **40s** presence wait for tab inputs: `.banner-tab-header .radio-tab:nth-child(1) input` (image), `nth-child(2) input` (HTML).
  - **25s** visibility wait for the same tab input after click.
  - Image tab: **25s** wait for `//*[@name='imageUrl']` (tab content).

- **Segment**  
  - `awaitForSegmentEditPanelLoaded` and `awaitForSegmentUserTypeInListingPage` called with **30s** (was 20s) in SegmentTest.

- **Facet**  
  - `awaitForActiveToggle` increased to **40s** (was 30s) in `fillFacetDetails`.
  - **Update facet** button: use `facetableFieldsActions.awaitForUpdateFacetButton(25)` before click; uses `UPDATE_FACET_BUTTON_BY` (supports “Update facet” / “Update Facet”).

## Recommendations for future fixes

1. **Prefer By-based waits**  
   Use `WebDriverWait` with `By` (e.g. `ExpectedConditions.presenceOfElementLocated(By...)`) instead of resolving a FluentWebElement first, so the wait runs until the element exists.

2. **Overlays before click**  
   If you see “element click intercepted”, wait for the blocking overlay to disappear or become non-blocking (e.g. opacity) before clicking, then use JS click as fallback.

3. **Stale elements**  
   In list/table flows (e.g. promotion rules, status), wrap interactions in 2–3 retries on `StaleElementReferenceException`, re-finding the element each time.

4. **Condition-type assertions**  
   Before asserting “SELECTED CONDITION TYPE”, wait for the condition panel to contain the expected text (e.g. “Pinned”, “Boost”) with a short polling wait, then read and assert.

5. **Browse-picker values**  
   If “value not found in browse-picker”, increase value-option retries and add a short wait after typing/filtering so options can load (e.g. in CommercePageActions).

6. **Remote vs local**  
   For remote runs, consider a system property or env (e.g. `REMOTE_RUN=true`) to double key timeouts (e.g. 25 → 50s) without slowing local runs.

## Key files

- **UiBase.java** – `AWAIT_PRESENCE_TIMEOUT_SEC`, `threadWait()`, `awaitTillElementDisplayed`, `waitForElementToBeClickable`.
- **MerchandisingActions.java** – similar-query modal wait, attribute/sort/condition/timezone waits.
- **CommercePageActions.java** – condition title, toast, stop-campaign, browse-picker.
- **BannerActions.java** – banner tab and content waits.
- **SegmentActions.java** – segment edit/listing waits.
- **FacetableFieldsActions.java** – active toggle, display-name input, `awaitForUpdateFacetButton`.
