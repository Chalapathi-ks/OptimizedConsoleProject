package core.consoleui.actions;

import core.consoleui.page.MerasurementSearchPage;
import org.testng.Assert;

import static lib.constants.UnbxdErrorConstants.SUCCESS_MESSAGE_FAILURE;

        public class MerasurementSearchAction extends MerasurementSearchPage {

            public void enableMeasurementSearchIfDisabled() throws InterruptedException 
            {

                Thread.sleep(5000);
                if (awaitForElementPresence(enableButton)!=true) {
                    await();
                    click(disableButtonKnob);
                    await();
                }
            }

            public void deleteExistingConfigAttribute () throws InterruptedException
            {
                Thread.sleep(2000);
                if(aList.size() > 0)
                { 
                    await();
                    for(int i =0; i < aList.size(); i++)
                    {
                        aList.get(i).find(".delete-attribute-btn").click();
                        await();
                    }
                }
                
            }


        public void selectConfigAttributeFromDropDown()
         {
                click(attributeDropdown);
                click(attributeSearchInput);
                ThreadWait();
                attributeSearchInput.fill().with("price");
                ThreadWait();
                for(int i=0; i<attributeDropDownValuesList.size();i++)
                {
                    if(attributeDropDownValuesList.get(i).getText().equalsIgnoreCase("price"))
                        ThreadWait();
                    {
                        click(attributeDropDownValuesList.get(i));
                        break;
                    }
                }
                awaitForElementPresence(priceAttributeOption);
                click(priceAttributeOption);
            }

            public void selectDimensionsFromDropDown()
            {
                click(dimensionDropdown);
                ThreadWait();
                click(dimensionOption1);
            }

            public void selectUnitFromDropDown()
            {
                click(unitDropDown.get(1));
                ThreadWait();
                click(usdOption);
            }

            public void saveChanges()
            {
                click(saveButton);
            }



}

