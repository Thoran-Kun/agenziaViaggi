package com.salvatore.agenziaViaggi.services;

import com.salvatore.agenziaViaggi.entities.StatoViaggio;
import com.salvatore.agenziaViaggi.entities.Viaggio;
import com.salvatore.agenziaViaggi.exceptions.NotFoundException;
import com.salvatore.agenziaViaggi.payloads.ViaggioDTO;
import com.salvatore.agenziaViaggi.repositories.ViaggiRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ViaggioService {
    private final ViaggiRepository viaggiRepository;

    @Autowired
    public ViaggioService(ViaggiRepository viaggiRepository) {
        this.viaggiRepository = viaggiRepository;
    }

    public Page<Viaggio> findAll(int page, int size, String orderBy, String sortCriteria){
        if(size > 100 || size < 0) size = 10;
        if(page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size,
                sortCriteria.equalsIgnoreCase("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.viaggiRepository.findAll(pageable);
    }

    public Viaggio saveViaggio(ViaggioDTO payload){
        Viaggio viaggio = new Viaggio();
        viaggio.setDestinazione(payload.destinazione());
        viaggio.setDataViaggio(payload.dataViaggio());
        viaggio.setStatoViaggio(payload.statoViaggio());

        Viaggio savedViaggio = viaggiRepository.save(viaggio);
        log.info("il viaggio con id: " + savedViaggio.getId() + " è stato salvato correttamente!");
        return savedViaggio;
    }

    public Viaggio findById(long id){
        return viaggiRepository.findById(id).orElseThrow(()-> new NotFoundException(id));
    }

   public Viaggio findByIdAndUpdate(long viaggioId, ViaggioDTO payload){
        Viaggio found = this.findById(viaggioId);
        found.setDestinazione(payload.destinazione());
        found.setStatoViaggio(payload.statoViaggio());
        found.setDataViaggio(payload.dataViaggio());
        return viaggiRepository.save(found);
   }

   public void findByIdAndDelete(long viaggioId){
        Viaggio found = this.findById(viaggioId);
        this.viaggiRepository.delete(found);
   }

   public Viaggio updateStatoViaggio(long viaggioId, StatoViaggio newStatoViaggio){
        Viaggio found = this.findById(viaggioId);
        found.setStatoViaggio(newStatoViaggio);
        return viaggiRepository.save(found);
   }
}
