const { Server } = require("ssh2");
const fs = require("fs");

const server = new Server(
  {
    hostKeys: [fs.readFileSync("ssh_host_rsa_key")],
  },
  (client) => {
    console.log("Client connected");

    client.on("authentication", (ctx) => {
      if (ctx.method === "password" && ctx.username === "user" && ctx.password === "pass") {
        ctx.accept();
      } else {
        ctx.reject();
      }
    });

    client.on("session", (accept) => {
      const session = accept();
      session.on("shell", (acceptShell) => {
        const stream = acceptShell();
        stream.write("Welcome to Secure SSH Server\n");
        stream.on("data", (data) => {
          console.log("Received:", data.toString());
        });
      });
    });
  }
);

server.listen(2222, "127.0.0.1", () => {
  console.log("SSH Server running on port 2222...");
});
