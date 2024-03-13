package com.domicilioFiscal.df.Controllers.InterfaceController;

import com.domicilioFiscal.df.Models.Dtos.NotificationGet;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@RequestMapping("/notification")
public interface IntNotificationController {

    //@Operation(summary = "Busca todas las notificaciones", description = "Retorna todas las notificaciones")
    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<List<NotificationGet>> GetAllNotification();

}
