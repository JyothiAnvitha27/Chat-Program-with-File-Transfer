PROJECT 3 README


Jyothi Anvitha Ambati (UFID 83307174) | Divyasri Naraharisetti (UFID 71955711)

PROJECT DESCRIPTION:

The project requires us to write a chat program that creates two threads - a main thread used as the reading thread, and a writing thread. The program creates a new thread called the writing thread, which takes a port number from the keyboard and connects to it. After a connection is established, the thread reads messages from the keyboard and writes them to the connection. If the message is "transfer filename", the file is transmitted. The program also creates a ServerSocket, listens for new connections, and attempts to read messages from the connection socket. The demo involves transferring a file between two users.


STEPS OF IMPLEMENTATION:

1. To identify the relevant packages needed for the program, it first scans the import statements.
2. A main function is specified for the class "ChatProgram". The main method runs the program when it is executed.
3. A new ServerSocket object is created in the main procedure with an open port. The port number is thereafter printed to the console.
4. The port number is given together with the creation of a fresh writeThread object. A new thread is created and the writeThread is started.
5. The application waits for a client to establish a connection to the ServerSocket.
6. When a client connects, a fresh readThread object is constructed and sent to the client socket and writeThread. A new thread is opened before the readThread.
7. The user is prompted to input their name and the port they wish to connect to by the writeThread. A new Socket object is generated and linked to the given port as soon as the user enters this information.
8. The writeThread then uses the output stream of the socket to deliver the user's name to the client.
9. The writeThread begins a loop and requests a message from the user. The output stream of the socket is then used to transmit the message to the client. The loop is broken and the socket is closed if the user types "exit1". Input "transfer" from the user causes the writeThread to ask the client to make a request for a file.
10. The write thread attempts to send the file to the client if a file transfer request is received by reading the filename from the message.
11. A message stating that a connection has been made is printed to the console by readThread after reading the client's name from the socket's input stream.
12. The read thread starts reading messages from the input stream of the socket and then goes into a loop. If "exit" is sent as the message, the socket is closed and the loop is broken. In the case of a "transfer" message, the read thread receives the filename from the message and tries to download the file from the client.
13. If a file transfer request is received, the ReadingThread reads the message to get the file name and creates a new file with a different name. The ReadingThread then retrieves file data from the socket's input stream and writes it to the newly formed file.
14. When the loop is finished, the ReadingThread signals the WritingThread to halt and closes the connection.


STEPS OF EXECUTION:

1. Open a command prompt or terminal window and navigate to the directory where you saved the PROJECT file.
2. In the terminal first we have to compile the Chat Program files using javac ChatProgram.java
3. Once the file is compiled successfully, start the Program by typing java ChatProgram in the command prompt/terminal window and pressing enter and this is the client Alice and also in another command prompt we get another client Bob.
4. You will see a message indicating the port number assigned to the server.
5. Enter your name and the port number of the user you want to connect to, and we want two clients Alice and Bob then the connection between Alice and Bob is established after entering the port numbers of each other's port numbers.
6. If the connection is successful, you can start sending and receiving messages.
7. To transfer a file, type "transfer <filename>" where "filename" is the name of the file you want to transfer.
8. The file will be sent or received and saved in the current directory with a new name.
9. To exit the program, type "exit".
10. Close the connections and free up any resources after testing.

