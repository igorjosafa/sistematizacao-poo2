package ceub;


import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ContactManagerGUI extends BasicOperations implements ClientInterface {

    private static JFrame frame;
    private static final JPanel panel = new JPanel();
    private static final JTextField nameField = new JTextField(20);
    private static final JTextField phoneField = new JTextField(9);
    private static final JTextField emailField = new JTextField(20);
    private static final JTextArea contactListArea = new JTextArea(10, 30);
    private static final JScrollPane scrollPane = new JScrollPane(contactListArea);
    private static final JLabel nameLabel = new JLabel("Nome:");
    private static final JLabel phoneLabel = new JLabel("Telefone (9 dígitos):");
    private static final JLabel emailLabel = new JLabel("E-mail (contendo @ e terminando em .com):");

    public ContactManagerGUI() {
        this.showTitle();
        this.showOptions();
        contactListArea.setEditable(false);
    }

    private boolean validadeName() {
        String nameRegex = "^[a-zA-Z ]+$";

        if (!Pattern.matches(nameRegex, nameField.getText())) {
            JOptionPane.showMessageDialog(frame, "Nome inválido: Somente letras e espaços são permitidos.");
            return false;
        }
        return true;
    }

    private boolean validadePhone() {
        String phoneRegex = "^\\d{9}$";

        if (!Pattern.matches(phoneRegex, phoneField.getText())) {
            JOptionPane.showMessageDialog(frame, "Telefone inválido: Deve conter exatamente 9 dígitos.");
            return false;
        }
        return true;
    }

    private boolean validadeEmail() {
        String emailRegex = "^(|[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.com)$";

        if (!Pattern.matches(emailRegex, emailField.getText())) {
            JOptionPane.showMessageDialog(frame, "Email inválido: Deve ser vazio ou conter um formato válido com .com.");
            return false;
        }

        return true;
    }

    private void erasePanel() {
        panel.removeAll();
        frame.getContentPane().remove(scrollPane);
        contactListArea.setText("");
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
    }

    private void refreshPanel() {
        panel.revalidate();
        panel.repaint();
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }

    @Override
    public final void showTitle() {
        frame = new JFrame("Agenda Telefônica");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 300);
    }

    @Override
    public final void showOptions() {
        panel.setLayout(new GridLayout(5, 2));
        erasePanel();
        int i = 0;

        for (String operationString : operationsStrings) {
            final int buttonValue = i + 1;
            i += 1;
            JButton botao = new JButton(operationString);
            botao.addActionListener(e -> receiveInput(new int[]{buttonValue}));
            panel.add(botao);
        }

        refreshPanel();
    }

    @Override
    public void receiveInput(int[] options) {
        int opcao = options[0];
        if (opcao == 1) {
            colectDataAndAddContact();
        }
        if (opcao == 2) {
            colectNameAndSearch();
        }
        if (opcao == 3) {
            colectPhoneNumberAndSearch();
        }
        if (opcao == 4) {
            showListOfContacts();
        }
        if (opcao == 5) {
            colectNameAndRemove();
        }
        if (opcao == 6) {
            colectPhoneNumberAndRemove();
        }
    }

    @Override
    public void colectDataAndAddContact() {
        erasePanel();
        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(phoneLabel);
        panel.add(phoneField);
        panel.add(emailLabel);
        panel.add(emailField);
        JButton submitButton = new JButton("Salvar contato");
        submitButton.addActionListener(e -> {
            if (validadeName() & validadePhone() & validadeEmail()) {
                int phoneNumber = Integer.parseInt(phoneField.getText());
                int response = JOptionPane.showConfirmDialog(panel, "Confirma a inclusão do contato?", "Confirmação", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    Contact contato = new Contact(nameField.getText(), phoneNumber, emailField.getText());
                    BasicOperations.addContact(contato);
                    JOptionPane.showMessageDialog(frame, "Contato adicionado com sucesso!");
                    showOptions();
                } else if (response == JOptionPane.NO_OPTION) {
                    JOptionPane.showMessageDialog(frame, "Inclusão de contato cancelada!");
                    showOptions();
                }
            }
        });
        JButton menuButton = new JButton("Retornar");
        menuButton.addActionListener(e -> {
            showOptions();
        });
        panel.add(submitButton);
        panel.add(menuButton);
        refreshPanel();
    }

    @Override
    public void colectNameAndSearch() {
        erasePanel();
        panel.add(nameLabel);
        panel.add(nameField);
        frame.getContentPane().add(BorderLayout.SOUTH, scrollPane);
        JButton submitButton = new JButton("Buscar contato");
        submitButton.addActionListener(e -> {
            if (validadeName()) {
                Contact contato = BasicOperations.searchContactByName(nameField.getText());
                if (contato == null) {
                    contactListArea.setText("Nenhum contato encontrado.");
                } else {
                    contactListArea.setText(contato.toString());
                }
            }
        });
        JButton menuButton = new JButton("Retornar");
        menuButton.addActionListener(e -> {
            showOptions();
        });
        panel.add(submitButton);
        panel.add(menuButton);
        refreshPanel();
    }

    @Override
    public void colectPhoneNumberAndSearch() {
        erasePanel();
        panel.add(phoneLabel);
        panel.add(phoneField);
        frame.getContentPane().add(BorderLayout.SOUTH, scrollPane);
        JButton submitButton = new JButton("Buscar contato");
        submitButton.addActionListener(e -> {
            if (validadePhone()) {
                int phoneNumber = Integer.parseInt(phoneField.getText());
                Contact contato = BasicOperations.searchContactByPhoneNumber(phoneNumber);
                if (contato == null) {
                    contactListArea.setText("Nenhum contato encontrado.");
                } else {
                    contactListArea.setText(contato.toString());
                }
            }
        });
        JButton menuButton = new JButton("Retornar");
        menuButton.addActionListener(e -> {
            showOptions();
        });
        panel.add(submitButton);
        panel.add(menuButton);
        refreshPanel();
    }

    @Override
    public void showListOfContacts() {
        erasePanel();
        frame.getContentPane().add(BorderLayout.NORTH, scrollPane);
        ArrayList<String> listOfContacts = BasicOperations.getListOfContacts();
        contactListArea.setText("Quantidade de contatos salvos: " + BasicOperations.getNumberOfContacts() + "\n");
        for (String contato : listOfContacts) {
            contactListArea.setText(contactListArea.getText() + contato + "\n");
        }
        JButton menuButton = new JButton("Retornar");
        menuButton.addActionListener(e -> {
            showOptions();
        });
        panel.add(menuButton);
        refreshPanel();

    }

    @Override
    public void colectNameAndRemove() {
        erasePanel();
        panel.add(nameLabel);
        panel.add(nameField);
        frame.getContentPane().add(BorderLayout.SOUTH, scrollPane);
        JButton submitButton = new JButton("Excluir contato");
        submitButton.addActionListener(e -> {
            if (validadeName()) {
                Contact contato = BasicOperations.searchContactByName(nameField.getText());
                if (contato == null) {
                    contactListArea.setText("Nenhum contato encontrado.");
                } else {
                    contactListArea.setText(contato.toString());
                    int response = JOptionPane.showConfirmDialog(frame, "Confirma a exlusão deste contato?", "Confirmação", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        BasicOperations.removeContactByName(nameField.getText());
                        contato = BasicOperations.searchContactByName(nameField.getText());
                        if (contato == null) {
                            contactListArea.setText("Nenhum contato encontrado.");
                            JOptionPane.showMessageDialog(frame, "Contato removido com sucesso!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Erro na exclusão do contato!");
                        }
                    } else if (response == JOptionPane.NO_OPTION) {
                        JOptionPane.showMessageDialog(frame, "Exclusão cancelada.");
                        showOptions();
                    }

                }
            }
        });
        JButton menuButton = new JButton("Retornar");
        menuButton.addActionListener(e -> {
            showOptions();
        });
        panel.add(submitButton);
        panel.add(menuButton);
        refreshPanel();
    }

    @Override
    public void colectPhoneNumberAndRemove() {
        erasePanel();
        panel.add(phoneLabel);
        panel.add(phoneField);
        frame.getContentPane().add(BorderLayout.SOUTH, scrollPane);
        JButton submitButton = new JButton("Excluir contato");
        submitButton.addActionListener(e -> {
            if (validadePhone()) {
                int phoneNumber = Integer.parseInt(phoneField.getText());
                Contact contato = BasicOperations.searchContactByPhoneNumber(phoneNumber);
                if (contato == null) {
                    contactListArea.setText("Nenhum contato encontrado.");
                } else {
                    contactListArea.setText(contato.toString());
                    int response = JOptionPane.showConfirmDialog(frame, "Confirma a exlusão deste contato?", "Confirmação", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        BasicOperations.removeContactByPhoneNumber(phoneNumber);
                        contato = BasicOperations.searchContactByPhoneNumber(phoneNumber);
                        if (contato == null) {
                            contactListArea.setText("Nenhum contato encontrado.");
                            JOptionPane.showMessageDialog(frame, "Contato removido com sucesso!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Erro na exclusão do contato!");
                        }
                    } else if (response == JOptionPane.NO_OPTION) {
                        JOptionPane.showMessageDialog(frame, "Exclusão cancelada.");
                        showOptions();
                    }

                }
            }
        });
        JButton menuButton = new JButton("Retornar");
        menuButton.addActionListener(e -> {
            showOptions();
        });
        panel.add(submitButton);
        panel.add(menuButton);
        refreshPanel();
    }
}
