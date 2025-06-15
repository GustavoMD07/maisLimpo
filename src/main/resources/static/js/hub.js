

(async function handleAuthentication() {
    console.log("LOG 1: Função de autenticação iniciada.");
    try {
        const response = await fetch('/usuario/login-com-token');
        console.log("LOG 2: Resposta do fetch recebida. Status:", response.status);

        if (!response.ok) {
            console.error("LOG 2.1: Resposta não foi OK. Redirecionando para o login.");
            window.location.href = '/index.html';
            return;
        }
        
        console.log("LOG 3: Resposta OK. Tentando extrair JSON...");
        const usuario = await response.json();
        
        console.log("LOG 4: Objeto 'usuario' recebido do backend:", usuario);
        
        console.log("LOG 5: Guardando dados no sessionStorage...");
        sessionStorage.setItem('usuarioId', usuario.id);
        sessionStorage.setItem('usuarioEmail', usuario.email);
        sessionStorage.setItem('tipoUsuario', usuario.tipoUsuario);
        console.log("LOG 5.1: Dados guardados. Chamando setupDashboard...");

        setupDashboard(usuario.email, usuario.tipoUsuario);

    } catch (error) {
        console.error('LOG ERRO: Falha crítica na autenticação ou no processamento do JSON.', error);
        window.location.href = '/index.html';
    }
})();


function setupDashboard(email, tipo) {
    console.log("LOG 6: Dentro de setupDashboard. Recebido email:", email, "e tipo:", tipo);
    
    const saudacaoElement = document.getElementById('saudacao-usuario');
    if(saudacaoElement) {
        console.log("LOG 6.1: Elemento de saudação encontrado. Atualizando texto...");
        saudacaoElement.textContent = `Logado como: (${tipo})`;
    } else {
        console.error("LOG ERRO: Elemento com id 'saudacao-usuario' não foi encontrado no HTML!");
    }

    const btnVerDenuncias = document.getElementById('btn-ver-denuncias');
    const mensagemErro = document.getElementById('mensagem-erro');

    if(btnVerDenuncias) {
        console.log("LOG 6.2: Botão de ver denúncias encontrado. Adicionando listener...");
        btnVerDenuncias.addEventListener('click', () => {
            if (tipo === 'ong') {
                window.location.href = 'relatorios.html'; 
            } else {
                mensagemErro.textContent = 'Acesso negado. Apenas ONGs podem visualizar o painel de denúncias.';
                mensagemErro.style.display = 'block';
                setTimeout(() => {
                    mensagemErro.style.display = 'none';
                }, 4000);
            }
        });
    } else {
        console.error("LOG ERRO: Botão com id 'btn-ver-denuncias' não foi encontrado!");
    }
}