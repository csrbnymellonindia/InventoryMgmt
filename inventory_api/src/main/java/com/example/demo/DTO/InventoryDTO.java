package com.example.demo.DTO;

import com.example.demo.entities.Category;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class InventoryDTO {

    private Integer assetId;
    private String assetName;
    private Integer categoryId;
    private Integer quantity;
    private Integer bor_quantity;
    private Integer minQuantity;
}
