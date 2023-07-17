package com.example.demo.Services;

import com.example.demo.repository.UserInterfaceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserInterfaceRepo userInterfaceRepo;

    public String getPasswordForUserId(Integer userId){
        return userInterfaceRepo.getPassword(userId);
    }

    public Boolean userIsAdmin(Integer userId){
        return userInterfaceRepo.isAdmin(userId);
    }
}
