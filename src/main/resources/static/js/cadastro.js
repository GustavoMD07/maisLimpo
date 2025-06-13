// Espera o HTML carregar completamente
document.addEventListener('DOMContentLoaded', () => {
    
    // Pega os elementos do formulário
    const form = document.querySelector('.form-cadastro');
    const emailInput = document.getElementById('email');
    const senhaInput = document.getElementById('senha');
    const confirmaSenhaInput = document.getElementById('confirmaSenha');
    const tipoUsuarioSelect = document.getElementById('tipoUsuario');
    
    // Cria um lugar para mostrar mensagens de erro
    const messageDiv = document.createElement('div');
    messageDiv.id = 'error-message';
    messageDiv.className = 'mensagem-erro';
    form.insertBefore(messageDiv, form.querySelector('.login')); // Insere a div de erro antes do botão

    form.addEventListener('submit', async (event) => {
        event.preventDefault(); // Impede o envio padrão do formulário
        messageDiv.textContent = ''; // Limpa erros antigos

        // --- VALIDAÇÕES DO FRONTEND ---
        const email = emailInput.value;
        const senha = senhaInput.value;
        const confirmaSenha = confirmaSenhaInput.value;
        
        if (senha !== confirmaSenha) {
            messageDiv.textContent = 'As senhas não coincidem!';
            return;
        }

        if (senha.length < 6) {
            messageDiv.textContent = 'A senha deve ter no mínimo 6 caracteres.';
            return;
        }

        if (!/[a-z]/.test(senha) || !/[A-Z]/.test(senha)) {
            messageDiv.textContent = 'A senha deve conter letras maiúsculas e minúsculas.';
            return;
        }

        // --- ENVIO PARA O BACKEND ---
        const btnCadastrar = form.querySelector('.login');
        btnCadastrar.disabled = true;
        btnCadastrar.textContent = 'Enviando...';

        try {
            const dadosParaEnviar = {
                email: email,
                senha: senha,
                tipoUsuario: tipoUsuarioSelect.value
            };

            const response = await fetch('http://localhost:8080/usuario/cadastro', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(dadosParaEnviar)
            });

            const responseBody = await response.text();

            if (response.ok) {
                // SUCESSO! A MÁGICA DO REDIRECIONAMENTO ACONTECE AQUI!
                window.location.href = 'confirmacao-pendente.html';
            } else {
                // Mostra o erro que veio do backend (ex: email já existe)
                const errorMessages = Object.values(errorData).join(" ");
                messageDiv.textContent = errorMessages;
            }

        } catch (error) {
            console.error('Erro de conexão:', error);
            messageDiv.textContent = 'Não foi possível conectar ao servidor. Tente mais tarde.';
        } finally {
            // Reabilita o botão aconteça o que acontecer
            btnCadastrar.disabled = false;
            btnCadastrar.textContent = 'Cadastrar';
        }
    });
});