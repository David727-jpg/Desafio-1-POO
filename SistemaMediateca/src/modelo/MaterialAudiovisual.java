package modelo;

public abstract class MaterialAudiovisual extends Material {
    protected int duracion;
    protected String genero;
    
    public MaterialAudiovisual(String codigo, String titulo, int unidades, 
                              int duracion, String genero) {
        super(codigo, titulo, unidades);
        this.duracion = duracion;
        this.genero = genero;
    }
    
    public int getDuracion() { return duracion; }
    public String getGenero() { return genero; }
    
    public void setDuracion(int duracion) { this.duracion = duracion; }
    public void setGenero(String genero) { this.genero = genero; }
    
    @Override
    public void mostrarInfo() {
        super.mostrarInfo();
        System.out.println("Duración: " + duracion + " minutos");
        System.out.println("Género: " + genero);
    }
}