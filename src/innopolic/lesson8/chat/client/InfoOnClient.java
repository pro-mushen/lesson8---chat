package innopolic.lesson8.chat.client;

import java.net.Socket;


public class InfoOnClient {

    private String nickName;
    private Socket socket;

    public InfoOnClient(Socket socket, String nickName) {
        this.socket = socket;
        this.nickName = nickName;
    }

    public Socket getSocket() {
        return socket;
    }

    public InfoOnClient() {
    }


    public String getNickName() {
        return nickName;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InfoOnClient that = (InfoOnClient) o;
        return nickName.equals(that.nickName);
    }

    @Override
    public int hashCode() {
        if (nickName != null){
            return nickName.hashCode();
        }else {
            return 0;
        }

    }
}
