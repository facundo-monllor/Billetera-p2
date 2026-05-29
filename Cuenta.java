
import java.util.ArrayList;
import java.util.List;

public abstract class Cuenta {
    
    private String CVU;
    private String alias;
    private Double saldo;
    private List<Operacion> listaOperaciones;

    // Constructor
    public Cuenta(String alias) {
        this(alias, 0.00);
    }

    public Cuenta(String alias, double saldoInicial) {
        if (alias == null || alias.isEmpty()) throw new IllegalArgumentException("El alias es obligatorio");
        if (saldoInicial < 0) throw new IllegalArgumentException("El saldo no puede ser negativo");

        this.CVU = Utilitarios.generarSiguienteCvu();
        this.alias = alias;
        this.saldo = saldoInicial;

        this.listaOperaciones = new ArrayList<>();
    }
 
    // Getters
    public String getCVU(){
        return CVU;
    }
    public String getAlias(){
        return alias;
    }
    public Double getSaldo(){
        return saldo;
    }
    public List<Operacion> getListaOperaciones(){
        return listaOperaciones;
    }

    // Setters
     public void setSaldo(Double nuevoSaldo){
        saldo = nuevoSaldo;
    }

    // to String
    @Override
    public String toString() {
        return "CVU: " + CVU + " alias: " + alias + " saldo: " + saldo + "listaOperaciones:" + listaOperaciones;
    }
    
    // Metodos
    public abstract void operar();

    public void validarLimiteRecepcion(Double montoAAcreditar) {
        // Por defecto no hace nada. Las cuentas sin límite usarán este comportamiento.
    }
      
}
