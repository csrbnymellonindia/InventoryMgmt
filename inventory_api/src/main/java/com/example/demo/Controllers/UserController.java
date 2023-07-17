package com.example.demo.Controllers;

import com.example.demo.Services.UserService;
import com.example.demo.entities.User;
import com.example.demo.repository.UserInterfaceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserInterfaceRepo userInterfaceRepo;
    private final UserService userService;

    @PostMapping("/new")
//    userId, userName, userPwd, isAdmin
    public ResponseEntity<String> createUser(@RequestBody User userDetails){
        try{
            if(userInterfaceRepo.existsById(userDetails.getUserId())){
                return ResponseEntity.status(400).body("User Id Exists");
            }
            if(userDetails.getUserName() == null || userDetails.getIsAdmin() == null ||
                    userDetails.getUserPwd() == null){
                return ResponseEntity.status(400).body("Invalid");
            }
            userInterfaceRepo.save(userDetails);
        }
        catch(Exception e){
            return ResponseEntity.status(400).body("Error");
        }
        return ResponseEntity.ok("User Created");
    }

    @GetMapping("/adminLogin")
//    enteredId, enteredPassword
    public ResponseEntity<String> adminLogin(@RequestBody Map<String,Object> credentials){
        try{
            if(!(credentials.get("enteredId") instanceof Integer) || !userInterfaceRepo.existsById((Integer) credentials.get("enteredId"))){
                return ResponseEntity.status(400).body("Invalid Credentials");
            }
            Integer userId = (Integer) credentials.get("enteredId");
            if(!userService.getPasswordForUserId(userId).equals(credentials.get("enteredPassword")) || !userService.userIsAdmin(userId)){
                return ResponseEntity.status(400).body("Invalid Credentials");
            }
        }
        catch(Exception e){
            return ResponseEntity.status(400).body("Invalid Credentials");
        }
        return ResponseEntity.ok("Successful login");
    }

    @GetMapping("/userLogin")
//    enteredId, enteredPassword
    public ResponseEntity<String> userLogin(@RequestBody Map<String,Object> credentials){
        try{
            if(!(credentials.get("enteredId") instanceof Integer) || !userInterfaceRepo.existsById((Integer) credentials.get("enteredId"))){
                return ResponseEntity.status(400).body("Invalid Credentials");
            }
            Integer userId = (Integer) credentials.get("enteredId");
            if(!userService.getPasswordForUserId(userId).equals(credentials.get("enteredPassword")) || userService.userIsAdmin(userId)){
                return ResponseEntity.status(400).body("Invalid Credentials");
            }
        }
        catch(Exception e){
            return ResponseEntity.status(400).body("Invalid Credentials");
        }
        return ResponseEntity.ok("Successful login");
    }
}
