//import jcifs.smb.NtlmPasswordAuthentication;
//import jcifs.smb.SmbFile;
//import jcifs.smb.SmbFileOutputStream;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {

        //Samba autenticaçao
//        String userSmb = "rodger";
//        String passSmb = "secret";
//        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",userSmb, passSmb);
//
//        //Smb file
//        String path = "smb://192.168.1.111/chat-sd/db.txt";
//        SmbFile smbFile = new SmbFile(path,auth);
//        SmbFileOutputStream messagesSmb = new SmbFileOutputStream(smbFile);

        // Limpar console do terminal
        new ProcessBuilder(
                "/bin/bash", "-c", "clear")
                .inheritIO()
                .start()
                .waitFor();

        //Scanner para inserir o nome de usuario
        Scanner xx = new Scanner(System.in);
        System.out.print("username: ");
        String username = xx.nextLine();

        //Clear console apos o nome de usuario ser inserido
        new ProcessBuilder(
                "/bin/bash", "-c", "clear")
                .inheritIO()
                .start()
                .waitFor();

        //Cria o arquivo txt e checa se ele existe
        File messages = new File("db.txt");
        if (messages.createNewFile()) {
            System.out.println("");
        } else {
            System.out.println("");
        }

        //Loop infinte para escrever no arquivo
        for (int i = 0; i < 10; i--) {

            //Mostrar o nome de usuario
            Scanner ss = new Scanner(System.in);
            System.out.print("<" + username + "> ");
            String msg = ss.nextLine();

            //Clear a tela do terminal apos cada mensagem ser enviada
            new ProcessBuilder("/bin/bash", "-c", "clear").inheritIO().start().waitFor();

            //Pegar a data atual e formata
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "MM-dd-yyyy");
            String formatDateTime = now.format(formatter);

            String contentToAppend = "<" + username + " - " + formatDateTime + "> " + msg + "\r\n";

            //Escrever no arquivo compartilhado no smb
//            messagesSmb.write(contentToAppend.getBytes());

            //Escreve no arqivo txt
            Files.write(
                    Paths.get("db.txt"),
                    contentToAppend.getBytes(),
                    StandardOpenOption.APPEND
            );

            //Digite "-logout" para sair do chat
            if ("-logout".equals(msg)) {
                new ProcessBuilder("/bin/bash", "-c", "exit").inheritIO().start().waitFor();
            }

            // Digite "-clear" para limpar o arquivo txt (apenas admin)
            if ("-clear".equals(msg)) {
                if (username.equals("admin"))
                    clearTheFile();
            }
        }
    }

    //Limpa o arquivo txt
    public static void clearTheFile() throws IOException {
        FileWriter fwOb = new FileWriter("db.txt", false);
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
    }
}