package org.fundacionjala.sfdc.pages.base;

import org.fundacionjala.sfdc.core.CommonActions;
import org.fundacionjala.sfdc.core.driver.DriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Abstract class that gets common info in Home Page.
 */
public abstract class HomeBase extends BasePage {

    @FindBy(css = "a[title='New']")
    protected WebElement newButton;

    protected WebElement displayedItem;

    protected WebElement dropDownListLink;

    @FindBy(xpath = "//div[contains(@class, 'visible positioned')]/descendant::a[@title='Edit']")
    protected WebElement editButton;

    @FindBy(xpath = "//div[contains(@class, 'visible positioned')]/descendant::a[@title='Delete']")
    protected WebElement deleteButton;

    @FindBy(css = "button[title='Delete']")
    protected WebElement confirmDeleteButton;

    @FindBy(xpath = "//div[@class='slds-spinner_container slds-grid slds-hide']")
    protected WebElement spinner;

    @FindBy(xpath = "//span[contains(@class, 'toastMessage')]")
    protected WebElement successMessage;

    /**
     * Gets the Displayed Item.
     *
     * @return WebElement displayed.
     */
    public WebElement getDisplayedItem() {
        return displayedItem;
    }

    /**
     * Determines if the Item is Displayed on the Page.
     *
     * @param name String.
     * @return Boolean.
     */
    public boolean isDisplayedItem(String name) {
        try {
            DriverManager.getInstance().setUpdateWait(5);
            String xpathSelector = String.format("//a[contains(text(),'%s')]", name);
            displayedItem = driver.findElement(By.xpath(xpathSelector));
            return displayedItem.isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        } finally {
            DriverManager.getInstance().backPreviousWait();
        }
    }

    /**
     * Clicks the Displayed Item.
     *
     * @param name String.
     * @return DetailBase.
     */
    public abstract DetailBase clickDisplayedItem(String name);

    /**
     * Clicks the New Button to open Form Base.
     *
     * @return FormBase.
     */
    public abstract FormBase clickNewButton();

    /**
     * Clicks the Edit Button to open Form Base.
     *
     * @param name String.
     * @return FormBase.
     */
    public abstract FormBase clickEditButton(String name);

    /**
     * Click the DropDown List of the Item.
     *
     * @param name String.
     */
    public void clickDropDownListLink(String name) {
        String xpathSelector = String.format("//a[contains(text(),'%s')]/ancestor::tr/"
                + "descendant::a[contains(@class,'slds-button slds-button--icon-x-small')]", name);
        dropDownListLink = wait.until(ExpectedConditions
                .elementToBeClickable(By.xpath(xpathSelector)));
        dropDownListLink.click();
    }

    /**
     * Clicks the Delete Button.
     */
    public void clickDeleteButton() {

        CommonActions.clickElement(deleteButton);
    }

    /**
     * Clicks the Confirm Delete Button.
     */
    public void clickConfirmDeleteButton() {
        CommonActions.clickElement(confirmDeleteButton);
    }

    /**
     * Deletes the Element.
     *
     * @param name String.
     */
    public void deleteElement(String name) {
        clickDropDownListLink(name);
        clickDeleteButton();
        clickConfirmDeleteButton();
    }

    /**
     * Waits until the spinner is hidden.
     */
    public void waitUntilSpinnerIsHidden() {
        wait.until(ExpectedConditions.invisibilityOf(spinner));
    }

}