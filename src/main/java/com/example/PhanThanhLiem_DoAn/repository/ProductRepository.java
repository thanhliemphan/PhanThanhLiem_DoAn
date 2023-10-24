package com.example.PhanThanhLiem_DoAn.repository;

import com.example.PhanThanhLiem_DoAn.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    @Query("select p from Product p where p.currentQuantity > 0")
//    Page<Product> pageProduct(Pageable pageable);

    @Query("select p from Product p where (p.description like %?1% or p.name like %?1%) and p.currentQuantity > 0")
    Page<Product> searchProducs(String keyword, Pageable pageable);

    @Query("select p from Product p where (p.description like %?1% or p.name like %?1%) and p.is_activated = true and p.is_deleted = false and p.currentQuantity > 0 order by p.costPrice asc")
    List<Product> searchProductsListAsc(String keyword);
    @Query("select p from Product p where (p.description like %?1% or p.name like %?1%) and p.is_activated = true and p.is_deleted = false and p.currentQuantity > 0 order by p.costPrice desc")
    List<Product> searchProductsListDesc(String keyword);

    @Query("select p from Product p where p.is_activated = true and p.is_deleted = false and p.currentQuantity > 0")
    List<Product> getAllProduct();
    @Query(value = "select p.product_id, p.name, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image, p.is_activated, p.is_deleted from products p where p.is_deleted = false and p.is_activated = true and p.current_quantity > 0 limit 4", nativeQuery = true)
    List<Product> listViewProduct();

    @Query(value = "select p from Product p inner join Category c on c.id = ?1 and p.category.id = ?1 where p.is_activated = true and p.is_deleted = false and p.currentQuantity > 0")
    List<Product> getRelatedProducts(Long categoryId);
//    @Query(value = "select " +
//            "p.product_id, p.name, p.description, p.current_quantity, p.cost_price, p.category_id, p.sale_price, p.image, p.is_activated, p.is_deleted " +
//            "from products p where p.is_activated = true and p.is_deleted = false and p.current_quantity > 0 order by rand() limit 16", nativeQuery = true)
//    List<Product> randomProduct();

    @Query("select p from Product p inner join Category c on c.id = p.category.id where c.id = ?1 and p.is_deleted = false and p.is_activated = true and p.currentQuantity > 0")
    List<Product> getProductsInCategory(Long categoryId);

//    @Query("select p from Product p where p.is_activated = true and p.is_deleted = false and p.currentQuantity > 0 order by p.costPrice desc limit 16")
//    List<Product> filterHighPrice();
//
//    @Query("select p from Product p where p.is_activated = true and p.is_deleted = false and p.currentQuantity > 0 order by p.costPrice asc limit 16")
//    List<Product> filterLowPrice();

    @Query("select p from Product p inner join Category c ON c.id = p.category.id" +
            " where p.category.name = ?1 and p.is_activated = true and p.is_deleted = false and p.currentQuantity > 0")
    List<Product> findAllByCategory(String category);
    @Query("select p from Product p where (p.name like %?1% or p.description like %?1%) and p.currentQuantity > 0")
    List<Product> findAllByNameOrDescription(String keyword);

}
