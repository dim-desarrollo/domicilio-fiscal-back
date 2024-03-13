package com.domicilioFiscal.df.Models.Dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class NotificationGet {

    @NotNull
    private Long id;
    @NotNull
    //@Min(value = 10,message = "El CUIT tiene que tener como minimo 10 caracteres")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private LocalDateTime dateRead;
    @NotNull
    @PastOrPresent(message = "[dateNotification] debe ser actual o pasada")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private LocalDateTime dateNotification;
    @NotNull
    @PastOrPresent(message = "[dateDelivered] debe ser actual o pasada")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "UTC")
    private LocalDateTime dateDelivered;

    private boolean statusDateReported;

}
