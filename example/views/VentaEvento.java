package views;

import controllers.EventoController;
import models.Evento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaEvento extends JFrame {

    private EventoController controller;
    private JTabbedPane pestañas;

    // EVENTO
    public JTextField txtDescription = new JTextField(15);
    public JTextField txtEmail = new JTextField(15);
    public JTextField txtDate = new JTextField(8);
    public JRadioButton rbDaily = new JRadioButton("Daily");
    public JRadioButton rbWeekly = new JRadioButton("Weekly");
    public JRadioButton rbMonthly = new JRadioButton("Monthly", true);
    public JCheckBox chkAlarm = new JCheckBox("Alarm");
    public JButton btnSave = new JButton("Save");
    public JButton btnClean = new JButton("Clean");

    // TABLA EVENTOS
    public JTable tablaEventos;
    public DefaultTableModel modeloTabla;

    // ELIMINAR
    public JTable tablaEliminar;
    public DefaultTableModel modeloEliminar;
    public JButton btnRemover = new JButton("Remover");
    public JButton btnSeleccionarTodos = new JButton("Seleccionar Todos");
    public JButton btnCancelar = new JButton("Cancel");

    // INVITADO
    public JTextField txtNombreInv = new JTextField(15);
    public JTextField txtTelefonoInv = new JTextField(15);
    public JButton btnRegistrarInv = new JButton("Registrar");

    public VentanaEvento() {

        controller = new EventoController();

        setTitle("Event Manager");
        setSize(650, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pestañas = new JTabbedPane();
        pestañas.addTab("New event", crearPanelNuevo());
        pestañas.addTab("Events", crearPanelListar());
        pestañas.addTab("Remove Event", crearPanelEliminar());
        pestañas.addTab("Registrar invitado", crearPanelInvitado());

        add(pestañas);
        setLocationRelativeTo(null);

        // EVENTOS
        btnSave.addActionListener(e -> guardarEvento());
        btnClean.addActionListener(e -> limpiarCampos());
        btnSeleccionarTodos.addActionListener(e -> seleccionarTodos());
        btnCancelar.addActionListener(e -> cancelarSeleccion());
        btnRemover.addActionListener(e -> eliminarEventos());
        btnRegistrarInv.addActionListener(e -> registrarInvitado());
    }

    // NUEVO EVENTO
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
        gp.add(rbDaily); gp.add(rbWeekly); gp.add(rbMonthly);

        chkAlarm.setBounds(50, 220, 100, 25);
        btnSave.setBounds(150, 220, 100, 30);
        btnClean.setBounds(270, 220, 100, 30);

        panel.add(lblDesc); panel.add(txtDescription);
        panel.add(lblMail); panel.add(txtEmail);
        panel.add(lblDate); panel.add(txtDate);
        panel.add(lblFreq); panel.add(rbDaily); panel.add(rbWeekly); panel.add(rbMonthly);
        panel.add(chkAlarm); panel.add(btnSave); panel.add(btnClean);

        return panel;
    }

    // LISTAR
    private JPanel crearPanelListar() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] col = {"Date", "Description", "Frequency", "E-mail", "Alarm"};
        modeloTabla = new DefaultTableModel(col, 0);
        tablaEventos = new JTable(modeloTabla);

        panel.add(new JScrollPane(tablaEventos), BorderLayout.CENTER);

        return panel;
    }

    // ELIMINAR
    private JPanel crearPanelEliminar() {
        JPanel panel = new JPanel(new BorderLayout());

        String[] col = {"Date", "Description", "Frequency", "E-mail", "Alarm", "Eliminar"};
        modeloEliminar = new DefaultTableModel(col, 0) {
            @Override
            public Class<?> getColumnClass(int c) {
                return (c == 5) ? Boolean.class : String.class;
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

    // PANEL INVITADO COMPLETO
    private JPanel crearPanelInvitado() {

        JPanel panel = new JPanel(null);

        JLabel lblNombre = new JLabel("Ingrese Nombre:");
        lblNombre.setBounds(50, 40, 150, 25);
        txtNombreInv.setBounds(200, 40, 250, 25);

        JLabel lblCel = new JLabel("Ingrese número celular:");
        lblCel.setBounds(50, 80, 150, 25);
        txtTelefonoInv.setBounds(200, 80, 250, 25);

        JLabel lblGenero = new JLabel("Género:");
        lblGenero.setBounds(50, 120, 100, 25);

        JRadioButton rbMasc = new JRadioButton("Masculino", true);
        JRadioButton rbFem = new JRadioButton("Femenino");

        rbMasc.setBounds(200, 120, 100, 25);
        rbFem.setBounds(320, 120, 100, 25);

        ButtonGroup grupoGenero = new ButtonGroup();
        grupoGenero.add(rbMasc);
        grupoGenero.add(rbFem);

        JLabel lblFecha = new JLabel("Fecha de Nacimiento:");
        lblFecha.setBounds(50, 160, 150, 25);

        JComboBox<String> cbDia = new JComboBox<>();
        JComboBox<String> cbMes = new JComboBox<>();
        JComboBox<String> cbAnio = new JComboBox<>();

        for (int i = 1; i <= 31; i++) cbDia.addItem(String.valueOf(i));
        for (int i = 1; i <= 12; i++) cbMes.addItem(String.valueOf(i));
        for (int i = 1990; i <= 2025; i++) cbAnio.addItem(String.valueOf(i));

        cbDia.setBounds(200, 160, 60, 25);
        cbMes.setBounds(270, 160, 60, 25);
        cbAnio.setBounds(340, 160, 80, 25);

        JLabel lblDir = new JLabel("Dirección:");
        lblDir.setBounds(50, 200, 100, 25);

        JTextField txtDireccion = new JTextField();
        txtDireccion.setBounds(200, 200, 250, 25);

        JCheckBox chkTerminos = new JCheckBox("Acepta Términos y Condiciones");
        chkTerminos.setBounds(50, 240, 300, 25);

        btnRegistrarInv.setBounds(200, 280, 120, 30);

        panel.add(lblNombre); panel.add(txtNombreInv);
        panel.add(lblCel); panel.add(txtTelefonoInv);
        panel.add(lblGenero); panel.add(rbMasc); panel.add(rbFem);
        panel.add(lblFecha); panel.add(cbDia); panel.add(cbMes); panel.add(cbAnio);
        panel.add(lblDir); panel.add(txtDireccion);
        panel.add(chkTerminos);
        panel.add(btnRegistrarInv);

        return panel;
    }

    // GUARDAR
    private void guardarEvento() {
        String nombre = txtDescription.getText();
        String fecha = txtDate.getText();

        if (nombre.isEmpty() || fecha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete los campos");
            return;
        }

        controller.agregarEvento(nombre, fecha);

        modeloTabla.addRow(new Object[]{fecha, nombre,
                rbDaily.isSelected() ? "Daily" :
                        rbWeekly.isSelected() ? "Weekly" : "Monthly",
                txtEmail.getText(), chkAlarm.isSelected()});

        modeloEliminar.addRow(new Object[]{fecha, nombre,
                rbDaily.isSelected() ? "Daily" :
                        rbWeekly.isSelected() ? "Weekly" : "Monthly",
                txtEmail.getText(), chkAlarm.isSelected(), false});

        JOptionPane.showMessageDialog(this, "Evento guardado");
    }

    private void eliminarEventos() {
        for (int i = modeloEliminar.getRowCount() - 1; i >= 0; i--) {
            if ((boolean) modeloEliminar.getValueAt(i, 5)) {
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
            JOptionPane.showMessageDialog(this, "Seleccione un evento primero");
            return;
        }

        Evento evento = controller.getEventos().get(fila);
        controller.agregarInvitado(evento, txtNombreInv.getText());

        JOptionPane.showMessageDialog(this, "Invitado registrado");

        txtNombreInv.setText("");
        txtTelefonoInv.setText("");
    }

    private void limpiarCampos() {
        txtDescription.setText("");
        txtEmail.setText("");
        txtDate.setText("");
    }

}
