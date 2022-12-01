package com.burakbenli.studentmanagement.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="lectures")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Lecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;


    @Column(name="lectureName")
    private String lectureName;

    @Column(name="lectureCode")
    private String lectureCode;

    @Column(name="createdAt")
    private OffsetDateTime createdAt;

    @Column(name="updatedAt")
    private OffsetDateTime updatedAt;

    @Column(name="maxStudentNumber")
    private int maxStudentNumber;

    @Column(name="currentStudentNumber")
    private int currentStudentNumber;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "lecture_student",
            joinColumns = @JoinColumn(name = "lecture_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "student_id",referencedColumnName = "id"))
    private List<Student> students;


}
