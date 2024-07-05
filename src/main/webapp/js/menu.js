document.addEventListener("DOMContentLoaded", function() {
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                var usuario = JSON.parse(xhr.responseText);
                if (usuario) {
                    document.getElementById('menu-nueva-publicacion').style.display = 'block';
                }
            }
        };
        xhr.open("GET", "/cac_java_tp-final/GetUsuarioServlet", true);
        xhr.send();
    });

