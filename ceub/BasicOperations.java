package ceub;

import java.util.ArrayList;

abstract class BasicOperations {
    private final static ContactList agenda = ContactList.getInstance();

    protected static int[] operationValues = {
        1,
        2,
        3,
        4,
        5,
        6
    };

    protected static String[] operationsStrings = {
        "1 - Adicionar Contato",
        "2 - Buscar Contato pelo Nome",
        "3 - Buscar Contato pelo Telefone",
        "4 - Listar Contatos",
        "5 - Remover Contato pelo Nome",
        "6 - Remover Contato pelo Telefone"};

    protected static int getNumberOfContacts() {
        return agenda.getNumberOfContacts();
    }

    protected static void addContact(Contact contact) {
        agenda.addContact(contact);
    }

    protected static Contact searchContactByName(String name) {
        Contact contato = agenda.searchContactByName(name);
        return contato;
    }

    protected static Contact searchContactByPhoneNumber(int phoneNumber) {
        Contact contato = agenda.searchContactByPhoneNumber(phoneNumber);
        return contato;
    }

    protected static ArrayList<String> getListOfContacts() {
        ArrayList<String> contatos = agenda.listContacts();
        return contatos;
    }

    protected static void removeContactByName(String name) {
        agenda.removeContactByName(name);
    }

    protected static void removeContactByPhoneNumber(int phoneNumber) {
        agenda.removeContactByPhoneNumber(phoneNumber);
    }
}
