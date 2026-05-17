package com.citt.controller;

import com.citt.persistence.entity.Venta;
import com.citt.persistence.repository.VentaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/v1/ventas")
@Tag(name = "Venta", description = "Controlador para gestionar ventas")
public class VentaController {

    @Autowired
    private VentaRepository ventaRepository;

    @Operation(summary = "Obtener todas las ventas")
    @GetMapping
    public ResponseEntity<List<Venta>> getAllVentas() {
        return ResponseEntity.ok(ventaRepository.findAll());
    }

    @Operation(summary = "Obtener una venta por ID")
    @GetMapping("/{id}")
    public ResponseEntity<Venta> getVentaById(@PathVariable Long id) {
        return ventaRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una nueva venta")
    @PostMapping
    public ResponseEntity<Venta> createVenta(@RequestBody Venta venta) {
        return ResponseEntity.ok(ventaRepository.save(venta));
    }

    @Operation(summary = "Actualizar una venta existente")
    @PutMapping("/{id}")
    public ResponseEntity<Venta> updateVenta(@PathVariable Long id, @RequestBody Venta venta) {
        return ventaRepository.findById(id).map(existing -> {
            existing.setDireccionCompra(venta.getDireccionCompra());
            existing.setFechaCompra(venta.getFechaCompra());
            existing.setValorCompra(venta.getValorCompra());
            existing.setDespachoGenerado(venta.isDespachoGenerado());
            return ResponseEntity.ok(ventaRepository.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Eliminar una venta")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVenta(@PathVariable Long id) {
        ventaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
