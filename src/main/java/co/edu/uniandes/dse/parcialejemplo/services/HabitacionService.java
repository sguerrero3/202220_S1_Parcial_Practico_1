package co.edu.uniandes.dse.parcialejemplo.services;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.entities.HabitacionEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.HabitacionRepository;
import co.edu.uniandes.dse.parcialejemplo.repositories.HotelRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HabitacionService {

    @Autowired
    HabitacionRepository habitacionRepository;

    @Autowired
    HotelRepository hotelRepository;

    /**
     * @param hotelId
     * @param habitacionEntity
     * @return
     * @throws IllegalOperationException
     */
    @Transactional
    public HabitacionEntity createHabitacion(Long hotelId, HabitacionEntity habitacionEntity) throws IllegalOperationException{

        log.info("Inicia proceso creacion habitacion");

        if (habitacionEntity.getNumero_banios() > habitacionEntity.getNumero_personas())

            throw new IllegalOperationException("Mayor numero de banios quye personas");
        

        Optional<HotelEntity> hotelEntity = hotelRepository.findById(hotelId);

        if (hotelEntity.isEmpty())
			throw new IllegalOperationException("Hotel no encontrado");
        
        habitacionEntity.setHotel(hotelEntity.get());

        return habitacionRepository.save(habitacionEntity);
    }
    

    
}
