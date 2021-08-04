/* EE422C Assignment #4 submission by
 * Arya Amin
 * aa82356
 */

package assignment_4;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class ServerMain {

    static List<ClientHandler> clientList = new ArrayList<>();  //list that keeps track of all of the clients

    /**
     * Creates a server socket on PORT 6666 and handles messages sent by clients
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException{

        try{
            ServerSocket ss = new ServerSocket(6666);   //SERVER SOCKET CREATED
            Socket s;

            //LOOP TO CONTINUE GETTING CLIENTS
            while(true) {

                s = ss.accept();//establishes connection
                ClientHandler clientThread = new ClientHandler(s);  //passes in socket and name to client handler

                Thread t = new Thread(clientThread);
                t.start();  //kicks off thread
            }

            //maybe do this
            //close the sockets
            //ss.close();

        }catch(Exception e){System.out.println(e);}

    }
}
