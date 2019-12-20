package global.ututaxfree.androidui;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gjz on 9/3/16.
 */
public class Contact {
    private String index;
    private String name;

    public Contact(String index, String name) {
        this.index = index;
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public String getName() {
        return name;
    }

    public static List<Contact> getEnglishContacts() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact("A", "Abbey"));
        contacts.add(new Contact("A", "Alex"));
        contacts.add(new Contact("A", "Amy"));
        contacts.add(new Contact("A", "Anne"));
        contacts.add(new Contact("B", "Betty"));
        contacts.add(new Contact("B", "Bob"));
        contacts.add(new Contact("B", "Brian"));
        contacts.add(new Contact("C", "Carl"));
        contacts.add(new Contact("C", "Candy"));
        contacts.add(new Contact("C", "Carlos"));
        contacts.add(new Contact("C", "Charles"));
        contacts.add(new Contact("C", "Christina"));
        contacts.add(new Contact("D", "David"));
        contacts.add(new Contact("D", "Daniel"));
        contacts.add(new Contact("E", "Elizabeth"));
        contacts.add(new Contact("E", "Eric"));
        contacts.add(new Contact("E", "Eva"));
        contacts.add(new Contact("F", "Frances"));
        contacts.add(new Contact("F", "Frank"));
        contacts.add(new Contact("G", "Germany"));
        contacts.add(new Contact("G", "Greece"));
        contacts.add(new Contact("G", "Georgia"));
        contacts.add(new Contact("G", "Grenada"));
        contacts.add(new Contact("I", "Ivy"));
        contacts.add(new Contact("J", "James"));
        contacts.add(new Contact("J", "John"));
        contacts.add(new Contact("J", "Jessica"));
        contacts.add(new Contact("K", "Karen"));
        contacts.add(new Contact("K", "Karl"));
        contacts.add(new Contact("K", "Kim"));
        contacts.add(new Contact("L", "Leon"));
        contacts.add(new Contact("L", "Lisa"));
        contacts.add(new Contact("P", "Paul"));
        contacts.add(new Contact("P", "Peter"));
        contacts.add(new Contact("S", "Sarah"));
        contacts.add(new Contact("S", "Steven"));
        contacts.add(new Contact("R", "Robert"));
        contacts.add(new Contact("R", "Ryan"));
        contacts.add(new Contact("T", "Tom"));
        contacts.add(new Contact("T", "Tony"));
        contacts.add(new Contact("W", "Wendy"));
        contacts.add(new Contact("W", "Will"));
        contacts.add(new Contact("W", "William"));
        contacts.add(new Contact("Z", "Zoe"));
        return contacts;
    }



}
