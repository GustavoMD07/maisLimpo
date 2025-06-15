document.addEventListener('DOMContentLoaded', () => {

    const form = document.querySelector('.form-cadastro');
    const emailInput = document.getElementById('email');
    const senhaInput = document.getElementById('senha');
    const confirmaSenhaInput = document.getElementById('confirmaSenha');
    const tipoUsuarioSelect = document.getElementById('tipoUsuario');
    const messageDiv = document.getElementById('messageDiv');
    const btnCadastrar = form.querySelector('.login');

    const camposParaMonitorar = [emailInput, senhaInput, confirmaSenhaInput];
    camposParaMonitorar.forEach(input => {
        input.addEventListener('input', () => {
            if (messageDiv.textContent !== '') {
                messageDiv.textContent = '';
            }
        });
    });

    form.addEventListener('submit', async (event) => {
        event.preventDefault(); 

        messageDiv.textContent = ''; 

        const email = emailInput.value.trim();
        const senha = senhaInput.value.trim();
        const confirmaSenha = confirmaSenhaInput.value.trim();
        const tipoUsuario = tipoUsuarioSelect.value;

        if (senha !== confirmaSenha) {
            messageDiv.textContent = 'As senhas não coincidem!';
            return;
        }

        btnCadastrar.disabled = true;
        btnCadastrar.textContent = 'Enviando...';

        try {
            const dadosParaEnviar = {
                email: email,
                senha: senha,
                tipoUsuario: tipoUsuario
            };

            const response = await fetch('http://localhost:8080/usuario/cadastro', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(dadosParaEnviar)
            });

            if (response.ok) {
                window.location.href = 'confirmacao-pendente.html';
            } else {
                const errorData = await response.json();
                const errorMessage = Object.values(errorData)[0];
                messageDiv.textContent = errorMessage;
            }

        } catch (error) {
            console.error("Erro de conexão:", error);
            messageDiv.textContent = 'ERRO: Não foi possível conectar ao servidor.';
        } finally {
            btnCadastrar.disabled = false;
            btnCadastrar.textContent = 'Cadastrar';
        }
    });
});