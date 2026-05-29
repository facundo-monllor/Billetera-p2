
public class CuentaCorporativa extends Cuenta{
    
    private String cuitEmpresa;
    
    // Constructor 

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
    
}
