
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.temporal.ChronoUnit;

public class Billetera implements IBilletera {

    private Map<String, Usuario> usuarios = new HashMap<>();
    private Map<Integer, Operacion> operaciones = new HashMap<>();
    private Map<String, Empresa> empresas = new HashMap<>();

    public void registrarUsuario(String dni, String nombre, String telefono, String email) {
        if (usuarios.containsKey(dni)) {
            throw new RuntimeException("El usuario ya existe");
        }

        Usuario usuario = new Usuario(dni, nombre, email, telefono);
        usuarios.put(dni, usuario);
    }

    public String crearCuentaRegular(String dniUsuario, String alias){
        if (!usuarios.containsKey(dniUsuario)) {
            throw new RuntimeException("El usuario no existe");
        }

        usuarios.forEach((dni, usuario) -> {
            for (Cuenta cuenta : usuario.getCuentas()) {
                if (cuenta.getAlias().equals(alias)) {
                    throw new RuntimeException("El alias ya está en uso");
                }
            }
        });

        CuentaRegular cuentaRegular = new CuentaRegular(alias);
        Usuario usuario = usuarios.get(dniUsuario);
        usuario.getCuentas().add(cuentaRegular);
        
        return cuentaRegular.getCVU();
    }

    public String crearCuentaPremium(String dniUsuario, String alias, double depositoInicial){
        if (!usuarios.containsKey(dniUsuario)) {
            throw new RuntimeException("El usuario no existe");
        }

        usuarios.forEach((dni, usuario) -> {
            for (Cuenta cuenta : usuario.getCuentas()) {
                if (cuenta.getAlias().equals(alias)) {
                    throw new RuntimeException("El alias ya está en uso");
                }
            }
        });

        CuentaPremium cuentaPremium = new CuentaPremium(alias, depositoInicial);
        Usuario usuario = usuarios.get(dniUsuario);
        usuario.getCuentas().add(cuentaPremium);
    
        return cuentaPremium.getCVU();
    }

    public String crearCuentaCorporativa(String dniUsuario, String alias, String cuitEmpresa){
        if (!usuarios.containsKey(dniUsuario)) {
            throw new RuntimeException("El usuario no existe");
        }
        if (!empresas.containsKey(cuitEmpresa)) {
            throw new RuntimeException("La empresa no existe");
        }

        usuarios.forEach((dni, usuario) -> {
            for (Cuenta cuenta : usuario.getCuentas()) {
                if (cuenta.getAlias().equals(alias)) {
                    throw new RuntimeException("El alias ya está en uso");
                }
            }
        });
        
        Usuario usuario = usuarios.get(dniUsuario);
        CuentaCorporativa cuentaCorporativa = new CuentaCorporativa(alias, cuitEmpresa);
        usuario.getCuentas().add(cuentaCorporativa);

        // Empresa empresa = empresas.get(cuitEmpresa);
        // List<String> autorizados = empresa.getUsuariosDNI();

        // for(int i=0 ; i < autorizados.size() ;i++){
        //     if (autorizados.get(i).equals(dniUsuario)) {
        //         throw new RuntimeException("La persona ya está autorizada");
        //     }
        // }

        // autorizados.add(dniUsuario);
    
        return cuentaCorporativa.getCVU();
    }

    public List<String> obtenerCuentas(String dniUsuario) {
        if (!usuarios.containsKey(dniUsuario)) {
            throw new RuntimeException("El usuario no existe");
        }

        Usuario usuario = usuarios.get(dniUsuario);
        List<String> listaFormateada = new ArrayList<>();

        for (Cuenta cuenta : usuario.getCuentas()) {
            String cuentaFormateada = cuenta.getClass().getSimpleName() + ": " + cuenta.getAlias() + " (" + cuenta.getCVU() + ")";
            listaFormateada.add(cuentaFormateada);
        }

        return listaFormateada;        
    }
    
    public double obtenerSaldoDisponible(String cvu){
        for (Usuario usuario : usuarios.values()) {
            for (Cuenta cuenta : usuario.getCuentas()) {
                if (cuenta.getCVU().equals(cvu)) {
                    return cuenta.getSaldo();
                }
            }
        }
        throw new RuntimeException("La cuenta no existe");
    }

    public void realizarTransferencia(String cvuOrigen, String cvuDestino, double monto){
        if (monto <= 0) throw new IllegalArgumentException("El monto a transferir debe ser mayor a cero");
        Cuenta cuentaOrigen = null;
        Cuenta cuentaDestino = null;
        Usuario usuarioDestino = null;
        for (Usuario usuario : usuarios.values()) {
            for (Cuenta cuenta : usuario.getCuentas()) {
                if (cuenta.getCVU().equals(cvuOrigen)) {
                    cuentaOrigen = cuenta;
                }
                if (cuenta.getCVU().equals(cvuDestino)) {
                    cuentaDestino = cuenta;
                    usuarioDestino = usuario;
                }
            }
        }
        
        if(cuentaOrigen == null) throw new RuntimeException("La cuenta origen no existe");
        if(cuentaDestino == null) throw new RuntimeException("La cuenta destino no existe");
        cuentaDestino.validarLimiteRecepcion(monto);
        Boolean esAprobada = monto <= cuentaOrigen.getSaldo();

        Transferencia transferencia = new Transferencia(monto, cvuOrigen, cvuDestino,usuarioDestino.getDNI(), esAprobada);
        
        cuentaOrigen.getListaOperaciones().add(transferencia);
        cuentaDestino.getListaOperaciones().add(transferencia);
        operaciones.put(transferencia.getIdOperacion(), transferencia);

        if(esAprobada){
            cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - monto);
            cuentaDestino.setSaldo(cuentaDestino.getSaldo() + monto);
        }
    };

    public int realizarInversionRentaFija(String dni, String cvu, double monto, int plazoDias){
        if (!usuarios.containsKey(dni)) {
            throw new RuntimeException("El usuario no existe");
        }
        
        Cuenta cuentaUsuario = null;
        Usuario usuario = usuarios.get(dni);
            for (Cuenta cuenta : usuario.getCuentas()) {
                if (cuenta.getCVU().equals(cvu)) {
                    cuentaUsuario = cuenta;
                }
            }
        
        
        if(cuentaUsuario == null) throw new RuntimeException("La cuenta no existe");
        Boolean esAprobada = monto <= cuentaUsuario.getSaldo();

        InversionRentaFija inversionRentaFija = new InversionRentaFija(monto, plazoDias, esAprobada);

        cuentaUsuario.getListaOperaciones().add(inversionRentaFija);
        if(esAprobada){
            cuentaUsuario.setSaldo(cuentaUsuario.getSaldo() - monto);
            usuario.sumarInversion(monto);
        }
        operaciones.put(inversionRentaFija.getIdOperacion(), inversionRentaFija);
        return inversionRentaFija.getIdOperacion();
    };
    
    public int realizarInversionDivisa(String dni, String cvu, double monto, int plazoDias, String divisa, double tasa){
        if (!usuarios.containsKey(dni)) {
            throw new RuntimeException("El usuario no existe");
        }
        
        Cuenta cuentaUsuario = null;
        Usuario usuario = usuarios.get(dni);
            for (Cuenta cuenta : usuario.getCuentas()) {
                if (cuenta.getCVU().equals(cvu)) {
                    cuentaUsuario = cuenta;
                }
            }
        
        
        if(cuentaUsuario == null) throw new RuntimeException("La cuenta no existe");
        Boolean esAprobada = monto <= cuentaUsuario.getSaldo();

        InversionVinculadaDivisa inversionVinculadaDivisa = new InversionVinculadaDivisa(monto, plazoDias, divisa, tasa, esAprobada);

        cuentaUsuario.getListaOperaciones().add(inversionVinculadaDivisa);
        if(esAprobada){
            cuentaUsuario.setSaldo(cuentaUsuario.getSaldo() - monto);
            usuario.sumarInversion(monto);
        }
        operaciones.put(inversionVinculadaDivisa.getIdOperacion(), inversionVinculadaDivisa);
        return inversionVinculadaDivisa.getIdOperacion();
    };

    public int realizarInversionLiquidez(String dni, String cvu, double monto, int plazoDias){
        if (!usuarios.containsKey(dni)) {
            throw new RuntimeException("El usuario no existe");
        }
        
        Cuenta cuentaUsuario = null;
        Usuario usuario = usuarios.get(dni);
            for (Cuenta cuenta : usuario.getCuentas()) {
                if (cuenta.getCVU().equals(cvu)) {
                    cuentaUsuario = cuenta;
                }
            }
        
        if (!(cuentaUsuario instanceof CuentaCorporativa)) {
            throw new IllegalArgumentException("La cuenta no es corporativa");
        }
        if(cuentaUsuario == null) {
            throw new RuntimeException("La cuenta no existe");
        }
        CuentaCorporativa cuentaCorporativa = (CuentaCorporativa) cuentaUsuario;
        String cuitEmpresa = cuentaCorporativa.getCuitEmpresa();

        Boolean esAprobada = monto <= cuentaUsuario.getSaldo();        

        // Empresa empresa = empresas.get(cuitEmpresa);
        // List<String> autorizados = empresa.getUsuariosDNI();

        // for(int i=0 ; i < autorizados.size() ;i++){
        //     if (!autorizados.get(i).equals(dni)) {
        //         throw new RuntimeException("La persona no está autorizada");
        //     }
        // }
        
        InversionFondoLiquidez inversionFondoLiquidez = new InversionFondoLiquidez(monto, plazoDias, esAprobada);
        cuentaUsuario.getListaOperaciones().add(inversionFondoLiquidez);

        if(esAprobada){
            cuentaUsuario.setSaldo(cuentaUsuario.getSaldo() - monto);
            usuario.sumarInversion(monto);
        }
        operaciones.put(inversionFondoLiquidez.getIdOperacion(), inversionFondoLiquidez);
        return inversionFondoLiquidez.getIdOperacion();
    };

    public List<String> consultarHistorialGlobal() {
        List<String> historial = new ArrayList<>();

        for (Usuario usuario : usuarios.values()) {
            for (Cuenta cuenta : usuario.getCuentas()) {
                for (Operacion operacion : cuenta.getListaOperaciones()) {

                    String estado = operacion.getEstado() ? "Aprobado" : "Rechazado";
                    String detalle = "";

                    if (operacion instanceof Transferencia) {
                        Transferencia transferencia = (Transferencia) operacion;
                        detalle =
                                "fecha: " + transferencia.getFecha() + "\n" +
                                "origen: " + usuario.getDNI() + " (" + transferencia.getRemitenteCVU() + ")\n" +
                                "destino: " + transferencia.getDestinatarioDNI() + " " + transferencia.getDestinatarioCVU() + "\n" +
                                "monto: " + transferencia.getMonto() + "\n" +
                                estado;
                    } else if (operacion instanceof Inversion) {
                        Inversion inversion = (Inversion) operacion;
                        detalle =
                                "fecha: " + inversion.getFechaConstitucion() + "\n" +
                                "origen: " + usuario.getDNI() + " (" + cuenta.getCVU() + ")\n" +
                                "desc: " + inversion.getClass().getSimpleName() + "\n" +
                                "monto: " + inversion.getMontoInvertido() + "\n" +
                                "plazo: " + inversion.getPlazo() + "\n" +
                                estado;
                    }
                    
                    historial.add(detalle);
                }
            }
        }

        return historial;
    }

    public List<String> consultarHistorialCuenta(String cvu) {

        List<String> historial = new ArrayList<>();
        Cuenta cuentaEncontrada = null;
        Usuario usuarioCuenta = null;

        for (Usuario usuario : usuarios.values()) {
            for (Cuenta cuenta : usuario.getCuentas()) {
                if (cuenta.getCVU().equals(cvu)) {
                    cuentaEncontrada = cuenta;
                    usuarioCuenta = usuario;
                }
            }
        }

        if (cuentaEncontrada == null) {
            throw new RuntimeException("La cuenta no existe");
        }

        for (Operacion operacion : cuentaEncontrada.getListaOperaciones()) {
            String estado = operacion.getEstado() ? "Aprobado" : "Rechazado";
            String detalle = "";

            if (operacion instanceof Transferencia) {
                Transferencia transferencia = (Transferencia) operacion;
                detalle =
                        "fecha: " + transferencia.getFecha() + "\n" +
                        "origen: " + usuarioCuenta.getDNI() +" (" + transferencia.getRemitenteCVU() + ")\n" +
                        "destino: " + transferencia.getDestinatarioDNI() +" (" + transferencia.getDestinatarioCVU() + ")\n" +
                        "monto: " + transferencia.getMonto() + "\n" +
                        estado;
            } else if (operacion instanceof Inversion) {
                Inversion inversion = (Inversion) operacion;
                detalle =
                        "fecha: " + inversion.getFechaConstitucion() + "\n" +
                        "origen: " + usuarioCuenta.getDNI() +" (" + cuentaEncontrada.getCVU() + ")\n" +
                        "desc: " + inversion.getClass().getSimpleName() + "\n" +
                        "monto: " + inversion.getMontoInvertido() + "\n" +
                        "plazo: " + inversion.getPlazo() + "\n" +
                        estado;
            }

            historial.add(detalle);
        }

        return historial;
    }

    public List<String> consultarHistorialUsuario(String dniUsuario) {
        if (!usuarios.containsKey(dniUsuario)) {
            throw new RuntimeException("El usuario no existe");
        }

        Usuario usuario = usuarios.get(dniUsuario);
        List<String> historial = new ArrayList<>();

        for (Cuenta cuenta : usuario.getCuentas()) {
            for (Operacion operacion : cuenta.getListaOperaciones()) {
                String estado = operacion.getEstado() ? "Aprobado" : "Rechazado";
                String detalle = "";

                if (operacion instanceof Transferencia) {
                    Transferencia transferencia = (Transferencia) operacion;
                    detalle =
                            "fecha: " + transferencia.getFecha() + "\n" +
                            "origen: " + usuario.getDNI() + " (" + transferencia.getRemitenteCVU() + ")\n" +
                            "destino: " + transferencia.getDestinatarioDNI() + " (" + transferencia.getDestinatarioCVU() + ")\n" +
                            "monto: " + transferencia.getMonto() + "\n" +
                            estado;
                }else if (operacion instanceof Inversion) {
                    Inversion inversion = (Inversion) operacion;
                    detalle =
                            "fecha: " + inversion.getFechaConstitucion() + "\n" +
                            "origen: " + usuario.getDNI() + " (" + cuenta.getCVU() + ")\n" +
                            "desc: " + inversion.getClass().getSimpleName() + "\n" +
                            "monto: " + inversion.getMontoInvertido() + "\n" +
                            "plazo: " + inversion.getPlazo() + "\n" +
                            estado;
                }

                historial.add(detalle);
            }
        }

        return historial;
    }

    public double obtenerTotalInvertido(String dniUsuario){
        if (!usuarios.containsKey(dniUsuario)) {
            throw new RuntimeException("El usuario no existe");
        }
        Usuario usuario = usuarios.get(dniUsuario);

        return usuario.getTotalInvertido();
    }

    public List<String> cuentasConMayorVolumen(int cantidadTop) {
        if (cantidadTop <= 0) {
            throw new RuntimeException("El numero debe ser positivo");
        }

        List<Cuenta> cuentas = new ArrayList<>();
        for (Usuario usuario : usuarios.values()) {
            for (Cuenta cuenta : usuario.getCuentas()) {
                cuentas.add(cuenta);
            }
        }

        cuentas.sort((cuentaA, cuentaB) -> 
            cuentaB.getListaOperaciones().size() - cuentaA.getListaOperaciones().size()
        );
        
        if(cantidadTop > cuentas.size()) {
            throw new RuntimeException("El número del top es más grande que las cuentas disponibles");
        }

        List<String> listaFormateada = new ArrayList<>();
        for (int i = 0; i < cantidadTop && i < cuentas.size(); i++) {
            Cuenta cuenta = cuentas.get(i);
            listaFormateada.add(cuenta.getClass().getSimpleName() + ": " + cuenta.getAlias() + " (" + cuenta.getCVU() + ")");
        }

        return listaFormateada;
    }

    public String consultarCvu(String alias){
         for (Usuario usuario : usuarios.values()) {
            for (Cuenta cuenta : usuario.getCuentas()) {
                if(cuenta.getAlias() == alias){
                    return cuenta.getCVU();
                }
            }
        }
        throw new IllegalArgumentException("El CVU no fue encontrado");
    }

    public void registrarEmpresa(String cuit, String nombreFantasia, String telefono, String email, String nombreContacto){
        if (empresas.containsKey(cuit)) {
            throw new RuntimeException("La empresa ya existe");
        }

        Empresa empresa = new Empresa(cuit, nombreFantasia, telefono, email, nombreContacto);
        empresas.put(cuit, empresa);
    }

    void agregarPersonaAutorizada(String cuitEmpresa, String dniAutorizado){
         if (!empresas.containsKey(cuitEmpresa)) {
            throw new RuntimeException("La empresa no existe");
        }

        Empresa empresa = empresas.get(cuitEmpresa);
        List<String> autorizados = empresa.getUsuariosDNI();

        for(int i=0 ; i < autorizados.size() ;i++){
            if (autorizados.get(i).equals(dniAutorizado)) {
                throw new RuntimeException("La persona ya está autorizada");
            }
        }

        autorizados.add(dniAutorizado);
    };



    /**
     * [Nuevo]
     * 13) Precancela una inversión activa de forma anticipada.
     * Lanza error si algun dato es inválido, la inversión no existe o no está
     * activa.
     *
     * @param dni         El DNI del usuario.
     * @param cvu         El CVU de la cuenta asociada a la inversión.
     * @param idInversion El identificador único de la inversión a cancelar.
     */
    public void precancelarInversion(String dni, String cvu, int idInversion){
        if (!operaciones.containsKey(idInversion)) {
            throw new RuntimeException("La operacion no existe");
        }
        if (!usuarios.containsKey(dni)) {
            throw new RuntimeException("El usuario no existe");
        }

        Usuario usuario = usuarios.get(dni);
        Cuenta cuentaUsuario = null;
        for (Cuenta cuenta : usuario.getCuentas()) {
                if (cuenta.getCVU().equals(cvu)) {
                    cuentaUsuario = cuenta;
                }
            }
        if(cuentaUsuario == null) throw new RuntimeException("La cuenta no existe");

        Operacion operacion = operaciones.get(idInversion);

        if(!operacion.getEstado()){
            throw new RuntimeException("La operacion no esta aprobada");
        }

        if(operacion.getTipo().equals("Transferencia")){
            throw new RuntimeException("La operacion es una transferencia");
        }else{
            Inversion inversion = (Inversion) operacion;
                if(!inversion.getEsPrecancelable()){
                    throw new RuntimeException("La operacion no es precancelable");
                }
                if (inversion.getCobrada()) {
                    throw new RuntimeException("La inversion ya fue cobrada");
                }
                usuario.sumarInversion(-inversion.getMontoInvertido());
                inversion.setCobrada(true);

                // TODO: COMO CALCULAR LOS DIAS SIN USAR CHRONOUNIT
                long diasPasados = ChronoUnit.DAYS.between(inversion.getFechaConstitucion(), Utilitarios.hoy());
                System.out.print("diasPasados " + diasPasados);
                double montoADevolver = 0.0;

                if (inversion instanceof InversionRentaFija) {
                InversionRentaFija rentaFija = (InversionRentaFija) inversion;
        
                // intereses = monto * (tasa / 365) * días
                double intereses = rentaFija.getMontoInvertido() * (rentaFija.getTasaInteres() / 365.0) * diasPasados;
                // capital inicial + la mitad de los intereses
                montoADevolver = rentaFija.getMontoInvertido() + (intereses / 2.0);

                } else if (inversion instanceof InversionVinculadaDivisa) {
                InversionVinculadaDivisa divisa = (InversionVinculadaDivisa) inversion;
        
                // cantidad de dlares originales que se compraron.
                double dolaresOriginales = divisa.getMontoDivisa(); 
        
                //  intereses se calculan sobre los dolares en vez de los pesos
                double interesesDolares = dolaresOriginales * (divisa.getTasaInteresDivisa() / 365.0) * diasPasados;
        
                // dolares totales a devolver (capital original en dolares + mitad de intereses en dolares)
                double totalDolares = dolaresOriginales + (interesesDolares / 2.0);
        
                // convertimos dolares a pesos con la cotización de hoy
                montoADevolver = totalDolares * Utilitarios.consultarCotizacion(divisa.getNombreDivisa());
                }
                cuentaUsuario.setSaldo(cuentaUsuario.getSaldo() + montoADevolver);
        }
    };

}