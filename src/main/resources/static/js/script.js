
//aqui ele espera o html carregar todo antes de executar o arquivo js, pra evitar erro de loading e etc
document.addEventListener("DOMContentLoaded", () => {
    
    const loginForm = document.getElementById("loginForm");
    const emailInput = document.getElementById("emailInput");
    const lembrarCheckbox = document.querySelector(".remember-forgot input[type='checkbox']");

    // =================================================================
    // PARTE 1: LER O EMAIL SALVO QUANDO A PÁGINA CARREGA
    // =================================================================
    const emailSalvo = localStorage.getItem('emailLembrado');
    if (emailSalvo) {
        emailInput.value = emailSalvo;
        lembrarCheckbox.checked = true;
        console.log("LOG: E-mail recuperado do LocalStorage e preenchido.");
    }

    //aqui é o botão de eventos, quando o usuário clicar no "login", o formulário é enviado e todas as ações
    loginForm.addEventListener("submit", async (event) => {
        event.preventDefault();

        const email = emailInput.value;
        const senha = document.getElementById("passwordInput").value;
        const lembrar = lembrarCheckbox.checked; 
        const messageDiv = document.getElementById("message");

            messageDiv.textContent = "";
            messageDiv.className = ""; // limpa a mensagem anterior 

        try {
            const response = await fetch("http://localhost:8080/usuario/login", {
                method: "POST", 
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ email: email, senha: senha, lembrar: lembrar }),
            });

            if (response.ok) { 
                // =================================================================
                // PARTE 2: SALVAR OU REMOVER O EMAIL APÓS O LOGIN
                // =================================================================
                if (lembrar) {
                    localStorage.setItem('emailLembrado', email);
                    console.log("LOG: E-mail salvo no LocalStorage.");
                } else {
                    localStorage.removeItem('emailLembrado');
                    console.log("LOG: E-mail removido do LocalStorage.");
                }
                // =================================================================

                messageDiv.textContent = "Login bem-sucedido! Redirecionando...";
                messageDiv.className = 'mensagem-sucesso'; 
                setTimeout(() => {
                    window.location.href = "/principal.html";
                }, 1000); 
            } else {
                const errorData = await response.json();
                const mensagem = Object.values(errorData)[0];
                messageDiv.textContent = mensagem;
                messageDiv.className = 'mensagem-erro';
            }
        } catch (error) {
            console.error("Erro ao conectar com o servidor:", error);
            messageDiv.textContent = "Não foi possível conectar ao servidor. Tente novamente mais tarde.";
            messageDiv.className = 'mensagem-erro';
        }
    });
});