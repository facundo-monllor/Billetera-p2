package Diagrama;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Cuenta {
    
    private String CVU;
    private String alias;
    private Double saldo;
    private List<Operacion> listaOperaciones;

    // Constructor
    public Cuenta(String alias) {
        if (alias == null || alias.isEmpty()) throw new RuntimeException("El alias es obligatorio");

        // this.CVU = Utilitarios.generarSiguienteCvu();
        this.alias = alias;
        this.saldo = 0.00;

        this.listaOperaciones = new ArrayList<>();
    }
 
    // Getters
    public String getCVU(){
        return CVU;
    }
    public String getAlias(){
        return alias;
    }
    public Double getSaldo(){
        return saldo;
    }
    public List<Operacion> getListaOperaciones(){
        return listaOperaciones;
    }

    // Setters
    public void setAlias(String newAlias){
        alias = newAlias;
    }
     public void setSaldo(Double newSaldo){
        saldo = newSaldo;
    }

    // to String
    @Override
    public String toString() {
        return "CVU: " + CVU + " alias: " + alias + " saldo: " + saldo + "listaOperaciones:" + listaOperaciones;
    }
    
    // Metodos
    public abstract void operar();

    // public List<Double> obtenerComisiones() {
    // }

    // public Void invertirDinero(Inversion inversion) {
    // }

    // public List<Operacion> getListaOperaciones() {
    // }
      
}
