import java.io.*;
import java.net.*;

public class ChatProgram {
    public static void main(String[] args) {
        try {
            ServerSocket servSocket = new ServerSocket(0);
            int portNo = servSocket.getLocalPort();
            System.out.println("Port number: " + portNo);
            Thread writeThread = new Thread(new WriteThread(portNo));
            writeThread.start();
            Socket socket = servSocket.accept();
            Thread readThread = new Thread(new ReadThread(socket, writeThread));
            readThread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class WriteThread implements Runnable {
    private int portNo;
    private Socket socket;

    public WriteThread(int portNo) {
        this.portNo = portNo;
    }

    public void run() {
        try {
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Please enter your name:");
            String name = inFromUser.readLine();
            System.out.println("Please enter the port number you want to connect to: ");
            int targetPort = Integer.parseInt(inFromUser.readLine());
            socket = new Socket("localhost", targetPort);
            System.out.println("Connected to the port " + targetPort);

            DataOutputStream outToServer = new DataOutputStream(socket.getOutputStream());
            outToServer.writeUTF(name);

            while (true) {
                String message = inFromUser.readLine();
                outToServer.writeUTF(message);

                if (message.equals("exit")) {
                    break;
                } else if (message.startsWith("transfer")) {
                    String filename = message.substring(9);
                    File file = new File(filename);
                    if (!file.exists()) {
                        System.out.println("File does not exist.");

                    } else {
                        System.out.println("Sending file: " + filename);
                        outToServer.writeUTF(message);
                        sendFile(filename, outToServer);
                    }
                }

            }
            socket.close();
        } catch (SocketException e) {
            System.err.println("Connection lost.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void sendFile(String filename, DataOutputStream out) throws IOException {
        File file = new File(filename);
        byte[] buffer = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream bis = new BufferedInputStream(fis);
        bis.read(buffer, 0, buffer.length);
        out.write(buffer, 0, buffer.length);
        out.flush();
        System.out.println("File sent.");
        fis.close();
        bis.close();
    }
}

class ReadThread implements Runnable {
    private Socket socket;
    private Thread writingThread;

    public ReadThread(Socket socket, Thread writingThread) {
        this.socket = socket;
        this.writingThread = writingThread;
    }
    public void run() {
        try {
            InputStream inFromServer = socket.getInputStream();
            DataInputStream in = new DataInputStream(inFromServer);
            OutputStream outToServer = socket.getOutputStream();
            DataOutputStream out = new DataOutputStream(outToServer);

            String name = in.readUTF();
            System.out.println("Connected to " + name);
            while (true) {
                String message = in.readUTF();
                if (message.equals("exit")) {
                    break;
                } else if (message.startsWith("transfer")) {
                    String filename = message.substring(9);
                        System.out.println(name + " is sending the file: " + filename);
                        receiveFile(filename, in);
                    } else {
                        System.out.println(name + ": " + message);
                        System.out.println("..........Reply.........");
                    }
                }
                socket.close();
                writingThread.interrupt();
            } catch(EOFException e){
                    System.out.println();
            }
            catch (SocketException e) {
                System.err.println("Connection lost.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            private void receiveFile(String filename, DataInputStream in) throws IOException {
            String newFilename = "newFile" + filename;
             File file = new File(newFilename);
            FileOutputStream fos = new FileOutputStream(file);
            byte[] buffer = new byte[4096];
            int read = 0;
            long totalRead = 0;
            int remaining = buffer.length;        
            while ((read = in.read(buffer, 0, Math.min(buffer.length, remaining))) > 0) {
                totalRead += read;
                remaining -= read;
                fos.write(buffer, 0, read);
                if (remaining == 0) {
                    remaining = buffer.length;
                }
                if (totalRead >= new File(filename).length()) {
                    break;
                }
            }
            System.out.println("File received: " + newFilename);
            fos.close();
        }        
    }        