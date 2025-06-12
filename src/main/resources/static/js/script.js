
//aqui ele espera o html carregar todo antes de executar o arquivo js, pra evitar erro de loading e etc
document.addEventListener("DOMContentLoaded", () => {
    
    const loginForm = document.getElementById("loginForm");


    //aqui é o botão de eventos, quando o usuário clicar no "login", o formulário é enviado e todas as ações
    loginForm.addEventListener("submit", async (event) => {
        event.preventDefault();

        const email = document.getElementById("emailInput").value;
        const senha = document.getElementById("passwordInput").value;
        const lembrar = document.querySelector(".remember-forgot input[type='checkbox']").checked; 
        const messageDiv = document.getElementById("message");

            messageDiv.textContent = "";
            messageDiv.className = ""; // limpa a mensagem anterior 

        try {
            // aqui realmente chama o backend
            const response = await fetch("http://localhost:8080/usuario/login", {
                method: "POST", 
                headers: {
                    "Content-Type": "application/json", 
                },
                body: JSON.stringify({ email: email, senha: senha, lembrar: lembrar }),
            });

            if (response.ok) { 
                messageDiv.textContent = "Login bem-sucedido! Redirecionando...";
                messageDiv.className = 'mensagem-sucesso'; 
                setTimeout(() => {
                window.location.href = "/principal.html";
            }, 1000); // espera 1 segundo antes de redirecionar
            } else {
                const errorData = await response.json();
                const mensagem = Object.values(errorData)[0];
                messageDiv.textContent = mensagem;
                messageDiv.className = 'mensagem-erro';
            }
        } catch (error) {
            //erro que não deveria acontecer na teoria, mas caso aconteça...
            console.error("Erro ao conectar com o servidor:", error);
            messageDiv.textContent = "Não foi possível conectar ao servidor. Tente novamente mais tarde.";
            messageDiv.className = 'mensagem-erro';
        }
    });
});