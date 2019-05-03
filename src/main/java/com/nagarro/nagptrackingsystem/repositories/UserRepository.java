package com.nagarro.nagptrackingsystem.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.nagarro.nagptrackingsystem.constant.UserType;
import com.nagarro.nagptrackingsystem.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	public List<User> findByUserType(UserType userType);

	public User findFirstByEmail(String email);

	public User findFirstByContactNo(String contactNo);

	@Modifying
	@Query(value = "UPDATE user u SET u.password = ?1, u.name = ?2, u.contact_number = ?3, u.type = ?4 WHERE u.user_id = ?5", nativeQuery = true)
	public int updateUser(String password, String name, String contactNo, String type, int userId);
}
