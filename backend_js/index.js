import express from 'express'
import jwt from 'jsonwebtoken'
import bodyParser from 'body-parser';

const app = express();
const PORT = 3000;
const SECRET_KEY = 'your_secret_key';

app.use(bodyParser.json());

app.listen(PORT, () => {
    console.log(`Server running on http://localhost:${PORT}`);
});

// Login Endpoint
app.post('/login', (req, res) => {
    const { username, password } = req.body;
    if (username == 'user' && password == 'password') {
        const token = jwt.sign({ username }, SECRET_KEY, { expiresIn: '1h' });
        res.json({ token });
    } else {
        res.status(401).json({ error: 'Invalid credentials' });
    }
});

// Token Validation Endpoint
app.get('/validate', (req, res) => {
    const token = req.headers['authorization'];
    if (!token) return res.status(403).json({ error: 'No token provided' });
    
    jwt.verify(token, SECRET_KEY, (err, decoded) => {
        if (err) return res.status(401).json({ error: 'Invalid token' });
        res.json({ username: decoded.username, status: 'valid' });
    });
});
