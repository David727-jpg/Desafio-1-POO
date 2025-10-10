package modelo;

import java.util.Date;

public class Revista extends MaterialEscrito {
    private String periodicidad;
    private Date fechaPublicacion;
    
    public Revista(String codigo, String titulo, int unidades, String editorial,
                  String periodicidad, Date fechaPublicacion) {
        super(codigo, titulo, unidades, editorial);
        this.periodicidad = periodicidad;
        this.fechaPublicacion = fechaPublicacion;
    }
    
    public String getPeriodicidad() { return periodicidad; }
    public Date getFechaPublicacion() { return fechaPublicacion; }
    
    public void setPeriodicidad(String periodicidad) { this.periodicidad = periodicidad; }
    public void setFechaPublicacion(Date fechaPublicacion) { this.fechaPublicacion = fechaPublicacion; }
    
    @Override
    public String getTipo() {
        return "REVISTA";
    }
    
    @Override
    public void mostrarInfo() {
        System.out.println("=== REVISTA ===");
        super.mostrarInfo();
        System.out.println("Periodicidad: " + periodicidad);
        System.out.println("Fecha Publicación: " + fechaPublicacion);
        System.out.println("------------------------");
    }
}