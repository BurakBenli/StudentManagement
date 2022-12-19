package com.burakbenli.studentmanagement.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity
@Table(name = "students")
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","lectures"})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Column(unique = true,name = "securityNumber")
    private String securityNumber;

    @Column(name="firstName")
    private String firstName;

    @Column(name="lastName")
    private String lastName;

    @Column(name="createdAt")
    private OffsetDateTime createdAt;

    @Column(name="updatedAt")
    private OffsetDateTime updatedAt;


    @ManyToMany(mappedBy = "students",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Lecture> lectures;

}
