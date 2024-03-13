package com.domicilioFiscal.df.Models.Dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class NotificationPut {

    @NotNull
    //@Min(value = 10, message = "El CUIT tiene que tener como minimo 10 caracteres")
    //@Max(value = 10, message = "El CUIT tiene que tener como maximo 10 caracteres") //3423433343
    //@Digits(integer = 10, fraction = 0, message = "El CUIT debe tener exactamente 10 dígitos")
    //@Range(min= 10, max= 10)
    private int cuit;
    @NotNull
    private String userEnv;
    @NotNull
    private String tributo;
    @NotNull
    private String notification;
    @NotNull
    private String pdf;
    @NotNull
    @PastOrPresent(message = "[dateRead] debe ser actual o pasada")
    private LocalDateTime dateRead;
    @NotNull
    @PastOrPresent(message = "[dateNotification] debe ser actual o pasada")
    private LocalDateTime dateNotification;
    @NotNull
    @PastOrPresent(message = "[dateDelivered] debe ser actual o pasada")
    private LocalDateTime dateDelivered;

    private boolean statusDateReported;

}