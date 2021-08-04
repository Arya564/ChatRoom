/* EE422C Assignment #4 submission by
 * Arya Amin
 * aa82356
 */

package assignment_4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

//thread allows for the client to keep reading in messages
public class ChatReader implements Runnable{

    private Socket server;
    private BufferedReader in;  //reads in data from the server

    public ChatReader(Socket server) throws IOException {
        this.server = server;
        in = new BufferedReader(new InputStreamReader(server.getInputStream()));    //gets input stream for the client
    }

    //handling inputs from the server
    //waits for server to say something and then displays to the user

    /**
     * Continuously reads in messages from the server
     */
    @Override
    public void run() {

        try {
            String serverResponse;

            while (true) {

                //server to client
                serverResponse = in.readLine(); //error here fix later, when exit is typed

                //MAKE SURE THIS IS OKAY
                //break out of the loop
//                if(serverResponse == null){
//                    System.out.println("chat reader exiting");
//                    break;
//                }

                System.out.println(serverResponse);
            }


        } catch (SocketException ex) {
            try {
                this.server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                this.server.close();
                in.close();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
