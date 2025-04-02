import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.security.KeyStore;

public class SSLServer {
    public static void main(String[] args) throws Exception {
        int port = 8443;

        KeyStore keyStore = KeyStore.getInstance("JKS");
        try (FileInputStream keyStoreFile = new FileInputStream("server.keystore")) {
            keyStore.load(keyStoreFile, "password".toCharArray());
        }

        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
        keyManagerFactory.init(keyStore, "password".toCharArray());

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), null, null);

        SSLServerSocketFactory serverSocketFactory = sslContext.getServerSocketFactory();
        try (SSLServerSocket serverSocket = (SSLServerSocket) serverSocketFactory.createServerSocket(port)) {
            System.out.println("SSL Server is listening on port " + port);
            try (SSLSocket socket = (SSLSocket) serverSocket.accept()) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String receivedText = reader.readLine();
                System.out.println("Received: " + receivedText);
            }
        }
    }
}
