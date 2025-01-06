package com.example.Economapa.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Economapa.models.PromocaoModel;
@Repository
public interface PromocaoRepository extends JpaRepository<PromocaoModel, Integer>{

}
