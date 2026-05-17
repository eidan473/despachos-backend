package com.citt.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idVenta;

    private String direccionCompra;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaCompra;

    private Long valorCompra;

    private boolean despachoGenerado = false;
}
