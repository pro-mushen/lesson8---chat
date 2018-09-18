package innopolic.lesson8.chat.client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientThreadWriter extends Thread {

    Socket socket;

    ClientThreadWriter(Socket socket){
        this.socket = socket;
    }

    public void writeInServer(){


        try {

            OutputStreamWriter serverOutput = new OutputStreamWriter(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);
            String message;

            while ((message = scanner.nextLine())!=""){

                BufferedWriter bufferedWriter = new BufferedWriter(serverOutput);
                bufferedWriter.write(message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                if (message.equals("exit")){
                    sleep(1000);
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        writeInServer();
    }

}
