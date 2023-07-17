package com.example.demo.Controllers;


import com.example.demo.Services.AssetService;
import com.example.demo.Services.BorrowedAssetService;
import com.example.demo.entities.BorrowedAssetEntity;
import com.example.demo.entities.History;
import com.example.demo.entities.Inventory;
import com.example.demo.entities.User;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.HistoryRepository;
import com.example.demo.repository.UserInterfaceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/assets")
public class BorrowController {
    private final AssetService assetService;
    private final BorrowedAssetService borrowedAssetService;
    private final HistoryRepository historyRepository;
    private final AssetRepository assetRepository;
    private final UserInterfaceRepo userInterfaceRepo;

    @PostMapping(value = "/borrow")
//    Map will have fields: userId, assetId, assetQuantity, assetName
    public ResponseEntity<String> borrowAsset(@RequestBody Map<String,Integer> borrowDetails){
        String asset_name=assetRepository.getAssetName(borrowDetails.get("assetId"));

        String user_name=userInterfaceRepo.getAll(borrowDetails.get("userId"));

        try{

            Integer assetQuantity = assetService.getAvailableQuantity(borrowDetails.get("assetId"));
            if(assetQuantity < borrowDetails.get("assetQuantity")){
                return ResponseEntity.ok("Not enough assets");
            }
            if(borrowedAssetService.checkPreviousRecord(borrowDetails.get("assetId"),
                    borrowDetails.get("userId"))){
//                Update existing record (Whether the due date is extended)
                borrowedAssetService.updateBorrowedAssetRecord(
                        borrowDetails.get("assetId"),borrowDetails.get("userId"),borrowDetails.get("assetQuantity"));
            }
            else{
//                Add new Record
                borrowedAssetService.addBorrowedRecord(borrowDetails);
            }
////            update existing value of current borrowed assets
            assetService.setAssetBorQuantity(borrowDetails.get("assetId"), borrowDetails.get("assetQuantity"));
//
////            update history

        }
        catch(Exception e){
            System.out.println("Exception occurred");
            return ResponseEntity.ok(e.getMessage());
        }
        History historyObject=new History();
        historyObject.setAsset_name(asset_name);
        historyObject.setQuantity(borrowDetails.get("assetQuantity"));
        historyObject.setDate(LocalDate.now());
        historyObject.setAsset_id(borrowDetails.get("assetId"));
        historyObject.setMessage("Asset Borrowed : "+borrowDetails.get("assetQuantity"));
        historyObject.setUser_id(borrowDetails.get("userId"));
        historyObject.setUser_name(user_name);
        historyRepository.save(historyObject);
        return ResponseEntity.ok("Success");
    }

    @PostMapping(value = "/return")
//    Map will have fields: userId, assetId, assetQuantity
    public ResponseEntity<String> returnAsset(@RequestBody Map<String,Integer> returnDetails){


        String asset_name=assetRepository.getAssetName(returnDetails.get("assetId"));

        String user_name=userInterfaceRepo.getAll(returnDetails.get("userId"));
        try{
            BorrowedAssetEntity prevRecord = borrowedAssetService.getBorrowRecord(returnDetails);
            if(prevRecord == null){
                return ResponseEntity.ok("Record not found");
            }
            if(prevRecord.getBorQuantity() < returnDetails.get("assetQuantity")){
                return ResponseEntity.ok("Record not found");
            }
//            Delete Record
            else if(prevRecord.getBorQuantity().equals(returnDetails.get("assetQuantity"))){
                borrowedAssetService.deleteBorrowRecord(returnDetails.get("assetId"), returnDetails.get("userId"));
            }
            else{
                borrowedAssetService.updateBorrowedAssetRecord(returnDetails.get("assetId"),
                        returnDetails.get("userId"), -returnDetails.get("assetQuantity"));
            }
            assetService.setAssetBorQuantity(returnDetails.get("assetId"), -returnDetails.get("assetQuantity"));
        }
        catch(Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
        History historyObject=new History();
        historyObject.setAsset_name(asset_name);
        historyObject.setQuantity(returnDetails.get("assetQuantity"));
        historyObject.setDate(LocalDate.now());
        historyObject.setAsset_id(returnDetails.get("assetId"));
        historyObject.setMessage("Asset Returned : "+returnDetails.get("assetQuantity"));
        historyObject.setUser_id(returnDetails.get("userId"));
        historyObject.setUser_name(user_name);
        historyRepository.save(historyObject);
        return ResponseEntity.ok("Success");
    }
}
