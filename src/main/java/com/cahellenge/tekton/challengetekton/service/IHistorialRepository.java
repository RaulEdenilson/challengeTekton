package com.cahellenge.tekton.challengetekton.service;

import com.cahellenge.tekton.challengetekton.model.HistorialEntryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IHistorialRepository
        extends JpaRepository<HistorialEntryEntity, Long> {

}