package Diagrama;

public class CuentaPremium extends Cuenta{
    
    private Double montoMinimo = 500000.00;

    // Constructor 
    public CuentaPremium(String alias, double depositoInicial) {
        super(alias);
        if(depositoInicial < montoMinimo) throw new RuntimeException("El Monto minimo es $500.000");
    }
    
    // to String
    @Override
    public String toString() {
        return super.toString() + " MontoMinimo: " + montoMinimo;
    }

    // Metodos
    @Override
    public void operar() {
        // lógica específica de CuentaRegular
    }
}
