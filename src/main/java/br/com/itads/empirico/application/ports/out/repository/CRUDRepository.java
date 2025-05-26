package br.com.itads.empirico.application.ports.out.repository;

public interface CRUDRepository<T, ID> {

	T saveOrUpdate(T t);
	
	T getBy(ID id);
	
}
