package ClientSide;

import ClientSide.MessageInterface;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author IDEA Developers
 */
public class connectClientJava {
    private static connectClientJava instance;
    private Socket socketConn;
    private InputStreamReader isr;
    private OutputStreamWriter osw;
    
    private clientPanel mi;
    
    public static connectClientJava getInstance(){
        if(instance == null){
            instance = new connectClientJava();
        }
        return instance;
    }
    
    public void connectToServer(clientPanel clientPanel) throws Exception{
        this.mi = clientPanel;
        System.out.println("Connection to server...");
        socketConn = new Socket("localhost", 3535);
        isr = new InputStreamReader(socketConn.getInputStream());
        osw = new OutputStreamWriter(socketConn.getOutputStream());
        System.out.println("Conneted to server");
        listenForMessages();
    }
    
    public void listenForMessages(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    try{
                        char[] charMessage = new char[1024];
                        if(isr.read(charMessage, 0, charMessage.length) != -1){
                            String message = new String(charMessage);
                            mi.onMessageReceived(message);
                            System.out.println(message);
                        }
                    }catch(Exception e){
                        System.err.println(e.getMessage());
                    }
                }
            }
        }).start();
    }
    
    public void sendMessage(String message)throws Exception{
        osw.write(message);
        osw.flush();
    }
}