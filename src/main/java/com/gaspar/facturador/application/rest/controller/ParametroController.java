package com.gaspar.facturador.application.rest.controller;

import com.gaspar.facturador.persistence.ParametroRepository;
import com.gaspar.facturador.persistence.entity.ParametroEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/parametros")
public class ParametroController {

    private final ParametroRepository parametroRepository;

    public ParametroController(ParametroRepository parametroRepository){
        this.parametroRepository=parametroRepository;
    }

    @GetMapping("/documentos-identidad")
    public List<ParametroEntity> getDocumentosIdentidad() {
        return parametroRepository.getDocumentosIdentidad();
    }

    @GetMapping("/unidades-medida")
    public List<ParametroEntity> getUnidadesMedida() {
        List<String> codigosClasificador = Arrays.asList(
                "57",  // UNIDAD (BIENES) - Para productos individuales (panes, pasteles)
                "14",  // DOCENA - Para ventas por docena (12 panes, 12 galletas)
                "4",   // BOLSA - Empaques de panes/galletas
                "6",   // CAJA - Para productos agrupados en cajas
                "22",  // KILOGRAMO - Ingredientes a granel (harina, azúcar)
                "28",  // LITRO - Líquidos (leche, aceite)
                "42",  // PAQUETE - Paquetes de productos (ej: cupcakes)
                "119", // PACK - Similar a paquete
                "20",  // HOJA - Para masas/hojaldres
                "17",  // GRAMO - Pequeñas cantidades (levadura, especias)
                "75" ,  // BOLSA (alternativa)
                "102", // BADEJA
                "47" //PIEZA
        );
        return parametroRepository.getUnidadesMedida(codigosClasificador);
    }
    @GetMapping("/tipo-moneda")
    public List<ParametroEntity> getTipoMoneda() {
        return parametroRepository.getTipoMoneda();
    }
}
