package ceub;

import java.util.Arrays;

public class Populator {

    public Populator(int size) {
        final ContactList agenda = ContactList.getInstance();
        int quantityOfChars = 1;
        int countChars = size;
        String email;

        while (countChars / 26 >= 1) {
            quantityOfChars += 1;
            countChars = countChars / 26;
        }

        int phoneNumber = 999999999;
        char[] name = new char[quantityOfChars];
        Arrays.fill(name, 'A');

        char[] alphabet = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
            'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'
        };

        for (int i = 0; i < size; i++) {
            int index = i;
            for (int k = name.length - 1; k >= 0; k--) {
                name[k] = alphabet[index % 26];
                index /= 26;
            }

            String nameStr = new String(name);
            email = nameStr + "@" + nameStr + ".com";

            Contact contato = new Contact(nameStr, phoneNumber, email);
            agenda.addContact(contato);

            phoneNumber -= 1;

        }
    }
}
