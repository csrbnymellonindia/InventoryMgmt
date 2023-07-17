package com.example.demo.Services;

import com.example.demo.repository.AssetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AssetService {
    private final AssetRepository assetRepository;

    public Integer getAvailableQuantity(Integer assetID){
        Integer borrowedQuantity = assetRepository.getAssetBorrowQuantity(assetID);
        System.out.println("Borrow Quantity : "+borrowedQuantity);
        Integer totalQuantity = assetRepository.getAssetQuantity(assetID);
        System.out.println("Total Quantity : "+totalQuantity);
        return totalQuantity - borrowedQuantity;
    }

    public void setAssetBorQuantity(Integer assetId, Integer assetQuantity){
        assetRepository.updateAssetBorQuantity(assetId,assetQuantity);
    }
}
