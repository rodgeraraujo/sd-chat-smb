import java.io.File;
import java.util.Scanner;
import java.io.IOException;

public class Server {

    public static void main(String... arg) throws IOException, InterruptedException {

        //Cria o arquivo txt e checa se ele existe
        File messages = new File("db.txt");

        if (messages.createNewFile()){
            System.out.println(""); }
        else {
            System.out.println("");}

        // Clear o console do terminal
        new ProcessBuilder(
                "/bin/bash", "-c", "clear")
                .inheritIO()
                .start()
                .waitFor();

        //Loop infinte para ler o arquivo
        for (int i = 0; i < 10; i--) {

            //Limpa o console do terminal a cada verificaçao de nova mensagem
            new ProcessBuilder(
                    "bash", "-c", "clear")
                    .inheritIO()
                    .start()
                    .waitFor();

            //Scanner to read each line
            Scanner scanner = new Scanner(new File("db.txt"));
            while (scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());}

            Thread.sleep(500); //Delay no loop por meio segundo (0.5)
        }
    }
}