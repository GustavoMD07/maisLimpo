document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('form-redefinir-senha');
    const mensagemDiv = document.getElementById('mensagem');
    const botao = form.querySelector('button');

    const token = sessionStorage.getItem('resetToken');

    if (!token) {
        mensagemDiv.textContent = "ERRO: Token não encontrado. Por favor, valide seu token novamente";
        mensagemDiv.style.color = 'red';
        form.style.display = 'none';
        return;
    }

    form.addEventListener('submit', async function(event) {
        event.preventDefault();

        const novaSenha = document.getElementById('nova-senha').value;
        const confirmarSenha = document.getElementById('confirmar-senha').value;

        if (novaSenha !== confirmarSenha) {
            mensagemDiv.textContent = 'As senhas não são iguais!';
            mensagemDiv.style.color = 'coral';
            return;
        }

        botao.disabled = true;
        botao.textContent = 'Salvando...';

        try {
            const response = await fetch(`http://localhost:8080/usuario/redefinir-senha?token=${token}`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ novaSenha: novaSenha })
            });

            const textoResposta = await response.text();

            if (response.ok) {
                mensagemDiv.textContent = textoResposta;
                mensagemDiv.style.color = 'lightgreen';
                botao.textContent = 'Ir para o Login';
                botao.onclick = () => { window.location.href = 'index.html'; };
                botao.disabled = false;
            } else {
                mensagemDiv.textContent = `Erro: ${textoResposta}`;
                mensagemDiv.style.color = 'coral';
                botao.disabled = false;
                botao.textContent = 'Salvar Nova Senha';
            }

        } catch (error) {
            console.error('Erro na requisição:', error);
            mensagemDiv.textContent = 'Ocorreu um erro de rede.';
            mensagemDiv.style.color = 'red';
            botao.disabled = false;
            botao.textContent = 'Salvar Nova Senha';
        }
    });
});