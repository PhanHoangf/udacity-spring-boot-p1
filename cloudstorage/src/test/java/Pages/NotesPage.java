package Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class NotesPage {
    @FindBy(id = "create-note-btn")
    private WebElement createNoteBtn;

    @FindBy(id = "note-title")
    private WebElement noteTitleInput;

    @FindBy(id = "note-description")
    private WebElement noteDescriptionInput;

    @FindBy(id = "note-save-change-btn")
    private WebElement noteSaveChangeBtn;

    @FindBy(id = "userTable")
    private WebElement notesTable;

    @FindBy(id = "delete-note-btn")
    private List<WebElement> deleteNoteBtn;

    @FindBy(id = "edit-note-btn")
    private  List<WebElement> editNoteBtn;

    private WebDriver driver;

    public NotesPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void createNote(String noteTitle, String noteDescription) {
        createNoteBtn.click();

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));

        noteTitleInput.click();
        noteTitleInput.sendKeys(noteTitle);

        noteDescriptionInput.click();
        noteDescriptionInput.sendKeys(noteDescription);

        noteSaveChangeBtn.click();
    }

    public void updateNote(String noteTitle, String noteDescription) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));

        noteTitleInput.click();
        noteTitleInput.clear();
        noteTitleInput.sendKeys(noteTitle);

        noteDescriptionInput.click();
        noteDescriptionInput.clear();
        noteDescriptionInput.sendKeys(noteDescription);

        noteSaveChangeBtn.click();
    }

    public WebElement getCreateNoteBtn() {
        return createNoteBtn;
    }

    public WebElement getNoteSaveChangeBtn() {
        return noteSaveChangeBtn;
    }

    public WebElement getNotesTable() {
        return notesTable;
    }

    public WebElement getNoteTitleInput() {
        return noteTitleInput;
    }

    public WebElement getNoteDescriptionInput() {
        return noteDescriptionInput;
    }

    public List<WebElement> getDeleteNoteBtn() {
        return deleteNoteBtn;
    }

    public List<WebElement> getEditNoteBtn() {
        return editNoteBtn;
    }

    public WebDriver getDriver() {
        return driver;
    }
}
