document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('token-form');
    const tokenInput = document.getElementById('token-input');
    const messageDiv = document.getElementById('message-div');
    const submitButton = form.querySelector('button');

    form.addEventListener('submit', async (event) => {
        event.preventDefault();
        const token = tokenInput.value.trim();

        if (!token) {
            messageDiv.style.color = 'coral';
            messageDiv.textContent = 'Por favor, insira o token.';
            return;
        }

        submitButton.disabled = true;
        submitButton.textContent = 'Validando...';
        messageDiv.textContent = '';

        try {
            const response = await fetch('http://localhost:8080/usuario/confirmar-token', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                // Enviando o token como uma string simples no corpo da requisição
                body: JSON.stringify(token) 
            });

            const responseBody = await response.text();

            if (response.ok) {
                // SUCESSO!
                messageDiv.style.color = 'lightgreen';
                messageDiv.textContent = responseBody;
                
                // Transforma o botão de confirmar em um botão para ir pro login
                submitButton.textContent = 'Ir para o Login';
                submitButton.onclick = () => { window.location.href = 'index.html'; };
                submitButton.disabled = false;
                tokenInput.disabled = true;

            } else {
                // ERRO!
                messageDiv.style.color = 'coral';
                messageDiv.textContent = responseBody;
                submitButton.disabled = false;
                submitButton.textContent = 'Confirmar Cadastro';
            }

        } catch (error) {
            messageDiv.style.color = 'coral';
            messageDiv.textContent = 'Erro de conexão com o servidor.';
            submitButton.disabled = false;
            submitButton.textContent = 'Confirmar Cadastro';
        }
    });
});