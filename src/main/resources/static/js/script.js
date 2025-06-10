
//aqui ele espera o html carregar todo antes de executar o arquivo js, pra evitar erro de loading e etc
document.addEventListener("DOMContentLoaded", () => {
    
    const loginForm = document.getElementById("loginForm");


    //aqui é o botão de eventos, quando o usuário clicar no "login", o formulário é enviado e todas as ações
    loginForm.addEventListener("submit", async (event) => {
        event.preventDefault();

        const email = document.getElementById("emailInput").value;
        const senha = document.getElementById("passwordInput").value;
        const messageDiv = document.getElementById("message");

        try {
            // aqui realmente chama o backend
            const response = await fetch("/usuario/login", {
                method: "POST", 
                headers: {
                    "Content-Type": "application/json", //json padrão
                },
                body: JSON.stringify({ email: email, senha: senha }), //dados em texto
            });

            const responseBody = await response.text();

            if (response.ok) { 
                messageDiv.textContent = responseBody;
                messageDiv.style.color = 'lightgreen';
                window.location.href = "/principal.html"; //se estiver ok ele manda pra tela preincipal
            } else {
                messageDiv.textContent = 'Erro: ' + responseBody;
                messageDiv.style.color = 'coral';
            }
        } catch (error) {
            //erro que não deveria acontecer na teoria, mas caso aconteça...
            console.error("Erro ao conectar com o servidor:", error);
            messageDiv.textContent = "Não foi possível conectar ao servidor. Tente novamente mais tarde.";
            messageDiv.style.color = 'red';
        }
    });
});