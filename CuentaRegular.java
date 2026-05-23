
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

    @Override
    public void validarLimiteRecepcion(Double monto) {
        if (this.getSaldo() + monto > this.saldoMaximo) {
            throw new IllegalStateException("La transferencia supera el límite de saldo de la Cuenta Regular");
        }
    }
}
