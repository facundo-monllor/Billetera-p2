
import java.time.LocalDate;

public abstract class Inversion extends Operacion{
    
    private LocalDate fechaConstitucion;
    private Integer plazo;
    private Double montoInvertido;
    private Boolean esPrecancelable;
    private Boolean cobrada;

    // Constructor
    public Inversion(Integer plazo, Double monto, Boolean esPrecancelable, Boolean estado) {
        super("Inversion", estado);
        if (plazo == null) throw new IllegalArgumentException("El plazo es obligatorio");
        if (monto == null) throw new IllegalArgumentException("El monto es obligatorio");

        this.fechaConstitucion = Utilitarios.hoy();
        this.plazo = plazo;
        this.montoInvertido = monto;
        this.esPrecancelable = esPrecancelable;
        this.cobrada = false;
    }


    // Getters
    public LocalDate getFechaConstitucion(){
        return fechaConstitucion;
    }
    public Integer getPlazo(){
        return plazo;
    }
    public Double getMontoInvertido(){
        return montoInvertido;
    }
    public Boolean getEsPrecancelable(){
        return esPrecancelable;
    }
    public Boolean getCobrada(){
        return cobrada;
    }

    // Setters
    public void setCobrada(Boolean newCobrada){
        cobrada = newCobrada;
    }


    // to String
    @Override
    public String toString() {
        return super.toString() + "FechaConstitucion: " + fechaConstitucion + " Plazo: " + plazo + " MontoInvertido: " + montoInvertido + " EsPrecancelable: " + esPrecancelable;
    }

}
