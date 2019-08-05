package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Set;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();
        if (app.contact().list().size() == 0) {
            app.contact().create(new ContactData()
                    .withFirstName("John").withLastName("Travolta").withPhone("6171112233").withEmail("jt777@gmail.com").withGroup("test1"), true);
        }
    }

    @Test
    public void testContactModification() {
        Set<ContactData> before = app.contact().all();
        ContactData modifiedCOntact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedCOntact.getId()).withFirstName("John1").withLastName("Travolta1").withPhone("6171111111").withEmail("111@gmail.com");
        app.contact().modify(contact);
        Set<ContactData> after = app.contact().all();
        Assert.assertEquals(after.size(), before.size());

        before.remove(modifiedCOntact);
        before.add(contact);
        Assert.assertEquals(before, after);
    }
}
