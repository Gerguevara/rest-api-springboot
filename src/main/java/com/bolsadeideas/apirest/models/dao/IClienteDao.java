package com.bolsadeideas.apirest.models.dao;

import com.bolsadeideas.apirest.models.entity.Cliente;
import com.bolsadeideas.apirest.models.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface IClienteDao  extends JpaRepository<Cliente, Long> {


    @Query("from Region")
    public List<Region> findAllRegiones();
}

/*
* LA interfaz repository esta por encima de todas, pero haoy mas interfaces con mas especificidad que heredadn de CRud Repository
* JPARepósitor hereda de PAginAndSortingRepository
* */