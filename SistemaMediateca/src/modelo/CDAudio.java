package modelo;

public class CDAudio extends MaterialAudiovisual {
    private String artista;
    private int numCanciones;
    
    public CDAudio(String codigo, String titulo, int unidades, 
                  int duracion, String genero, String artista, int numCanciones) {
        super(codigo, titulo, unidades, duracion, genero);
        this.artista = artista;
        this.numCanciones = numCanciones;
    }
    
    public String getArtista() { return artista; }
    public int getNumCanciones() { return numCanciones; }
    
    public void setArtista(String artista) { this.artista = artista; }
    public void setNumCanciones(int numCanciones) { this.numCanciones = numCanciones; }
    
    @Override
    public String getTipo() {
        return "CDAUDIO";
    }
    
    @Override
    public void mostrarInfo() {
        System.out.println("=== CD DE AUDIO ===");
        super.mostrarInfo();
        System.out.println("Artista: " + artista);
        System.out.println("Número de Canciones: " + numCanciones);
        System.out.println("------------------------");
    }
}