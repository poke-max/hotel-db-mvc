package org.fcyt.modelo.tabla;

import org.fcyt.modelo.Hotel;
import javax.swing.table.AbstractTableModel;
import java.util.List;

public class HotelTableModel extends AbstractTableModel {

    List<Hotel> lista;
    String[] columnName = {"Id", "Nombre", "Direccion", "Teléfono", "Editar", "Borrar"};

    public void setColumnName(String[] columnName) {
        this.columnName = columnName;
    }

    public String getColumnName(int index) {
        return columnName[index];
    }

    public void setList(List<Hotel> lista) {
        this.lista = lista;
    }

    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return columnName.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Hotel h = lista.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> h.getId();
            case 1 -> h.getNombre();
            case 2 -> h.getDireccion();
            case 3 -> h.getTelefono();
            case 4 -> "editar";
            case 5 -> "borrar";
            default -> "";
        };
    }

    public Hotel getSelectedEntity(int index) {
        return lista.get(index);
    }


}
