package dao;

import java.util.ArrayList;

public interface GenericDAO<T> {

	ArrayList<T> cargar();

	int update(T t);

	T encontrarPorId(Integer i);

}
