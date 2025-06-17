package com.makers.tamagotchi.Repository;
import com.makers.tamagotchi.Model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet, Long>{

}
