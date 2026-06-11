const baseUrl = "http://localhost:8080/api/auth";

function switchTab(tab) {
    document.getElementById('tab-login').classList.remove('active');
    document.getElementById('tab-register').classList.remove('active');
    
    document.getElementById('form-login').style.display = 'none';
    document.getElementById('form-register').style.display = 'none';

    document.getElementById(`tab-${tab}`).classList.add('active');
    document.getElementById(`form-${tab}`).style.display = 'block';
}

async function handleLogin(event) {
    event.preventDefault();
    const msgDiv = document.getElementById('login-msg');
    msgDiv.textContent = "";

    const login = document.getElementById('login-user').value;
    const senha = document.getElementById('login-pass').value;

    try {
        const response = await fetch(`${baseUrl}/login`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ login, senha })
        });

        if (response.ok) {
            const data = await response.json();
            localStorage.setItem("token", data.token);
            window.location.href = "/dashboard.html";
        } else {
            msgDiv.textContent = "Credenciais inválidas.";
        }
    } catch (error) {
        msgDiv.textContent = "Erro ao conectar com o servidor.";
    }
}

async function handleRegister(event) {
    event.preventDefault();
    const msgDiv = document.getElementById('reg-msg');
    msgDiv.textContent = "";

    const login = document.getElementById('reg-user').value;
    const senha = document.getElementById('reg-pass').value;

    try {
        const response = await fetch(`${baseUrl}/registrar`, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ login, senha, funcao: "USUARIO" })
        });

        if (response.ok) {
            alert("Conta criada com sucesso! Faça login.");
            switchTab('login');
            document.getElementById('form-register').reset();
        } else {
            msgDiv.textContent = "Usuário já existe ou dados inválidos.";
        }
    } catch (error) {
        msgDiv.textContent = "Erro ao conectar com o servidor.";
    }
}
