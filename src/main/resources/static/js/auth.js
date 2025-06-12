(async function() {
    try {
        const response = await fetch('/usuarios/login-com-token', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            // Se a resposta não for OK (ex: 401 Unauthorized), chuta o usuário pro login
            console.log('Sessão não encontrada, redirecionando para o login...');
            window.location.href = '/index.html';
        } else {
            // O usuário está logado! Podemos até pegar os dados dele se quisermos
            const usuario = await response.json();
            console.log(`Bem-vindo de volta, ${usuario.email}!`);
            // Podemos guardar o ID do usuário no sessionStorage pra usar depois, por exemplo
            sessionStorage.setItem('usuarioId', usuario.id); // Supondo que o backend retorne o ID
        }
    } catch (error) {
        console.error('Erro na verificação de autenticação, redirecionando...', error);
        window.location.href = '/index.html';
    }
})();