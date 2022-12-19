package com.burakbenli.studentmanagement.repository;

import com.burakbenli.studentmanagement.entity.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface LectureRepocitory extends JpaRepository<Lecture,Long> {

    Optional<Lecture> findById(final Long id);

    Lecture findLectureByLectureCode(final String lectureCode);

}
