document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector("form");
    const mensagemSucesso = document.getElementById("mensagem-enviada");
    const nomeArquivoSpan = document.getElementById("nome-arquivo");

    form.addEventListener("submit", async (event) => {
        event.preventDefault(); 

        const usuarioId = sessionStorage.getItem('usuarioId');

          if (!usuarioId) {
            mensagemSucesso.textContent = 'Erro: Usuário não identificado. Faça o login novamente.';
            mensagemSucesso.style.color = 'red';
            mensagemSucesso.style.display = 'block';
            return;
        }
        const descricao = document.getElementById("descricao").value;
        const nomePraia = document.getElementById("praia-nome").textContent;
        const btnEnviar = document.getElementById("btn-enviar");
        
        btnEnviar.disabled = true;
        btnEnviar.textContent = 'Enviando...';

        try {
            const dadosParaEnviar = {
                textoDenuncia: descricao,
                nomePraia: nomePraia,
                usuarioId: parseInt(usuarioId)
            };

            const response = await fetch('http://localhost:8080/denuncias', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(dadosParaEnviar),
            });

            const textoResposta = await response.text();

            if (response.ok) {
                mensagemSucesso.textContent = textoResposta;
                mensagemSucesso.style.color = '#00695c';
                mensagemSucesso.style.display = 'block';
                form.reset(); 
                nomeArquivoSpan.textContent = "Nenhum arquivo selecionado";
            } else {
                mensagemSucesso.textContent = 'Erro: ' + textoResposta;
                mensagemSucesso.style.color = 'red';
                mensagemSucesso.style.display = 'block';
            }

        } catch (error) {
            console.error("Erro ao conectar com o servidor:", error);
            mensagemSucesso.textContent = 'Não foi possível conectar ao servidor. Tente mais tarde.';
            mensagemSucesso.style.color = 'red';
            mensagemSucesso.style.display = 'block';
        } finally {
            btnEnviar.disabled = false;
            btnEnviar.textContent = 'Enviar Denúncia';
        }
    });

    const inputArquivo = document.getElementById("imagem");
    inputArquivo.addEventListener("change", function () {
        if (inputArquivo.files.length > 0) {
            nomeArquivoSpan.textContent = inputArquivo.files[0].name;
        } else {
            nomeArquivoSpan.textContent = "Nenhum arquivo selecionado";
        }
    });
});