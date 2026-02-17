# Time Savings Estimate: MerchandizingTestcases.xml Optimizations

## Suite scope
- **Suite file:** `src/test/resources/testNG/MerchandizingTestcases.xml`
- **Test count:** 58 test methods
- **Execution:** `parallel="tests"`, `thread-count="40"` (many tests run in parallel)

---

## 1. Current wait time (before optimization)

Estimated **total wait time** that runs when the full suite executes (sum across all tests; wall-clock will be lower due to parallelism).

### 1.1 MerchandisingActions.java (shared by all 58 tests)

| Delay type | Est. runs per test | Seconds per run | Tests | Total (sec) |
|------------|--------------------|-----------------|-------|-------------|
| publishCampaign/GlobalRule Thread.sleep(7s/10s) | 1–2 | 14–24 | 58 | ~1,100 |
| goToSectionInMerchandising (threadWait + 5s + ThreadWait) | 1–2 | ~12 | 58 | ~700 |
| clickOnApplyButton (5 waits: 3+4+3+3+3 s) | 2–4 | ~16 each | 58 | ~2,300 |
| fillRowValues / selectAttribute (ThreadWait + 3s×3) | 5–15 ops | ~12 each | 58 | ~2,500 |
| selectCondition (ThreadWait×2) | ~5 | 6 each | 58 | ~600 |
| openPreviewAndSwitchTheTab + switchPreviewTab | ~40 tests open preview | 3+4+3+4 + (3+4) + retries | 40 | ~900 |
| goToLandingPage (threadWait + ThreadWait×2) | 1 | ~10 | 58 | ~580 |
| goToSearch_browsePreview (threadWait + 3s + threadWait) | ~40 | ~10 | 40 | ~400 |
| selectAISuggestedSimilarQueryData (8s + threadWait×2) | 1 | ~16 | 6 | ~96 |
| selectSimilarQueryData (threadWait×2) | 1–2 | 8 | ~15 | ~120 |
| Other (getMerchandisingCondition, deleteCondition, pinProduct, etc.) | — | — | — | ~1,200 |
| **Subtotal MerchandisingActions** | | | | **~10,496** |

### 1.2 CampaignCreationPage.java
- Thread.sleep(10000) × 2 per campaign creation.
- ~58 tests × ~1.2 campaign creations × 20 s ≈ **~1,400 s**

### 1.3 CommercePageActions.java
- Thread.sleep(5000) + ThreadWait/threadWait in getMerchandisingCondition and related flows.
- ~25 tests use these flows × ~25 s ≈ **~625 s**

### 1.4 Test-class Thread.sleep (only tests in XML)

| Test class | Methods in XML | Sleeps (sec) | Total (sec) |
|------------|----------------|--------------|-------------|
| PromotionStatusTest | 5 | 5, 7, 5, 7, 7, 5, 7, 5, 7 | 55 |
| BrowsePinningTest | 2 | 5, 5 | 10 |
| SearchBannerTest | 2 | 7, 5, 5, 5, 7 | 29 |
| SearchCEDBannerTest | 1 | 5, 5 | 10 |
| SearchRedirectTest | 1 | 7 | 7 |
| BrowseBoostTest | 2 | 9 | 9 |
| SimilarQueryTest | 6 | 5 (one method) | 5 |
| FieldRuleFacetTest | 10 | 7 each | 70 |
| BrowseFilterTest | 1 | 7 | 7 |
| **Subtotal test classes** | | | **202** |

---

## 2. Total current wait time

| Source | Seconds | Minutes |
|--------|---------|---------|
| MerchandisingActions | ~10,496 | ~175 |
| CampaignCreationPage | ~1,400 | ~23 |
| CommercePageActions | ~625 | ~10 |
| Test classes | 202 | ~3.4 |
| **Total** | **~12,723** | **~212** |

So there are roughly **3.5 hours** of pure wait time in the suite when all tests run (summed; not wall-clock).

---

## 3. After optimization (conditional waits)

- Fixed sleeps are replaced by **wait until condition** with a **max timeout** (e.g. 10–15 s).
- When the UI is ready quickly (typical case), the wait ends in **~1–3 seconds** instead of 3–10 s.

Assumptions:
- Average effective wait after optimization: **~2 s** per wait (condition met quickly).
- Some waits (e.g. publish loader) may still take 5–10 s when backend is slow; we keep a cap (e.g. 20 s) instead of fixed 7–10 s every time.

Rough split:
- **~70%** of current wait time is “over-waiting” (element was ready in &lt; 2 s but we waited 3–10 s).
- **~30%** remains as necessary conditional wait time (2–10 s until condition is met).

So:
- **Remaining wait after optimization:** 12,723 × 0.30 ≈ **3,817 s** (~64 min).
- **Saved:** 12,723 − 3,817 ≈ **8,906 s** ≈ **148 min** ≈ **2.5 hours**.

---

## 4. Summary (time you can save)

| Metric | Value |
|--------|--------|
| **Current total wait time (summed)** | ~**3.5 hours** |
| **Estimated remaining after optimization** | ~**1 hour** |
| **Estimated time saved (summed)** | **~2.5 hours** |

### Wall-clock impact (suite run with 40 parallel tests)

- Total suite time is dominated by the **slowest tests** and setup/teardown, not the sum of all waits.
- If the longest test today is ~15–20 min, removing 2–5 min of waits per test shortens the **critical path**.
- **Conservative:** suite wall-clock could be **15–25% faster** (e.g. 5–10 min saved on a 40–50 min run).
- **Optimistic:** if many tests are wait-bound, **25–40% faster** (e.g. 10–20 min saved).

So in practice you can expect:
- **Summed wait time saved:** ~**2.5 hours** (when considering all tests together).
- **Real run (wall-clock) saved:** on the order of **5–20 minutes** per full suite run, depending on how wait-heavy the slowest tests are.

---

## 5. Assumptions and caveats

1. **Execution order and branches:** Not every test hits every code path (e.g. publishGlobalRule vs publishCampaign); the table uses average “runs per test” and may over- or under-count.
2. **Parallelism:** With 40 threads, total run time is not “total wait / 40”; the estimate focuses on **total wait removed** and a rough **wall-clock range**.
3. **Environment:** Local vs remote (e.g. Selenium Grid) can change how quickly conditions are met; savings may be higher when the app is fast and current fixed waits are unnecessary.
4. **Conditional wait time:** The 30% “remaining” assumes conditions usually pass within 2–10 s; if the app is often slow, that 30% could be higher and savings slightly lower.

---

*Generated for the MerchandizingTestcases.xml optimization scope.*
