// VERSÃO DE DEBUG "DEDO-DURO" PARA confirmar-token.js

document.addEventListener('DOMContentLoaded', () => {
    console.log("LOG 1: Script de confirmação INICIADO.");

    const form = document.getElementById('token-form');
    const messageDiv = document.getElementById('message-div');
    
    // Espionando a URL que o navegador realmente está vendo
    const urlCompleta = window.location.href;
    console.log("LOG 2: URL completa da página:", urlCompleta);

    const params = new URLSearchParams(window.location.search);
    const action = params.get('action');

    // A PROVA FINAL: O que a variável 'action' realmente contém?
    console.log("LOG 3: Valor da variável 'action' é:", action, "| Tipo da variável é:", typeof action);

    // Se 'action' for nula ou vazia, a gente já para aqui.
    if (!action) {
        console.error("LOG 4: ERRO CRÍTICO! A variável 'action' está vazia. Verifique o link na página anterior.");
        messageDiv.textContent = 'ERRO GRAVE: O parâmetro de ação (?action=...) não foi encontrado na URL.';
        return; 
    }
    
    form.addEventListener('submit', async (event) => {
        event.preventDefault();
        console.log("LOG 5: Botão 'Confirmar' foi clicado.");
        console.log("LOG 6: Dentro do clique, o valor de 'action' que estou usando é:", action);

        const token = document.getElementById('token-input').value.trim();
        const submitButton = form.querySelector('.botao-confirmar'); // Usando a classe correta

        submitButton.disabled = true;
        submitButton.textContent = 'Validando...';

        if (action === 'cadastro') {
            console.log("LOG 7: CONDIÇÃO VERDADEIRA -> action é 'cadastro'. Vou confirmar o cadastro.");
            await handleCadastroConfirmation(token, submitButton, messageDiv);
        } else if (action === 'reset') {
            console.log("LOG 8: CONDIÇÃO VERDADEIRA -> action é 'reset'. Vou resetar a senha.");
            await handleResetConfirmation(token, submitButton, messageDiv);
        } else {
            console.error("LOG 9: NENHUMA CONDIÇÃO FOI SATISFEITA. Ação desconhecida.");
            messageDiv.textContent = `ERRO: Ação desconhecida. A ação que eu encontrei foi '${action}'.`;
            submitButton.disabled = false;
            submitButton.textContent = 'Confirmar';
        }
    });
});

async function handleCadastroConfirmation(token, submitButton, messageDiv) {
    try {
        const response = await fetch('http://localhost:8080/usuario/confirmar-token', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(token) 
        });
        const responseBody = await response.text();
        if (response.ok) {
            messageDiv.style.color = 'lightgreen';
            messageDiv.textContent = responseBody;
            submitButton.textContent = 'Ir para o Login';
            submitButton.onclick = () => { window.location.href = 'index.html'; };
            document.getElementById('token-input').disabled = true;
        } else {
            messageDiv.style.color = 'coral';
            messageDiv.textContent = responseBody;
        }
    } catch (error) {
        messageDiv.textContent = 'Erro de rede ao confirmar token.';
    } finally {
         submitButton.disabled = false;
    }
}

async function handleResetConfirmation(token, submitButton, messageDiv) {
    // Lógica do reset (não é o foco agora)
}