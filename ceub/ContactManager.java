package ceub;

public class ContactManager {

    public static void main(String[] args) {
        ContactManagerGUI GUI = new ContactManagerGUI();
        ContactManagerCLI CLI = new ContactManagerCLI();

        if (args.length > 0) {
            try {
                int size = Integer.parseInt(args[0]);
                System.out.println("Populando a agenda com " + size + " contatos.");
                Populator populator = new Populator(size);
            } catch (NumberFormatException e) {
                System.out.println("Argumentos não reconhecidos.");
            }
        }

        while (true) {
            int options[] = ContactManagerCLI.operationValues;
            CLI.showTitle();
            CLI.showOptions();
            CLI.receiveInput(options);
        }
    }
}
