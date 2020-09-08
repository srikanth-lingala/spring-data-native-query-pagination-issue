package net.lingala.repository;

import net.lingala.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.Date;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query(value = "SELECT DISTINCT c.SHOP_ID " +
        "FROM CUSTOMER c " +
        "GROUP BY c.SHOP_ID " +
        "HAVING MAX (COALESCE (c.END_DATE, TO_DATE('31.12.9999', 'dd.mm.yyyy'))) < ?1", nativeQuery = true)
    Page<BigDecimal> findContractsToBeDeleted(Date earliestEndDateToKeep, Pageable pageable);
}
