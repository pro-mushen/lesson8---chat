package innopolic.lesson8.chat.Server;

import innopolic.lesson8.chat.Sender;
import innopolic.lesson8.chat.client.InfoOnClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BlockingQueue;

public class ServerThreadReader extends Thread {
    InfoOnClient client;
    private BlockingQueue<InfoOnClient> clients;
    Sender sender;

    ServerThreadReader(InfoOnClient client, BlockingQueue<InfoOnClient> clients){
        this.client = client;
        this.clients = clients;
        this.sender = new Sender(clients);
    }

    @Override
    public void run() {
        try{
            InputStreamReader serverInput = new InputStreamReader(client.getSocket().getInputStream());
            BufferedReader bufferedReader = new BufferedReader(serverInput);
            String message;
            sender.sayAll("Server: " + " приветствуем нового участника - " + client.getNickName()+ ".");
            while (!client.getSocket().isClosed() && !Thread.currentThread().isInterrupted()) {
                if ((message = bufferedReader.readLine())!=null){
                    if (!message.equals("exit")){
                        sender.sayAll(client.getNickName()+ ": " + message, client);
                    }else{
                        sender.sayAll(client.getNickName()+ " покинул наш чат", client);
                        sender.tellTheClient("exit",client);
                        try {
                            sleep(2000);
                        } catch (InterruptedException e) {
                        }
                        client.getSocket().close();
                        clients.remove(client);
                    }
                }
            }
        } catch (IOException e) {
            try {
                client.getSocket().close();
                clients.remove(client);
            } catch (IOException e1) {}
            e.printStackTrace();
        }
    }
}
