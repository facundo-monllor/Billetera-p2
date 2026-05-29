
public class CuentaPremium extends Cuenta{
    
    private Double montoMinimo = 500000.00;

    // Constructor 
    public CuentaPremium(String alias, double depositoInicial) {
        super(alias, depositoInicial);
        if(depositoInicial < montoMinimo) throw new IllegalArgumentException("El Monto minimo es $500.000");
    }
    
    // to String
    @Override
    public String toString() {
        return super.toString() + " MontoMinimo: " + montoMinimo;
    }

}
