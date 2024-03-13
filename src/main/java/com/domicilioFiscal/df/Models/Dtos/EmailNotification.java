package com.domicilioFiscal.df.Models.Dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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
public class EmailNotification {

    private String toEmail;
    private String body;
    private String subject;

    private int cuit;
    private String userEnv;
    private String tributo;
    private String notification;
    private String pdf;
    private LocalDateTime dateRead;
    private LocalDateTime dateNotification;
    private LocalDateTime dateDelivered;
    private boolean statusDateReported;

    @Override
    public String toString() {
        return "EmailNotification{" +
                "toEmail='" + toEmail + '\'' +
                ", body='" + body + '\'' +
                ", subject='" + subject + '\'' +
                ", cuit=" + cuit +
                ", userEnv='" + userEnv + '\'' +
                ", tributo='" + tributo + '\'' +
                ", notification='" + notification + '\'' +
                ", pdf='" + pdf + '\'' +
                ", dateRead=" + dateRead +
                ", dateNotification=" + dateNotification +
                ", dateDelivered=" + dateDelivered +
                ", statusDateReported=" + statusDateReported +
                '}';

    }
}
