document.addEventListener('DOMContentLoaded', () => {

    const toggleIcons = document.querySelectorAll('.password-toggle-icon');

    // Para cada ícone encontrado, adiciona a funcionalidade
    toggleIcons.forEach(icon => {
        icon.addEventListener('click', () => {
            // Acha o campo de senha que é o "irmão" anterior do ícone no HTML
            const passwordInput = icon.previousElementSibling;

            // Verifica o tipo do campo e faz a mágica de trocar
            if (passwordInput.type === 'password') {
                // Se for senha, muda pra texto e troca o ícone
                passwordInput.type = 'text';
                icon.classList.remove('bx-show');
                icon.classList.add('bx-hide');
            } else {
                // Se for texto, muda pra senha e troca o ícone de volta
                passwordInput.type = 'password';
                icon.classList.remove('bx-hide');
                icon.classList.add('bx-show');
            }
        });
    });
});