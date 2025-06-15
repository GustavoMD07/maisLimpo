(async function() {
    try {
        const response = await fetch('http://localhost:8080/usuario/login-com-token', {
            method: 'GET',
            headers: { 'Content-Type': 'application/json' }
        });

        if (!response.ok) {
            console.log('Sessão não encontrada, redirecionando para o login...');
            window.location.href = '/index.html';
        } else {
            const usuario = await response.json();
            console.log(`Bem-vindo de volta, ${usuario.email}!`);

            sessionStorage.setItem('usuarioId', usuario.id); 
        
            const saudacaoElement = document.getElementById('saudacao-usuario');
            if (saudacaoElement) {
                saudacaoElement.textContent = `Olá, ${usuario.email}!`;
            }

            const logoutButton = document.getElementById('botao-logout');
            if (logoutButton) {
                logoutButton.addEventListener('click', async () => {
                    window.location.href = '/usuario/logout';
                });
            }
        }
    } catch (error) {
        console.error('Erro na verificação de autenticação, redirecionando...', error);
        window.location.href = '/index.html';
    }
})();