package com.domicilioFiscal.df.Services.InterfaceServices;

import com.domicilioFiscal.df.Models.Entitys.Notification;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface IntNotificationServices{

    List<Notification> getNotification();

    List<Notification> getNotificationByCuit(int cuit_notification);

    boolean deletNoficationById(Long id_notification);

    Notification addNotification(Notification notification) throws JsonProcessingException;

    boolean verifyCredentials(Notification notification);

    void Notificated(Long id);
}
