package com.plm.pdmservice.repository;

import com.plm.pdmservice.entity.BomItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BomItemRepository extends JpaRepository<BomItem, Long> {
    
    List<BomItem> findByProductId(Long productId);
    
    List<BomItem> findByParentItemId(Long parentItemId);
    
    List<BomItem> findByProductIdAndParentItemIdIsNull(Long productId);
}