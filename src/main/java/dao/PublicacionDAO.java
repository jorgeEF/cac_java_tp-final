package dao;

import conexion.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Publicacion;

public class PublicacionDAO {

    public boolean guardarPublicacion(Publicacion post) {
        String sql = "INSERT INTO publicaciones (titulo, contenido, img_path, creator_id, fecha) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, post.getTitulo());
            pstmt.setString(2, post.getContenido());
            pstmt.setString(3, post.getImg_path());
            pstmt.setInt(4, post.getCreator_id());
            pstmt.setDate(5, post.getFecha());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Publicacion extraerPublicacionDeResultSet(ResultSet rs) throws SQLException {
        Publicacion post = new Publicacion();
        post.setId(rs.getInt("id"));
        post.setTitulo(rs.getString("titulo"));
        post.setContenido(rs.getString("contenido"));
        post.setImg_path(rs.getString("img_path"));
        post.setFecha(rs.getDate("fecha"));
        post.setCreator_id(rs.getInt("creator_id"));
        return post;
    }

    public List<Publicacion> obtenerTodas() {
        List<Publicacion> publicaciones = new ArrayList<>();
        String query = "SELECT * FROM publicaciones";

        try (Connection conn = ConexionDB.obtenerConexion(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Publicacion post = extraerPublicacionDeResultSet(rs);
                publicaciones.add(post);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publicaciones;
    }

    public List<Publicacion> obtenerPublicacionesPaginadas(int pagina, int cantidadPorPagina) {
        List<Publicacion> publicaciones = new ArrayList<>();
        int offset = (pagina - 1) * cantidadPorPagina; // Calcular el offset para la paginación

        String query = "SELECT * FROM publicaciones LIMIT ? OFFSET ?";
        try (Connection conn = ConexionDB.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, cantidadPorPagina);
            pstmt.setInt(2, offset);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Publicacion publicacion = extraerPublicacionDeResultSet(rs);
                    publicaciones.add(publicacion);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return publicaciones;
    }

    public Publicacion obtenerPorId(int id) {
        String query = "SELECT * FROM publicaciones WHERE id = ?";
        try (Connection conn = ConexionDB.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extraerPublicacionDeResultSet(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean modificar(Publicacion post) {
        String query = "UPDATE publicaciones SET titulo = ?, contenido = ?, img_id = ?, creator_id = ?, fecha = ? WHERE id = ?";
        try (Connection conn = ConexionDB.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, post.getTitulo());
            pstmt.setString(2, post.getContenido());
            pstmt.setString(3, post.getImg_path());
            pstmt.setInt(4, post.getCreator_id());
            pstmt.setDate(5, post.getFecha());

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String query = "DELETE FROM publicaciones WHERE id = ?";
        try (Connection conn = ConexionDB.obtenerConexion(); PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}
