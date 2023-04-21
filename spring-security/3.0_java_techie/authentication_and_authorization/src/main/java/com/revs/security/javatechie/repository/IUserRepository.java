package com.revs.security.javatechie.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revs.security.javatechie.entity.User;

public interface IUserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByName(String username);

}