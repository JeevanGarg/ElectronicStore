package com.ElectronicStore.repository;

import com.ElectronicStore.entities.Category;
import com.ElectronicStore.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,String>
{
    Page<Product> findByTitleContaining(String title, Pageable pageable);
    //all products which are live
    Page<Product> findByLiveTrue(Pageable pageable);

    Page<Product> findByCategory(Category category,Pageable pageable);
}
