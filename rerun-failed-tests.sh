#!/bin/bash

# Script to rerun failed TestNG tests
# Usage: ./rerun-failed-tests.sh [profile] [hubUrl]
# Example: ./rerun-failed-tests.sh ProdUK http://selenium-hub.netcorein.com:4444/wd/hub

PROFILE=${1:-ProdUK}
HUB_URL=${2:-"http://selenium-hub.netcorein.com:4444/wd/hub"}
FAILED_XML="target/surefire-reports/testng-failed.xml"

echo "=========================================="
echo "Rerunning Failed Tests"
echo "=========================================="
echo "Profile: $PROFILE"
echo "Selenium Grid URL: $HUB_URL"
echo "Failed Tests XML: $FAILED_XML"
echo "=========================================="

# Check if failed tests XML exists
if [ ! -f "$FAILED_XML" ]; then
    echo "❌ Error: Failed tests XML not found at $FAILED_XML"
    echo "   Please run the test suite first to generate failed tests."
    exit 1
fi

# Check if the XML file has any failed tests
if ! grep -q "<include name=" "$FAILED_XML"; then
    echo "✅ No failed tests found in $FAILED_XML"
    echo "   All tests passed or no tests were executed."
    exit 0
fi

echo ""
echo "📋 Found failed tests. Rerunning..."
echo ""

# Run Maven with the failed tests XML
mvn test -P"$PROFILE" \
    -Denv.profile="$PROFILE" \
    -DhubUrl="$HUB_URL" \
    -DsuiteXmlFile="$FAILED_XML"

EXIT_CODE=$?

echo ""
if [ $EXIT_CODE -eq 0 ]; then
    echo "✅ All failed tests passed on rerun!"
else
    echo "⚠️  Some tests still failed. Check the reports for details."
fi

exit $EXIT_CODE

