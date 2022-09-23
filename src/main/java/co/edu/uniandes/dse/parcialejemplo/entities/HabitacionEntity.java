package co.edu.uniandes.dse.parcialejemplo.entities;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;

@Getter
@Setter
@Entity
public class HabitacionEntity extends BaseEntity{
    
    //Tiene numero identificacion, numero de personas, numero de camas, banios, id

    private Integer identificacion;
    private Integer numero_personas;
    private Integer numero_camas;
    private Integer numero_banios;


    /*
     * Relaciopn con hotel 
     */
    @PodamExclude
    @ManyToOne
    private HotelEntity hotel;

}
