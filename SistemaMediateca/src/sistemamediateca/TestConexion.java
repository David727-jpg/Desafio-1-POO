package sistemamediateca;

import base_datos.GestorBaseDatos;
import modelo.Libro;
import java.util.List;

public class TestConexion {
    public static void main(String[] args) {
        System.out.println("=== PROBANDO CONEXIÓN JAVA-MYSQL ===");
        
        GestorBaseDatos gestor = new GestorBaseDatos();
        
        if (gestor.probarConexion()) {
            System.out.println("✅ CONEXIÓN EXITOSA A MYSQL");
            
            // Insertar libro de prueba
            System.out.println("\n--- INSERTANDO LIBRO DE PRUEBA ---");
            if (gestor.insertarLibroPrueba()) {
                System.out.println("📖 Libro de prueba insertado");
            }
            
            // Leer libros de la BD
            System.out.println("\n--- LEYENDO LIBROS DE LA BD ---");
            List<Libro> libros = gestor.obtenerLibros();
            
            if (libros.isEmpty()) {
                System.out.println("ℹ️ No hay libros en la base de datos");
            } else {
                for (Libro libro : libros) {
                    libro.mostrarInfo();
                }
            }
            
        } else {
            System.out.println("❌ ERROR EN LA CONEXIÓN");
            System.out.println("💡 Verifica que:");
            System.out.println("   - XAMPP esté ejecutándose");
            System.out.println("   - MySQL esté en STARTED");
            System.out.println("   - El conector MySQL esté agregado al proyecto");
        }
        
        gestor.cerrarConexion();
    }
}