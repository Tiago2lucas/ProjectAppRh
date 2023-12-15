package com.AppRh.AppRh.models;


import java.io.Serializable;
import java.util.List;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;



public class Vaga implements Serializable {


	private static final long serialVersionUID = 1L ;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long codigo;

}