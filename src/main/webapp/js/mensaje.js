AOS.init();            
window.onload = function () {                
    var urlParams = new URLSearchParams(window.location.search);
    if (urlParams.get('exito') === 'true') {
        document.getElementById('mensajeExito').style.display = 'block';
    } else if (urlParams.get('error') === 'true') {
        document.getElementById('mensajeError').style.display = 'block';
    }
};


