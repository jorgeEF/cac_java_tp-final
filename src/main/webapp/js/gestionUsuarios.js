document.addEventListener('DOMContentLoaded', cargarUsuarios);

// ----------------------------------------- //
// -- Cargar usuarios -- //
function cargarUsuarios() {

    fetch('/proyectoJava_24111/GestionUsuariosServlet')
        .then(response => response.json())
        .then(usuarios => {
            //console.log(usuarios)
            const tbody = document.querySelector('#usuariosTable tbody');
            tbody.innerHTML = '';
            usuarios.forEach(usuario => {
                const fechaFormateada = new Date(usuario.fechaNacimiento).toISOString().split('T')[0];
                tbody.innerHTML += `
                    <tr>
                        <td>${usuario.id}</td>
                        <td>${usuario.nombre}</td>
                        <td>${usuario.apellido}</td>
                        <td>${usuario.email}</td>
                        <td>${usuario.password}</td>
                        <td>${fechaFormateada}</td>
                        <!-- <td>${usuario.fechaNacimiento}</td> -->
                        <td>${usuario.pais}</td>
                        <td>
                            <button class="btn btn-primary btn-sm" onclick="mostrarModificarModal(${usuario.id})">Modificar</button>
                            <button class="btn btn-danger btn-sm" onclick="eliminarUsuario(${usuario.id})">Eliminar</button>
                        </td>
                    </tr>
                `;
            });
        })
}