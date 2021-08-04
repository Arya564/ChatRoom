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
import java.nio.Buffer;
import java.util.StringTokenizer;

//helps with the multi-threading of multiple clients
public class ClientHandler implements Runnable{
    private Socket client;  //Socket for the specific client
    private String name = null;    //for the name of the client
    private BufferedReader in; //input stream for the client thread
    private PrintWriter out;    //output stream for the client thread

    //CONSTRUCTOR CONTAINS CLIENT SOCKET AND NAME
    public ClientHandler(Socket client) throws IOException {
        this.client = client;
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        out = new PrintWriter(client.getOutputStream(), true);
    }

    //HELPER METHODS FOR THE THREAD

    /**
     * Takes in the message from the client and sends it to another client if client is VALID
     * @param request which is the message sent by the client
     */
    public void privateChat(String request){
        int flag = 0;   //if flag is zero say that
        StringTokenizer stringTokenizer = new StringTokenizer(request, "@");
        String message = stringTokenizer.nextToken();   //gets the message to send
        String recipient = stringTokenizer.nextToken(); //gets the recipient of private message

        //search for specific client to send message to
        if(!recipient.equals(this.name)) {
            for (ClientHandler myClient : ServerMain.clientList) {
                if (myClient.name.equals(recipient)) {
                    myClient.out.println(this.name + ": " + message); //write something on other clients output stream
                    myClient.out.flush();
                    out.println("Private message sent to " + recipient);    //confirmation that private message went through
                    flag = 1;
                    break;
                }
            }
        }

        if(recipient.equals(this.name)){
            flag = 1;
            out.println("Error, cannot message yourself!");
        }

        if(flag == 0){
            out.println("User not found, please type in a correct name to chat with!");
        }
    }

    /**
     * Removes person from the client list and notifies the other clients that he/she has left the chat room
     */
    public void exitChatRoom(){
        for(ClientHandler myClient : ServerMain.clientList) {
            if (!myClient.name.equals(this.name)) {
                myClient.out.println(this.name + " has left the chatroom");   //lets the other clients know when he/she has LEFT the chat
            }
        }

        for(ClientHandler myClient : ServerMain.clientList) {
            if(myClient.name.equals(this.name)) {
                ServerMain.clientList.remove(myClient);
                break;
            }
        }
    }

    /**
     * Main implementation of the thread that takes in client input and notifies other clients
     */
    @Override
    public void run() {

        try{
            int count = 0;
            String request;

            while (true){

                request = in.readLine();    //read in message from the client

                //EXITING THE CHATROOM, this should close because the client has exited and request will become NULL
                if((request == null)){

                    exitChatRoom();
                    System.out.println(this.name + " has left the chatroom");
                    this.client.close();    //JUST CHANGED THIS maybe comment out idk why this works tbh
                    break;
                }

                //GETS THE NAME OF THE CLIENT AND TELLS THE SERVER THAT THE CLIENT HAS JOINED THE CHATROOM
                //WHEN THE CLIENT FIRST ENTERS THE CHAT ROOM
                else if(count == 0){
                    count++;

                    this.name = request;

                    request = "Welcome to the chat room " + this.name + ". To leave, enter <exit> in a new line.";
                    out.println(request);   //sends this message to the client
                    System.out.println(this.name + " has joined the chatroom");


                    //TESTING TESTING
                    ClientHandler clientThread = new ClientHandler(this.client);    //make it have the exact same socket
                    clientThread.name = this.name;
                    ServerMain.clientList.add(clientThread);   //adds each client to the list


                    String output = "";  //outputs people to chat with
                    //do if list is greater than 1, say a new client has joined the chat room
                    if(ServerMain.clientList.size() > 1) {
                        for(ClientHandler myClient : ServerMain.clientList) {
                            if ((!myClient.name.equals(this.name))){
                                output = output + myClient.name + " ";    //TESTING TESTING PURPOSES ONLY
                                myClient.out.println("A new user " + this.name + " has joined the chat room");   //lets the other clients know when another client has joined the chat
                            }
                        }
                        out.println("People in chatroom: " + output);
                    }

                    else{
                        out.println("No one in chatroom");  //only needed for very first person in the chat room
                    }

                    continue;
                }

                //IF MESSAGE DOES NOT CONTAIN @, TYPE INVALID MESSAGE, check if exit still works
                else if((request != null) && (!request.contains("@"))){
                    out.println("Invalid Command!, please type <message> @Person");
                }

                else{
                    if(request != null) {
                        privateChat(request);
                    }
                }
            }


        //END OF TRY BLOCK
        } catch (IOException e){
            e.printStackTrace();
        }


//        finally {
//            out.close();
//            try{
//                in.close();
//            } catch (IOException e){
//                e.printStackTrace();
//            }
//        }

    }
}

