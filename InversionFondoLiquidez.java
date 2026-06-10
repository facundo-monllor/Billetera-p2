
public class InversionFondoLiquidez extends Inversion{
    
    private Double montoMinimo = 20000000.00;
    private Double tasaInteres;

    // Constructor
    public InversionFondoLiquidez(double monto, int plazoDias, Boolean estado) {
        super(plazoDias, monto, false, estado);
        if (monto < montoMinimo) throw new IllegalArgumentException("El monto mínimo es de 20 millones de pesos");
        this.tasaInteres = 8.00;
    }


    @Override
    public double calcularMontoVencimiento(int dias) {
        double intereses = getMontoInvertido() * (tasaInteres / 365.0) * dias;
        return getMontoInvertido() + intereses;
    }

    @Override
    public double calcularMontoPrecancelacion(long diasPasados) {
        throw new UnsupportedOperationException("El fondo de liquidez no es precancelable");
    }

    public Double getTasaInteres() {
        return tasaInteres;
    }
    
    // to String
    @Override
    public String toString() {
        return super.toString() + "MontoMinimo: " + montoMinimo;
    }

}
