package innopolic.lesson8.chat;

import innopolic.lesson8.chat.client.InfoOnClient;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Sender {
    private BlockingQueue<InfoOnClient> clients = new LinkedBlockingQueue<>();

    public Sender(BlockingQueue<InfoOnClient> clients) {
        this.clients = clients;
    }

    public void sayAll(String message, InfoOnClient unlessClient){
        clients.stream().filter(client -> !client.equals(unlessClient)).forEach(client -> tellTheClient(message, client));
    }

    public void sayAll(String message){
        clients.stream().forEach(client -> tellTheClient(message, client));
    }

    public void tellTheClient(String message, InfoOnClient client){

        if (client!=null){
            if (client.getSocket() != null){
                Socket socket = client.getSocket();
                try {
                    OutputStream toClient = socket.getOutputStream();
                    BufferedWriter clientWriter = new BufferedWriter(new OutputStreamWriter(toClient));
                    clientWriter.write(message);
                    clientWriter.newLine();
                    clientWriter.flush();
                } catch (IOException e) {
                    try {
                        socket.close();
                    } catch (IOException e1) {}
                    e.printStackTrace();
                }

            }
        }

    }
}
