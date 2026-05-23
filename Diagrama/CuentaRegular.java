package Diagrama;

public class CuentaRegular extends Cuenta{
    
    private Double saldoMaximo;

    // Constructor 
    public CuentaRegular(String alias) {
        super(alias);
        this.saldoMaximo = 5000000.00;
    }
    
    // to String
    @Override
    public String toString() {
        return super.toString() + " SaldoMaximo: " + saldoMaximo;
    }

    // Metodos
    @Override
    public void operar() {
        // lógica específica de CuentaRegular
    }
}
