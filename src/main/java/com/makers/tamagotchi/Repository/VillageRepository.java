package com.makers.tamagotchi.Repository;
import com.makers.tamagotchi.Model.Village;
import com.makers.tamagotchi.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VillageRepository extends JpaRepository<Village, Long>{

    List<Village> findAllByUser(User user);
}
