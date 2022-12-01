package com.burakbenli.studentmanagement.repository;

import com.burakbenli.studentmanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findById(final Long id);

    Student findStudentBySecurityNumber(String securityNumber);


}
