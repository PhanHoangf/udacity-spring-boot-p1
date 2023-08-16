package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class CredentialsPage {
    @FindBy(id = "create-cre-btn")
    private WebElement createCredentialBtn;

    @FindBy(id = "res-credential-url")
    private List<WebElement> resCredentialUrls;

    @FindBy(id = "res-credential-username")
    private List<WebElement> resCredentialUsernames;

    @FindBy(id = "res-credential-password")
    private List<WebElement> resCredentialPasswords;

    @FindBy(id = "credential-url")
    private WebElement credentialUrlInput;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameInput;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordInput;

    @FindBy(id = "cre-save-change-btn")
    private WebElement saveChangeBtn;

    @FindBy(id = "edit-credential-btn")
    private List<WebElement> editCredentialBtn;

    @FindBy(id = "delete-credential-btn")
    private List<WebElement> deleteCredentialBtn;

    private WebDriver driver;

    public CredentialsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void createCredential(String url, String username, String password) {
        createCredentialBtn.click();

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));

        credentialUrlInput.click();
        credentialUrlInput.sendKeys(url);

        credentialUsernameInput.click();
        credentialUsernameInput.sendKeys(username);

        credentialPasswordInput.click();
        credentialPasswordInput.sendKeys(password);

        saveChangeBtn.click();
    }

    public void updateCredential(String url, String username, String password) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));

        credentialUrlInput.click();
        credentialUrlInput.clear();
        credentialUrlInput.sendKeys(url);

        credentialUsernameInput.click();
        credentialUsernameInput.clear();
        credentialUsernameInput.sendKeys(username);

        credentialPasswordInput.click();
        credentialPasswordInput.clear();
        credentialPasswordInput.sendKeys(password);

        saveChangeBtn.click();
    }

    public WebElement getCreateCredentialBtn() {
        return createCredentialBtn;
    }

    public List<WebElement> getResCredentialUrls() {
        return resCredentialUrls;
    }

    public List<WebElement> getResCredentialUsernames() {
        return resCredentialUsernames;
    }

    public List<WebElement> getResCredentialPasswords() {
        return resCredentialPasswords;
    }

    public WebElement getCredentialUrlInput() {
        return credentialUrlInput;
    }

    public WebElement getCredentialUsernameInput() {
        return credentialUsernameInput;
    }

    public WebElement getCredentialPasswordInput() {
        return credentialPasswordInput;
    }

    public WebElement getSaveChangeBtn() {
        return saveChangeBtn;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public List<WebElement> getEditCredentialBtn() {
        return editCredentialBtn;
    }

    public List<WebElement> getDeleteCredentialBtn() {
        return deleteCredentialBtn;
    }
}
