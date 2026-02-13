package com.salvatore.agenziaViaggi.controllers;

import com.salvatore.agenziaViaggi.entities.Dipendente;
import com.salvatore.agenziaViaggi.entities.Viaggio;
import com.salvatore.agenziaViaggi.exceptions.ValidationException;
import com.salvatore.agenziaViaggi.payloads.DipendenteDTO;
import com.salvatore.agenziaViaggi.payloads.ViaggioDTO;
import com.salvatore.agenziaViaggi.services.DipendentiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/dipendenti")
public class DipendentiController {
    private final DipendentiService dipendentiService;

    @Autowired
    public DipendentiController(DipendentiService dipendentiService) {
        this.dipendentiService = dipendentiService;
    }

    @GetMapping
    public Page<Dipendente> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "username") String orderBy,
            @RequestParam(defaultValue = "asc") String sortCriteria) {
        return this.dipendentiService.findAll(page, size, orderBy, sortCriteria);
    }

    @GetMapping("/{dipendenteId}")
    public Dipendente getDipendenteById(@PathVariable long dipendenteId){
        return this.dipendentiService.findById(dipendenteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Dipendente createDipendente(@RequestBody @Validated DipendenteDTO payload, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValidationException(errorsList);
        } else {
            return this.dipendentiService.saveDipendente(payload);
        }
    }

    @PutMapping("/{dipendenteId}")
    public Dipendente updateDipendente(@PathVariable long dipendenteId,
                                 @RequestBody @Validated DipendenteDTO payload,
                                 BindingResult validationResult){
        if(validationResult.hasErrors()){
            throw new ValidationException(validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        return this.dipendentiService.findByIdAndUpdate(dipendenteId, payload);
    }

    @PatchMapping("/{dipendenteId}/avatar")
    public String uploadAvatar(@PathVariable long dipendenteId,
                               @RequestParam("avatar")MultipartFile file){
        return this.dipendentiService.uploadAvatar(dipendenteId, file);
    }

    @DeleteMapping("/{dipendenteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getDipendenteAndDelete(@PathVariable long dipendenteId){
        this.dipendentiService.findByIdAndDelete(dipendenteId);
    }
}
