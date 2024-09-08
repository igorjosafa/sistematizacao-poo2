package ceub;

public class Node {
    private Contact contact;
    private Node previous, next;

    public Node(Node previous, Contact contact) {
        this.previous = previous;
        this.contact = contact;
        this.next = null;
    }

    public Contact getNodeContact() {
        return this.contact;
    }

    public Node getPreviousNode() {
        return this.previous;
    }

    public Node getNextNode() {
        return this.next;
    }

    public void setPreviousNode(Node node) {
        this.previous = node;
    }

    public void setNextNode(Node node) {
        this.next = node;
    }

    public void nullifyNode() {
        this.contact = null;
        this.previous = null;
        this.next = null;
    }
}