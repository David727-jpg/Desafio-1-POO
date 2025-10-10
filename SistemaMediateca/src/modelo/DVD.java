package modelo;

public class DVD extends MaterialAudiovisual {
    private String director;
    
    public DVD(String codigo, String titulo, int unidades, 
               int duracion, String genero, String director) {
        super(codigo, titulo, unidades, duracion, genero);
        this.director = director;
    }
    
    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }
    
    @Override
    public String getTipo() {
        return "DVD";
    }
    
    @Override
    public void mostrarInfo() {
        System.out.println("=== DVD ===");
        super.mostrarInfo();
        System.out.println("Director: " + director);
        System.out.println("------------------------");
    }
}