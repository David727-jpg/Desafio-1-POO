package sistemamediateca;

import interfaz.MenuPrincipal;

public class SistemaMediateca {
    public static void main(String[] args) {
        System.out.println("=== INICIANDO SISTEMA MEDIATECA ===");
        System.out.println("✅ Cargando interfaz gráfica...");
        
        // Método simple y directo
        MenuPrincipal menu = new MenuPrincipal();
        menu.setVisible(true);
        
        System.out.println("🎉 Sistema listo para usar");
    }
}