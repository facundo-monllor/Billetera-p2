

public class InversionVinculadaDivisa extends Inversion{
    
    private Double tasaInteresDivisa;
    private String nombreDivisa;
    private Double montoDivisa;

    // Constructor
    public InversionVinculadaDivisa(double monto, int plazoDias, String divisa, Double tasa, Boolean estado) {
        super(plazoDias, monto, true, estado);
        if (divisa == null || divisa.isEmpty()) throw new RuntimeException("La divisa es obligatoria");
        if (tasa == null) throw new RuntimeException("La tasa es obligatoria");

        this.tasaInteresDivisa = tasa;
        this.nombreDivisa = divisa;
        this.montoDivisa = monto / Utilitarios.consultarCotizacion(divisa);
    }

     // Getters
    public Double getTasaInteresDivisa(){
        return tasaInteresDivisa;
    }
    public String getNombreDivisa(){
        return nombreDivisa;
    }
    public Double getMontoDivisa() { 
        return montoDivisa;
    }

    // to String
    @Override
    public String toString() {
        return super.toString() + "TasaInteresDivisa: " + tasaInteresDivisa + " NombreDivisa: " + nombreDivisa + " MontoDivisa: " + montoDivisa;
    }

}
