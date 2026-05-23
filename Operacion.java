
import java.util.Random;

public abstract class Operacion {

    private Integer idOperacion;
    private String tipo;
    private Boolean estado;

    // Constructor
    public Operacion(String tipo, Boolean estado) {
        Random random = new Random();
        this.idOperacion = random.nextInt(1000000);
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

    // to String
    @Override
    public String toString() {
        return "IdOperacion: " + idOperacion + "Tipo: " + tipo;
    }
    
}
