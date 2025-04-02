import fs from 'fs'
import tls from 'tls'

const options = {
    key: fs.readFileSync("key.pem"),
    cert: fs.readFileSync("cert.pem"),
  };
  

const server = tls.createServer(options, (socket) => {
    console.log('Client connected');

    socket.on('data', (data) => {
        console.log(`Received from client: ${data.toString()}`);
        socket.write('Hello from Secure Server!');
    });

    socket.on('end', () => {
        console.log('Client disconnected');
    });
});

server.listen(8443, () => {
    console.log('TLS Server running on port 8443');
});
