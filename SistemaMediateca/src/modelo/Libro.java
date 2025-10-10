package modelo;

public class Libro extends MaterialEscrito {
    private String autor;
    private int numPaginas;
    private String isbn;
    private int añoPublicacion;
    
    public Libro(String codigo, String titulo, int unidades, String editorial,
                String autor, int numPaginas, String isbn, int añoPublicacion) {
        super(codigo, titulo, unidades, editorial);
        this.autor = autor;
        this.numPaginas = numPaginas;
        this.isbn = isbn;
        this.añoPublicacion = añoPublicacion;
    }
    
    public String getAutor() { return autor; }
    public int getNumPaginas() { return numPaginas; }
    public String getIsbn() { return isbn; }
    public int getAñoPublicacion() { return añoPublicacion; }
    
    public void setAutor(String autor) { this.autor = autor; }
    public void setNumPaginas(int numPaginas) { this.numPaginas = numPaginas; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public void setAñoPublicacion(int añoPublicacion) { this.añoPublicacion = añoPublicacion; }
    
    @Override
    public String getTipo() {
        return "LIBRO";
    }
    
    @Override
    public void mostrarInfo() {
        System.out.println("=== LIBRO ===");
        super.mostrarInfo();
        System.out.println("Autor: " + autor);
        System.out.println("Páginas: " + numPaginas);
        System.out.println("ISBN: " + isbn);
        System.out.println("Año: " + añoPublicacion);
        System.out.println("------------------------");
    }
}

