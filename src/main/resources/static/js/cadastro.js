// Conteúdo completo e correto para o arquivo js/cadastro.js

document.addEventListener('DOMContentLoaded', () => {

    // Seleção de todos os elementos do formulário
    const form = document.querySelector('.form-cadastro');
    const emailInput = document.getElementById('email');
    const senhaInput = document.getElementById('senha');
    const confirmaSenhaInput = document.getElementById('confirmaSenha');
    const tipoUsuarioSelect = document.getElementById('tipoUsuario');
    const messageDiv = document.getElementById('messageDiv');
    const btnCadastrar = form.querySelector('.login');

    // Bloco para limpar a mensagem de erro assim que o usuário começa a digitar
    const camposParaMonitorar = [emailInput, senhaInput, confirmaSenhaInput];
    camposParaMonitorar.forEach(input => {
        input.addEventListener('input', () => {
            if (messageDiv.textContent !== '') {
                messageDiv.textContent = '';
            }
        });
    });

    // Evento principal que acontece ao clicar em "Cadastrar"
    form.addEventListener('submit', async (event) => {
        event.preventDefault(); // Impede o recarregamento da página

        messageDiv.textContent = ''; 

        const email = emailInput.value.trim();
        const senha = senhaInput.value.trim();
        const confirmaSenha = confirmaSenhaInput.value.trim();
        const tipoUsuario = tipoUsuarioSelect.value;

        // Validação rápida para as senhas
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
                // <<< AQUI ESTÁ A LÓGICA CORRETA E DEFINITIVA >>>
                // Se o backend retornou sucesso, redireciona para a página de confirmação pendente.
                window.location.href = 'confirmacao-pendente.html';
            } else {
                // Se o backend retornou um erro, exibe a mensagem de forma amigável
                const errorData = await response.json();
                const errorMessage = Object.values(errorData)[0];
                messageDiv.textContent = errorMessage;
            }

        } catch (error) {
            console.error("Erro de conexão:", error);
            messageDiv.textContent = 'ERRO: Não foi possível conectar ao servidor.';
        } finally {
            // No final, reabilita o botão
            btnCadastrar.disabled = false;
            btnCadastrar.textContent = 'Cadastrar';
        }
    });
});