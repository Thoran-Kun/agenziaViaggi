package com.salvatore.agenziaViaggi.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record DipendenteDTO(
        @NotBlank(message = "Lo username è obbligatorio")
        @Size(min = 2, max = 30, message = "Il nome deve rientrare tra i 2 e i 30 caratteri")
        String username,
        @NotBlank(message = "Il nome è obbligatorio")
        @Size(min = 2, max = 30, message = "Il nome deve rientrare tra i 2 e i 30 caratteri")
        String nome,
        @NotBlank(message = "Il cognome è obbligatorio")
        @Size(min = 2, max = 30, message = "Il cognome deve rientrare tra i 2 e i 30 caratteri")
        String cognome,
        @NotBlank(message = "La mail è obbligatoria")
        @Email(message = "Email non valida")
        String email
) {
}
