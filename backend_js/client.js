import tls from 'tls'
import fs from 'fs'

const options = {
    ca: fs.readFileSync('cert.pem') // Trust the server's certificate
};

const client = tls.connect(8443, options, () => {
    console.log('Connected to secure server');
    client.write('Hello from Secure Client!');
});

client.on('data', (data) => {
    console.log(`Received from server: ${data.toString()}`);
    client.end();
});

client.on('end', () => {
    console.log('Disconnected from server');
});
