package com.salvatore.agenziaViaggi.repositories;

import com.salvatore.agenziaViaggi.entities.Viaggio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ViaggiRepository extends JpaRepository<Viaggio, Long> {
    //ho tutti i metodi base delle query, posso però implementare una ricerca specifica per la destinazione
    List<Viaggio> findByDestinazione(String destinazione);
}
