package core.ui.actions;

import core.ui.page.PreviewTemplatePage;
import lib.enums.UnbxdEnum;

public class PreviewTemplateActions extends PreviewTemplatePage {
    public void selectTemplate(UnbxdEnum type) {
        switch (type) {
            case TEMPLATE_1:
                click(previewTemplate1);
                break;

            case TEMPLATE_2:
                click(previewTemplate2);
                break;

            case TEMPLATE_3:
                click(previewTemplate3);
                break;

            case TEMPLATE_4:
                click(previewTemplate4);
                break;

            case TEMPLATE_5:
                click(previewTemplate5);
                break;

            case TEMPLATE_6:
                scrollToElement(previewTemplate6, templates);
                click(previewTemplate6);
                break;

            case TEMPLATE_7:
                scrollToElement(previewTemplate7, templates);
                click(previewTemplate7);
                break;

            case TEMPLATE_8:
                scrollToElement(previewTemplate8, templates);
                click(previewTemplate8);
                break;

            default:
        }
    }

    public void closePopUpIfOpened() {
        if (awaitForElementPresence(templatePopupCloseButton) == true) {
            click(templatePopupCloseButton);
        }
    }

    public int verifyFieldPresence(UnbxdEnum type) {
        int listSize = 0;

        switch (type) {
            case PREVIEW_TITLE:
                listSize =productTitle.size();
                break;

            case PREVIEW_BADGES:
                listSize = previewTemplateBadges.size();
                break;

            case PREVIEW_SKU:
                listSize = previewTemplateSku.size();
                break;
        }
        return listSize;
    }
    public String getAppliedTemplateName() {
        return appliedTemplateName.getText();
    }


}
