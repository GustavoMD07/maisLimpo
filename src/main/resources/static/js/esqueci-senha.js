document.getElementById('form-esqueci-senha').addEventListener('submit', async function(event) {
    event.preventDefault();

    const email = document.getElementById('email').value;
    const mensagemDiv = document.getElementById('mensagem');
    const botao = this.querySelector('button');

    botao.disabled = true;
    botao.textContent = 'Enviando...';

    try {
        const response = await fetch('/usuarios/esqueci-senha', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email: email })
        });
        const textoResposta = await response.text();

        // Mostra a mensagem de sucesso que vem do backend
        mensagemDiv.textContent = textoResposta;
        mensagemDiv.style.color = 'lightgreen';
        document.getElementById('email').disabled = true;

    } catch (error) {
        console.error('Erro na requisição:', error);
        mensagemDiv.textContent = 'Ocorreu um erro de rede. Tente novamente.';
        mensagemDiv.style.color = 'red';
        botao.disabled = false;
        botao.textContent = 'Enviar Link de Recuperação';
    }
});