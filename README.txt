Name: Arya Amin
UT EID: aa82356
Date: 4/14/2021

***********************************
Instructions on how to run program:
***********************************

1) Unzip the file
2) DO javac assignment_4/*.java to compile all java files
3) Run java assignment_4/ServerMain to start the server, server then waits for clients to connect
4) Run java assignment_4/ClientMain to add more clients to the chatroom
5) OR for the jar files do java -jar ServerMain.jar
6) Then to add more clients, do java -jar ClientMain.jar

********************
PROGRAM DESCRIPTION:
********************

1) When the first client enters the chatroom a welcome message will be displayed that tells the user
how to exit and how many people are currently in the chatroom.

2) To send a private message to someone, type <message> @Person and a private message will be sent
to that specific person. If Person is not found or does not exist, an error message will be displayed and
you will be asked to type the message correctly.

3) Similarly, if a message that does not contain @ is typed, an error message will be displayed.
When exiting the chatroom, everyone still in the chatroom will be notified.

HAVE FUN!!!

********************
DESIGN SUMMARY:
********************

For the design, I have a Server class that keeps waiting for new clients to enter the chat room and when they enter,
a thread is kicked off for that client which handles all of the messages sent by that client.
On the Client side of things, my Client class also kicks off a thread that allows for the client to continuously
read in messages from the server. As a result, the client can read and write messages at the same time.
