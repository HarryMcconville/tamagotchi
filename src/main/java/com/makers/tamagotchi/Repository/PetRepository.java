package com.makers.tamagotchi.Repository;
import com.makers.tamagotchi.Model.Pet;
import com.makers.tamagotchi.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long>{

    List<Pet> findAllByUser(User user);
}
