package dao;

import conexion.ConexionDB;
import modelo.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {

    public Usuario validarUsuario(String email, String password) {
        Usuario usuario = null;

        String sql = "SELECT * FROM usuarios WHERE email = ? AND password = ?";

        try {
            Connection conexion = ConexionDB.obtenerConexion();
            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.setString(1, email);
            consulta.setString(2, password);
            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                usuario = new Usuario();
                usuario.setId(resultado.getInt("id"));
                usuario.setEmail(resultado.getString("email"));
                usuario.setNombre(resultado.getString("nombre"));
                // Configura otros campos según tu modelo
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return usuario;
    }
}
