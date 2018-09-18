package innopolic.lesson8.chat.client;

import innopolic.lesson8.chat.Server.ServerStarter;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class ClientStarter {


    public void start() throws InterruptedException {
        Socket socket;
        boolean serverResponseAdd = false;
        Thread threadReader;
        Thread threadWriter;

        socket = connection();
        System.out.println("Ура, соединение установлено.");
        try {
            OutputStreamWriter serverOutput = new OutputStreamWriter(socket.getOutputStream());
            InputStreamReader serverInput = new InputStreamReader(socket.getInputStream());
            Scanner scanner = new Scanner(System.in);
            String message;
            BufferedReader bufferedReader = new BufferedReader(serverInput);
            System.out.println("Введите, пожалуйста, никнейм нового клиента:");

            while ((message = scanner.nextLine()) != "") {
                BufferedWriter bufferedWriter = new BufferedWriter(serverOutput);
                bufferedWriter.write(message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                serverResponseAdd = Boolean.parseBoolean(bufferedReader.readLine());

                if (serverResponseAdd) {
                    System.out.println("Теперь Вы в нашем чате. Для выхода введите: exit");
                    break;
                }else {
                    System.out.println("Извините, но такой никнейм уже заняли, придуймайте другой.");
                }
            }

            threadReader = new ClientThreadReader(socket);
            threadWriter = new ClientThreadWriter(socket);
            threadReader.setDaemon(true);
            threadWriter.setDaemon(true);
            threadReader.start();
            threadWriter.start();
            threadWriter.join();
            threadReader.interrupt();

        } catch (IOException e) {
            System.out.println("Сбой соединения с сервером.");
            System.exit(0);
        }

    }


    private Socket connection(){
        System.out.println("Добро пожаловать в наш чат.");
        System.out.println("Подождите, пожалуйста, идёт подключение к серверу.");

        Socket socket = null;
        try {
            socket = new Socket("127.0.0.1", ServerStarter.SERVER_PORT);
        } catch (IOException e) {
            System.out.println("Простите, возникли проблемы с сервером, попробуйте позже. Мы работаем над проблемой.");
            System.exit(0);
        }
        return socket;
    }

}
