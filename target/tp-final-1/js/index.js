const content = document.getElementById("main-content");

function toggleMobileMenu(menu) {
  menu.classList.toggle("open");
  content.style.marginTop = menu.classList.contains("open") ? "200px" : "0";
}
