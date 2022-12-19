package com.burakbenli.studentmanagement.controller;


import com.burakbenli.studentmanagement.entity.Lecture;
import com.burakbenli.studentmanagement.entity.Student;
import com.burakbenli.studentmanagement.repository.LectureRepocitory;
import com.burakbenli.studentmanagement.repository.StudentRepository;
import com.burakbenli.studentmanagement.services.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {


    private final StudentRepository studentRepository;

    private final LectureRepocitory lectureRepocitory;

    private final StudentService studentService;

    private static final Logger logger = LoggerFactory.getLogger(LectureController.class);

    public StudentController(StudentRepository studentRepository,LectureRepocitory lectureRepocitory,StudentService studentService) {
        this.studentRepository = studentRepository;
        this.lectureRepocitory = lectureRepocitory;
        this.studentService = studentService;
    }



    @GetMapping("/{id}")
    public Optional<Student> getStudentById(@PathVariable(value = "id") Long id) {
        logger.info(id+" li ogrencinin bilgilerinin getirilmesi");
        return studentService.getStudentById(id);
    }

    @GetMapping("/allStudent")
    public List<Student> getAllStudent() {
        logger.info("Bütün Ogrencilerin Getirilmesi");
        return studentService.getAllStudent();
    }

    @PostMapping("")
    public Student createStudent(@RequestBody Student studentRequest) throws Exception {
     logger.info(studentRequest.getFirstName()+" " + studentRequest.getLastName() + "adlı ogrencinin kayıt işlemi yapılmaktadır");
     return studentService.createStudent(studentRequest);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable(value = "id") Long id, @RequestBody Student studentRequest)
            throws Exception {
        return studentService.updateStudent(id,studentRequest);

    }

    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable(value = "id") Long id) throws Exception {
        logger.info(id+" kayıt silme isleminin yapılması");
        studentService.deleteStudent(id);
    }

    @DeleteMapping("/{id}/deleteLecture/{lectureId}")
    public void deleteLectureByStudent(@PathVariable(value = "id") Long id,@PathVariable(value = "lectureId") Long lectureId) throws Exception {
        studentService.deleteLectureByStudent(id,lectureId);

    }

    @PostMapping("/{id}/addLecture/{lectureId}")
    @Transactional
    public void addLectureByStudent(@PathVariable(value = "id") Long id,@PathVariable(value = "lectureId") Long lectureId) throws RuntimeException{
        studentService.addLectureByStudent(id,lectureId);
    }

    @GetMapping("/lecturesByStudents/{id}")
    public List<Lecture> getAllLectureByStudent(@PathVariable(value = "id") Long id) {
      logger.info(" Ogrenci idlerine göre derslerin getirilmesi islemi gerceklesecektir ");
      return studentService.getAllLectureByStudent(id);
    }


 }


