package org.fcyt.controlador;

import org.fcyt.modelo.Hotel;
import org.fcyt.modelo.dao.HotelDaoImpl;
import org.fcyt.modelo.tabla.HotelTableModel;
import org.fcyt.vista.GUIHotel;
import org.fcyt.vista.GUIHotelRegister;

import javax.swing.*;
import java.awt.event.*;

public class HotelController implements ActionListener {
    GUIHotel gui;
    HotelDaoImpl abm;
    HotelTableModel model;
    GUIHotelRegister reg;
    int selectedRow;
    char operation;

    public HotelController(HotelDaoImpl dao, GUIHotel gui) {
        this.gui = gui;
        this.abm = dao;
        gui.btnNuevo.addActionListener(this);
        model = new HotelTableModel();
        listar();
        configurarTabla();
        buscar();

        gui.jTable1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                selectedRow = gui.jTable1.getSelectedRow();
                int selectedCol = gui.jTable1.getSelectedColumn();
                if (selectedCol == gui.jTable1.getColumnCount() - 2) {
                    operation = 'E';
                    abrirFormulario("EDITAR HOTEL");
                    setFormulario();
                    reg.setVisible(true);
                }
                if (selectedCol == gui.jTable1.getColumnCount() - 1) {
                    int ok = JOptionPane.showConfirmDialog(gui, "¿Deseas eliminar este registro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                    if (ok == 0) {
                        Hotel h = model.getSelectedEntity(selectedRow);
                        abm.eliminar(h);
                        listar();
                    }
                }

            }
        });

        mostrar();

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.btnNuevo) {
            operation = 'N';
            int rowCount = gui.jTable1.getRowCount();
            int ultimoId = 0;

            if (rowCount != 0) {
                ultimoId = model.getSelectedEntity(rowCount - 1).getId();
            }
            abrirFormulario("NUEVO HOTEL");
            reg.txtId.setText(String.valueOf(ultimoId + 1));
            reg.setVisible(true);
        }
        if (e.getSource() == reg.btnGuardar) {
            switch (operation) {
                case 'N':
                    Hotel h = new Hotel();
                    h.setNombre("dasd");
                    h.setTelefono("dasdas");
                    h.setDireccion("das");
                    abm.insertar(getFormulario());
                    limpiarCampos();
                    listar();
                    reg.setVisible(false);
                    break;
                case 'E':
                    abm.modificar(getFormulario());
                    limpiarCampos();
                    listar();
                    reg.setVisible(false);
                    break;
            }
        }
    }

    public void listar() {
        model.setList(abm.listar(gui.txtBuscar.getText()));
        gui.jTable1.setModel(model);
        gui.jTable1.updateUI();
    }

    public void mostrar() {
        gui.setLocationRelativeTo(null);
        gui.setVisible(true);

    }

    public void buscar() {
        gui.txtBuscar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                listar();
            }
        });

        gui.btnX.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                gui.txtBuscar.setText("");
                listar();
            }
        });
    }

    public void limpiarCampos() {
        reg.txtNombre.setText("");
        reg.txtDireccion.setText("");
        reg.txtTelefono.setText("");
    }

    public void abrirFormulario(String titulo) {
        reg = new GUIHotelRegister(new JFrame(), true);
        reg.txtId.setEnabled(false);
        reg.btnGuardar.addActionListener(this);
        reg.jLabel2.setText(titulo);
        reg.setLocationRelativeTo(null);
    }

    public void configurarTabla() {
        gui.jTable1.getColumnModel().getColumn(gui.jTable1.getColumnCount() - 1).setPreferredWidth(40);
        gui.jTable1.getColumnModel().getColumn(gui.jTable1.getColumnCount() - 2).setPreferredWidth(40);
    }

    public Hotel getFormulario() {
        Hotel h = new Hotel();
        h.setId(Integer.parseInt(reg.txtId.getText()));
        h.setNombre(reg.txtNombre.getText());
        h.setDireccion(reg.txtDireccion.getText());
        h.setTelefono(reg.txtTelefono.getText());
        return h;
    }

    public void setFormulario() {
        Hotel h = model.getSelectedEntity(selectedRow);
        reg.txtId.setText(String.valueOf(h.getId()));
        reg.txtNombre.setText(h.getNombre());
        reg.txtDireccion.setText(h.getDireccion());
        reg.txtTelefono.setText(h.getTelefono());
    }

}
