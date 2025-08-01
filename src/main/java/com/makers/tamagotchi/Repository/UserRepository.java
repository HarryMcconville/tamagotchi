package com.makers.tamagotchi.Repository;
import com.makers.tamagotchi.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>{

    Optional<User> findUserByEmail(String username);
}