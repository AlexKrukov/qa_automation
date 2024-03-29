package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData()
                    .withFirstName("John").withLastName("Travolta").withHomePhone("6171112233").withEmail1("jt777@gmail.com").withGroup("test1"), true);
        }
    }

    @Test
    public void testContactDeletion() {
        Contacts before = app.contact().all();
        ContactData deletedContact = before.iterator().next();
        app.contact().delete(deletedContact);
        app.goTo().homePage();

        assertThat(app.contact().count(), equalTo(before.size() - 1));

        Contacts after = app.contact().all();

        assertThat(after, equalTo(before.without(deletedContact)));

        /*
        before.remove(deletedContact);
        Assert.assertEquals(before, after);
         */
    }
}
