package org.fcyt.modelo.dao;

import java.util.List;

public interface dao<T> {
    public List<T> listar(String value);

    public void insertar(T t);

    public void modificar(T t);

    public void eliminar(T t);
}
