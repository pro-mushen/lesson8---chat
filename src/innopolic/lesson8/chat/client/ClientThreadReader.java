package innopolic.lesson8.chat.client;

import java.io.*;
import java.net.Socket;

public class ClientThreadReader extends Thread {

    Socket socket;

    ClientThreadReader(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        outputOnDisplay();
    }

    public void outputOnDisplay(){

        try{
            InputStreamReader serverInput = new InputStreamReader(socket.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(serverInput);
            while (!socket.isClosed() && !Thread.currentThread().isInterrupted()) {
                String message;
                if ((message = bufferedReader.readLine())!=null) {
                    if (!message.equals("exit")){
                        System.out.println(message);
                    }else{
                        System.out.println("Вы покинули наш чат, заходите ещё.");
                        socket.close();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
