package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();
        if (app.contact().all().size() == 0) {
            app.contact().create(new ContactData()
                    .withFirstName("John").withLastName("Travolta").withPhone("6171112233").withEmail("jt777@gmail.com").withGroup("test1"), true);
        }
    }

    @Test
    public void testContactModification() {
        Contacts before = app.contact().all();
        ContactData modifiedCOntact = before.iterator().next();
        ContactData contact = new ContactData()
                .withId(modifiedCOntact.getId()).withFirstName("John1").withLastName("Travolta1").withPhone("6171111111").withEmail("111@gmail.com");
        app.contact().modify(contact);

        assertThat(app.contact().count(), equalTo(before.size()));

        Contacts after = app.contact().all();

        assertThat(after, equalTo(before.without(modifiedCOntact).withAdded(contact)));

        /*
        before.remove(modifiedContact);
        before.add(contact);
        Assert.assertEquals(before, after);
         */
    }
}
