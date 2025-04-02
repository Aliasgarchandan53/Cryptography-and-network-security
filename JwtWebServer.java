import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtWebServer {
    private static final SecretKey SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("JWT Web Server started on port 8080");
            while (true) {
                try (Socket socket = serverSocket.accept();
                     BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                     PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)) {

                    String request = reader.readLine();
                    if (request.startsWith("LOGIN")) {
                        String[] parts = request.split(" ");
                        if (parts.length == 3 && "user".equals(parts[1]) && "password".equals(parts[2])) {
                            String token = Jwts.builder()
                                    .setSubject(parts[1])
                                    .setIssuedAt(new Date())
                                    .setExpiration(new Date(System.currentTimeMillis() + 600000))
                                    .signWith(SECRET_KEY)
                                    .compact();
                            writer.println("TOKEN " + token);
                        } else {
                            writer.println("ERROR Invalid credentials");
                        }
                    } else if (request.startsWith("VALIDATE")) {
                        String[] parts = request.split(" ");
                        if (parts.length == 2) {
                            try {
                                Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(parts[1]);
                                writer.println("VALID User: " + claims.getBody().getSubject());
                            } catch (JwtException e) {
                                writer.println("ERROR Invalid token");
                            }
                        }
                    } else {
                        writer.println("ERROR Unknown command");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
