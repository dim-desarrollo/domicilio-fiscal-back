package com.domicilioFiscal.df.Repository;

import com.domicilioFiscal.df.Models.Entitys.Notification;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findAllByCuit(int cuit_notification);

    Optional<Notification> findByCuit(Long cuit_notification);

    @Modifying
    @Transactional
    @Query(value = "update notificaciones set estado_fecha_notificado = 'true' where id_notificaciones = ?1", nativeQuery = true)
    void updateAllStatusDateReportedByCuitNotification(Long id);
}