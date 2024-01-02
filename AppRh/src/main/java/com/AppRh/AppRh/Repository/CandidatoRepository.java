package com.AppRh.AppRh.Repository;
import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.AppRh.AppRh.models.Candidato;
import com.AppRh.AppRh.models.Vaga;


public interface CandidatoRepository extends CrudRepository<Candidato, String> {

	//Consulta o Coleção de candidato, vinculado a vaga
	Iterable<Candidato> findByVaga (Vaga vaga); 
	//Consulta o candido pelo Rg
	Candidato findByRg (String rg);
	
	//Consulta o candido pelo Id
	Candidato findById (long Id);
	
	//Consulta uma List Candidado pelo nome
	List<Candidato> findByNomeCandidato (String NomeCandidato );

	
}
