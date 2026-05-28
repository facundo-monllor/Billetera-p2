
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Usuario {
    
    private String DNI;
    private String nombre;
    private String email;
    private String telefono;
    private Double totalInvertido;

    private List<Cuenta> cuentas;
    private Map<String, Boolean> cuitEmpresas;

    // Constructor
    public Usuario(String DNI, String nombre, String email, String telefono) {
        if (DNI == null || DNI.isEmpty()) throw new IllegalArgumentException("El DNI es obligatorio");
        if (nombre == null || nombre.isEmpty()) throw new IllegalArgumentException("El nombre es obligatorio");
        if (email == null || email.isEmpty()) throw new IllegalArgumentException("El email es obligatorio");
        if (telefono == null || telefono.isEmpty()) throw new IllegalArgumentException("El teléfono es obligatorio");

        this.DNI = DNI;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;

        this.totalInvertido = 0.0;
        this.cuentas = new ArrayList<>();
        this.cuitEmpresas = new HashMap<>();
    }

    // Getters
    public String getDNI(){
        return DNI;
    }
    public Double getTotalInvertido(){
        return totalInvertido;
    }
    public List<Cuenta> getCuentas(){
        return cuentas;
    }
    public Boolean getAutorizadoEmpresa(String CUIT) {
        if (!cuitEmpresas.containsKey(CUIT))
            throw new RuntimeException("El CUIT '" + CUIT + "' no está registrado.");
        
        return cuitEmpresas.get(CUIT);
    }
    
    // Setters
    public void sumarInversion(Double monto) {
        if (this.totalInvertido == null) {
            this.totalInvertido = 0.0;
        }
        this.totalInvertido += monto;
    }
    
    // to String
    @Override
    public String toString() {
        return "DNI: " + DNI + " Nombre: " + nombre + " Email: " + email + " Teléfono: " + telefono + " Total invertido: " + totalInvertido;
    }
};
