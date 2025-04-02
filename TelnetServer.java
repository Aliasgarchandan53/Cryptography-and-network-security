import java.io.*;
import java.net.*;
import java.util.Scanner;

public class TelnetServer {
    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(2323)) {
            System.out.println("Telnet Server started on port 2323...");
            Socket socket = serverSocket.accept();
            System.out.println("Client connected...");

            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            
            writer.println("Welcome to the Telnet Server. Type your message:");
            String receivedData;
            while ((receivedData = reader.readLine()) != null) {
                System.out.println("Received: " + receivedData);
                writer.println("Echo: " + receivedData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
