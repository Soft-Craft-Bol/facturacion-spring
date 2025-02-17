package com.gaspar.facturador.persistence;

import com.gaspar.facturador.persistence.crud.HorarioCrudRepository;
import com.gaspar.facturador.persistence.entity.HorarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HorarioRepository {
    @Autowired
    private HorarioCrudRepository horarioCrudRepository;

    public List<HorarioEntity> getAll() {
        return (List<HorarioEntity>) horarioCrudRepository.findAll();
    }

    public Optional<HorarioEntity> getById(Long id) {
        return horarioCrudRepository.findById(id);
    }

    public HorarioEntity save(HorarioEntity horario) {
        return horarioCrudRepository.save(horario);
    }

    public HorarioEntity update(Long id, HorarioEntity horarioDetails) {
        Optional<HorarioEntity> optionalHorario = horarioCrudRepository.findById(id);
        if (optionalHorario.isPresent()) {
            HorarioEntity horario = optionalHorario.get();
            horario.setPanadero(horarioDetails.getPanadero());
            horario.setHoraEntrada(horarioDetails.getHoraEntrada());
            horario.setHoraSalida(horarioDetails.getHoraSalida());
            horario.setFechaEntrada(horarioDetails.getFechaEntrada());
            horario.setFechaSalida(horarioDetails.getFechaSalida());
            horario.setDias(horarioDetails.getDias());
            return horarioCrudRepository.save(horario);
        } else {
            return null;
        }
    }

    public void delete(Long id) {
        horarioCrudRepository.deleteById(id);
    }
}
