
public class InversionRentaFija extends Inversion{
    
    private Double tasaInteres;

    // Constructor
    public InversionRentaFija(double monto, int plazoDias, Boolean estado) {
        super(plazoDias, monto, true, estado);

        // this.tasaInteres = Utilitarios.consultarCotizacion();
        this.tasaInteres = 0.20;
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
