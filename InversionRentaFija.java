
public class InversionRentaFija extends Inversion{
    
    private Double tasaInteres;

    // Constructor
    public InversionRentaFija(double monto, int plazoDias, Boolean estado) {
        super(plazoDias, monto, true, estado);

        // this.tasaInteres = Utilitarios.consultarCotizacion();
        this.tasaInteres = 0.20;
    }

    @Override
    public double calcularMontoVencimiento(int dias) {
        double intereses = getMontoInvertido() * (tasaInteres / 365.0) * dias;
        return getMontoInvertido() + intereses;
    }

    @Override
    public double calcularMontoPrecancelacion(long diasPasados) {
        double intereses = getMontoInvertido() * (tasaInteres / 365.0) * diasPasados;
        return getMontoInvertido() + (intereses / 2.0);
    }

    // Getters
    public Double getTasaInteres(){
        return tasaInteres;
    }

    // to String
    @Override
    public String toString() {
        return super.toString() + "TasaInteres: " + tasaInteres;
    }

}
