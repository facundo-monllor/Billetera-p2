package Diagrama;

public class InversionRentaFija extends Inversion{
    
    private Double tasaInteres;

    // Constructor
    public InversionRentaFija(double monto, int plazoDias, Boolean estado) {
        super(plazoDias, monto, true, estado);
        if (tasaInteres == null) throw new RuntimeException("El monto es obligatorio");

        // this.tasaInteres = Utilitarios.consultarCotizacion();
        this.tasaInteres = 100.00;
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
