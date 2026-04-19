package controllers;

import models.Evento;
import java.util.ArrayList;

public class EventoController {

    private ArrayList<Evento> listaEventos;

    public EventoController() {
        listaEventos = new ArrayList<>();
    }

    public void agregarEvento(String nombre, String fecha) {
        Evento nuevo = new Evento(nombre, fecha);
        listaEventos.add(nuevo);
    }

    public ArrayList<Evento> getEventos() {
        return listaEventos;
    }

    public void eliminarEvento(Evento evento) {
        if (evento != null) {
            listaEventos.remove(evento);
        }
    }

    public void agregarInvitado(Evento evento, String invitado) {
        if (evento != null && invitado != null && !invitado.trim().isEmpty()) {
            evento.agregarInvitado(invitado);
        }
    }
}
