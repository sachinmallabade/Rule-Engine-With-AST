package com.zeotap.ruleengine.Service.Impl;

import com.zeotap.ruleengine.Model.User;
import com.zeotap.ruleengine.Repository.UserRepository;
import com.zeotap.ruleengine.Service.UserService;
import com.zeotap.ruleengine.Exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public User getUserById(Long id) {
		return userRepository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@Override
	public User createUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public void updateUser(Long id, User userUpdates) {
		User existingUser = getUserById(id);

		if (userUpdates.getAge() != 0) {
			existingUser.setAge(userUpdates.getAge());
		}
		if (userUpdates.getDepartment() != null) {
			existingUser.setDepartment(userUpdates.getDepartment());
		}
		if (userUpdates.getSalary() != 0) {
			existingUser.setSalary(userUpdates.getSalary());
		}
		if (userUpdates.getExperience() != 0) {
			existingUser.setExperience(userUpdates.getExperience());
		}

		userRepository.save(existingUser);
	}

	@Override
	public void deleteUser(Long id) {
		User user = getUserById(id);
		if (user != null) {
			userRepository.deleteById(id);
		}
	}
}
