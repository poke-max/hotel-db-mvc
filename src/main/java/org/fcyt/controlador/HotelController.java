package org.fcyt.controlador;

import org.fcyt.modelo.Hotel;
import org.fcyt.modelo.dao.HotelDaoImpl;
import org.fcyt.modelo.tabla.HotelTableModel;
import org.fcyt.vista.GUIHotel;
import org.fcyt.vista.GUIHotelRegister;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.event.*;
import java.util.List;

public class HotelController implements ActionListener {
    GUIHotel gui;
    HotelDaoImpl abm;
    HotelTableModel model;
    GUIHotelRegister reg;
    int selectedRow;
    int selectedCol;
    int rowCount;
    int columnCount;
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
                selectedCol = gui.jTable1.getSelectedColumn();

                String selectedColumnName = gui.jTable1.getColumnName(selectedCol);

                switch (selectedColumnName) {
                    case "Editar":
                        operation = 'E';
                        abrirFormulario("EDITAR HOTEL");
                        setFormulario();
                        reg.setVisible(true);
                        break;
                    case "Borrar":
                        int ok = JOptionPane.showConfirmDialog(gui, "¿Deseas eliminar este registro?", "Confirmar", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                        if (ok == 0) {
                            Hotel h = model.getSelectedEntity(selectedRow);
                            abm.eliminar(h);
                            listar();
                        }
                        break;
                }
            }
        });

        mostrar();
        gui.jTable1.requestFocus();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == gui.btnNuevo) {
            operation = 'N';
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
                    abm.insertar(getFormulario());
                    limpiarCampos();
                    listar();
                    gui.jTable1.getSelectionModel().setSelectionInterval(rowCount - 1, rowCount - 1);
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
        List<Hotel> lista = abm.listar(gui.txtBuscar.getText());
        model.setList(lista);
        rowCount = lista.size();
        columnCount = model.getColumnCount();
        gui.jTable1.setModel(model);
        gui.jTable1.updateUI();
    }

    public void mostrar() {
        gui.jTable1.requestFocus();
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
        TableColumn deleteColumn = gui.jTable1.getColumnModel().getColumn(gui.jTable1.getColumnCount() - 1);
        TableColumn editColumn = gui.jTable1.getColumnModel().getColumn(gui.jTable1.getColumnCount() - 2);

        deleteColumn.setPreferredWidth(40);
        editColumn.setPreferredWidth(40);

        deleteColumn.setResizable(false);
        editColumn.setResizable(false);

        gui.jTable1.setDragEnabled(false);
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
