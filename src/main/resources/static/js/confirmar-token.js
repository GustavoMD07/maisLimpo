// VERSÃO COMPLETA E CORRIGIDA para confirmar-token.js

document.addEventListener('DOMContentLoaded', () => {
    console.log("LOG 1: Script de confirmação INICIADO.");

    const form = document.getElementById('token-form');
    const messageDiv = document.getElementById('message-div');
    
    const urlCompleta = window.location.href;
    console.log("LOG 2: URL completa da página:", urlCompleta);

    const params = new URLSearchParams(window.location.search);
    const action = params.get('action');

    console.log("LOG 3: Valor da variável 'action' é:", action, "| Tipo da variável é:", typeof action);

    if (!action) {
        console.error("LOG 4: ERRO CRÍTICO! A variável 'action' está vazia.");
        messageDiv.textContent = 'ERRO GRAVE: O parâmetro de ação (?action=...) não foi encontrado na URL.';
        return; 
    }
    
    form.addEventListener('submit', async (event) => {
        event.preventDefault();
        console.log("LOG 5: Botão 'Confirmar' foi clicado.");
        console.log("LOG 6: Dentro do clique, o valor de 'action' que estou usando é:", action);

        const token = document.getElementById('token-input').value.trim();
        const submitButton = form.querySelector('.botao-confirmar');

        submitButton.disabled = true;
        submitButton.textContent = 'Validando...';
        messageDiv.textContent = ''; // Limpa mensagens antigas

        if (action === 'cadastro') {
            console.log("LOG 7: CONDIÇÃO VERDADEIRA -> action é 'cadastro'. Vou confirmar o cadastro.");
            await handleCadastroConfirmation(token, submitButton, messageDiv);
        } else if (action === 'reset') {
            console.log("LOG 8: CONDIÇÃO VERDADEIRA -> action é 'reset'. Vou resetar a senha.");
            // Agora chamando a função com a lógica implementada!
            await handleResetConfirmation(token, submitButton, messageDiv);
        } else {
            console.error("LOG 9: NENHUMA CONDIÇÃO FOI SATISFEITA. Ação desconhecida.");
            messageDiv.textContent = `ERRO: Ação desconhecida: '${action}'.`;
            submitButton.disabled = false;
            submitButton.textContent = 'Confirmar';
        }
    });
});

async function handleCadastroConfirmation(token, submitButton, messageDiv) {
    // ... (A lógica de confirmação de cadastro, que já estava ok, permanece a mesma)
    try {
        const response = await fetch('http://localhost:8080/usuario/confirmar-token', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(token) 
        });
        const responseBody = await response.text();
        if (response.ok) {
            messageDiv.style.color = '#00695c';
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
         if(submitButton.textContent === 'Validando...'){ // Garante que o texto volte ao normal em caso de erro
             submitButton.textContent = 'Confirmar';
         }
    }
}

// ==========================================================
// AQUI ESTÁ A LÓGICA QUE FALTAVA!
// ==========================================================
async function handleResetConfirmation(token, submitButton, messageDiv) {
    try {
        const response = await fetch('http://localhost:8080/usuario/validar-token-senha', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ token: token }) // O backend espera um JSON com a chave "token"
        });

        const responseBody = await response.text();

        if (response.ok) {
            // SUCESSO! O token é válido.
            messageDiv.style.color = 'lightgreen';
            messageDiv.textContent = "Token válido! Redirecionando para a criação da nova senha...";
            
            // Guardamos o token na sessão do navegador para a próxima página usar
            sessionStorage.setItem('resetToken', token);

            // Redireciona para a página de redefinir a senha
            setTimeout(() => {
                window.location.href = 'redefinir-senha.html';
            }, 1500); // Espera 1.5s pra dar tempo do usuário ler a mensagem

        } else {
            // ERRO! O token é inválido ou expirou.
            messageDiv.style.color = 'coral';
            messageDiv.textContent = `Erro: ${responseBody}`;
            submitButton.disabled = false;
            submitButton.textContent = 'Confirmar';
        }

    } catch (error) {
        console.error("Erro de rede na validação do token de reset:", error);
        messageDiv.textContent = 'Erro de rede. Não foi possível conectar ao servidor.';
        messageDiv.disabled = false;
        submitButton.textContent = 'Confirmar';
    }
}