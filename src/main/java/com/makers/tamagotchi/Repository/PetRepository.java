package com.makers.tamagotchi.Repository;
import com.makers.tamagotchi.Model.Pet;
import com.makers.tamagotchi.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long>{

    Optional<Pet> findByUser(Optional<User> userOptional);

    List<Pet> findAllByUser(Optional<User> userOptional);
}
