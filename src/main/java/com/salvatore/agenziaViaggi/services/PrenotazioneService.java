package com.salvatore.agenziaViaggi.services;

import com.salvatore.agenziaViaggi.entities.Dipendente;
import com.salvatore.agenziaViaggi.entities.Prenotazione;
import com.salvatore.agenziaViaggi.entities.Viaggio;
import com.salvatore.agenziaViaggi.exceptions.BadRequestException;
import com.salvatore.agenziaViaggi.payloads.PrenotazioneDTO;
import com.salvatore.agenziaViaggi.repositories.DipendentiRepository;
import com.salvatore.agenziaViaggi.repositories.PrenotazioneRepository;
import com.salvatore.agenziaViaggi.repositories.ViaggiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    }
}
