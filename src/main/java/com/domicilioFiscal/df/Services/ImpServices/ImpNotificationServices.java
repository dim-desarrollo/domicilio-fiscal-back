package com.domicilioFiscal.df.Services.ImpServices;


import com.domicilioFiscal.df.Models.Entitys.Notification;
import com.domicilioFiscal.df.Repository.NotificationRepository;
import com.domicilioFiscal.df.Services.InterfaceServices.IntNotificationServices;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImpNotificationServices implements IntNotificationServices {

    private final NotificationRepository notificationRepository;

    //private final MyObjectProducer myObjectProducer;

    @Override
    public List<Notification> getNotification() {return notificationRepository.findAll();}

    @Override
    public List<Notification> getNotificationByCuit(int cuit_notification) {
        return notificationRepository.findAllByCuit(cuit_notification);
    }

    @Override
    public boolean deletNoficationById(Long id_notification) {

        if (notificationRepository.existsById(id_notification)) {
            notificationRepository.deleteById(id_notification);
            return true;
        } else {
            return false;
        }


    }

    @Override
    public Notification addNotification(Notification notification) throws JsonProcessingException {


        final ObjectMapper objectMapper = new ObjectMapper();

        //objectMapper.registerModule(new JavaTimeModule());

        //myObjectProducer.sendMessage(objectMapper.writeValueAsString(notification));

        return notificationRepository.save(notification);

    }

    @Override
    public boolean verifyCredentials(Notification notification) {
        return false;
    }
    @Override
    public void Notificated(Long id) {
        notificationRepository.updateAllStatusDateReportedByCuitNotification(id);
    }
}

























