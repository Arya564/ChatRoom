/* EE422C Assignment #4 submission by
 * Arya Amin
 * aa82356
 */

package assignment_4;
import java.io.*;
import java.net.*;

public class ClientMain {

    private static int count = 0;

    /**
     * Creates a client socket on PORT 6666 and keeps reading and writing messages until exit is called
     * @param args
     */
    public static void main(String[] args) {
        try{
            Socket s = new Socket("localhost",6666); //connect to hostname and port number
            //binding the socket to localhost means that I will only be able to obtain data from my own system

            String message;

            //INITIALIZE ALL OF THE INPUT/OUTPUT STREAMS
            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in)); //gets input from the client
            PrintWriter dataOut = new PrintWriter(s.getOutputStream());  //sends message to SERVER INPUT STREAM


            //MAKING IT A MULTI-THREADED CLIENT
            ChatReader serverReader = new ChatReader(s);
            Thread reader = new Thread(serverReader);
            reader.start(); //kicks off thread so that client can keep reading data from the server

            while(true) {

                //FOR SENDING THE NAME OF CLIENT TO THE SERVER
                if (count == 0) {
                    System.out.println("Enter Desired Client Name: ");
                    message = keyboard.readLine();  //gets the name of the client
                    dataOut.println(message);   //putting message on client OUT STREAM
                    dataOut.flush(); //send this to the server
                    count++;
                    continue;
                }

                message = keyboard.readLine();

                //CLIENT MAIN EXITS AND SOCKETS CLOSE, THREAD TERMINATES
                if(message.equals("exit")){
                    break;
                }

                //client to server, puts this on the server socket input stream
                dataOut.println(message);
                dataOut.flush();
            }

            //close sockets when done with them
            dataOut.close();
            s.close();

        }catch(Exception e){
            System.out.println(e);
        }

    }
}
