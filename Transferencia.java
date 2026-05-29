
import java.time.LocalDate;

public class Transferencia extends Operacion{
    
    private Double monto;
    private String remitenteCVU;
    private String remitenteDNI;
    private String destinatarioCVU;
    private String destinatarioDNI;
    private LocalDate fecha;

    // Constructor
    public Transferencia(Double monto, String remitenteCVU, String remitenteDNI, String destinatarioCVU, String destinatarioDNI, Boolean estado) {
        super("Transferencia", estado);
        if (monto == null) throw new IllegalArgumentException("El monto es obligatorio");
        if (remitenteCVU == null || remitenteCVU.isEmpty()) throw new IllegalArgumentException("El CVU remitente es obligatorio");
        if (remitenteDNI == null || remitenteDNI.isEmpty()) throw new IllegalArgumentException("El DNI del remitente es obligatorio");
        if (destinatarioCVU == null || destinatarioCVU.isEmpty()) throw new IllegalArgumentException("El CVU del destinatario es obligatorio");
        if (destinatarioDNI == null || destinatarioDNI.isEmpty()) throw new IllegalArgumentException("El DNI del destinatario es obligatorio");

        this.monto = monto;
        this.remitenteCVU = remitenteCVU;
        this.remitenteDNI = remitenteDNI;
        this.destinatarioCVU = destinatarioCVU;
        this.destinatarioDNI = destinatarioDNI;
        this.fecha = Utilitarios.hoy();
    }

     // Getters
    public Double getMonto(){
        return monto;
    }
    public String getRemitenteCVU(){
        return remitenteCVU;
    }
    public String getRemitenteDNI(){
        return remitenteDNI;
    }
    public String getDestinatarioCVU(){
        return destinatarioCVU;
    }
    public String getDestinatarioDNI(){
        return destinatarioDNI;
    }
    public LocalDate getFecha(){
        return fecha;
    }

    // to String
    @Override
    public String toString(String dniContexto, String cvuContexto) {
        String estadoOperacion = getEstado() ? "Aprobado" : "Rechazado";
        return "fecha: " + getFecha() + "\n" +
               "origen: " + getRemitenteDNI() + " (" + getRemitenteCVU() + ")\n" +
               "destino: " + getDestinatarioDNI() + " (" + getDestinatarioCVU() + ")\n" +
               "monto: " + getMonto() + "\n" +
               estadoOperacion;
    }

}
