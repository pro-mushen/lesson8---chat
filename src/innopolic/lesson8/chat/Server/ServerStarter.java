package innopolic.lesson8.chat.Server;

import innopolic.lesson8.chat.client.InfoOnClient;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerStarter {

    public static final int SERVER_PORT = 4998;
    private BlockingQueue<InfoOnClient> clients = new LinkedBlockingQueue<>();

    public void startServer(){

        ServerSocket server = null;
        try {
            server = new ServerSocket(SERVER_PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true) {
            Socket tmpSocket = null;
            try {

                tmpSocket = server.accept();
                checkMessageClientCreator(tmpSocket);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void checkMessageClientCreator(Socket socket){
        String nickName = null;
        try {
            BufferedReader clientReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter clientWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            while ((nickName = clientReader.readLine()) != null){
                InfoOnClient client = new InfoOnClient(socket,nickName);
                String addClient;
                addClient = String.valueOf(addClient(client));
                clientWriter.write(addClient);
                clientWriter.newLine();
                clientWriter.flush();

                if (addClient.equals("true")) {
                    Thread thread = new ServerThreadReader(client, clients);
                    thread.setDaemon(true);
                    thread.start();
                    break;
                }
            }

        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException e1) {
            }
        }

    }


    private boolean addClient( InfoOnClient client){
        if (!hasClient(client)){
            clients.add(client);
            return true;
        }else {
            return false;
        }

    }

    private boolean hasClient(InfoOnClient client){
        return clients.stream().anyMatch(o -> o.equals(client));
    }
}
