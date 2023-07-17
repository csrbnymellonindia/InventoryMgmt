package com.example.demo.Services;


import com.example.demo.entities.BorrowedAssetEntity;
import com.example.demo.repository.AssetRepository;
import com.example.demo.repository.BorrowedAssetInterfaceRepo;
import com.example.demo.repository.UserInterfaceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BorrowedAssetService {
    private final BorrowedAssetInterfaceRepo borrowedAssetInterfaceRepo;
    private final UserInterfaceRepo userInterfaceRepo;
    private final AssetRepository assetRepository;

    public Boolean checkPreviousRecord(Integer asset_ID, Integer user_ID){
        return borrowedAssetInterfaceRepo.checkRecordCount(asset_ID,user_ID) > 0;
    }

    public void addBorrowedRecord(Map<String,Integer> borrowDetails){
        LocalDate currDate = LocalDate.now();
        LocalDate dueDate = currDate.plus(15, ChronoUnit.DAYS);
        BorrowedAssetEntity borrowAssetRecord = new BorrowedAssetEntity();
        borrowAssetRecord.setBorQuantity(borrowDetails.get("assetQuantity"));
        borrowAssetRecord.setBorDate(currDate);
        borrowAssetRecord.setDueDate(dueDate);
        borrowAssetRecord.setUser(userInterfaceRepo.getReferenceById(borrowDetails.get("userId")));
        borrowAssetRecord.setInventory(assetRepository.getReferenceById(borrowDetails.get("assetId")));
        borrowedAssetInterfaceRepo.save(borrowAssetRecord);
    }

    public void updateBorrowedAssetRecord(Integer assetID, Integer userID, Integer assetQuantity){
        borrowedAssetInterfaceRepo.updateBorrowedAssetRecord(assetID, userID, assetQuantity);
    }

    public BorrowedAssetEntity getBorrowRecord(Map<String,Integer> returnDetails){
        return borrowedAssetInterfaceRepo.getBorrowRecord(returnDetails.get("assetId"), returnDetails.get("userId"));
    }

    public void deleteBorrowRecord(Integer assetId, Integer userId){
        borrowedAssetInterfaceRepo.deleteBorrowedAssetEntity(assetId, userId);
    }

    public List<BorrowedAssetEntity> getBorrowRecordsForUser(Integer userId){
        return borrowedAssetInterfaceRepo.getBorrowRecordsUser(userId);
    }
}
