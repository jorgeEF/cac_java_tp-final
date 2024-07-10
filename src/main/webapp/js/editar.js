document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const publicacionId = urlParams.get('id');

    // Función para cargar la publicación
    function cargarPublicacion(id) {
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var publicacion = JSON.parse(xhr.responseText);
                document.getElementById('id').value = publicacion.id;
                document.getElementById('titulo').value = publicacion.titulo;
                document.getElementById('contenido').value = publicacion.contenido;
            }
        };
        xhr.open("GET", "/cac_java_tp-final/publicacion?id=" + id, true);
        xhr.send();
    }

    // Cambiar el método del formulario a PATCH al enviar
    const form = document.getElementById('form-publicacion');
    form.addEventListener('submit', function (e) {
        e.preventDefault(); // Previene el envío del formulario por defecto

        const formData = new FormData(form);
        const xhr = new XMLHttpRequest();
        xhr.open("PUT", "/cac_java_tp-final/publicacion", true);
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                    document.getElementById('mensajeExito').style.display = 'block';
                    setTimeout(function () {
                        window.location.href = `/cac_java_tp-final/pages/publicacion.html?id=${publicacionId}`;
                    }, 2000);
                } else {
                    document.getElementById('mensajeError').style.display = 'block';
                }
            }
        };

        // Añadir los parámetros al FormData
        formData.append('id', publicacionId);
        xhr.send(formData);
    });

    // Cargar la publicación al cargar la página
    if (publicacionId) {
        cargarPublicacion(publicacionId);

        // Añadir eventos a los botones de cancelar
        document.getElementById('cancelar').addEventListener('click', function () {
            window.location.href = "/cac_java_tp-final/index.html";
        });
    } else {
        console.error("No se encontró el ID de la publicación en la URL.");
    }
});
