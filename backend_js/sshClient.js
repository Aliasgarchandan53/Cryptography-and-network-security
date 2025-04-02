const { Client } = require("ssh2");

const conn = new Client();

conn
  .on("ready", () => {
    console.log("Connected to SSH Server");
    conn.shell((err, stream) => {
      if (err) throw err;

      stream.on("data", (data) => {
        console.log("SSH Response:", data.toString());
      });

      stream.write("Hello, SSH Server!\n");
    });
  })
  .connect({
    host: "127.0.0.1",
    port: 2222,
    username: "user",
    password: "pass",
  });
