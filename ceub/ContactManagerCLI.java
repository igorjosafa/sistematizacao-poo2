package ceub;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

class ContactManagerCLI extends BasicOperations implements ClientInterface {

    private void performCLIOperation(int option) {
        if (option == 1) {
            this.colectDataAndAddContact();
        }

        if (option == 2) {
            this.colectNameAndSearch();
        }

        if (option == 3) {
            this.colectPhoneNumberAndSearch();
        }

        if (option == 4) {
            this.showListOfContacts();
        }

        if (option == 5) {
            this.colectNameAndRemove();
        }

        if (option == 6) {
            this.colectPhoneNumberAndRemove();
        }
    }

    @Override
    public void showTitle() {
        System.out.println("\n----Agenda Telefônica----\n");
    }

    @Override
    public void showOptions() {
        System.out.println("Opções:");
        for (String operation : operationsStrings) {
            System.out.println(operation);
        }
        System.out.println("\n");
    }

    @Override
    public void receiveInput(int[] options) {
        Integer[] optionsList = Arrays.stream(options).boxed().toArray(Integer[]::new);
        int userInput = 0;
        Scanner scanner = new Scanner(System.in);

        while (!Arrays.asList(optionsList).contains(userInput)) {
            System.out.print("Digite um número entre " + operationValues[0] + " e " + operationValues[operationValues.length - 1] + ": ");
            if (scanner.hasNextInt()) {
                userInput = scanner.nextInt();
                if (!Arrays.asList(optionsList).contains(userInput)) {
                    System.out.println("Opção inválida! Tente novamente.");
                }
            } else {
                System.out.println("Por favor, insira uma opção válida.");
                scanner = new Scanner(System.in);
            }
        }

        System.out.println("Você selecionou a opção: " + userInput);

        performCLIOperation(userInput);
    }

    @Override
    public void colectDataAndAddContact() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Adicionando contato");

        System.out.println("Digite o nome do contato: ");
        String name = scanner.nextLine();

        String number = "";
        while (!number.matches("\\d{9}")) {
            System.out.println("Digite o telefone do contato (exatamente 9 números): ");
            number = scanner.nextLine();
        }
        int phoneNumber = Integer.parseInt(number);

        String email = "placeholder";
        while (!email.isEmpty() && !email.matches(".+@.+\\.com")) {
            System.out.println("Digite o email do contato (em branco ou deve conter '@' e terminar com '.com'): ");
            email = scanner.nextLine();
        }

        System.out.println("\n");
        System.out.println("Confirma a inclusão de " + name + ", telefone " + phoneNumber + " email " + email + " na lista de contatos?");

        System.out.println("Digite qualquer tecla para confirmar ou n para cancelar.");
        String cancela = scanner.nextLine();
        if ("n".equals(cancela)) {
            System.out.println("Cancelando operação.");
            return;
        }

        Contact contact = new Contact(name, phoneNumber, email);

        BasicOperations.addContact(contact);
    }

    @Override
    public void colectNameAndSearch() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o nome do contato que deseja encontrar: ");
        String nome = scanner.nextLine();

        Contact contato = BasicOperations.searchContactByName(nome);

        if (contato == null) {
            System.out.println("\n");
            System.out.println("Nenhum contato encontrado.");
            System.out.println("\n");
        } else {
            System.out.println("\n");
            System.out.println("Contato encontrado:");
            System.err.println(contato);
            System.out.println("\n");
        }
    }

    @Override
    public void colectPhoneNumberAndSearch() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o telefone do contato que deseja encontrar: ");
        String input = scanner.nextLine();

        int phoneNumber;

        try {
            phoneNumber = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("\n");
            System.out.println("Nenhum contato encontrado.");
            System.out.println("\n");
            return;
        }

        Contact contato = BasicOperations.searchContactByPhoneNumber(phoneNumber);

        if (contato == null) {
            System.out.println("\n");
            System.out.println("Nenhum contato encontrado.");
            System.out.println("\n");
        } else {
            System.out.println("\n");
            System.out.println("Contato encontrado:");
            System.err.println(contato);
            System.out.println("\n");
        }
    }

    @Override
    public void showListOfContacts() {
        ArrayList<String> contatos = BasicOperations.getListOfContacts();
        System.out.println("\n");
        System.out.println("---Contatos Salvos---");
        System.out.println("Quantidade de contatos salvos: " + BasicOperations.getNumberOfContacts());
        if (contatos.isEmpty()) {
            System.out.println("Nenhum contato na lista.");
        } else {
            for (String contato : contatos) {
                System.out.println(contato);
            }
        }
        System.out.println("\n");
    }

    @Override
    public void colectNameAndRemove() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o nome do contato que deseja encontrar: ");
        String name = scanner.nextLine();

        Contact contato = BasicOperations.searchContactByName(name);

        if (contato == null) {
            System.out.println("\n");
            System.out.println("Nenhum contato encontrado.");
            System.out.println("\n");
        } else {
            System.out.println("\n");
            System.out.println("Contato encontrado:");
            System.err.println(contato);
            System.out.println("\n");
            System.out.println("Deseja excluir o contato? Digite s para excluir ou qualquer outra tecla para cancelar.");
            String confirma = scanner.nextLine();
            if ("s".equals(confirma)) {
                System.out.println("Excluindo contato.");
                System.out.println("\n");
                BasicOperations.removeContactByName(name);
            } else {
                System.out.println("Operação cancelada.");
                System.out.println("\n");
            }
        }

    }

    @Override
    public void colectPhoneNumberAndRemove() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Digite o telefone do contato que deseja encontrar: ");
        String input = scanner.nextLine();

        int phoneNumber;

        try {
            phoneNumber = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            System.out.println("\n");
            System.out.println("Nenhum contato encontrado.");
            System.out.println("\n");
            return;
        }

        Contact contato = BasicOperations.searchContactByPhoneNumber(phoneNumber);

        if (contato == null) {
            System.out.println("\n");
            System.out.println("Nenhum contato encontrado.");
            System.out.println("\n");
        } else {
            System.out.println("\n");
            System.out.println("Contato encontrado:");
            System.err.println(contato);
            System.out.println("\n");
            System.out.println("Deseja excluir o contato? Digite s para excluir ou qualquer outra tecla para cancelar.");
            String confirma = scanner.nextLine();
            if ("s".equals(confirma)) {
                System.out.println("Excluindo contato.");
                System.out.println("\n");
                BasicOperations.removeContactByPhoneNumber(phoneNumber);
            } else {
                System.out.println("Operação cancelada.");
                System.out.println("\n");
            }
        }
    }

}
