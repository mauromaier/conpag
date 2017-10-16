package br.com.conpag.controller;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Table;

import br.com.conpag.controller.sistema.LogController;



public class BaseController<T> {

	@PersistenceContext
	protected EntityManager em;

	@Inject
	Connection connection;

	@Inject
	private LogController logController;
	
	private Class<T> persistentClass;

	@SuppressWarnings("unchecked")
	@PostConstruct
	private void init() {
		Type t = getClass().getGenericSuperclass();
		ParameterizedType pt = (ParameterizedType) t;
		persistentClass = (Class<T>) pt.getActualTypeArguments()[0];
	}

	public void insert(T t) {
		this.em.persist(t);
	}

	public void update(T t) {
		this.em.merge(t);
	}

	public void delete(int id) {
		T t = (T) this.em.getReference(this.persistentClass, id);
		this.em.remove(t);
	}

	public T getById(int id) {
		return this.em.find(this.persistentClass, id);
	}

	@SuppressWarnings("unchecked")
	public List<T> getAll() {
		return this.em.createQuery("SELECT t FROM " + this.persistentClass.getName() + " t").getResultList();
	}

	public boolean desativa(Object id) {
		PreparedStatement ps = null;
		try {
			if (persistentClass.isAnnotationPresent(Table.class)) {
				Table t = (Table) persistentClass.getAnnotation(Table.class);
				String tabela = t.name();
				String sql = String.format("update %s set fg_excluido = 1 where cd_id = ?", tabela);

				if (id != null) {
					ps = connection.prepareStatement(sql);
					ps.setObject(1, id);
					ps.execute();
					return true;
				}
			}
		} catch (Exception e) {
			logController.printLog(e, true);
		}
		return false;
	}

	public T getReference(long id) {
		return (T) em.getReference(this.persistentClass, id);

	}
}
