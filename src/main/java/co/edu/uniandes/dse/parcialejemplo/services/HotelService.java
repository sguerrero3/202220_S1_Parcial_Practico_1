package co.edu.uniandes.dse.parcialejemplo.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialejemplo.repositories.HotelRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class HotelService {

    @Autowired
    HotelRepository hotelRepository;

    @Transactional
    public HotelEntity createHotel(HotelEntity hotelEntity) throws IllegalOperationException{
        
        log.info("Inicia Proceso de creacion del libro ");

        
        if (!hotelRepository.findByNombre(hotelEntity.getNombre()).isEmpty())

            throw new IllegalOperationException("Nombre del hotel ya existe");

        if (hotelEntity.getEstrellas()<2 ||  hotelEntity.getEstrellas()>5)

        throw new IllegalOperationException("El hotel no esta en el rango de estrellas requerido");


        log.info("Termina proceso de creacion del libro");
        return hotelRepository.save(hotelEntity);

    }
    
    
}
