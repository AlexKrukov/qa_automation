package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;

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
        type(By.name("home"), contactData.getHomePhone());
        type(By.name("email"), contactData.getEmail1());
        attach(By.name("photo"), contactData.getPhoto());

        /*
        if (creation) {
            new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
        } else {
            Assert.assertFalse(isElementPresent(By.name("new_group")));
        }
         */
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
        contactCache = null;
        returnToHomePage();
    }

    public void modify(ContactData contact) {
        initContactModification();
        fillContactForm(contact, false);
        updateContact();
        contactCache = null;
        returnToHomePage();
    }

    public void delete(ContactData contact) {
        selectContactById(contact.getId());
        deleteContact();
        contactCache = null;
    }

    public boolean isThereAContact() {
        return isElementPresent(By.name("selected[]"));
    }

    public int count() {
        return wd.findElements(By.name("selected[]")).size();
    }

    private Contacts contactCache = null;

    public Contacts all() {
        if (contactCache != null) {
            return new Contacts(contactCache);
        }
        contactCache = new Contacts();
        //List<WebElement> elements = wd.findElements(By.cssSelector("[name = entry]"));
        List<WebElement> elements = wd.findElements(By.name("entry"));
        for (WebElement element : elements) {
            List<WebElement> cells = element.findElements(By.tagName("td"));
            /*
            String first_name = element.findElement(By.cssSelector("td:nth-child(3)")).getText();
            String last_name = element.findElement(By.cssSelector("td:nth-child(2)")).getText();
            int id = Integer.parseInt(element.findElement(By.xpath("//input[@type='checkbox' and @name='selected[]']")).getAttribute("id"));
            //String[] phones = element.findElement(By.cssSelector("td:nth-child(6)")).getText().split("\n");
            String allPhones = element.findElement(By.cssSelector("td:nth-child(6)")).getText();
            String address = element.findElement(By.cssSelector("td:nth-child(4)")).getText();
            String allEmails = element.findElement(By.cssSelector("td:nth-child(5)")).getText();
            */
            //int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("id"));
            String last_name = cells.get(1).getText();
            String first_name = cells.get(2).getText();
            String address = cells.get(3).getText();
            String allEmails = cells.get(4).getText();
            String allPhones = cells.get(5).getText();
            contactCache.add(new ContactData()
                    //.withId(id)
                    .withFirstName(first_name)
                    .withLastName(last_name)
                    .withAddress(address)
                    .withAllPhones(allPhones)
                    .withAllEmails(allEmails));
        }
        return new Contacts(contactCache);
    }

    /*
    public Set<ContactData> all() {
        Set<ContactData> contacts = new HashSet<ContactData>();
        List<WebElement> rows = wd.findElements(By.name("entry"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.tagName("td"));
            int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));
            String lastname = cells.get(1).getText();
            String firstname = cells.get(2).getText();
            String allPhones = cells.get(5).getText();
            contacts.add(new ContactData().withId(id).withFirstName(firstname).withLastName(lastname));
        }
        return contacts;
    }
     */

    public ContactData infoFromEditForm(ContactData contact) {
        initContactModificationById(contact.getId());
        String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
        String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
        String home = wd.findElement(By.name("home")).getAttribute("value");
        String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
        String work = wd.findElement(By.name("work")).getAttribute("value");
        String address = wd.findElement(By.name("address")).getText();
        String email1 = wd.findElement(By.name("email")).getAttribute("value");
        String email2 = wd.findElement(By.name("email2")).getAttribute("value");
        String email3 = wd.findElement(By.name("email3")).getAttribute("value");
        wd.navigate().back();
        return new ContactData().withId(contact.getId()).withFirstName(firstname).withLastName(lastname)
                .withHomePhone(home).withMobilePhone(mobile).withWorkPhone(work).withAddress(address).withEmail1(email1).withEmail2(email2).withEmail3(email3);
    }

    private void initContactModificationById (int id) {
        WebElement checkbox = wd.findElement(By.cssSelector(String.format("input[value='%s']", id)));
        WebElement row = checkbox.findElement(By.xpath("./../.."));
        List<WebElement> cells = row.findElements(By.tagName("td"));
        cells.get(7).findElement(By.tagName("a")).click();
    }
}
