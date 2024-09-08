package ceub;

public class Contact {
    private final String name;
    private final int phoneNumber;
    private final String email;

    public Contact(String name, int phoneNumber, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    public String getName() {
        return this.name;
    }

    public int getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return this.name + " - " + this.phoneNumber + " - " + this.email;
    }
}