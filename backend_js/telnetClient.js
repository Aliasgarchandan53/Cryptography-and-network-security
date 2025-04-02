import { Telnet } from "telnet-client";
async function run() {
  let connection = new Telnet();
  let params = {
    host: "127.0.0.1",
    port: 2323,
    shellPrompt: "/ # ",
    timeout: 60000,
  };

  try {
    await connection.connect(params);
    console.log("Connected to Telnet server");

    let response = await connection.send("Hello, Telnet Server!");
    console.log("Server Response:", response);
  } catch (err) {
    console.error("Error:", err.message);
  } finally {
    connection.end();
  }
}

run();
