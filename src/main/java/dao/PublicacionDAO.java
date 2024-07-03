/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;
import modelo.Publicacion;

/**
 *
 * @author Jef
 */
public class PublicacionDAO {
    public boolean guardarPublicacion(Publicacion post) {
        String sql = "INSERT INTO publicaciones (titulo, contenido, img_id, creator_id, fecha) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, post.getTitulo());
            pstmt.setString(2, post.getContenido());
            pstmt.setInt(3, post.getImg_id());
            pstmt.setInt(4, post.getCreator_id());
            pstmt.setDate(5, post.getFecha());
            

            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private Publicacion extraerPublicacionDeResultSet(ResultSet rs) throws Exception {
        Publicacion post = new Publicacion();
        post.setId(rs.getInt("id"));
        post.setTitulo(rs.getString("titulo"));
        post.setContenido(rs.getString("contenido"));        
        post.setFecha(rs.getDate("fecha"));     
        return post;
    }

    public List<Publicacion> obtenerTodas() {
        
        List<Publicacion> publicaciones = new ArrayList<>();
        String query = "SELECT * FROM publicaciones";
        
        try (Connection conn = ConexionDB.obtenerConexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Publicacion post = extraerPublicacionDeResultSet(rs);
                publicaciones.add(post);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return publicaciones;
    }

    public Publicacion obtenerPorId(int id) {
        String query = "SELECT * FROM publicaciones WHERE id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return extraerPublicacionDeResultSet(rs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean modificar(Publicacion post) {
        String query = "UPDATE publicaciones SET titulo = ?, contenido = ?, img_id = ?, creator_id = ?, fecha = ? WHERE id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setString(1, post.getTitulo());
            pstmt.setString(2, post.getContenido());
            pstmt.setInt(3, post.getImg_id());
            pstmt.setInt(4, post.getCreator_id());
            pstmt.setDate(5, post.getFecha());            
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean eliminar(int id) {
        String query = "DELETE FROM publicaciones WHERE id = ?";
        try (Connection conn = ConexionDB.obtenerConexion();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            
            pstmt.setInt(1, id);
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
