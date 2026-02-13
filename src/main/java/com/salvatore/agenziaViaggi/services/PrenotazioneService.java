package com.salvatore.agenziaViaggi.services;

import com.salvatore.agenziaViaggi.entities.Dipendente;
import com.salvatore.agenziaViaggi.entities.Prenotazione;
import com.salvatore.agenziaViaggi.entities.Viaggio;
import com.salvatore.agenziaViaggi.exceptions.BadRequestException;
import com.salvatore.agenziaViaggi.exceptions.NotFoundException;
import com.salvatore.agenziaViaggi.payloads.PrenotazioneDTO;
import com.salvatore.agenziaViaggi.repositories.DipendentiRepository;
import com.salvatore.agenziaViaggi.repositories.PrenotazioneRepository;
import com.salvatore.agenziaViaggi.repositories.ViaggiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PrenotazioneService {

    private final PrenotazioneRepository prenotazioneRepository;

    private final DipendentiService dipendentiService;

    private final ViaggioService viaggioService;

    @Autowired
    public PrenotazioneService(PrenotazioneRepository prenotazioneRepository, DipendentiService dipendentiService, ViaggioService viaggioService) {
        this.prenotazioneRepository = prenotazioneRepository;
        this.dipendentiService = dipendentiService;
        this.viaggioService = viaggioService;
    }

    public Prenotazione savePrenotazione(PrenotazioneDTO payload){
        //recupero il dipendente e il viaggio
        Dipendente dipendente = dipendentiService.findById(payload.dipendenteId());
        Viaggio viaggio = viaggioService.findById(payload.viaggioId());
        //controllo se il dipendente ha già un viaggio per quella data
        if(prenotazioneRepository.existsByDipendenteIdAndViaggio_DataViaggio(
                dipendente.getId(), viaggio.getDataViaggio())){
            throw new BadRequestException("il dipendente " + dipendente.getUsername() +
                    " ha già un viaggio prenotato in data: " + viaggio.getDataViaggio());
        }
        // nel caso tutto fila liscio creo la prenotazione
        Prenotazione prenotazione = new Prenotazione();
        prenotazione.setDipendente(dipendente);
        prenotazione.setViaggio(viaggio);
        prenotazione.setNote(payload.note());
        prenotazione.setDataPrenotazione(LocalDate.now());

        return prenotazioneRepository.save(prenotazione);
    }

    public Page<Prenotazione> findAll(int page, int size, String orderBy, String sortCriteria){
        if(size > 100 || size < 0) size = 10;
        if(page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size,
                sortCriteria.equalsIgnoreCase("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.prenotazioneRepository.findAll(pageable);
    }

    public Prenotazione findById(long id) {
        return prenotazioneRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }

    public void findByIdAndDelete(long prenotazioneId) {
        Prenotazione found = this.findById(prenotazioneId);
        this.prenotazioneRepository.delete(found);
    }
}
