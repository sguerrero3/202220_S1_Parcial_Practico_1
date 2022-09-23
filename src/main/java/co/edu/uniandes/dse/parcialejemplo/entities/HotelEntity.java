package co.edu.uniandes.dse.parcialejemplo.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;
import uk.co.jemos.podam.common.PodamExclude;


@Getter
@Setter
@Entity
public class HotelEntity extends BaseEntity{

    //Tiene nombre, direccion,numero de estrellas id tipo long

    private String nombre;
    private String direccion;
    private Integer estrellas;


    /*
     * Relacion con habitacion (un hotel puede tener varias habitaciones)
     */
    @PodamExclude
    @OneToMany(mappedBy = "hotel", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<HabitacionEntity> habitaciones = new ArrayList<>();

    


    
}
