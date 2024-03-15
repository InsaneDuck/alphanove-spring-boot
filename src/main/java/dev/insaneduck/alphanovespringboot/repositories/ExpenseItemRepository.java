package dev.insaneduck.alphanovespringboot.repositories;

import dev.insaneduck.alphanovespringboot.entities.ExpenseItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseItemRepository extends JpaRepository<ExpenseItem, Integer> {
}