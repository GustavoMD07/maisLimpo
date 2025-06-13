console.log("LOG 1: Script cadastro.js foi lido pelo navegador.");

document.addEventListener('DOMContentLoaded', () => {
    console.log("LOG 2: DOM totalmente carregado. O script vai começar a rodar.");

    const form = document.querySelector('.form-cadastro');
    if (!form) {
        console.error("ERRO CRÍTICO: Não encontrei o formulário com a classe '.form-cadastro'. Verifique o HTML.");
        return;
    }
    console.log("LOG 3: Formulário encontrado com sucesso.", form);

    const emailInput = document.getElementById('email');
    const senhaInput = document.getElementById('senha');
    const confirmaSenhaInput = document.getElementById('confirmaSenha');
    const tipoUsuarioSelect = document.getElementById('tipoUsuario');
    const messageDiv = document.getElementById('messageDiv'); // Pegando a div que criei no HTML
    const btnCadastrar = form.querySelector('.login-button');

    if (!emailInput || !senhaInput || !confirmaSenhaInput || !tipoUsuarioSelect || !messageDiv || !btnCadastrar) {
        console.error("ERRO CRÍTICO: Um ou mais elementos do formulário não foram encontrados. Verifique os IDs no HTML.");
        return;
    }
    console.log("LOG 4: Todos os elementos do formulário foram encontrados.");

    form.addEventListener('submit', async (event) => {
        console.log("LOG 5: Botão de CADASTRO clicado! O evento 'submit' foi disparado.");
        event.preventDefault();
        console.log("LOG 6: Ação padrão do formulário (recarregar a página) foi PREVENIDA.");
        
        messageDiv.textContent = '';
        messageDiv.style.color = '#D32F2F'; // Cor de erro padrão

        const email = emailInput.value.trim();
        const senha = senhaInput.value.trim();
        const confirmaSenha = confirmaSenhaInput.value.trim();
        
        console.log("LOG 7: Verificando senhas. Senha 1: '" + senha + "', Senha 2: '" + confirmaSenha + "'");

        if (senha !== confirmaSenha) {
            console.log("LOG 8: As senhas NÃO coincidem. Exibindo erro e parando.");
            messageDiv.textContent = 'As senhas não coincidem!';
            return;
        }

        console.log("LOG 9: Senhas OK. Enviando requisição para o backend...");
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
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(dadosParaEnviar)
            });

            const responseBodyAsText = await response.text();
            console.log("LOG 10: Resposta recebida do backend. Status:", response.status, "Corpo:", responseBodyAsText);

            if (response.ok) {
                console.log("LOG 11: Sucesso! Redirecionando para a confirmação.");
                window.location.href = 'confirmacao-pendente.html';
            } else {
                console.log("LOG 12: Erro retornado pelo backend. Exibindo mensagem.");
                messageDiv.textContent = responseBodyAsText;
            }

        } catch (error) {
            console.error("LOG 13: ERRO DE CONEXÃO! O 'fetch' falhou.", error);
            messageDiv.textContent = 'ERRO DE REDE: Não foi possível conectar ao servidor. Verifique se o backend está rodando.';
        } finally {
            console.log("LOG 14: Bloco 'finally' executado. Reabilitando o botão.");
            btnCadastrar.disabled = false;
            btnCadastrar.textContent = 'Cadastrar';
        }
    });
});