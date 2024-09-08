package ceub;

import java.util.ArrayList;
import java.util.function.Predicate;

interface SearchByName {
    Node searchByName(String name, Node headNode, Node lastNode);
}

interface SearchByNumber {
    Node searchByNumber(int phoneNumber, Node headNode, Node lastNode);
}

abstract class BaseSearch {

    protected Node search(Predicate<Contact> condition, Node headNode, Node lastNode) {
        if (headNode == null) {
            return null;
        }

        if (condition.test(headNode.getNodeContact())) {
            return headNode;
        }
        else {
            Node node = headNode.getNextNode();

            if (node == null) {
                return null;
            }

            while (!condition.test(node.getNodeContact())) {
                if (node.equals(lastNode)) {
                    return null;
                }
                node = node.getNextNode();
            }
            return node;
        }
    }
}

class SearchContactByName extends BaseSearch implements SearchByName {

    @Override
    public Node searchByName(String name, Node headNode, Node lastNode) {
        return search(contact -> name.equals(contact.getName()), headNode, lastNode);
    }
}

class SearchContactByNumber extends BaseSearch implements SearchByNumber {

    @Override
    public Node searchByNumber(int phoneNumber, Node headNode, Node lastNode) {
        return search(contact -> phoneNumber == contact.getPhoneNumber(), headNode, lastNode);
    }
}

public class ContactList {
    private int numberOfContacts;
    private Node headNode, lastNode;
    private static ContactList instance;

    private ContactList() {
        this.numberOfContacts = 0;
        this.headNode = null;
        this.lastNode = null;
    }

    public static synchronized ContactList getInstance() {
        if (instance == null) {
            instance = new ContactList();
        }
        return instance;
    }

    public void clear() {
        this.numberOfContacts = 0;
        this.headNode = null;
        this.lastNode = null;
    }

    public int getNumberOfContacts() {
        return this.numberOfContacts;
    }

    public void addContact(Contact contact) {
        if (this.numberOfContacts == 0) {
            this.headNode = new Node(this.lastNode, contact);
            this.lastNode = this.headNode;
        }
        else {
            this.lastNode = new Node(this.lastNode, contact);
            this.lastNode.getPreviousNode().setNextNode(this.lastNode);
        }

        this.numberOfContacts += 1;
    }  

    private Node searchNodeByName(String name) {
        SearchContactByName searchStrategy = new SearchContactByName();

        Node node = searchStrategy.searchByName(name, this.headNode, this.lastNode);

        return node;
    } 

    private Node searchNodeByPhoneNumber(int phoneNumber) {
        SearchContactByNumber searchStrategy = new SearchContactByNumber();

        Node node = searchStrategy.searchByNumber(phoneNumber, this.headNode, this.lastNode);

        return node;
    } 

    public Contact searchContactByName(String name) {
        Node node = searchNodeByName(name);
        if (node != null) {
            return node.getNodeContact();
        } else {
            return null;
        }
    }

    public Contact searchContactByPhoneNumber(int phoneNumber) {
        Node node = searchNodeByPhoneNumber(phoneNumber);
        if (node != null) {
            return node.getNodeContact();
        } else {
            return null;
        }
    }

    public Node removeContactByName(String name) {
        Node node = searchNodeByName(name);
        if (node == null) {
            return null;
        }
        return removeContact(node);
    } 

    public Node removeContactByPhoneNumber(int phoneNumber) {
        Node node = searchNodeByPhoneNumber(phoneNumber);
        if (node == null) {
            return null;
        }
        return removeContact(node);
    } 

    private Node removeContact(Node node) {
        if (this.numberOfContacts == 1) {
            this.headNode = null;
            this.lastNode = null;
            node.nullifyNode();
        }
        else if (node.equals(this.headNode)) {
            this.headNode = node.getNextNode();
            this.headNode.setPreviousNode(null);
            node.nullifyNode();
        }
        else if (node.equals(this.lastNode)) {
            this.lastNode = node.getPreviousNode();
            this.lastNode.setNextNode(null);
            node.nullifyNode();
        }
        else {
            node.getPreviousNode().setNextNode(node.getNextNode());
            node.getNextNode().setPreviousNode(node.getPreviousNode());
            node.nullifyNode();
        }
        this.numberOfContacts -= 1;
        return node;
    }

    public ArrayList<String> listContacts() {
        Node node = this.headNode;
        ArrayList<String> listOfContacts = new ArrayList<>();

        if (node == null) {
            return listOfContacts;
        }

        while (!node.equals(this.lastNode)) {
            listOfContacts.add(node.getNodeContact().toString());
            node = node.getNextNode();
        }

        listOfContacts.add(node.getNodeContact().toString());

        return listOfContacts;
    }

}

