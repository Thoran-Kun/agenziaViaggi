package com.salvatore.agenziaViaggi.controllers;

import com.salvatore.agenziaViaggi.entities.Prenotazione;
import com.salvatore.agenziaViaggi.exceptions.ValidationException;
import com.salvatore.agenziaViaggi.payloads.PrenotazioneDTO;
import com.salvatore.agenziaViaggi.services.PrenotazioneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;

    @Autowired
    public PrenotazioneController(PrenotazioneService prenotazioneService) {
        this.prenotazioneService = prenotazioneService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Prenotazione createPrenotazione(@RequestBody @Validated PrenotazioneDTO payload, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValidationException(errorsList);
        } else {
            return this.prenotazioneService.savePrenotazione(payload);
        }
    }

    @GetMapping
    public Page<Prenotazione> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "username") String orderBy,
            @RequestParam(defaultValue = "asc") String sortCriteria) {
        return this.prenotazioneService.findAll(page, size, orderBy, sortCriteria);
    }

    @GetMapping("/{prenotazioneId}")
    public Prenotazione getPrenotazioneById(@PathVariable long prenotazioneId){
        return this.prenotazioneService.findById(prenotazioneId);
    }

    @DeleteMapping("/{prenotazioneId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void findByIdAndDelete(@PathVariable long prenotazioneId) {
        this.prenotazioneService.findByIdAndDelete(prenotazioneId);
    }
}
