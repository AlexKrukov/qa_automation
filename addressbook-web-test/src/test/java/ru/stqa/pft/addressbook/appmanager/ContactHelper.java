package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ContactHelper extends HelperBase {


    public ContactHelper(WebDriver wd) {
        super(wd);
    }

    public void returnToHomePage() {
        click(By.linkText("home page"));
    }

    public void enterNewContact() {
        click(By.xpath("//div[@id='content']/form/input[21]"));
    }

    public void fillContactForm(ContactData contactData, boolean creation) {
        type(By.name("firstname"), contactData.getFirstName());
        type(By.name("lastname"), contactData.getLastName());
        type(By.name("home"), contactData.getPhone());
        type(By.name("email"), contactData.getEmail());

        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
    }

    public void initContactCreation() {
        click(By.linkText("add new"));
    }

    public void selectContactById(int id) {
        wd.findElement(By.cssSelector("input[value = '" + id +"']")).click();
    }

    public void deleteContact() {
        click(By.xpath("//input[@value='Delete']"));
        wd.switchTo().alert().accept();
    }

    public void initContactModification() {
        click(By.xpath("//img[@alt='Edit']"));
    }

    public void updateContact() {
        click(By.xpath("(//input[@name='update'])[2]"));
    }

    public void create(ContactData contact, boolean a) {
        initContactCreation();
        fillContactForm(contact, a);
        enterNewContact();
        returnToHomePage();
    }

    public void modify(ContactData contact) {
        initContactModification();
        fillContactForm(contact, false);
        updateContact();
        returnToHomePage();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteContact();
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int getContactCount() {
        return wd.findElements(By.name("selected[]")).size();
    }

    public List<ContactData> list() {
        List<ContactData> contacts = new ArrayList<>();
        List<WebElement> elements = wd.findElements(By.cssSelector("[name = entry]"));
        for (WebElement element : elements) {
            String first_name = element.findElement(By.cssSelector("td:nth-child(3)")).getText();
            String last_name = element.findElement(By.cssSelector("td:nth-child(2)")).getText();
            int id = Integer.parseInt(element.findElement(By.xpath("//input[@type='checkbox' and @name='selected[]']")).getAttribute("id"));
            contacts.add(new ContactData().withId(id).withFirstName(first_name).withLastName(last_name));
        }
        return contacts;
    }

    public Set<ContactData> all() {
        Set<ContactData> contacts = new HashSet<ContactData>();
        List<WebElement> elements = wd.findElements(By.cssSelector("[name = entry]"));
        for (WebElement element : elements) {
            String first_name = element.findElement(By.cssSelector("td:nth-child(3)")).getText();
            String last_name = element.findElement(By.cssSelector("td:nth-child(2)")).getText();
            int id = Integer.parseInt(element.findElement(By.xpath("//input[@type='checkbox' and @name='selected[]']")).getAttribute("id"));
            contacts.add(new ContactData().withId(id).withFirstName(first_name).withLastName(last_name));
        }
        return contacts;
    }
}
