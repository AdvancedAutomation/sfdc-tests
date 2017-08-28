package org.fundacionjala.sfdc.stepdefinitions;

import java.util.Map;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.asserts.Assertion;

import org.fundacionjala.sfdc.core.CommonActions;
import org.fundacionjala.sfdc.core.driver.DriverManager;
import org.fundacionjala.sfdc.entities.Helper;
import org.fundacionjala.sfdc.pages.acccounts.AccountDetail;
import org.fundacionjala.sfdc.pages.acccounts.AccountForm;
import org.fundacionjala.sfdc.pages.acccounts.AccountFormField;
import org.fundacionjala.sfdc.pages.acccounts.AccountHome;

/**
 * Create Steps for Accounts.
 */
public class AccountsSteps {

    private Helper helper;

    private Map<AccountFormField, String> map;

    private Assertion assertion;

    /**
     * Constructor with Dependency Injection.
     *
     * @param helper Helper.
     */
    public AccountsSteps(Helper helper) {
        this.helper = helper;
        assertion = helper.getAssertion();
    }

    /**
     * Fill the Account Form.
     *
     * @param formMapData Map.
     */
    @When("^I fill the Account form with:$")
    public void iFillTheAccountFormWith(Map<AccountFormField, String> formMapData) {
        map = formMapData;
        helper.setItemName(formMapData.get(AccountFormField.ACCOUNT_NAME));
        new AccountForm().fillAndSaveForm(formMapData);
        new AccountHome().waitUntilSpinnerIsHidden();
    }

    /**
     * The Account data should be displayed on Account Detail Page.
     */
    @Then("^the Account should be displayed$")
    public void theAccountShouldBeDisplayed() {
        AccountDetail accountDetail = new AccountDetail();
        accountDetail.waitObjectNameIs(helper.getItemName());
        CommonActions.waitForAppear();
        DriverManager.getInstance().getWait().until(ExpectedConditions.titleContains(helper.getItemName()));
        accountDetail.clickDetailButton();
        assertion.assertTrue(accountDetail.getAccountNameText().equals(map.get(AccountFormField.ACCOUNT_NAME)));
        assertion.assertTrue(accountDetail.getTypeText().equals(map.get(AccountFormField.ACCOUNT_TYPE)));
        assertion.assertTrue(accountDetail.getDescriptionText().equals(map.get(AccountFormField.ACCOUNT_DESCRIPTION)));
        assertion.assertTrue(accountDetail.getPhoneText().equals(map.get(AccountFormField.ACCOUNT_PHONE)));
        assertion.assertTrue(accountDetail.getIndustryText().equals(map.get(AccountFormField.ACCOUNT_INDUSTRY)));
        assertion.assertTrue(accountDetail.getEmployeesText().equals(map.get(AccountFormField.ACCOUNT_EMPLOYEES)));
    }

    /**
     * The Account Data should be displayed on Account Home Page.
     */
    @And("^the Account should be displayed on Home Page$")
    public void theAccountShouldBeDisplayedOnHomePage() {
        AccountHome accountHome = new AccountHome();
        accountHome.waitUntilSpinnerIsHidden();
        DriverManager.getInstance().getWait().until(ExpectedConditions.urlContains("Account"));
        assertion.assertTrue(accountHome.isDisplayedItem(map.get(AccountFormField.ACCOUNT_NAME)));
        assertion.assertTrue(accountHome.isAccountFieldDisplayed(map.get(AccountFormField.ACCOUNT_NAME),
                map.get(AccountFormField.ACCOUNT_PHONE)));
    }

    /**
     * Click on Edit from Account.
     */
    @When("^I click on Edit from Account$")
    public void iClickOnEditFromAccount() {
        new AccountDetail().clickEditButton();
    }

    /**
     * Click on Delete from Account.
     */
    @When("^I Click on Delete from Account$")
    public void iClickOnDeleteFromAccount() {
        new AccountDetail().deleteItem();
    }

    /**
     * The Account should not be displayed on Home Page.
     */
    @Then("^the Account should not be displayed on Home Page$")
    public void theAccountShouldNotBeDisplayedOnHomePage() {
        AccountHome accountHome = new AccountHome();
        assertion.assertFalse(accountHome.isDisplayedItem(map.get(AccountFormField.ACCOUNT_NAME)));
    }
}
