package org.fcyt;

import org.fcyt.controlador.HotelController;
import org.fcyt.modelo.dao.HotelDaoImpl;
import org.fcyt.vista.GUIHotel;

import javax.swing.*;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        new HotelController(new HotelDaoImpl(), new GUIHotel(new JFrame(), true));
    }
}
