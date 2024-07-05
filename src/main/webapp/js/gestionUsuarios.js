document.addEventListener('DOMContentLoaded', cargarUsuarios);

// ----------------------------------------- //
// -- Cargar usuarios -- //
function cargarUsuarios() {

    fetch('/cac_java_tp-final/GestionUsuariosServlet')
        .then(response => response.json())
        .then(usuarios => {
            //console.log(usuarios)
            const tbody = document.querySelector('#usuariosTable tbody');
            tbody.innerHTML = '';
            usuarios.forEach(usuario => {                
                tbody.innerHTML += `
                    <tr>
                        <td>${usuario.id}</td>
                        <td>${usuario.nombre}</td>
                        <td>${usuario.apellido}</td>
                        <td>${usuario.email}</td>
                        <td>${usuario.password}</td>                        
                        <td>
                            <button class="btn btn-primary btn-sm" onclick="mostrarModificarModal(${usuario.id})">Modificar</button>
                            <button class="btn btn-danger btn-sm" onclick="eliminarUsuario(${usuario.id})">Eliminar</button>
                        </td>
                    </tr>
                `;
            });
        })
}