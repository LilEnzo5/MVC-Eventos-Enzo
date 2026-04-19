package models;

import java.util.ArrayList;

public class Evento {

    private String nombre;
    private String fecha;
    private ArrayList<String> invitados;

    public Evento(String nombre, String fecha) {
        this.nombre = nombre;
        this.fecha = fecha;
        invitados = new ArrayList<>();
    }

    public void agregarInvitado(String invitado) {
        if (invitado != null && !invitado.isEmpty()) {
            invitados.add(invitado);
        }
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