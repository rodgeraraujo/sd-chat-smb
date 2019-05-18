package br.edu.ifpb.chat;

import br.edu.ifpb.chat.controller.CommandFileController;
import br.edu.ifpb.chat.model.Message;
import br.edu.ifpb.chat.service.CommandFileServiceImpl;
import jcifs.smb.*;

import java.net.MalformedURLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.*;

public class App {
    private static String login = "";

    private static void printMessages(PrintStream consoleOuput, ArrayList<Message> messages, boolean withMe) {
        for (Message m : messages) {
            if (!m.getId().equals(login) || withMe) {
                String line = String.format(
                        "[%s] <%s>: %s",
                        m.getTimestamp().format(DateTimeFormatter.ISO_LOCAL_DATE),
                        m.getId(),
                        m.getMensagem());
                consoleOuput.print(line + "\n");
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {

        CommandFileController commandFileController = new CommandFileController(new CommandFileServiceImpl());
        SmbFile file = null;

        final Logger log = Logger.getAnonymousLogger();

        //autentica a entrada na pasta compartilhada
        final String username = "rodger";
        final String password = "secret";
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(null, username, password);

        //tentar acessar o arquivo
        try {
            file = new SmbFile("smb://192.168.1.111/chat-sd/db.txt", auth);
        } catch (MalformedURLException e) {
            log.log(Level.WARNING, "Arquivo não encontrado!\n" + e.getMessage());
        }

        final int OPTION_LOGIN = 0;
        final int OPTION_MESSAGE = 1;
        final int OPTION_EXIT = 3;

        //autorização
        List<String> authorizedLogin = new ArrayList<String>();
        authorizedLogin.add("rodger");
        authorizedLogin.add("admin");
        authorizedLogin.add("ari");

        int optionMenu = OPTION_LOGIN;

        System.out.print("< < SD - Chat on SMB > >\nDigite seu login\nUsername: ");
        Scanner scannerLogin = new Scanner(System.in);
        login = scannerLogin.nextLine();
        //
        final PrintStream consoleOuput = System.out;
        if (authorizedLogin.contains(login)) {

            //caso o login dê certo apresenta uma mensagem
            consoleOuput.println("\n--- Mensagens Anteriores ---");
            printMessages(consoleOuput, commandFileController.readFile(file), true);

            //TODO: como controlar para apresentar a última mensagem
            Scanner scannerMessage = new Scanner(System.in);
            while (optionMenu != OPTION_EXIT) {
                consoleOuput.println("\n--- Mensagens Anteriores ---");

                //1- ler do arquivo //TODO: ler apenas o que precisa
                ArrayList<Message> messages = commandFileController.readFile(file);

                //2 - escreve no arquivo
                printMessages(consoleOuput, messages, true);

                //tarefa - a cada 10s cancela a escrita
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task, 10 * 1000); // timeout de 10s

                //3 - ler do console e enviar para o arquivo
                consoleOuput.print("Eu: ");
                String messageText = scannerMessage.nextLine();//bloqueante

                //4 - timeout de 10 para cancela o scanner
                timer.cancel();

                //5 - construir a mensagem e escrever no arquivo
                Message message = new Message(login, messageText, LocalDateTime.now());
                commandFileController.writeFile(file, message);

//                Thread.sleep(500); //aguarda 0.5s

                //6 - Limpar console do terminal
                clearConsole();

            }

        } else consoleOuput.println("Login inválido!");

    }

    private static void clearConsole() {
        try {
            new ProcessBuilder(
                    "/bin/bash", "-c", "clear")
                    .inheritIO()
                    .start()
                    .waitFor();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //TODO: recuperar apenas a última mensagem, depois que for lido as mensagens antigas
    //TODO: criar um looping para recuperar as mensagens e um timout para escrita (10s)
}
