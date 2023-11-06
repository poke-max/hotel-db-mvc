package org.fcyt.modelo.dao;

import org.fcyt.modelo.Conexion;
import org.fcyt.modelo.Hotel;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HotelDaoImpl implements dao<Hotel> {
    Connection con;
    PreparedStatement st;

    public HotelDaoImpl() {
        con = Conexion.Conectar();
        try{
        Statement statement = con.createStatement();
        statement.execute("select nextval('\"Hotel_id_seq\"')");
        } catch (Exception ignored) {}
    }

    @Override
    public List<Hotel> listar(String value) {
        List<Hotel> lista = new ArrayList<>();
        String cSQL = "select * from hotel where nombre ilike '%" + value + "%' order by id";
        try {
            st = con.prepareStatement(cSQL);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Hotel h = new Hotel();
                h.setId(rs.getInt("id"));
                h.setNombre(rs.getString("nombre"));
                h.setTelefono(rs.getString("telefono"));
                h.setDireccion(rs.getString("direccion"));
                lista.add(h);
            }
        } catch (Exception ignored) {
        }
        return lista;
    }

    @Override
    public void insertar(Hotel h) {
        String cSQL = "insert into hotel(nombre,direccion,telefono) values(?,?,?)";
        try {
            st = con.prepareStatement(cSQL);
            st.setString(1, h.getNombre());
            st.setString(2, h.getDireccion());
            st.setString(3, h.getTelefono());
            st.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(Hotel.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    @Override
    public void modificar(Hotel h) {
        String cSQL = "update hotel set nombre=?, direccion=?, telefono=? where id=?";
        try {
            st = con.prepareStatement(cSQL);
            st.setString(1, h.getNombre());
            st.setString(2, h.getDireccion());
            st.setString(3, h.getTelefono());
            st.setInt(4, h.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(Hotel.class.getName()).log(Level.SEVERE, null, e);
        }

    }

    @Override
    public void eliminar(Hotel h) {
        String cSQL = "delete from hotel where id=?";
        try {
            st = con.prepareStatement(cSQL);
            st.setInt(1, h.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            Logger.getLogger(Hotel.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public int siguienteId() {
        String cSQL = "select currval('\"Hotel_id_seq\"')";
        int id = 0;
        try {
            st = con.prepareStatement(cSQL);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }
}
