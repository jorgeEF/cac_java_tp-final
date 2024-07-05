const content = document.getElementById("main-content");

function toggleMobileMenu(menu) {
    menu.classList.toggle("open");
    content.style.marginTop = menu.classList.contains("open") ? "200px" : "0";
}


document.addEventListener("DOMContentLoaded", function () {
    // Función para cargar las publicaciones
    function cargarPublicaciones(pagina) {
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var publicaciones = JSON.parse(xhr.responseText);
                mostrarPublicaciones(publicaciones);
            }
        };
        xhr.open("GET", "publicaciones?pagina=" + pagina, true);
        xhr.send();
    }

    // Función para mostrar las publicaciones en el HTML
    function mostrarPublicaciones(publicaciones) {
        var contenidoHtml = '';
        publicaciones.forEach(function (publicacion) {
            contenidoHtml += `
            <div class="card bg-transparent mb-3 border border-0">
                <div class="row no-gutters">
                    <div class="col-md-2 d-flex align-items-center">
                        <img src="/cac_java_tp-final${publicacion.img_path}" class="img-fluid rounded-circle" alt="...">
                    </div>
                    <div class="col-md-10">
                        <div class="card-body">
                            <h5 class="card-title">${publicacion.titulo}</h5>
                            <p class="card-text">${publicacion.contenido}</p>
                            <p><a href="/cac_java_tp-final/pages/publicacion.html?id=${publicacion.id}" class="btn btn-outline-secondary btn-sm mt-2">Ver mas...</a></p>
                            <p class="card-text">
                                    <small class="text-body-secondary">Publicado el ${publicacion.fecha}</small>
                            </p>
                        </div>
                    </div>
                </div>
            </div>
        `;
        });
        document.getElementById('main-content').innerHTML = contenidoHtml;
    }

    // Cargar las primeras publicaciones al cargar la página
    cargarPublicaciones(1);
});

