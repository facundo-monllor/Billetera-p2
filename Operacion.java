
import java.util.Random;

public abstract class Operacion {

    private Integer idOperacion;
    private String tipo;
    private Boolean estado;

    // Constructor
    public Operacion(String tipo, Boolean estado) {
        Random random = new Random();
        this.idOperacion = random.nextInt(1000000);
        if (tipo == null || tipo.isEmpty()) throw new IllegalArgumentException("El tipo es obligatorio");
        if (estado == null ) throw new IllegalArgumentException("El estado es obligatorio");
        this.tipo = tipo;
        this.estado = estado;
    }
 
    // Getters
    public Integer getIdOperacion(){
        return idOperacion;
    }
    
    public String getTipo(){
        return tipo;
    }

    public Boolean getEstado(){
        return estado;
    }

    public abstract String toString(String dniOrigen, String cvuOrigen);
    
}
