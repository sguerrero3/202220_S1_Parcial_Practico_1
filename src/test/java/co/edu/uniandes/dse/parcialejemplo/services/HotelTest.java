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

import co.edu.uniandes.dse.parcialejemplo.entities.HotelEntity;
import co.edu.uniandes.dse.parcialejemplo.exceptions.IllegalOperationException;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
@Import(HotelService.class)
public class HotelTest {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

	private List<HotelEntity> hotelList = new ArrayList<>();
	
    @BeforeEach
	void setUp() {
		clearData();
		insertData();
	}

    private void clearData() {
		entityManager.getEntityManager().createQuery("delete from HotelEntity");
	}

    private void insertData() {
		for (int i = 0; i < 3; i++) {
			HotelEntity hotelEntity = factory.manufacturePojo(HotelEntity.class);
			entityManager.persist(hotelEntity);
			hotelList.add(hotelEntity);
		}

        HotelEntity hotelEntity = factory.manufacturePojo(HotelEntity.class);
        hotelEntity.setNombre("Hotel 2");
        entityManager.persist(hotelEntity);
		hotelList.add(hotelEntity);

    
	}


    @Test
    void testCreateBookGood() throws IllegalOperationException{

        HotelEntity newEntity = factory.manufacturePojo(HotelEntity.class);
		newEntity.setNombre("Hotel 1");
        newEntity.setEstrellas(3);

		HotelEntity result = hotelService.createHotel(newEntity);

		assertNotNull(result);
        
		HotelEntity entity = entityManager.find
        (HotelEntity.class, result.getId());

		assertEquals(newEntity.getId(), entity.getId());
		assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getDireccion(), entity.getDireccion());
        assertEquals(newEntity.getEstrellas(), entity.getEstrellas());
    }

    @Test
    void testCreateBookBadNombre() throws IllegalOperationException{

        HotelEntity newEntity = factory.manufacturePojo(HotelEntity.class);
		newEntity.setNombre("Hotel 2");

		HotelEntity result = hotelService.createHotel(newEntity);

		assertNotNull(result);
        
		HotelEntity entity = entityManager.find
        (HotelEntity.class, result.getId());

        assertEquals(newEntity.getId(), entity.getId());
		assertEquals(newEntity.getNombre(), entity.getNombre());
        assertEquals(newEntity.getDireccion(), entity.getDireccion());
        assertEquals(newEntity.getEstrellas(), entity.getEstrellas());

    }
    
    
}
