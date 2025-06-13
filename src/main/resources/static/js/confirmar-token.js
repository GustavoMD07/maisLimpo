document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('token-form');
    const tokenInput = document.getElementById('token-input');
    const messageDiv = document.getElementById('message-div');
    const submitButton = form.querySelector('button');

    //pega o parâmetro 'action' da URL para saber o que fazer
    const params = new URLSearchParams(window.location.search);
    const action = params.get('action'); 

    form.addEventListener('submit', async (event) => {
        event.preventDefault();
        const token = tokenInput.value.trim();

        if (!token) {
            messageDiv.textContent = 'Por favor, insira o token.';
            return;
        }

        submitButton.disabled = true;
        submitButton.textContent = 'Validando...';

        // Decide qual caminho seguir com base na ação
        if (action === 'cadastro') {
            await handleCadastroConfirmation(token);
        } else if (action === 'reset') {
            await handleResetConfirmation(token);
        } else {
            messageDiv.textContent = 'ERRO: Ação desconhecida.';
            submitButton.disabled = false;
            submitButton.textContent = 'Confirmar';
        }
    });

    // --- Função para confirmar CADASTRO ---
    async function handleCadastroConfirmation(token) {
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
                submitButton.disabled = false;
                tokenInput.disabled = true;
            } else {
                messageDiv.style.color = 'coral';
                messageDiv.textContent = responseBody;
                submitButton.disabled = false;
                submitButton.textContent = 'Confirmar';
            }
        } catch (error) {
            //...
        }
    }

    // --- Função para validar token de RESET DE SENHA ---
    async function handleResetConfirmation(token) {
        try {
            const response = await fetch('http://localhost:8080/usuario/validar-token-senha', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ token: token })
            });

            if (response.ok) {
                sessionStorage.setItem('resetToken', token);
                window.location.href = 'redefinir-senha.html';
            } else {
                const errorText = await response.text();
                messageDiv.textContent = errorText;
                submitButton.disabled = false;
                submitButton.textContent = 'Confirmar';
            }
        } catch (error) {
            //...
        }
    }
});