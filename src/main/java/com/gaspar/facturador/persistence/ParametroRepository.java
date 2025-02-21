package com.gaspar.facturador.persistence;

import bo.gob.impuestos.siat.api.facturacion.sincronizacion.ParametricasDto;
import com.gaspar.facturador.commons.ParametricaEnum;
import com.gaspar.facturador.domain.repository.IParametroRepository;
import com.gaspar.facturador.persistence.crud.ParametroCrudRepository;
import com.gaspar.facturador.persistence.entity.ParametroEntity;
import com.gaspar.facturador.persistence.mapper.ParametroMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Repository
public class ParametroRepository implements IParametroRepository {

    private final ParametroMapper parametroMapper;
    private final ParametroCrudRepository parametroCrudRepository;


    public ParametroRepository(ParametroMapper parametroMapper, ParametroCrudRepository parametroCrudRepository) {
        this.parametroMapper = parametroMapper;
        this.parametroCrudRepository = parametroCrudRepository;
    }

    @Override
    public void save(ParametricasDto parametricasDto, ParametricaEnum parametricaEnum) {
        ParametroEntity parametroEntity = this.parametroMapper.toParametroEntity(parametricasDto);
        parametroEntity.setCodigoTipoParametro(parametricaEnum.name());
        this.parametroCrudRepository.save(parametroEntity);
    }

    @Override
    public void deleteAll() {
        this.parametroCrudRepository.deleteAll();
    }

    public List<ParametroEntity> getDocumentosIdentidad() {
        return parametroCrudRepository.findByCodigoTipoParametro("TIPO_DOCUMENTO_IDENTIDAD");
    }

    public List<ParametroEntity> getUnidadesMedida(List<String> codigosClasificador) {
        return parametroCrudRepository.findUnidadesMedida(codigosClasificador);
    }

    public List<ParametroEntity> getTipoMoneda(){
        return parametroCrudRepository.getTipoMoneda();
    }

}
