package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase {

    @Test
    public void testContactModification() {
        app.getNavigationHelper().gotoHomePage();
        app.getContactHelper().initContactModification();
        app.getContactHelper().fillContactForm(new ContactData("John1", "Travolta1", "6171111111", "111@gmail.com", null), false);
        app.getContactHelper().updateContact();
        app.getContactHelper().returnToHomePage();
    }

}
