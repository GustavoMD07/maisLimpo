document.addEventListener('DOMContentLoaded', async () => {
    const corpoTabela = document.getElementById('corpo-tabela-denuncias');
    const mensagemSemDenuncias = document.getElementById('mensagem-sem-denuncias');

    try {
        const response = await fetch('http://localhost:8080/denuncias');

        if (!response.ok) {
            throw new Error(`Erro de rede: ${response.status}`);
        }

        const denuncias = await response.json();

        if (denuncias.length === 0) {
            // Se não houver denúncias, mostra a mensagem
            mensagemSemDenuncias.style.display = 'block';
        } else {
            // Se houver denúncias, preenche a tabela
            denuncias.forEach(denuncia => {
                const tr = document.createElement('tr');

                // Formata a data para um formato mais legível (ex: 15/06/2025 17:30)
                const dataFormatada = new Date(denuncia.dataHoraDenuncia).toLocaleString('pt-BR', {
                    day: '2-digit',
                    month: '2-digit',
                    year: 'numeric',
                    hour: '2-digit',
                    minute: '2-digit'
                });

                tr.innerHTML = `
                    <td>${denuncia.id}</td>
                    <td>${denuncia.nomePraia}</td>
                    <td>${dataFormatada}</td>
                    <td>${denuncia.emailUsuario}</td>
                    <td>${denuncia.textoDenuncia}</td>
                `;
                corpoTabela.appendChild(tr);
            });
        }

    } catch (error) {
        console.error('Erro ao buscar denúncias:', error);
        corpoTabela.innerHTML = `<tr><td colspan="5" style="color: red; text-align: center;">Falha ao carregar as denúncias. Tente novamente mais tarde.</td></tr>`;
    }
});