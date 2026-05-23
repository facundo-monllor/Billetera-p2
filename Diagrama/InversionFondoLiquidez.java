package Diagrama;

public class InversionFondoLiquidez extends Inversion{
    
    private Double montoMinimo = 20000000.00;
    private Double tasaInteres;

    // Constructor
    public InversionFondoLiquidez(double monto, int plazoDias, Boolean estado) {
        super(plazoDias, monto, false, estado);
        if (monto < montoMinimo) throw new RuntimeException("El monto mínimo es de 20 millones de pesos");
        this.tasaInteres = 8.00;
    }

     // Getters
    public Double getMontoMinimo(){
        return montoMinimo;
    }

    // to String
    @Override
    public String toString() {
        return super.toString() + "MontoMinimo: " + montoMinimo;
    }

}
