
import java.util.ArrayList;
import java.util.List;

public class Empresa {
    
    private String cuit;
    private String nombreFantasia;
    private String telefono;
    private String email;
    private String nombreContacto;

    private List<String> usuariosDNI;

    // Constructor
    public Empresa(String cuit, String nombreFantasia, String telefono, String email, String nombreContacto) {
        if (cuit == null || cuit.isEmpty()) throw new RuntimeException("El CUIT es obligatorio");
        if (nombreFantasia == null || nombreFantasia.isEmpty()) throw new RuntimeException("El nombre de fantasia es obligatorio");
        if (telefono == null || telefono.isEmpty()) throw new RuntimeException("El telefono es obligatorio");
        if (email == null || email.isEmpty()) throw new RuntimeException("El email es obligatorio");
        if (nombreContacto == null || nombreContacto.isEmpty()) throw new RuntimeException("El nombre de contacto es obligatorio");

        this.cuit = cuit;
        this.nombreFantasia = nombreFantasia;
        this.telefono = telefono;
        this.email = email;
        this.nombreContacto = nombreContacto;
        this.usuariosDNI = new ArrayList<>();
    }

    // Getters
    public String getCuit(){
        return cuit;
    }
    public String getNombreFantasia(){
        return nombreFantasia;
    }
    public String getTelefono(){
        return telefono;
    }
    public String getEmail(){
        return email;
    }
    public String getNombreContacto(){
        return nombreContacto;
    }
    public List<String> getUsuariosDNI(){
        return usuariosDNI;
    }

    // Setters
    public void setEmail(String newEmail){
         email = newEmail;
    }

    // to String
    @Override
    public String toString() {
        return "Cuit: " + cuit + " Nombre Fantasia: " + nombreFantasia + " Teléfono: " + telefono +  " Email: " + email + " Nombre Contacto: " + nombreContacto;
    }
};
