package com.salvatore.agenziaViaggi.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.salvatore.agenziaViaggi.entities.Dipendente;
import com.salvatore.agenziaViaggi.exceptions.BadRequestException;
import com.salvatore.agenziaViaggi.exceptions.NotFoundException;
import com.salvatore.agenziaViaggi.payloads.DipendenteDTO;
import com.salvatore.agenziaViaggi.payloads.ViaggioDTO;
import com.salvatore.agenziaViaggi.repositories.DipendentiRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class DipendentiService {

    private final DipendentiRepository dipendentiRepository;

    private final Cloudinary cloudinary;

    @Autowired
    public DipendentiService(DipendentiRepository dipendentiRepository, Cloudinary cloudinary){
        this.dipendentiRepository = dipendentiRepository;
        this.cloudinary = cloudinary;
    }

    public Page<Dipendente> findAll(int page, int size, String orderBy, String sortCriteria){
        if(size > 100 || size < 0) size = 10;
        if(page < 0) page = 0;
        Pageable pageable = PageRequest.of(page, size,
                sortCriteria.equalsIgnoreCase("desc") ? Sort.by(orderBy).descending() : Sort.by(orderBy));
        return this.dipendentiRepository.findAll(pageable);
    }

    public Dipendente saveDipendente(DipendenteDTO payload){
        dipendentiRepository.findByUsername(payload.username()).ifPresent(user->{
            throw new BadRequestException("L'username inserito: " + payload.username() + " è gia in uso!");
        });

        Dipendente dipendente = new Dipendente();
        dipendente.setUsername(payload.username());
        dipendente.setNome(payload.nome());
        dipendente.setCognome(payload.cognome());
        dipendente.setEmail(payload.email());
        Dipendente savedDipendente = dipendentiRepository.save(dipendente);
        log.info("l'utente " + savedDipendente.getUsername() + " è stato salvato correttamente!");
        return savedDipendente;
    }

    public Dipendente findById(long id){
        return dipendentiRepository.findById(id).orElseThrow(()->new NotFoundException(id));
    }

    public void findByIdAndDelete(long dipendenteId){
        Dipendente found = this.findById(dipendenteId);
        this.dipendentiRepository.delete(found);
    }

    public String uploadAvatar(long dipendenteId, MultipartFile file) {
        try {
            // 1. Mi assicuro che non esista un dipendente
            Dipendente found = this.findById(dipendenteId);

            // 2. Eseguiamo l'upload su Cloudinary
            Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());

            // 3. Recuperiamo l'URL (meglio 'secure_url' per avere l'https)
            String imageUrl = (String) result.get("secure_url");


            found.setAvatarUrl(imageUrl);
            dipendentiRepository.save(found);

            log.info("Avatar aggiornato per il dipendente con ID: " + dipendenteId);
            return imageUrl;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Dipendente findByIdAndUpdate(long dipendenteId, DipendenteDTO payload) {
        Dipendente found = this.findById(dipendenteId);
        found.setUsername(payload.username());
        found.setNome(payload.nome());
        found.setCognome(payload.cognome());
        found.setEmail(payload.email());

        return dipendentiRepository.save(found);
    }
}
