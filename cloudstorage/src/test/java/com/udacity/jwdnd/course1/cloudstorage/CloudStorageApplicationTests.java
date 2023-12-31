package com.udacity.jwdnd.course1.cloudstorage;

import Pages.CredentialsPage;
import Pages.NotesPage;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    private NotesPage notesPage;

    private CredentialsPage credentialsPage;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        notesPage = new NotesPage(driver);
        credentialsPage = new CredentialsPage(driver);
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void getLoginPage() {
        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    @Test
    public void authorizeLoginAndSignupForAnyUser() {
        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/login");
        Assertions.assertEquals("Login", driver.getTitle());

        driver.get("http://localhost:" + this.port + "/signup");
        Assertions.assertEquals("Sign Up", driver.getTitle());
    }

    @Test
    public void testAuthorizedFlow() {
        doMockSignUp("fName", "lName", "admin", "admin");
        doLogIn("admin", "admin");

        Assertions.assertEquals("Home", driver.getTitle());

        WebElement logoutBtn = driver.findElement(By.id("logout-btn"));
        logoutBtn.click();

        driver.get("http://localhost:" + this.port + "/home");
        Assertions.assertEquals("Login", driver.getTitle());
    }

    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doMockSignUp(
            String firstName,
            String lastName,
            String userName,
            String password
    ) {
        // Create a dummy account for logging in later.

        // Visit the sign-up page.
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        driver.get("http://localhost:" + this.port + "/signup");
        webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));

        // Fill out credentials
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
        WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
        inputFirstName.click();
        inputFirstName.sendKeys(firstName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
        WebElement inputLastName = driver.findElement(By.id("inputLastName"));
        inputLastName.click();
        inputLastName.sendKeys(lastName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement inputUsername = driver.findElement(By.id("inputUsername"));
        inputUsername.click();
        inputUsername.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement inputPassword = driver.findElement(By.id("inputPassword"));
        inputPassword.click();
        inputPassword.sendKeys(password);

        // Attempt to sign up.
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
        WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
        buttonSignUp.click();

		/* Check that the sign up was successful.
		// You may have to modify the element "success-msg" and the sign-up
		// success message below depening on the rest of your code.
		*/
        Assertions.assertTrue(driver.findElement(By.id("success-msg"))
                .getText()
                .contains("You successfully signed up!"));
    }


    /**
     * PLEASE DO NOT DELETE THIS method.
     * Helper method for Udacity-supplied sanity checks.
     **/
    private void doLogIn(String userName, String password) {
        // Log in to our dummy account.
        driver.get("http://localhost:" + this.port + "/login");
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
        WebElement loginUserName = driver.findElement(By.id("inputUsername"));
        loginUserName.click();
        loginUserName.sendKeys(userName);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
        WebElement loginPassword = driver.findElement(By.id("inputPassword"));
        loginPassword.click();
        loginPassword.sendKeys(password);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        webDriverWait.until(ExpectedConditions.titleContains("Home"));

    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling redirecting users
     * back to the login page after a succesful sign up.
     * Read more about the requirement in the rubric:
     * https://review.udacity.com/#!/rubrics/2724/view
     */
    @Test
    public void testRedirection() {
        // Create a test account
        doMockSignUp("Redirection", "Test", "RT", "123");

        // Check if we have been redirected to the log in page.
        Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling bad URLs
     * gracefully, for example with a custom error page.
     * <p>
     * Read more about custom error pages at:
     * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
     */
    @Test
    public void testBadUrl() {
        // Create a test account
        doMockSignUp("URL", "Test", "UT", "123");
        doLogIn("UT", "123");

        // Try to access a random made-up URL.
        driver.get("http://localhost:" + this.port + "/some-random-page");
        Assertions.assertFalse(driver.getPageSource()
                .contains("Whitelabel Error Page"));
    }

    /**
     * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the
     * rest of your code.
     * This test is provided by Udacity to perform some basic sanity testing of
     * your code to ensure that it meets certain rubric criteria.
     * <p>
     * If this test is failing, please ensure that you are handling uploading large files (>1MB),
     * gracefully in your code.
     * <p>
     * Read more about file size limits here:
     * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
     */
    @Test
    public void testLargeUpload() {
        // Create a test account
        doMockSignUp("Large File", "Test", "LFT", "123");
        doLogIn("LFT", "123");

        // Try to upload an arbitrary large file
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        String fileName = "upload5m.zip";

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
        WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
        fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

        WebElement uploadButton = driver.findElement(By.id("uploadButton"));
        uploadButton.click();
        try {
            webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
        } catch (org.openqa.selenium.TimeoutException e) {
            System.out.println("Large File upload failed");
        }
        Assertions.assertFalse(driver.getPageSource()
                .contains("HTTP Status 403 – Forbidden"));
    }

    private void doFullFlowSignUpAndLogin() {
        doMockSignUp("name", "last", "admin", "admin");
        doLogIn("admin", "admin");
    }

    @Test
    public void test_CreateNote_OK() {
        String noteDescription = "Des";
        String noteTitle = "Title";

        doFullFlowSignUpAndLogin();

        driver.get("http://localhost:" + port + "/home/notes");
        notesPage.createNote(noteTitle, noteDescription);

        List<WebElement> noteTitles = driver.findElements(By.id("res-note-title"));
        List<WebElement> noteDescriptions = driver.findElements(By.id("res-note-description"));

        Assertions.assertEquals(noteTitles.get(0)
                .getText(), noteTitle);
        Assertions.assertEquals(noteDescriptions.get(0)
                .getText(), noteDescription);
    }

    @Test
    public void test_DeleteNote_OK() {
        String noteDes1 = "note des 1";
        String noteTitle1 = "note title 1";

        String noteDes2 = "note des 2";
        String noteTitle2 = "note title 2";

        doFullFlowSignUpAndLogin();
        driver.get("http://localhost:" + port + "/home/notes");

        notesPage.createNote(noteTitle1, noteDes1);
        notesPage.createNote(noteDes2, noteTitle2);

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-note-btn")));

        notesPage.getDeleteNoteBtn()
                .get(1)
                .click();

        List<WebElement> noteTitles = driver.findElements(By.id("res-note-title"));

        for (WebElement ele : noteTitles) {
            Assertions.assertNotEquals(noteTitle2, ele.getText());
        }
    }


    @Test
    public void test_UpdateNote_OK() {
        String noteDes1 = "note des 1";
        String noteTitle1 = "note title 1";

        String noteDes2 = "note des 2";
        String noteTitle2 = "note title 2";

        doFullFlowSignUpAndLogin();
        driver.get("http://localhost:" + port + "/home/notes");

        notesPage.createNote(noteTitle1, noteDes1);
        notesPage.createNote(noteDes2, noteTitle2);

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("delete-note-btn")));

        notesPage.getEditNoteBtn()
                .get(1)
                .click();

        String noteDes3 = "note des 3";
        String noteTitle3 = "note title 3";

        notesPage.updateNote(noteTitle3, noteDes3);

        WebElement noteTitle = driver.findElements(By.id("res-note-title")).get(1);
        WebElement noteDes = driver.findElements(By.id("res-note-description")).get(1);

        Assertions.assertEquals(noteTitle3, noteTitle.getText());
        Assertions.assertEquals(noteDes3, noteDes.getText());
    }


    @Test
    public void test_CreateCredential_OK() {
        String credentialUrl = "testUrl";
        String credentialUsername = "Cre username";
        String credentialPassword = "Cre password";

        doFullFlowSignUpAndLogin();

        driver.get("http://localhost:" + port + "/home/credentials");
        credentialsPage.createCredential(credentialUrl, credentialUsername, credentialPassword);

        List<WebElement> credentialUrls = credentialsPage.getResCredentialUrls();
        List<WebElement> credentialUsernames = credentialsPage.getResCredentialUsernames();

        String actualUsername = credentialUsernames.get(0).getText();
        String actualCredentialUrl = credentialUrls.get(0).getText();

        Assertions.assertEquals(actualCredentialUrl, credentialUrl);
        Assertions.assertEquals(actualUsername, credentialUsername);

        //Viewable password
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-credential-btn")));

        credentialsPage.getEditCredentialBtn().get(0).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));

        String password = credentialsPage.getCredentialPasswordInput().getAttribute("value");
        Assertions.assertEquals(password, credentialPassword);
    }

    @Test
    public void test_UpdateCredential_OK() {
        String credentialUrl = "testUrl";
        String credentialUsername = "Cre username";
        String credentialPassword = "Cre password";

        String updateCredentialUrl = "updated url";
        String updateCredentialUsername = "updated username";
        String updateCredentialPassword = "updated password";

        doFullFlowSignUpAndLogin();

        driver.get("http://localhost:" + port + "/home/credentials");
        credentialsPage.createCredential(credentialUrl, credentialUsername, credentialPassword);


        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-credential-btn")));

        credentialsPage.getEditCredentialBtn().get(0).click();

        credentialsPage.updateCredential(updateCredentialUrl, updateCredentialUsername, updateCredentialPassword);

        List<WebElement> credentialUrls = credentialsPage.getResCredentialUrls();
        List<WebElement> credentialUsernames = credentialsPage.getResCredentialUsernames();

        String actualUsername = credentialUsernames.get(0).getText();
        String actualCredentialUrl = credentialUrls.get(0).getText();

        Assertions.assertEquals(actualCredentialUrl, updateCredentialUrl);
        Assertions.assertEquals(actualUsername, updateCredentialUsername);

        credentialsPage.getEditCredentialBtn().get(0).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));

        String password = credentialsPage.getCredentialPasswordInput().getAttribute("value");
        Assertions.assertEquals(password, updateCredentialPassword);
    }

    @Test
    public void test_DeleteCredential_OK() {
        String credentialUrl = "testUrl";
        String credentialUsername = "Cre username";
        String credentialPassword = "Cre password";

        String credentialUrl2 = "testUrl2";
        String credentialUsername2 = "Cre username2";
        String credentialPassword2 = "Cre password2";

        doFullFlowSignUpAndLogin();

        driver.get("http://localhost:" + port + "/home/credentials");
        credentialsPage.createCredential(credentialUrl, credentialUsername, credentialPassword);
        credentialsPage.createCredential(credentialUrl2, credentialUsername2, credentialPassword2);

        credentialsPage.getDeleteCredentialBtn().get(0).click();
        List<WebElement> credentialUrls = driver.findElements(By.id("res-credential-url"));

        for (WebElement url : credentialUrls) {
            Assertions.assertNotEquals(credentialUrl, url.getText());
        }
    }
}
