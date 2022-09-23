package co.edu.uniandes.dse.parcialejemplo.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import co.edu.uniandes.dse.parcialejemplo.entities.HabitacionEntity;
import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(HabitacionService.class)
public class HabitacionTest {

    @Autowired
    private HabitacionService habitacionService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private List<HabitacionEntity> habitacionList = new ArrayList<>();
	private List<HotelEntity> hotelList = new ArrayList<>();


    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

    private void clearData() {
		entityManager.getEntityManager().createQuery("delete from HotelEntity");

        entityManager.getEntityManager().createQuery("delete from HabitacionEntity");
	}
    

    private void insertData() {
		for (int i = 0; i < 3; i++) {
			HotelEntity hotelEntity = factory.manufacturePojo(HotelEntity.class);
			entityManager.persist(hotelEntity);
			hotelList.add(hotelEntity);
		}

        for (int i = 0; i < 3; i++) {
			HabitacionEntity entity = factory.manufacturePojo(HabitacionEntity.class);
			entity.setHotel(hotelList.get(0));
			hotelList.get(0).getHabitaciones().add(entity);
			entityManager.persist(entity);
			habitacionList.add(entity);
		}
    }

    @Test
    void testCrearHabitacionGood() throws IllegalOperationException {
        HabitacionEntity newEntity = factory.manufacturePojo(HabitacionEntity.class);
        newEntity.setNumero_banios(3);
        newEntity.setNumero_personas(8);

		newEntity.setHotel(hotelList.get(1));
		HabitacionEntity result = habitacionService.createHabitacion(habitacionList.get(1).getId(), newEntity);

		assertNotNull(result);
        
		HabitacionEntity entity = entityManager.find(HabitacionEntity.class, result.getId());

		assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getIdentificacion(), entity.getIdentificacion());
        assertEquals(newEntity.getNumero_banios(), entity.getNumero_banios());
        assertEquals(newEntity.getNumero_camas(), entity.getNumero_camas());
        assertEquals(newEntity.getNumero_personas(), entity.getNumero_personas());
		
    }

    @Test
    void testCrearHabitacionBad() throws IllegalOperationException {
        HabitacionEntity newEntity = factory.manufacturePojo(HabitacionEntity.class);
        newEntity.setNumero_banios(8);
        newEntity.setNumero_personas(3);

		newEntity.setHotel(hotelList.get(1));
		HabitacionEntity result = habitacionService.createHabitacion(habitacionList.get(1).getId(), newEntity);

		assertNotNull(result);
        
		HabitacionEntity entity = entityManager.find(HabitacionEntity.class, result.getId());

		assertEquals(newEntity.getId(), entity.getId());
        assertEquals(newEntity.getIdentificacion(), entity.getIdentificacion());
        assertEquals(newEntity.getNumero_banios(), entity.getNumero_banios());
        assertEquals(newEntity.getNumero_camas(), entity.getNumero_camas());
        assertEquals(newEntity.getNumero_personas(), entity.getNumero_personas());
		
    }
}
