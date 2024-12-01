import java.io.*;
import java.net.*;

public class ServerThread {

    public static void main(String[] args) throws IOException {
        ServerSocket handshake = new ServerSocket(6000);
        System.out.println("Server started at port " + handshake.getLocalPort());
        System.out.println("Waiting for clients...\n");

        int clientCount = 0;

        while (clientCount < 5) {
            Socket com_socket = handshake.accept();
            System.out.println("A new client is connected: " + com_socket);

            // Create input and output streams for the client
            DataOutputStream dos = new DataOutputStream(com_socket.getOutputStream());
            DataInputStream dis = new DataInputStream(com_socket.getInputStream());

            System.out.println("Assigning a new thread for this client");

            // Create and start a new thread for the client
            ClientHandler new_tunnel = new ClientHandler(com_socket, dis, dos);
            new_tunnel.start();

            clientCount++;
            System.out.println("Clients served: " + clientCount + " /5");
        }

        System.out.println("Server has reached its client limit. Shutting down.");
        handshake.close();
    }
}
