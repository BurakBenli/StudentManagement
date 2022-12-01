package com.burakbenli.studentmanagement.controller;

import com.burakbenli.studentmanagement.entity.Lecture;
import com.burakbenli.studentmanagement.entity.Student;
import com.burakbenli.studentmanagement.repository.LectureRepocitory;
import org.springframework.web.bind.annotation.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/lectures")
public class LectureController {

    private final LectureRepocitory lectureRepocitory;

    public LectureController(LectureRepocitory lectureRepocitory) {
        this.lectureRepocitory = lectureRepocitory;
    }

    @GetMapping("/{id}")
    public Optional<Lecture> getLectureById(@PathVariable(value = "id") Long id) {
        return lectureRepocitory.findById(id);
    }

    @GetMapping("/allLecture")
    public List<Lecture> getAllLecture() {
        return lectureRepocitory.findAll();
    }

    @PostMapping("")
    public Lecture createLecture(@RequestBody Lecture lectureRequest) throws Exception {
        Lecture lecture = lectureRepocitory.findLectureByLectureCode(lectureRequest.getLectureCode());
        if (lecture != null){
            throw new Exception("Ders sistemde kayıtlıdır");
        }
        lectureRequest.setCreatedAt(OffsetDateTime.now());
        lectureRequest.setUpdatedAt(OffsetDateTime.now());
        return lectureRepocitory.save(lectureRequest);
    }

    @PutMapping("/{id}")
    public Lecture updateLecture(@PathVariable(value = "id") Long id, @RequestBody Lecture lectureRequest)
            throws Exception {
        Optional<Lecture> lectureOption = lectureRepocitory.findById(id);
        if (!lectureOption.isPresent()) {
            throw new Exception("Kayıt Bulunamadı");
        }
        OffsetDateTime offsetdatetime = OffsetDateTime.now();
        return lectureOption.map(lecture ->{
            lecture.setLectureName(lectureRequest.getLectureName());
            lecture.setLectureCode(lectureRequest.getLectureCode());
            lecture.setUpdatedAt(offsetdatetime);
            return lectureRepocitory.save(lecture);
        }).get();

    }

    @GetMapping("/allStudentByAllLecture")
    public List<Student> getAllStudentByAllLecture() {
       return  lectureRepocitory.findAll().stream().flatMap(lecture -> {
             List<Student> students = lecture.getStudents();
             return students.stream();
         }).distinct().collect(Collectors.toList());

    }


}
