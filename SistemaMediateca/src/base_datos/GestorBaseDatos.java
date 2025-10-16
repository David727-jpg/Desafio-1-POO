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
    
    // ==================== MÉTODOS DE CONEXIÓN ====================
    
    public boolean probarConexion() {
        try {
            return conexion != null && !conexion.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
    
    public void probarConexionCompleta() {
        try {
            System.out.println("=== PRUEBA DE CONEXIÓN COMPLETA ===");
            
            // 1. Verificar conexión
            if (conexion == null || conexion.isClosed()) {
                System.out.println("❌ Conexión cerrada o nula");
                return;
            }
            
            // 2. Verificar que la base de datos existe
            DatabaseMetaData meta = conexion.getMetaData();
            ResultSet bases = meta.getCatalogs();
            boolean baseExiste = false;
            
            while (bases.next()) {
                String nombreBase = bases.getString(1);
                if ("mediateca".equals(nombreBase)) {
                    baseExiste = true;
                    break;
                }
            }
            bases.close();
            
            System.out.println("✅ Base de datos 'mediateca' existe: " + baseExiste);
            
            if (!baseExiste) {
                System.out.println("❌ La base de datos 'mediateca' no existe");
                return;
            }
            
            // 3. Verificar tablas
            String[] tablas = {"materiales", "libros", "revistas", "cds_audio", "dvds"};
            for (String tabla : tablas) {
                ResultSet rs = meta.getTables(null, null, tabla, null);
                boolean tablaExiste = rs.next();
                System.out.println("✅ Tabla '" + tabla + "' existe: " + tablaExiste);
                rs.close();
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error en prueba de conexión: " + e.getMessage());
        }
    }
    
    // ==================== GENERACIÓN DE CÓDIGOS ====================
    
    public String generarCodigo(String tipo) {
        String prefijo = "";
        switch(tipo) {
            case "LIBRO": prefijo = "LIB"; break;
            case "REVISTA": prefijo = "REV"; break;
            case "CDAUDIO": prefijo = "CDA"; break;
            case "DVD": prefijo = "DVD"; break;
        }
        
        try {
            // Buscar el código máximo actual
            String sql = "SELECT MAX(CAST(SUBSTRING(codigo, 4) AS UNSIGNED)) FROM materiales WHERE codigo LIKE ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, prefijo + "%");
            ResultSet rs = stmt.executeQuery();
            
            int maxNumero = 0;
            if (rs.next()) {
                maxNumero = rs.getInt(1);
            }
            
            // Generar nuevo código
            String nuevoCodigo = String.format("%s%05d", prefijo, maxNumero + 1);
            System.out.println("✅ Nuevo código generado: " + nuevoCodigo);
            return nuevoCodigo;
            
        } catch (SQLException e) {
            System.out.println("❌ Error al generar código: " + e.getMessage());
            return String.format("%s%05d", prefijo, 1);
        }
    }
    
    // ==================== MÉTODOS PARA INSERTAR ====================
    
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
    
    public boolean insertarRevista(Revista revista) {
        try {
            // Insertar en tabla materiales
            String sqlMaterial = "INSERT INTO materiales (codigo, tipo, titulo, unidades, editorial) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmtMaterial = conexion.prepareStatement(sqlMaterial);
            stmtMaterial.setString(1, revista.getCodigo());
            stmtMaterial.setString(2, "REVISTA");
            stmtMaterial.setString(3, revista.getTitulo());
            stmtMaterial.setInt(4, revista.getUnidades());
            stmtMaterial.setString(5, revista.getEditorial());
            stmtMaterial.executeUpdate();
            
            // Insertar en tabla revistas
            String sqlRevista = "INSERT INTO revistas (codigo, periodicidad, fecha_publicacion) VALUES (?, ?, ?)";
            PreparedStatement stmtRevista = conexion.prepareStatement(sqlRevista);
            stmtRevista.setString(1, revista.getCodigo());
            stmtRevista.setString(2, revista.getPeriodicidad());
            stmtRevista.setDate(3, new java.sql.Date(revista.getFechaPublicacion().getTime()));
            stmtRevista.executeUpdate();
            
            System.out.println("✅ Revista insertada: " + revista.getTitulo());
            return true;
            
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar revista: " + e.getMessage());
            return false;
        }
    }
    
    public boolean insertarCDAudio(CDAudio cd) {
        try {
            // Insertar en tabla materiales
            String sqlMaterial = "INSERT INTO materiales (codigo, tipo, titulo, unidades, duracion, genero) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmtMaterial = conexion.prepareStatement(sqlMaterial);
            stmtMaterial.setString(1, cd.getCodigo());
            stmtMaterial.setString(2, "CDAUDIO");
            stmtMaterial.setString(3, cd.getTitulo());
            stmtMaterial.setInt(4, cd.getUnidades());
            stmtMaterial.setInt(5, cd.getDuracion());
            stmtMaterial.setString(6, cd.getGenero());
            stmtMaterial.executeUpdate();
            
            // Insertar en tabla cds_audio
            String sqlCD = "INSERT INTO cds_audio (codigo, artista, num_canciones) VALUES (?, ?, ?)";
            PreparedStatement stmtCD = conexion.prepareStatement(sqlCD);
            stmtCD.setString(1, cd.getCodigo());
            stmtCD.setString(2, cd.getArtista());
            stmtCD.setInt(3, cd.getNumCanciones());
            stmtCD.executeUpdate();
            
            System.out.println("✅ CD de Audio insertado: " + cd.getTitulo());
            return true;
            
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar CD Audio: " + e.getMessage());
            return false;
        }
    }
    
    public boolean insertarDVD(DVD dvd) {
        try {
            // Insertar en tabla materiales
            String sqlMaterial = "INSERT INTO materiales (codigo, tipo, titulo, unidades, duracion, genero) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmtMaterial = conexion.prepareStatement(sqlMaterial);
            stmtMaterial.setString(1, dvd.getCodigo());
            stmtMaterial.setString(2, "DVD");
            stmtMaterial.setString(3, dvd.getTitulo());
            stmtMaterial.setInt(4, dvd.getUnidades());
            stmtMaterial.setInt(5, dvd.getDuracion());
            stmtMaterial.setString(6, dvd.getGenero());
            stmtMaterial.executeUpdate();
            
            // Insertar en tabla dvds
            String sqlDVD = "INSERT INTO dvds (codigo, director) VALUES (?, ?)";
            PreparedStatement stmtDVD = conexion.prepareStatement(sqlDVD);
            stmtDVD.setString(1, dvd.getCodigo());
            stmtDVD.setString(2, dvd.getDirector());
            stmtDVD.executeUpdate();
            
            System.out.println("✅ DVD insertado: " + dvd.getTitulo());
            return true;
            
        } catch (SQLException e) {
            System.out.println("❌ Error al insertar DVD: " + e.getMessage());
            return false;
        }
    }
    
    // ==================== MÉTODOS PARA OBTENER DATOS ====================
    
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
    
    public List<Revista> obtenerRevistas() {
        List<Revista> revistas = new ArrayList<>();
        try {
            String sql = "SELECT * FROM revistas r JOIN materiales m ON r.codigo = m.codigo";
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                Revista revista = new Revista(
                    rs.getString("codigo"),
                    rs.getString("titulo"),
                    rs.getInt("unidades"),
                    rs.getString("editorial"),
                    rs.getString("periodicidad"),
                    rs.getDate("fecha_publicacion")
                );
                revistas.add(revista);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener revistas: " + e.getMessage());
        }
        return revistas;
    }
    
    public List<CDAudio> obtenerCDsAudio() {
        List<CDAudio> cds = new ArrayList<>();
        try {
            String sql = "SELECT * FROM cds_audio c JOIN materiales m ON c.codigo = m.codigo";
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                CDAudio cd = new CDAudio(
                    rs.getString("codigo"),
                    rs.getString("titulo"),
                    rs.getInt("unidades"),
                    rs.getInt("duracion"),
                    rs.getString("genero"),
                    rs.getString("artista"),
                    rs.getInt("num_canciones")
                );
                cds.add(cd);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener CDs Audio: " + e.getMessage());
        }
        return cds;
    }
    
    public List<DVD> obtenerDVDs() {
        List<DVD> dvds = new ArrayList<>();
        try {
            String sql = "SELECT * FROM dvds d JOIN materiales m ON d.codigo = m.codigo";
            Statement stmt = conexion.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            
            while (rs.next()) {
                DVD dvd = new DVD(
                    rs.getString("codigo"),
                    rs.getString("titulo"),
                    rs.getInt("unidades"),
                    rs.getInt("duracion"),
                    rs.getString("genero"),
                    rs.getString("director")
                );
                dvds.add(dvd);
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener DVDs: " + e.getMessage());
        }
        return dvds;
    }
    
    // ==================== MÉTODOS ADICIONALES (PARA FUTURAS FUNCIONALIDADES) ====================
    
    public boolean modificarLibro(Libro libro) {
        try {
            // Actualizar tabla materiales
            String sqlMaterial = "UPDATE materiales SET titulo = ?, unidades = ?, editorial = ? WHERE codigo = ?";
            PreparedStatement stmtMaterial = conexion.prepareStatement(sqlMaterial);
            stmtMaterial.setString(1, libro.getTitulo());
            stmtMaterial.setInt(2, libro.getUnidades());
            stmtMaterial.setString(3, libro.getEditorial());
            stmtMaterial.setString(4, libro.getCodigo());
            stmtMaterial.executeUpdate();
            
            // Actualizar tabla libros
            String sqlLibro = "UPDATE libros SET autor = ?, num_paginas = ?, isbn = ?, año_publicacion = ? WHERE codigo = ?";
            PreparedStatement stmtLibro = conexion.prepareStatement(sqlLibro);
            stmtLibro.setString(1, libro.getAutor());
            stmtLibro.setInt(2, libro.getNumPaginas());
            stmtLibro.setString(3, libro.getIsbn());
            stmtLibro.setInt(4, libro.getAñoPublicacion());
            stmtLibro.setString(5, libro.getCodigo());
            stmtLibro.executeUpdate();
            
            System.out.println("✅ Libro modificado: " + libro.getCodigo());
            return true;
            
        } catch (SQLException e) {
            System.out.println("❌ Error al modificar libro: " + e.getMessage());
            return false;
        }
    }
    
    public boolean eliminarMaterial(String codigo) {
        try {
            // Como tenemos ON DELETE CASCADE, solo necesitamos borrar de materiales
            String sql = "DELETE FROM materiales WHERE codigo = ?";
            PreparedStatement stmt = conexion.prepareStatement(sql);
            stmt.setString(1, codigo);
            int filas = stmt.executeUpdate();
            
            if (filas > 0) {
                System.out.println("✅ Material eliminado: " + codigo);
                return true;
            } else {
                System.out.println("❌ Material no encontrado: " + codigo);
                return false;
            }
            
        } catch (SQLException e) {
            System.out.println("❌ Error al eliminar material: " + e.getMessage());
            return false;
        }
    }
    
    public Material buscarMaterialPorCodigo(String codigo) {
        try {
            // Primero obtener el tipo
            String sqlTipo = "SELECT tipo FROM materiales WHERE codigo = ?";
            PreparedStatement stmtTipo = conexion.prepareStatement(sqlTipo);
            stmtTipo.setString(1, codigo);
            ResultSet rsTipo = stmtTipo.executeQuery();
            
            if (rsTipo.next()) {
                String tipo = rsTipo.getString("tipo");
                
                switch(tipo) {
                    case "LIBRO":
                        return obtenerLibroPorCodigo(codigo);
                    case "REVISTA":
                        return obtenerRevistaPorCodigo(codigo);
                    case "CDAUDIO":
                        return obtenerCDAudioPorCodigo(codigo);
                    case "DVD":
                        return obtenerDVDPorCodigo(codigo);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al buscar material: " + e.getMessage());
        }
        return null;
    }
    
    private Libro obtenerLibroPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT * FROM libros l JOIN materiales m ON l.codigo = m.codigo WHERE l.codigo = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setString(1, codigo);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            return new Libro(
                rs.getString("codigo"),
                rs.getString("titulo"),
                rs.getInt("unidades"),
                rs.getString("editorial"),
                rs.getString("autor"),
                rs.getInt("num_paginas"),
                rs.getString("isbn"),
                rs.getInt("año_publicacion")
            );
        }
        return null;
    }
    
    private Revista obtenerRevistaPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT * FROM revistas r JOIN materiales m ON r.codigo = m.codigo WHERE r.codigo = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setString(1, codigo);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            return new Revista(
                rs.getString("codigo"),
                rs.getString("titulo"),
                rs.getInt("unidades"),
                rs.getString("editorial"),
                rs.getString("periodicidad"),
                rs.getDate("fecha_publicacion")
            );
        }
        return null;
    }
    
    private CDAudio obtenerCDAudioPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT * FROM cds_audio c JOIN materiales m ON c.codigo = m.codigo WHERE c.codigo = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setString(1, codigo);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            return new CDAudio(
                rs.getString("codigo"),
                rs.getString("titulo"),
                rs.getInt("unidades"),
                rs.getInt("duracion"),
                rs.getString("genero"),
                rs.getString("artista"),
                rs.getInt("num_canciones")
            );
        }
        return null;
    }
    
    private DVD obtenerDVDPorCodigo(String codigo) throws SQLException {
        String sql = "SELECT * FROM dvds d JOIN materiales m ON d.codigo = m.codigo WHERE d.codigo = ?";
        PreparedStatement stmt = conexion.prepareStatement(sql);
        stmt.setString(1, codigo);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            return new DVD(
                rs.getString("codigo"),
                rs.getString("titulo"),
                rs.getInt("unidades"),
                rs.getInt("duracion"),
                rs.getString("genero"),
                rs.getString("director")
            );
        }
        return null;
    }
    
    // ==================== MÉTODOS DE UTILIDAD ====================
    
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
}