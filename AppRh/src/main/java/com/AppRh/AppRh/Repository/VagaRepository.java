package com.AppRh.AppRh.Repository;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.AppRh.AppRh.models.Vaga;

//Interface Feita para CRUD  Banco de dados Para Vaga

public interface VagaRepository extends CrudRepository<Vaga, String> {

	// Retorna  a Vaga pelo Código
Vaga findByCodigo(long codigo);
	//Retorna a Vaga pelo nome
List<Vaga> findByNome(String nome);	
	
	
	
}
