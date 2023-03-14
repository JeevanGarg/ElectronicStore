package com.ElectronicStore.repository;

import com.ElectronicStore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String>
{
    //find for email
    Optional<User> findByEmail(String email);

    //find for both email and password
   // Optional<User> findByEmailAndPassword(String email,String password);

    //find name containing like
    List<User> findByNameContaining(String keyword);
}
