document.getElementById('form-esqueci-senha').addEventListener('submit', async function(event) {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const mensagemDiv = document.getElementById('mensagem');
    const form = this; // O próprio formulário
    const botaoSubmit = form.querySelector('button');

    botaoSubmit.disabled = true;
    botaoSubmit.textContent = 'Enviando...';

    try {
        const response = await fetch('http://localhost:8080/usuario/esqueci-senha', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email: email })
        });
        const textoResposta = await response.text();

        mensagemDiv.textContent = textoResposta;
        mensagemDiv.style.color = 'lightgreen';
        document.getElementById('email').disabled = true;
        botaoSubmit.textContent = 'Link Enviado!';
        
        // ===== MÁGICA AQUI: CRIA E MOSTRA O BOTÃO NOVO =====
        if (!document.getElementById('btn-ir-para-token')) {
            const btnIrParaToken = document.createElement('a');
            btnIrParaToken.id = 'btn-ir-para-token';
            btnIrParaToken.href = 'confirmar-token.html?action=reset'; // Avisando que a ação é de reset!
            btnIrParaToken.className = 'botao-voltar-login';
            btnIrParaToken.textContent = 'Já Tenho o Token';
            btnIrParaToken.style.display = 'block';
            btnIrParaToken.style.marginTop = '20px';
            form.parentElement.appendChild(btnIrParaToken); // Adiciona o botão ao container
        }

    } catch (error) {
        console.error('Erro na requisição:', error);
        mensagemDiv.textContent = 'Ocorreu um erro de rede. Tente novamente.';
        mensagemDiv.style.color = 'red';
        botaoSubmit.disabled = false;
        botaoSubmit.textContent = 'Enviar Link de Recuperação';
    }
});