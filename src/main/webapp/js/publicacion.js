const content = document.getElementById("main-content");

function toggleMobileMenu(menu) {
    menu.classList.toggle("open");
    content.style.marginTop = menu.classList.contains("open") ? "200px" : "0";
}

document.addEventListener("DOMContentLoaded", function () {
    // Obtener el parámetro 'id' de la URL
    const urlParams = new URLSearchParams(window.location.search);
    const publicacionId = urlParams.get('id');

    // Función para cargar la publicación
    function cargarPublicacion(id) {
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var publicacion = JSON.parse(xhr.responseText);
                mostrarPublicacion(publicacion);
                cargarUsuario(publicacion.creator_id);
            }
        };
        xhr.open("GET", "/cac_java_tp-final/publicacion?id=" + id, true);
        xhr.send();
    }

    // Función para cargar el usuario
    function cargarUsuario(userId) {
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var usuario = JSON.parse(xhr.responseText);
                document.getElementById('publicado-por').innerText = 'Publicado por ' + usuario.nombreUsuario;
            }
        };
        xhr.open("GET", "/cac_java_tp-final/usuario?id=" + userId, true);
        xhr.send();
    }

    // Función para mostrar la publicación en el HTML
    function mostrarPublicacion(publicacion) {
        document.getElementById('publicado-por').textContent = "Publicado por " + publicacion.creator_id;
        document.getElementById('publicado-el').textContent = "el " + publicacion.fecha;
        document.getElementById('titulo-publicacion').textContent = publicacion.titulo;
        document.getElementById('imagen-publicacion').src = "/cac_java_tp-final" + publicacion.img_path;
        document.getElementById('contenido-publicacion').textContent = publicacion.contenido;
    }

    function eliminarPublicacion(id) {
        var xhr = new XMLHttpRequest();
        xhr.open("DELETE", `/cac_java_tp-final/publicacion?id=${id}`, true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 204) {
                alert("Publicación eliminada con éxito");
                window.location.href = "/cac_java_tp-final/index.html";
            } else if (xhr.readyState === 4) {
                alert("Error al eliminar la publicación");
            }
        };
        xhr.send();
    }

    // Cargar la publicación al cargar la página
    if (publicacionId) {
        cargarPublicacion(publicacionId);

        // Añadir eventos a los botones de modificar y eliminar
        document.getElementById('eliminar-btn').addEventListener('click', function () {
            if (confirm("¿Estás seguro de que deseas eliminar esta publicación?")) {
                eliminarPublicacion(publicacionId);
            }
        });

        document.getElementById('modificar-btn').addEventListener('click', function () {
            window.location.href = `/cac_java_tp-final/pages/editar.html?id=${publicacionId}`;
        });
    } else {
        console.error("No se encontró el ID de la publicación en la URL.");
    }
});
