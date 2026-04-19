package models;

import java.util.ArrayList;


public class Evento {
    private String Evento;
    private String Fecha;
    private ArrayList<String> invitados;

    public Evento(String nombre, String fecha) {
        this.nombre = nombre;
        this.fecha = fecha;
        this.invitados = new ArrayList<>();

    }

    public void agregarInvitado(String invitado) {
        invitados.add(invitado);
    }

    public String getNombre() {
        return nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public ArrayList<String> getInvitados() {
        return invitados;
    }
}
