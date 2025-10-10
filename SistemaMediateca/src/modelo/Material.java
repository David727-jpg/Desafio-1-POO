package modelo;

public abstract class Material {
    protected String codigo;
    protected String titulo;
    protected int unidades;
    
    public Material(String codigo, String titulo, int unidades) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.unidades = unidades;
    }
    
    public String getCodigo() { return codigo; }
    public String getTitulo() { return titulo; }
    public int getUnidades() { return unidades; }
    
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setUnidades(int unidades) { this.unidades = unidades; }
    
    public abstract String getTipo();
    
    public void mostrarInfo() {
        System.out.println("Código: " + codigo);
        System.out.println("Título: " + titulo);
        System.out.println("Unidades: " + unidades);
    }
}