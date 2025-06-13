
document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector("form");
    const mensagemSucesso = document.getElementById("mensagem-enviada");
    const nomeArquivoSpan = document.getElementById("nome-arquivo");

    form.addEventListener("submit", async (event) => {
        event.preventDefault(); // Impede o envio padrão do formulário, que recarrega a página

        const usuarioId = sessionStorage.getItem('usuarioId');

          if (!usuarioId) {
            mensagemSucesso.textContent = 'Erro: Usuário não identificado. Faça o login novamente.';
            mensagemSucesso.style.color = 'red';
            mensagemSucesso.style.display = 'block';
            return;
        }

        //pega os dados da tela, pra poder mandar pro backend tbm
        const descricao = document.getElementById("descricao").value;
        const nomePraia = document.getElementById("praia-nome").textContent;
        const btnEnviar = document.getElementById("btn-enviar");
        
        // Desabilita o botão para evitar cliques duplos
        btnEnviar.disabled = true;
        btnEnviar.textContent = 'Enviando...';

        try {
            // Monta o objeto para enviar ao backend
            const dadosParaEnviar = {
                textoDenuncia: descricao,
                nomePraia: nomePraia,
                usuarioId: parseInt(usuarioId)
            };

            // Faz a chamada para a nossa API REST
            const response = await fetch('http://localhost:8080/denuncias', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(dadosParaEnviar),
            });

            const textoResposta = await response.text();

            if (response.ok) {
                // Deu tudo certo!
                mensagemSucesso.textContent = textoResposta;
                mensagemSucesso.style.color = '#00695c';
                mensagemSucesso.style.display = 'block';
                form.reset(); // Limpa o formulário
                nomeArquivoSpan.textContent = "Nenhum arquivo selecionado";
            } else {
                // Ocorreu algum erro no backend
                mensagemSucesso.textContent = 'Erro: ' + textoResposta;
                mensagemSucesso.style.color = 'red';
                mensagemSucesso.style.display = 'block';
            }

        } catch (error) {
            // Erro de conexão, etc.
            console.error("Erro ao conectar com o servidor:", error);
            mensagemSucesso.textContent = 'Não foi possível conectar ao servidor. Tente mais tarde.';
            mensagemSucesso.style.color = 'red';
            mensagemSucesso.style.display = 'block';
        } finally {
            // Reabilita o botão depois que tudo acabou
            btnEnviar.disabled = false;
            btnEnviar.textContent = 'Enviar Denúncia';
        }
    });

    // Bônus: Lógica do input de arquivo que você já tinha
    const inputArquivo = document.getElementById("imagem");
    inputArquivo.addEventListener("change", function () {
        if (inputArquivo.files.length > 0) {
            nomeArquivoSpan.textContent = inputArquivo.files[0].name;
        } else {
            nomeArquivoSpan.textContent = "Nenhum arquivo selecionado";
        }
    });
});