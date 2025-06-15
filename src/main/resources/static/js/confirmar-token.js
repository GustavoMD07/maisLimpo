document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('token-form');
    const messageDiv = document.getElementById('message-div');
    const tokenInput = document.getElementById('token-input');
    const submitButton = form.querySelector('.botao-confirmar');

    const params = new URLSearchParams(window.location.search);
    const action = params.get('action');

    if (!action) {
        console.error("Ação não especificada na URL. (Ex: ?action=cadastro)");
        messageDiv.textContent = 'ERRO GRAVE: O parâmetro de ação não foi encontrado na URL.';
        tokenInput.disabled = true;
        submitButton.disabled = true;
        return;
    }

    form.addEventListener('submit', async (event) => {
        event.preventDefault();

        const token = tokenInput.value.trim();
        submitButton.disabled = true;
        submitButton.textContent = 'Validando...';
        messageDiv.textContent = ''; 

        try {
            if (action === 'cadastro') {
                await handleCadastroConfirmation(token, submitButton, messageDiv, tokenInput);
            } else if (action === 'reset') {
                await handleResetConfirmation(token, submitButton, messageDiv);
            } else {
                throw new Error(`Ação desconhecida: '${action}'`);
            }
        } catch (error) {
            console.error("Erro no processo de confirmação:", error);
            messageDiv.style.color = 'coral';
            messageDiv.textContent = error.message || 'Ocorreu um erro inesperado.';
            submitButton.disabled = false;
            submitButton.textContent = 'Confirmar';
        }
    });
});

async function handleCadastroConfirmation(token, submitButton, messageDiv, tokenInput) {
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
            tokenInput.disabled = true;
            submitButton.disabled = false; 
        } else {
            throw new Error(responseBody);
        }
    } catch (error) {
        throw error;
    } finally {
        if (submitButton.textContent === 'Validando...') {
            submitButton.disabled = false;
            submitButton.textContent = 'Confirmar';
        }
    }
}

async function handleResetConfirmation(token, submitButton, messageDiv) {
    try {
        const response = await fetch('http://localhost:8080/usuario/validar-token-senha', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ token: token })
        });

        const responseBody = await response.text();

        if (response.ok) {
            messageDiv.style.color = 'lightgreen';
            messageDiv.textContent = "Token válido! Redirecionando...";
            sessionStorage.setItem('resetToken', token);
            setTimeout(() => {
                window.location.href = 'redefinir-senha.html';
            }, 1500);
        } else {
            throw new Error(responseBody);
        }
    } catch (error) {
        throw error;
    }
}