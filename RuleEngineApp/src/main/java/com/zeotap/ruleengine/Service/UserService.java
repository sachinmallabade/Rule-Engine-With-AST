package com.zeotap.ruleengine.Service;

import java.util.List;

import com.zeotap.ruleengine.Model.User;

public interface UserService {

	User createUser(User user);

	User getUserById(Long id);

	List<User> getAllUsers();

	void updateUser(Long id, User userUpdates);

	void deleteUser(Long id);

}
