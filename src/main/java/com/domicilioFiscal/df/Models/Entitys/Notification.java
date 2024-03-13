package com.domicilioFiscal.df.Models.Entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificaciones")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notificaciones")
    private Long id;
    @Column(name = "cuit")
    private int cuit;
    @Column(name = "usuario_env")
    private String userEnv;

    @Column(name = "tributo")
    private String tributo;
    @Column(name = "notificacion")
    private String notification;
    @Column(name = "pdf")

    private String pdf;
    @Column(name = "fecha_leido")

    private LocalDateTime  dateRead;
    @Column(name = "fecha_notificado")
    private LocalDateTime  dateNotification;
    @Column(name = "fecha_entregado")
    private LocalDateTime dateDelivered;
    @Column(name = "estado_fecha_notificado")
    private boolean statusDateReported;

    @Override
    public String toString() {
        return "Notification{" +
                "cuit=" + cuit +
                ", user_env='" + userEnv + '\'' +
                ", notification='" + notification + '\'' +
                ", pdf='" + pdf + '\'' +
                ", date_read=" + dateRead +
                ", date_notification=" + dateNotification +
                ", date_delivered=" + dateDelivered +
                ", statusDateReported=" + statusDateReported +
                '}';
    }
}



/*

CREATE TABLE notificaciones
(
    id_notificaciones bigserial NOT NULL,
    cuit bigint,
    usuario_env varchar(100),
    tributo varchar(100),
    notificacion varchar(255),
    pdf varchar(255),
    fecha_leido timestamp without time zone,
    fecha_notificado timestamp without time zone,
    fecha_entregado timestamp without time zone,
    estadoFechaNotificado boolean,
    primary key (id_notificaciones)
);



 */