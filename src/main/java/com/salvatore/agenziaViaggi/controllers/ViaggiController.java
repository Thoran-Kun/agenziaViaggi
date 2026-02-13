package com.salvatore.agenziaViaggi.controllers;

import com.salvatore.agenziaViaggi.entities.Viaggio;
import com.salvatore.agenziaViaggi.exceptions.ValidationException;
import com.salvatore.agenziaViaggi.payloads.StatoViaggioDTO;
import com.salvatore.agenziaViaggi.payloads.ViaggioDTO;
import com.salvatore.agenziaViaggi.services.ViaggioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/viaggi")
public class ViaggiController {
    private final ViaggioService viaggioService;

    @Autowired
    public ViaggiController(ViaggioService viaggioService) {
        this.viaggioService = viaggioService;
    }

    @GetMapping
    public Page<Viaggio> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "destinazione") String orderBy,
            @RequestParam(defaultValue = "asc") String sortCriteria) {
        return this.viaggioService.findAll(page, size, orderBy, sortCriteria);
    }

    @GetMapping("/{viaggioId}")
    public Viaggio getViaggioById(@PathVariable long viaggioId){
        return this.viaggioService.findById(viaggioId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Viaggio createViaggio(@RequestBody @Validated ViaggioDTO payload, BindingResult validationResult){
        if (validationResult.hasErrors()) {
            List<String> errorsList = validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage())
                    .toList();

            throw new ValidationException(errorsList);
        } else {
            return this.viaggioService.saveViaggio(payload);
        }
    }

   @PutMapping("/{viaggioId}")
    public Viaggio updateViaggio(@PathVariable long viaggioId,
                                 @RequestBody @Validated ViaggioDTO payload,
                                 BindingResult validationResult){
        if(validationResult.hasErrors()){
            throw new ValidationException(validationResult.getFieldErrors().stream()
                    .map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        return this.viaggioService.findByIdAndUpdate(viaggioId, payload);
   }

   @DeleteMapping("/{viaggioId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void getViaggioIdAndDelete(@PathVariable long viaggioId){
        this.viaggioService.findByIdAndDelete(viaggioId);
   }

   @PatchMapping("/{viaggioId}/stato")
    public Viaggio updateStato(@PathVariable long viaggioId,
                               @RequestBody @Validated StatoViaggioDTO payload,
                               BindingResult validationResult){
      if(validationResult.hasErrors()){
          throw new ValidationException(validationResult.getFieldErrors().stream()
                  .map(DefaultMessageSourceResolvable::getDefaultMessage).toList());
      }
      return this.viaggioService.updateStatoViaggio(viaggioId, payload.statoViaggio());
   }
}