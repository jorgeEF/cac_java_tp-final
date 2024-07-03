document.addEventListener('DOMContentLoaded', () => {
    const loginForm = document.getElementById('loginForm');
    const contactForm = document.getElementById('contactForm');

    if (loginForm) {
        loginForm.addEventListener('submit', evento => {
            evento.preventDefault();
            if (validarLoginForm()) {
                loginForm.submit();
            }
        });
    }

    if (contactForm) {
        contactForm.addEventListener('submit', evento => {
            evento.preventDefault();
            if (validarContactForm()) {
                contactForm.submit();
            }
        });
    }
});

function mostrarError(campo, mensaje) {
    const campoError = document.getElementById(`${campo}-error`);
    if (campoError) {
        campoError.innerText = mensaje;
        campoError.style.color = 'red';
    }
}

function limpiarError(campo) {
    const campoError = document.getElementById(`${campo}-error`);
    if (campoError) {
        campoError.innerText = '';
    }
}

function validarEmail(email) {
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    return regex.test(email);
}

function validarLoginForm() {
    const email = document.getElementById('inputEmail').value.trim();
    const password = document.getElementById('inputPassword').value.trim();
    const robotCheck = document.getElementById('robotCheck').checked;

    let formularioValido = true;

    if (email === '') {
        mostrarError('email', 'Por favor ingresa tu email');
        formularioValido = false;
    } else if (!validarEmail(email)) {
        mostrarError('email', 'Por favor ingresa un email válido');
        formularioValido = false;
    } else {
        limpiarError('email');
    }

    if (password === '') {
        mostrarError('password', 'Contraseña/usuario incorrecta');
        formularioValido = false;
    } else {
        limpiarError('password');
    }

    if (!robotCheck) {
        mostrarError('robot', 'No se pudo validar que no es un robot');
        formularioValido = false;
    } else {
        limpiarError('robot');
    }

    return formularioValido;
}

function validarContactForm() {
    const nombre = document.getElementById('inputNombre').value.trim();
    const apellido = document.getElementById('inputApellido').value.trim();
    const email = document.getElementById('inputEmail').value.trim();
    const asunto = document.getElementById('inputAsunto').value.trim();
    const mensaje = document.getElementById('inputMensaje').value.trim();
    const robotCheck = document.getElementById('robotCheck').checked;

    let formularioValido = true;

    if (nombre === '') {
        mostrarError('nombre', 'Por favor ingresa tu nombre');
        formularioValido = false;
    } else {
        limpiarError('nombre');
    }

    if (apellido === '') {
        mostrarError('apellido', 'Por favor ingresa tu apellido');
        formularioValido = false;
    } else {
        limpiarError('apellido');
    }

    if (email === '') {
        mostrarError('email', 'Por favor ingresa tu email');
        formularioValido = false;
    } else if (!validarEmail(email)) {
        mostrarError('email', 'Por favor ingresa un email válido');
        formularioValido = false;
    } else {
        limpiarError('email');
    }

    if (asunto === '') {
        mostrarError('asunto', 'Por favor ingresa el asunto');
        formularioValido = false;
    } else {
        limpiarError('asunto');
    }

    if (mensaje === '') {
        mostrarError('mensajeContacto', 'Por favor ingresa un mensaje');
        formularioValido = false;
    } else {
        limpiarError('mensajeContacto');
    }

    if (!robotCheck) {
        mostrarError('robot', 'No se pudo validar que no es un robot');
        formularioValido = false;
    } else {
        limpiarError('robot');
    }

    return formularioValido;
}