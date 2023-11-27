package com.makogon.foodtracker.service;

import com.makogon.foodtracker.model.UserDetails;
import com.makogon.foodtracker.repository.UserDetailsRepository;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    public MyUserDetailsService(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    public UserDetails getUserDetailsById(long userDetailsId) {
        return userDetailsRepository.findById(userDetailsId)
                .orElseThrow(() -> new IllegalArgumentException("Информация о пользователе с идентификатором " + userDetailsId + " не найдена"));
    }

    public UserDetails saveUserDetails(UserDetails userDetails) {
        return userDetailsRepository.save(userDetails);
    }

    public UserDetails updateUserDetails(UserDetails updatedUserDetails) {
        UserDetails existingUserDetails = getUserDetailsById(updatedUserDetails.getUser_detailsid());

        existingUserDetails.setPerson(updatedUserDetails.getPerson());
        existingUserDetails.setHeight(updatedUserDetails.getHeight());
        existingUserDetails.setWeight(updatedUserDetails.getWeight());
        existingUserDetails.setAge(updatedUserDetails.getAge());
        existingUserDetails.setActivity(updatedUserDetails.getActivity());
        existingUserDetails.setSex(updatedUserDetails.getSex());

        return userDetailsRepository.save(existingUserDetails);
    }

    public void deleteUserDetailsById(long userDetailsId) {
        UserDetails userDetails = getUserDetailsById(userDetailsId);
        userDetailsRepository.delete(userDetails);
    }
}