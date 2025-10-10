package modelo;

public abstract class MaterialEscrito extends Material {
    protected String editorial;
    
    public MaterialEscrito(String codigo, String titulo, int unidades, String editorial) {
        super(codigo, titulo, unidades);
        this.editorial = editorial;
    }
    
    public String getEditorial() { return editorial; }
    public void setEditorial(String editorial) { this.editorial = editorial; }
    
    @Override
    public void mostrarInfo() {
        super.mostrarInfo();
        System.out.println("Editorial: " + editorial);
    }
}