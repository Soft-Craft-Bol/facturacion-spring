package com.gaspar.facturador.domain.service;

import com.gaspar.facturador.application.request.VentaSinFacturaRequest;
import com.gaspar.facturador.persistence.crud.AbonoCreditoRepository;
import com.gaspar.facturador.persistence.crud.ClienteCrudRepository;
import com.gaspar.facturador.persistence.crud.CuentaPorCobrarRepository;
import com.gaspar.facturador.persistence.crud.VentaCrudRepository;
import com.gaspar.facturador.persistence.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PagoPosteriorService {

    private final VentaCrudRepository ventaRepository;
    private final CuentaPorCobrarRepository cuentaPorCobrarRepository;
    private final AbonoCreditoRepository abonoCreditoRepository;
    private final ClienteCrudRepository clienteRepository;
    private final VentaService ventaService; // Inyecta VentaService

    @Transactional
    public VentasEntity crearVentaPagoPosterior(VentaSinFacturaRequest request) {
        // Validaciones específicas para pago posterior
        validarPagoPosterior(request);

        // Usar el método de VentaService para crear la venta base
        VentasEntity venta = ventaService.crearVentaBase(request);

        // Configurar como pago posterior
        venta.setEsCredito(false); // No es crédito tradicional
        venta.setEstado("PENDIENTE_PAGO");

        // Crear cuenta por cobrar específica para pago posterior
        CuentaPorCobrarEntity cuentaPosterior = new CuentaPorCobrarEntity();
        cuentaPosterior.setVenta(venta);
        cuentaPosterior.setCliente(venta.getCliente());
        cuentaPosterior.setMontoTotal(venta.getMonto());
        cuentaPosterior.setSaldoPendiente(venta.getMonto());
        cuentaPosterior.setFechaEmision(new Date());

        // Configurar fechas según el plazo acordado
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, request.getPlazoPagoPosterior());
        cuentaPosterior.setFechaVencimiento(calendar.getTime());

        cuentaPosterior.setDiasCredito(request.getPlazoPagoPosterior());
        cuentaPosterior.setEstado("PENDIENTE");
        cuentaPosterior.setTipoCuenta("PAGO_POSTERIOR");

        venta.setCuentaPorCobrar(cuentaPosterior);

        return ventaRepository.save(venta);
    }

    private void validarPagoPosterior(VentaSinFacturaRequest request) {
        if (Boolean.TRUE.equals(request.getEsPagoPosterior())) {
            if (request.getIdCliente() == null) {
                throw new IllegalArgumentException("Para ventas a posterior se requiere un cliente");
            }

            if (request.getPlazoPagoPosterior() == null || request.getPlazoPagoPosterior() <= 0) {
                throw new IllegalArgumentException("El plazo de pago posterior es obligatorio");
            }

            // Validar que el cliente existe y está activo
            ClienteEntity cliente = clienteRepository.findById(request.getIdCliente())
                    .orElseThrow(() -> new IllegalArgumentException("Cliente no encontrado"));

            // Puedes agregar más validaciones específicas para pago posterior
        }
    }

    @Transactional
    public AbonoCreditoEntity registrarAbonoPagoPosterior(Long ventaId, BigDecimal montoAbono,
                                                          String metodoPago, String referencia,
                                                          Long cajaId, String usuario) {

        VentasEntity venta = ventaRepository.findById(ventaId)
                .orElseThrow(() -> new IllegalArgumentException("Venta no encontrada"));

        CuentaPorCobrarEntity cuenta = venta.getCuentaPorCobrar();

        if (cuenta == null || !"PAGO_POSTERIOR".equals(cuenta.getTipoCuenta())) {
            throw new IllegalArgumentException("Esta venta no es de tipo pago posterior");
        }

        // Validar que el abono no exceda el saldo pendiente
        if (montoAbono.compareTo(cuenta.getSaldoPendiente()) > 0) {
            throw new IllegalArgumentException("El monto del abono excede el saldo pendiente");
        }

        // Crear abono
        AbonoCreditoEntity abono = new AbonoCreditoEntity();
        abono.setCuentaPorCobrar(cuenta);
        abono.setMontoAbono(montoAbono);
        abono.setFechaAbono(new Date());
        abono.setMetodoPago(metodoPago);
        abono.setReferencia(referencia);

        // Configurar caja y usuario
        CajasEntity caja = new CajasEntity();
        caja.setId(cajaId);
        abono.setCaja(caja);

        UserEntity user = new UserEntity();
        user.setUsername(usuario);
        abono.setUsuario(user);

        // Actualizar saldo pendiente
        BigDecimal nuevoSaldo = cuenta.getSaldoPendiente().subtract(montoAbono);
        cuenta.setSaldoPendiente(nuevoSaldo);

        // Actualizar estado según el saldo
        if (nuevoSaldo.compareTo(BigDecimal.ZERO) == 0) {
            cuenta.setEstado("CANCELADO");
            venta.setEstado("COMPLETADO");
        } else if (nuevoSaldo.compareTo(cuenta.getMontoTotal()) < 0) {
            cuenta.setEstado("PARCIAL");
        }

        cuentaPorCobrarRepository.save(cuenta);
        ventaRepository.save(venta);

        return abonoCreditoRepository.save(abono);
    }

    public List<CuentaPorCobrarEntity> obtenerCuentasPagoPosteriorPendientes() {
        return cuentaPorCobrarRepository.findByTipoCuentaAndEstadoNot("PAGO_POSTERIOR", "CANCELADO");
    }

    public List<CuentaPorCobrarEntity> obtenerCuentasPagoPosteriorPorCliente(Integer clienteId) {
        return cuentaPorCobrarRepository.findByClienteIdAndTipoCuenta(clienteId, "PAGO_POSTERIOR");
    }
}