package com.makers.tamagotchi.Repository;
import com.makers.tamagotchi.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String>{

}
