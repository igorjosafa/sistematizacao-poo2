# Sistema de Gerenciamento de Contatos

Sistema de gerenciamento de contatos desenvolvido em Java.

## Estrutura do Projeto

- `/ceub/` - Contém as classes principais do sistema.
- Classe principal: `ContactManager.java`.

## Compilação
Na pasta `/ceub/` execute o comando `javac BasicOperations.java ClientInterface.java Contact.java ContactList.java ContactManager.java ContactManagerCLI.java ContactManagerGUI.java Node.java Populator.java `

## Execução
Há duas possibilidades de execução:
- Execução com uma lista de contatos vazia: na pasta `/ceub/` execute o comando `java ContactManager.java`. O sistema será inicializado e você poderá fazer operações de adição, remoção e consultas de contatos na lista.
- Execução com uma lista de contatos inicialmente povoada com contatos de teste: na pasta `/ceub/` execute o comando `java ContactManager.java x`, onde o x deve ser substituído pela quantidade desejada de contatos. Exemplo: `java ContactManager.java 500` cria uma lista com 500 contatos.
