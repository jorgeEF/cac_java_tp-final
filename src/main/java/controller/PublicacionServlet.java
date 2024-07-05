package controller;

import com.google.gson.Gson;
import dao.PublicacionDAO;
import modelo.Publicacion;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.sql.Date;
import javax.servlet.http.HttpSession;
import modelo.Usuario;

@WebServlet("/publicacion")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)   // 50MB
public class PublicacionServlet extends HttpServlet {

    private static final String UPLOAD_DIRECTORY = "/assets/img"; // Directorio de subida de archivos relativo a Web Pages

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Obtener la ruta absoluta de Web Pages
        String applicationPath = getServletContext().getRealPath("");
        String uploadFilePath = applicationPath + File.separator + UPLOAD_DIRECTORY;

        // Asegurar que el directorio de subida exista
        File uploadDir = new File(uploadFilePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(); // Crea el directorio y subdirectorios si no existen
        }

        // Procesar el archivo subido
        Part filePart = request.getPart("img_path");
        String fileName = extractFileName(filePart); // Extraer nombre de archivo del contenido de la cabecera HTTP

        OutputStream out = null;
        InputStream filecontent = null;

        try {
            // Guardar el archivo en la carpeta deseada
            out = new FileOutputStream(new File(uploadDir, fileName));
            filecontent = filePart.getInputStream();

            int read;
            final byte[] bytes = new byte[1024];
            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            // Guardar la publicación en la base de datos con la ruta relativa del archivo
            String titulo = request.getParameter("titulo");
            String contenido = request.getParameter("contenido");
            String imgPath = UPLOAD_DIRECTORY + "/" + fileName; // Ruta relativa al archivo

            LocalDate fecha = LocalDate.now();
            
            //String creator_id = "1"; // Suponiendo que obtienes el ID del creador
            
            // Obtener el ID del creador desde la sesión
            HttpSession session = request.getSession();
            Usuario usuarioLogueado = (Usuario) session.getAttribute("usuario");
            if (usuarioLogueado == null) {
                response.sendRedirect("pages/login.html?error=not_logged_in");
                return;
            }
            int creator_id = usuarioLogueado.getId();

            Publicacion post = new Publicacion();
            post.setTitulo(titulo);
            post.setContenido(contenido);
            post.setImg_path(imgPath); // Guarda la ruta relativa al archivo en la base de datos
            post.setFecha(Date.valueOf(fecha));
            post.setCreator_id(creator_id);

            PublicacionDAO publicacionDAO = new PublicacionDAO();
            boolean publicacionExitosa = publicacionDAO.guardarPublicacion(post);

            if (publicacionExitosa) {
                response.sendRedirect("pages/publicar.html?exito=true");
            } else {
                response.sendRedirect("pages/publicar.html?error=true");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("pages/publicar.html?error=true");
        } finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
        }
    }

    // Extrae el nombre del archivo de la cabecera HTTP
    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] tokens = contentDisp.split(";");
        for (String token : tokens) {
            if (token.trim().startsWith("filename")) {
                return token.substring(token.indexOf("=") + 2, token.length() - 1);
            }
        }
        return "";
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idParam = request.getParameter("id");
        if (idParam != null) {
            int id = Integer.parseInt(idParam);
            PublicacionDAO publicacionDAO = new PublicacionDAO();
            Publicacion publicacion = publicacionDAO.obtenerPorId(id);
            if (publicacion != null) {
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                Gson gson = new Gson();
                String publicacionJson = gson.toJson(publicacion);
                response.getWriter().write(publicacionJson);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro 'id'.");
        }
    }
}
