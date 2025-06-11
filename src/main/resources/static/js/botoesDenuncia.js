/* Esse js serve pros botões de denúncia, mas n funciona sem um back-end Java, ent se precisar apagar pode apagar */

const inputArquivo = document.getElementById("imagem");
const nomeArquivo = document.getElementById("nome-arquivo");

inputArquivo.addEventListener("change", function () {
  if (inputArquivo.files.length > 0) {
    nomeArquivo.textContent = inputArquivo.files[0].name;
  } else {
    nomeArquivo.textContent = "Nenhum arquivo selecionado";
  }
});

const form = document.querySelector("form");
const mensagem = document.getElementById("mensagem-enviada");

form.addEventListener("submit", function (event) {
  event.preventDefault(); // impede envio real

  // Aqui você pode depois substituir por lógica Java com back-end
  mensagem.style.display = "block"; // exibe a mensagem
  form.reset(); // limpa os campos
  nomeArquivo.textContent = "Nenhum arquivo selecionado";

  // Oculta a mensagem após 3 segundos (opcional)
  setTimeout(() => {
    mensagem.style.display = "none";
  }, 3000);
});