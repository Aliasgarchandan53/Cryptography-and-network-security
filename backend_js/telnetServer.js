import net from "net"

const server = net.createServer((socket) => {
  console.log("Client connected");

  socket.write("Welcome to the Telnet Server!\n");

  socket.on("data", (data) => {
    console.log("Received:", data.toString());
    socket.write("Echo: " + data);
  });

  socket.on("end", () => {
    console.log("Client disconnected");
  });
});

server.listen(2323, () => {
  console.log("Telnet Server running on port 2323...");
});
