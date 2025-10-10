package base_datos;

import modelo.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GestorBaseDatos {
    private Connection conexion;
    private final String URL = "jdbc:mysql://localhost:3306/mediateca";
    private final String USUARIO = "root";      
    private final String CONTRASENA = "";       // Normalmente vacío en XAMPP
    
    public GestorBaseDatos() {
        conectar();
    }
    
    private void conectar() {
        try {
            System.out.println("🔧 Intentando cargar el driver MySQL...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("✅ Driver MySQL cargado correctamente");
            
            System.out.println("🔧 Intentando conectar a: " + URL);
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            System.out.println("✅ Conexión a MySQL establecida");
            
        } catch (ClassNotFoundException e) {
            System.out.println("❌ ERROR: No se encontró el driver MySQL");
            System.out.println("💡 Solución: Agrega el conector MySQL al proyecto");
            System.out.println("   - Descarga de: https://dev.mysql.com/downloads/connector/j/");
            System.out.println("   - Agrega el .jar en: Project → Properties → Libraries");
            
        } catch (SQLException e) {
            System.out.println("❌ ERROR de conexión SQL: " + e.getMessage());
            System.out.println("💡 Verifica que:");
            System.out.println("   - MySQL esté ejecutándose en XAMPP");
            System.out.println("   - La base de datos 'mediateca' exista");
            System.out.println("   - Usuario/contraseña sean correctos");
        }
    }
    
    // El resto del código se mantiene igual...
    public boolean probarConexion() {
        try {
            return conexion != null && !conexion.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
    
    public boolean insertarLibroPrueba() {
        try {
            Libro libro = new Libro("LIB00001", "El Principito", 3, 
                                   "Salamandra", "Antoine de Saint-Exupéry", 
                                   96, "978-8498381498", 1943);
            
            return insertarLibro(libro);
        } catch (Exception e) {
            System.out.println("Error en libro de prueba: " + e.getMessage());
            return false;
        }
    }
    
    public boolean insertarLibro(Libro libro) {
        try {
            // Insertar en tabla materiales
            String sqlMaterial = "INSERT INTO materiales (codigo, tipo, titulo, unidades, editorial) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmtMaterial = conexion.prepareStatement(sqlMaterial);
            stmtMaterial.setString(1, libro.getCodigo());
            stmtMaterial.setString(2, "LIBRO");
            stmtMaterial.setString(3, libro.getTitulo());
            stmtMaterial.setInt(4, libro.getUnidades());
            stmtMaterial.setString(5, libro.getEditorial());
            stmtMaterial.executeUpdate();
            
            // Insertar en tabla libros
            String sqlLibro = "INSERT INTO libros (codigo, autor, num_paginas, isbn, año_publicacion) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmtLibro = conexion.prepareStatement(sqlLibro);
            stmtLibro.setString(1, libro.getCodigo());
            stmtLibro.setString(2, libro.getAutor());
            stmtLibro.setInt(3, libro.getNumPaginas());
            stmtLibro.setString(4, libro.getIsbn());
            stmtLibro.setInt(5, libro.getAñoPublicacion());
            stmtLibro.executeUpdate();
            
            System.out.println("✅ Libro insertado: " + libro.getTitulo());
            return true;
            
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar libro: " + e.getMessage());
            return false;
        }
    }
    
    public List<Libro> obtenerLibros() {
        List<Libro> libros = new ArrayList<>();
        try {
            String sql = "SELECT * FROM libros l JOIN materiales m ON l.codigo = m.codigo";
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Libro libro = new Libro(
                    rs.getString("codigo"),
                    rs.getString("titulo"),
                    rs.getInt("unidades"),
                    rs.getString("editorial"),
                    rs.getString("autor"),
                    rs.getInt("num_paginas"),
                    rs.getString("isbn"),
                    rs.getInt("año_publicacion")
                );
                libros.add(libro);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener libros: " + e.getMessage());
        }
        return libros;
    }
    
    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
                System.out.println("🔌 Conexión cerrada");
            }
        } catch (SQLException e) {
            System.out.println("Error al cerrar conexión: " + e.getMessage());
        }
    }

    public String generarCodigo(String libro) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}