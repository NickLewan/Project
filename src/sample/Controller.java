//Written by: Nick Lewandowski
//This is the code to save my file.
package sample;
import javafx.collections.ObservableList;

import java.util.*;
import java.io.*;



public class Controller {
    public static void readPhoneBook() throws Exception {
        File F;
        F = new File("PhoneBook.txt");
        Scanner S = new Scanner(F);
        ObservableList<Person> data;
        Person[] contactList;
        contactList = new Person[200];
        int num_entries;
        num_entries = 0;
        while (S.hasNextLine()) {
            contactList = new Person[0];
            contactList[num_entries].setFirstName(S.next());
            contactList[num_entries].setLastName(S.next());
            contactList[num_entries].setNumber(S.next());
            contactList[num_entries].setNote(S.next());

            num_entries++;
        }
    }
}

