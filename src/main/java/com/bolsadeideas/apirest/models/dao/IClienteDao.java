package com.bolsadeideas.apirest.models.dao;

import com.bolsadeideas.apirest.models.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;


public interface IClienteDao  extends JpaRepository<Cliente, Long> {
}

/*
* LA interfaz repository esta por encima de todas, pero haoy mas interfaces con mas especificidad que heredadn de CRud Repository
* JPARepósitor hereda de PAginAndSortingRepository
* */