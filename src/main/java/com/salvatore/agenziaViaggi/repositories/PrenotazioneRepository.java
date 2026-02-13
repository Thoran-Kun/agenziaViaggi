package com.salvatore.agenziaViaggi.repositories;

import com.salvatore.agenziaViaggi.entities.Prenotazione;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione, Long> {
    boolean existsByDipendenteIdAndViaggio_DataViaggio(Long dipendenteId, LocalDate dataViaggio);
}
