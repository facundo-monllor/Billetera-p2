

public class InversionVinculadaDivisa extends Inversion{
    
    private Double tasaInteresDivisa;
    private String nombreDivisa;

    // Constructor
    public InversionVinculadaDivisa(double monto, int plazoDias, String divisa, Double tasa, Boolean estado) {
        super(plazoDias, monto, true, estado);
        if (divisa == null || divisa.isEmpty()) throw new RuntimeException("La divisa es obligatoria");
        if (tasa == null) throw new RuntimeException("La tasa es obligatoria");

        this.tasaInteresDivisa = Utilitarios.consultarCotizacion(divisa);
        this.nombreDivisa = divisa;
    }

     // Getters
    public Double getTasaInteresDivisa(){
        return tasaInteresDivisa;
    }
    public String getNombreDivisa(){
        return nombreDivisa;
    }

    // to String
    @Override
    public String toString() {
        return super.toString() + "TasaInteresDivisa: " + tasaInteresDivisa + " NombreDivisa: " + nombreDivisa;
    }

}
