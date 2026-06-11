const baseUrl = "http://localhost:8080/api/tasks";

document.addEventListener("DOMContentLoaded", () => {
    const token = localStorage.getItem("token");
    if (!token) {
        window.location.href = "/index.html";
        return;
    }
    carregarTasks();
});

function getAuthHeaders() {
    return {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${localStorage.getItem("token")}`
    };
}

function logout() {
    localStorage.removeItem("token");
    window.location.href = "/index.html";
}

function showMessage(msg) {
    const msgDiv = document.getElementById('msg-dashboard');
    msgDiv.textContent = msg;
    setTimeout(() => { msgDiv.textContent = ""; }, 3000);
}

async function carregarTasks() {
    try {
        const response = await fetch(baseUrl, {
            headers: getAuthHeaders()
        });

        if (response.status === 403) {
            logout();
            return;
        }

        const tasks = await response.json();
        const tbody = document.getElementById("taskTableBody");
        tbody.innerHTML = "";

        tasks.forEach(task => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${task.title}</td>
                <td>${task.description || ''}</td>
                <td><span class="status-tag status-${task.status}">${formatStatus(task.status)}</span></td>
                <td>
                    <button class="btn-primary btn-sm" onclick="editTask(${task.id}, '${task.title}', '${task.description || ''}', '${task.status}')">Editar</button>
                    <button class="btn-danger btn-sm" onclick="deleteTask(${task.id})">Excluir</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    } catch (error) {
        showMessage("Erro ao carregar tarefas.");
    }
}

function formatStatus(status) {
    if (status === 'PENDENTE') return 'Pendente';
    if (status === 'EM_ANDAMENTO') return 'Em Andamento';
    if (status === 'CONCLUIDA') return 'Concluída';
    return status;
}

async function saveTask(event) {
    event.preventDefault();
    const id = document.getElementById("taskId").value;
    const title = document.getElementById("taskTitle").value;
    const description = document.getElementById("taskDesc").value;
    const status = document.getElementById("taskStatus").value;

    const method = id ? "PUT" : "POST";
    const url = id ? `${baseUrl}/${id}` : baseUrl;

    try {
        const response = await fetch(url, {
            method: method,
            headers: getAuthHeaders(),
            body: JSON.stringify({ title, description, status })
        });

        if (response.ok) {
            clearForm();
            carregarTasks();
        } else {
            showMessage("Erro ao salvar tarefa.");
        }
    } catch (error) {
        showMessage("Erro ao conectar com o servidor.");
    }
}

function editTask(id, title, description, status) {
    document.getElementById("taskId").value = id;
    document.getElementById("taskTitle").value = title;
    document.getElementById("taskDesc").value = description;
    document.getElementById("taskStatus").value = status;
    
    document.getElementById("btnSaveTask").textContent = "Atualizar Tarefa";
    document.getElementById("btnCancelEdit").style.display = "inline-block";
}

function clearForm() {
    document.getElementById("taskForm").reset();
    document.getElementById("taskId").value = "";
    document.getElementById("btnSaveTask").textContent = "Salvar Tarefa";
    document.getElementById("btnCancelEdit").style.display = "none";
}

async function deleteTask(id) {
    if (!confirm("Tem certeza que deseja excluir esta tarefa?")) return;

    try {
        const response = await fetch(`${baseUrl}/${id}`, {
            method: "DELETE",
            headers: getAuthHeaders()
        });

        if (response.ok) {
            carregarTasks();
        } else {
            showMessage("Erro ao excluir tarefa.");
        }
    } catch (error) {
        showMessage("Erro ao conectar com o servidor.");
    }
}
