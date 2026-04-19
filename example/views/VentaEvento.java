package views;

import controllers.EventoController;
import models.Evento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaEvento extends JFrame {

    private EventoController controller;
    private JTabbedPane pestanas;

    public JTextField txtDescription = new JTextField(15);
    public JTextField txtEmail = new JTextField(15);
    public JTextField txtDate = new JTextField(8);

    public JRadioButton rbDaily = new JRadioButton("Daily");
    public JRadioButton rbWeekly = new JRadioButton("Weekly");
    public JRadioButton rbMonthly = new JRadioButton("Monthly", true);

    public JCheckBox chkAlarm = new JCheckBox("Alarm");

    public JButton btnSave = new JButton("Save");
    public JButton btnClean = new JButton("Clean");

    public JTable tablaEventos;
    public DefaultTableModel modeloTabla;

    public JTable tablaEliminar;
    public DefaultTableModel modeloEliminar;

    public JButton btnRemover = new JButton("Remover");
    public JButton btnSeleccionarTodos = new JButton("Seleccionar Todos");
    public JButton btnCancelar = new JButton("Cancel");

    public JTextField txtNombreInv = new JTextField(15);
    public JTextField txtTelefonoInv = new JTextField(15);
    public JButton btnRegistrarInv = new JButton("Registrar");

    public VentanaEvento() {

        controller = new EventoController();

        setTitle("Event Manager");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pestanas = new JTabbedPane();

        pestanas.addTab("New event", crearPanelNuevo());
        pestanas.addTab("Events", crearPanelListar());
        pestanas.addTab("Remove Event", crearPanelEliminar());
        pestanas.addTab("Registrar invitado", crearPanelInvitado());

        add(pestanas);
        setLocationRelativeTo(null);

        btnSave.addActionListener(e -> guardarEvento());
        btnClean.addActionListener(e -> limpiarCampos());
        btnSeleccionarTodos.addActionListener(e -> seleccionarTodos());
        btnCancelar.addActionListener(e -> cancelarSeleccion());
        btnRemover.addActionListener(e -> eliminarEventos());
        btnRegistrarInv.addActionListener(e -> registrarInvitado());
    }

    private JPanel crearPanelNuevo() {
        JPanel panel = new JPanel(null);

        JLabel lblDesc = new JLabel("Event description");
        lblDesc.setBounds(50, 50, 120, 25);
        txtDescription.setBounds(180, 50, 200, 25);

        JLabel lblMail = new JLabel("Forward e-mail");
        lblMail.setBounds(50, 90, 120, 25);
        txtEmail.setBounds(180, 90, 200, 25);

        JLabel lblDate = new JLabel("Date");
        lblDate.setBounds(50, 130, 120, 25);
        txtDate.setBounds(180, 130, 100, 25);

        JLabel lblFreq = new JLabel("Frequency");
        lblFreq.setBounds(50, 170, 120, 25);

        rbDaily.setBounds(180, 170, 70, 25);
        rbWeekly.setBounds(260, 170, 80, 25);
        rbMonthly.setBounds(350, 170, 80, 25);

        ButtonGroup gp = new ButtonGroup();
        gp.add(rbDaily);
        gp.add(rbWeekly);
        gp.add(rbMonthly);

        chkAlarm.setBounds(50, 220, 100, 25);

        btnSave.setBounds(150, 220, 100, 30);
        btnClean.setBounds(270, 220, 100, 30);

        panel.add(lblDesc);
        panel.add(txtDescription);
        panel.add(lblMail);
        panel.add(txtEmail);
        panel.add(lblDate);
        panel.add(txtDate);
        panel.add(lblFreq);
        panel.add(rbDaily);
        panel.add(rbWeekly);
        panel.add(rbMonthly);
        panel.add(chkAlarm);
        panel.add(btnSave);
        panel.add(btnClean);

        return panel;
    }

    private JPanel crearPanelListar() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] col = {"Date", "Description", "Frequency", "E-mail", "Alarm"};
        modeloTabla = new DefaultTableModel(col, 0);
        tablaEventos = new JTable(modeloTabla);

        panel.add(new JScrollPane(tablaEventos), BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelEliminar() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] col = {"Date", "Description", "Frequency", "E-mail", "Alarm", "Eliminar"};

        modeloEliminar = new DefaultTableModel(col, 0) {
            @Override
            public Class<?> getColumnClass(int c) {
                if (c == 5) {
                    return Boolean.class;
                }
                return String.class;
            }
        };

        tablaEliminar = new JTable(modeloEliminar);

        panel.add(new JScrollPane(tablaEliminar), BorderLayout.CENTER);

        JPanel abajo = new JPanel();
        abajo.add(btnSeleccionarTodos);
        abajo.add(btnCancelar);
        abajo.add(btnRemover);

        panel.add(abajo, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelInvitado() {

        JPanel panel = new JPanel(null);

        JLabel lblNombre = new JLabel("Ingrese Nombre:");
        lblNombre.setBounds(50, 40, 150, 25);
        txtNombreInv.setBounds(200, 40, 250, 25);

        JLabel lblCel = new JLabel("Ingrese número celular:");
        lblCel.setBounds(50, 80, 150, 25);
        txtTelefonoInv.setBounds(200, 80, 250, 25);

        btnRegistrarInv.setBounds(200, 130, 120, 30);

        panel.add(lblNombre);
        panel.add(txtNombreInv);
        panel.add(lblCel);
        panel.add(txtTelefonoInv);
        panel.add(btnRegistrarInv);

        return panel;
    }

    private void guardarEvento() {

        String nombre = txtDescription.getText().trim();
        String fecha = txtDate.getText().trim();
        String email = txtEmail.getText().trim();

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese descripción");
            return;
        }

        if (fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese fecha");
            return;
        }

        String frecuencia = "Monthly";

        if (rbDaily.isSelected()) {
            frecuencia = "Daily";
        } else if (rbWeekly.isSelected()) {
            frecuencia = "Weekly";
        }

        boolean alarma = chkAlarm.isSelected();

        Evento evento = controller.agregarEvento(nombre, fecha);

        modeloTabla.addRow(new Object[]{fecha, nombre, frecuencia, email, alarma});
        modeloEliminar.addRow(new Object[]{fecha, nombre, frecuencia, email, alarma, false});

        JOptionPane.showMessageDialog(this, "Evento guardado");

        limpiarCampos();
    }

    private void eliminarEventos() {

        for (int i = modeloEliminar.getRowCount() - 1; i >= 0; i--) {

            Boolean seleccionado = (Boolean) modeloEliminar.getValueAt(i, 5);

            if (seleccionado != null && seleccionado) {

                Evento evento = controller.getEventos().get(i);

                controller.eliminarEvento(evento);

                modeloEliminar.removeRow(i);
                modeloTabla.removeRow(i);
            }
        }
    }

    private void seleccionarTodos() {
        for (int i = 0; i < modeloEliminar.getRowCount(); i++) {
            modeloEliminar.setValueAt(true, i, 5);
        }
    }

    private void cancelarSeleccion() {
        for (int i = 0; i < modeloEliminar.getRowCount(); i++) {
            modeloEliminar.setValueAt(false, i, 5);
        }
    }

    private void registrarInvitado() {

        int fila = tablaEventos.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un evento");
            return;
        }

        String nombreInv = txtNombreInv.getText().trim();

        if (nombreInv.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese nombre");
            return;
        }

        Evento evento = controller.getEventos().get(fila);

        controller.agregarInvitado(evento, nombreInv);

        JOptionPane.showMessageDialog(this, "Invitado registrado");

        txtNombreInv.setText("");
        txtTelefonoInv.setText("");
    }

    private void limpiarCampos() {
        txtDescription.setText("");
        txtEmail.setText("");
        txtDate.setText("");

        rbMonthly.setSelected(true);
        chkAlarm.setSelected(false);
    }
}

