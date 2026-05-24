
public class CuentaCorporativa extends Cuenta{
    
    private String cuitEmpresa;
    
    public CuentaCorporativa(String alias, String cuitEmpresa) {
        super(alias);
        this.cuitEmpresa = cuitEmpresa;
    }

    // Getters
    public String getCuitEmpresa(){
        return cuitEmpresa;
    }

    // to String
    @Override
    public String toString() {
        return super.toString() + " CuitEmpresa: " + cuitEmpresa;
    }
    
    // Metodos
    @Override
    public void operar() {
        // lógica específica de CuentaRegular
    }
}
