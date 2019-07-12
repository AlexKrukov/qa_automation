package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;


public class ContactCreationTests extends TestBase {

    @Test
    public void testContactCreation() {
        app.getNavigationHelper().gotoHomePage();
        app.getContactHelper().initContactCreation();
        app.getContactHelper().fillContactForm(new ContactData("John", "Travolta", "6171112233", "jt777@gmail.com"));
        app.getContactHelper().enterNewContact();
        app.getContactHelper().returnToHomePage();
    }

}