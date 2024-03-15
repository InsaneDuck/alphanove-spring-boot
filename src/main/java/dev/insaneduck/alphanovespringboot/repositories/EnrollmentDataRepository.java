package dev.insaneduck.alphanovespringboot.repositories;

import dev.insaneduck.alphanovespringboot.entities.EnrollmentData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrollmentDataRepository extends JpaRepository<EnrollmentData, Integer> {
}