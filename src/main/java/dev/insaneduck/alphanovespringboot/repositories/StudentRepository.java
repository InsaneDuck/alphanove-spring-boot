package dev.insaneduck.alphanovespringboot.repositories;

import dev.insaneduck.alphanovespringboot.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Integer> {
}