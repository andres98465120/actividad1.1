package com.iudigital.view;

import com.iudigital.controller.FuncionarioController;
import com.iudigital.domain.Funcionario;
import com.iudigital.exceptions.DatabaseException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FuncionarioForm extends JFrame {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    private FuncionarioController controller;

    private JTextField txtId;
    private JComboBox<String> cbTipoDocumento;
    private JTextField txtNumeroDocumento;
    private JTextField txtNombres;
    private JTextField txtApellidos;
    private JComboBox<String> cbEstadoCivil;
    private JComboBox<String> cbSexo;
    private JTextField txtDireccion;
    private JTextField txtTelefono;
    private JFormattedTextField txtFechaNacimiento;

    private JTable tblFuncionarios;
    private DefaultTableModel tableModel;

    private JButton btnCrear;
    private JButton btnActualizar;
    private JButton btnEliminar;
    private JButton btnLimpiar;

    public FuncionarioForm() {
        controller = new FuncionarioController();
        initComponents();
        loadFuncionarios();
    }

    private void initComponents() {
        setTitle("Gestión de Funcionarios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;


        int y = 0;

        gbc.gridx = 0; gbc.gridy = y;
        panelFormulario.add(new JLabel("ID:"), gbc);
        txtId = new JTextField(5);
        txtId.setEditable(false);
        gbc.gridx = 1;
        panelFormulario.add(txtId, gbc);

        gbc.gridx = 0; gbc.gridy = ++y;
        panelFormulario.add(new JLabel("Tipo Documento:"), gbc);
        cbTipoDocumento = new JComboBox<>(new String[]{"CC", "TI", "CE", "PA"});
        gbc.gridx = 1;
        panelFormulario.add(cbTipoDocumento, gbc);

        gbc.gridx = 0; gbc.gridy = ++y;
        panelFormulario.add(new JLabel("Número Documento:"), gbc);
        txtNumeroDocumento = new JTextField(15);
        gbc.gridx = 1;
        panelFormulario.add(txtNumeroDocumento, gbc);

        gbc.gridx = 0; gbc.gridy = ++y;
        panelFormulario.add(new JLabel("Nombres:"), gbc);
        txtNombres = new JTextField(20);
        gbc.gridx = 1;
        panelFormulario.add(txtNombres, gbc);

        gbc.gridx = 0; gbc.gridy = ++y;
        panelFormulario.add(new JLabel("Apellidos:"), gbc);
        txtApellidos = new JTextField(20);
        gbc.gridx = 1;
        panelFormulario.add(txtApellidos, gbc);

        gbc.gridx = 0; gbc.gridy = ++y;
        panelFormulario.add(new JLabel("Estado Civil:"), gbc);
        cbEstadoCivil = new JComboBox<>(new String[]{"Soltero", "Casado", "Divorciado", "Viudo"});
        gbc.gridx = 1;
        panelFormulario.add(cbEstadoCivil, gbc);

        gbc.gridx = 0; gbc.gridy = ++y;
        panelFormulario.add(new JLabel("Sexo:"), gbc);
        cbSexo = new JComboBox<>(new String[]{"M", "F"});
        gbc.gridx = 1;
        panelFormulario.add(cbSexo, gbc);

        gbc.gridx = 0; gbc.gridy = ++y;
        panelFormulario.add(new JLabel("Dirección:"), gbc);
        txtDireccion = new JTextField(30);
        gbc.gridx = 1;
        panelFormulario.add(txtDireccion, gbc);

        gbc.gridx = 0; gbc.gridy = ++y;
        panelFormulario.add(new JLabel("Teléfono:"), gbc);
        txtTelefono = new JTextField(15);
        gbc.gridx = 1;
        panelFormulario.add(txtTelefono, gbc);

        gbc.gridx = 0; gbc.gridy = ++y;
        panelFormulario.add(new JLabel("Fecha Nacimiento (yyyy-MM-dd):"), gbc);
        txtFechaNacimiento = new JFormattedTextField(DATE_FORMAT);
        txtFechaNacimiento.setColumns(15);
        gbc.gridx = 1;
        panelFormulario.add(txtFechaNacimiento, gbc);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        btnCrear = new JButton("Crear");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");
        btnLimpiar = new JButton("Limpiar");
        panelBotones.add(btnCrear);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // Tabla
        tableModel = new DefaultTableModel(new String[]{
                "ID", "Tipo Doc", "Número Doc", "Nombres", "Apellidos",
                "Estado Civil", "Sexo", "Dirección", "Teléfono", "Fecha Nacimiento"
        }, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblFuncionarios = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tblFuncionarios);

        // Paneles
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        setContentPane(panelPrincipal);

        // Eventos
        btnCrear.addActionListener(e -> crearFuncionario());
        btnActualizar.addActionListener(e -> actualizarFuncionario());
        btnEliminar.addActionListener(e -> eliminarFuncionario());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        tblFuncionarios.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int fila = tblFuncionarios.getSelectedRow();
                if (fila != -1) {
                    cargarFuncionarioDesdeTabla(fila);
                }
            }
        });
    }

    private void loadFuncionarios() {
        try {
            List<Funcionario> funcionarios = controller.getFuncionarios();
            tableModel.setRowCount(0);
            for (Funcionario f : funcionarios) {
                String sexoMostrado;
                switch (f.getSexo()) {
                    case "M":
                        sexoMostrado = "Masculino";
                        break;
                    case "F":
                        sexoMostrado = "Femenino";
                        break;
                    default:
                        sexoMostrado = "Otro";
                        break;
                }

                tableModel.addRow(new Object[]{
                        f.getId(),
                        f.getTipoDocumento(),
                        f.getNumeroDocumento(),
                        f.getNombres(),
                        f.getApellidos(),
                        f.getEstadoCivil(),
                        sexoMostrado,
                        f.getDireccion(),
                        f.getTelefono(),
                        DATE_FORMAT.format(f.getFechaNacimiento())
                });
            }
        } catch (DatabaseException e) {
            JOptionPane.showMessageDialog(this, "Error cargando funcionarios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void crearFuncionario() {
        try {
            Funcionario funcionario = obtenerFuncionarioDesdeFormulario(false);
            controller.createFuncionario(funcionario);
            JOptionPane.showMessageDialog(this, "Funcionario creado correctamente.");
            limpiarFormulario();
            loadFuncionarios();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error creando funcionario: " + e.getClass().getName() + " - " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void actualizarFuncionario() {
        try {
            Funcionario funcionario = obtenerFuncionarioDesdeFormulario(true);
            controller.updateFuncionario(funcionario);
            JOptionPane.showMessageDialog(this, "Funcionario actualizado");
            limpiarFormulario();
            loadFuncionarios();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error actualizando funcionario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarFuncionario() {
        String idStr = txtId.getText().trim();
        if (idStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Seleccione un funcionario para eliminar", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Seguro que desea eliminar el funcionario con ID " + idStr + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                int id = Integer.parseInt(idStr);
                controller.deleteFuncionario(id);
                JOptionPane.showMessageDialog(this, "Funcionario eliminado");
                limpiarFormulario();
                loadFuncionarios();
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error eliminando funcionario: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiarFormulario() {
        txtId.setText("");
        cbTipoDocumento.setSelectedIndex(0);
        txtNumeroDocumento.setText("");
        txtNombres.setText("");
        txtApellidos.setText("");
        cbEstadoCivil.setSelectedIndex(0);
        cbSexo.setSelectedIndex(0);
        txtDireccion.setText("");
        txtTelefono.setText("");
        txtFechaNacimiento.setText("");
        tblFuncionarios.clearSelection();
    }

    private Funcionario obtenerFuncionarioDesdeFormulario(boolean conId) throws Exception {
        int id = 0;
        if (conId) {
            try {
                id = Integer.parseInt(txtId.getText());
            } catch (NumberFormatException e) {
                throw new Exception("ID inválido");
            }
        }

        String tipoDocumento = (String) cbTipoDocumento.getSelectedItem();
        String numeroDocumento = txtNumeroDocumento.getText().trim();
        String nombres = txtNombres.getText().trim();
        String apellidos = txtApellidos.getText().trim();
        String estadoCivil = (String) cbEstadoCivil.getSelectedItem();
        String sexo = (String) cbSexo.getSelectedItem();
        String direccion = txtDireccion.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String fechaStr = txtFechaNacimiento.getText().trim();

        if (numeroDocumento.isEmpty() || nombres.isEmpty() || apellidos.isEmpty() || fechaStr.isEmpty()) {
            throw new Exception("Por favor complete todos los campos obligatorios");
        }

        Date fechaNacimiento;
        try {
            fechaNacimiento = DATE_FORMAT.parse(fechaStr);
        } catch (Exception e) {
            throw new Exception("Fecha de nacimiento inválida. Use formato yyyy-MM-dd");
        }

        if (conId) {
            return new Funcionario(id, tipoDocumento, numeroDocumento, nombres, apellidos,
                    estadoCivil, sexo, direccion, telefono, fechaNacimiento);
        } else {
            return new Funcionario(tipoDocumento, numeroDocumento, nombres, apellidos,
                    estadoCivil, sexo, direccion, telefono, fechaNacimiento);
        }
    }

    private void cargarFuncionarioDesdeTabla(int fila) {
        txtId.setText(tableModel.getValueAt(fila, 0).toString());
        cbTipoDocumento.setSelectedItem(tableModel.getValueAt(fila, 1));
        txtNumeroDocumento.setText(tableModel.getValueAt(fila, 2).toString());
        txtNombres.setText(tableModel.getValueAt(fila, 3).toString());
        txtApellidos.setText(tableModel.getValueAt(fila, 4).toString());
        cbEstadoCivil.setSelectedItem(tableModel.getValueAt(fila, 5));

        String sexoTexto = tableModel.getValueAt(fila, 6).toString();
        if (sexoTexto.equalsIgnoreCase("Masculino")) {
            cbSexo.setSelectedItem("M");
        } else if (sexoTexto.equalsIgnoreCase("Femenino")) {
            cbSexo.setSelectedItem("F");
        } else {
            cbSexo.setSelectedItem("O");
        }

        txtDireccion.setText(tableModel.getValueAt(fila, 7).toString());
        txtTelefono.setText(tableModel.getValueAt(fila, 8).toString());
        txtFechaNacimiento.setText(tableModel.getValueAt(fila, 9).toString());
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FuncionarioForm().setVisible(true));
    }
}
