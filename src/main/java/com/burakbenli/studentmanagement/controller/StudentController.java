package com.burakbenli.studentmanagement.controller;


import com.burakbenli.studentmanagement.entity.Lecture;
import com.burakbenli.studentmanagement.entity.Student;
import com.burakbenli.studentmanagement.repository.LectureRepocitory;
import com.burakbenli.studentmanagement.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class StudentController {


    private final StudentRepository studentRepository;

    private final LectureRepocitory lectureRepocitory;
    public StudentController(StudentRepository studentRepository,LectureRepocitory lectureRepocitory) {
        this.studentRepository = studentRepository;
        this.lectureRepocitory = lectureRepocitory;
    }



    @GetMapping("/{id}")
    public Optional<Student> getStudentById(@PathVariable(value = "id") Long id) {
        return studentRepository.findById(id);
    }

    @GetMapping("/allStudent")
    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    @PostMapping("")
    public Student createStudent(@RequestBody Student studentRequest) throws Exception {
     Student student = studentRepository.findStudentBySecurityNumber(studentRequest.getSecurityNumber());
     if (student != null){
         throw new Exception("Ogrenci sistemde kayıtlıdır");
     }
     studentRequest.setCreatedAt(OffsetDateTime.now());
     studentRequest.setUpdatedAt(OffsetDateTime.now());
     return studentRepository.save(studentRequest);
    }

    @PutMapping("/{id}")
    public Student updateStudent(@PathVariable(value = "id") Long id, @RequestBody Student studentRequest)
            throws Exception {
        Optional<Student> studentOption = studentRepository.findById(id);
        if (!studentOption.isPresent()) {
            throw new Exception("Kayıt Bulunamadı");
        }
        OffsetDateTime offsetdatetime = OffsetDateTime.now();
        return studentOption.map(student ->{
            student.setFirstName(studentRequest.getFirstName());
            student.setLastName(studentRequest.getLastName());
            student.setUpdatedAt(offsetdatetime);
            return studentRepository.save(student);
        }).get();

    }

    @DeleteMapping("/delete/{id}")
    public void deleteStudent(@PathVariable(value = "id") Long id) throws Exception {
        studentRepository.deleteById(id);
    }

    @DeleteMapping("/{id}/deleteLecture/{lectureId}")
    public void deleteLectureByStudent(@PathVariable(value = "id") Long id,@PathVariable(value = "lectureId") Long lectureId) throws Exception {
        Optional<Student> studentOptional = studentRepository.findById(id);
        studentOptional.ifPresent(student -> {
          Optional<Lecture> foundLecture =  student.getLectures().stream().filter(lecture -> lecture.getId().equals(lectureId)).findFirst();
          student.getLectures().remove(foundLecture);
          studentRepository.save(student);
        });

    }

    @PostMapping("/{id}/addLecture/{lectureId}")
    public void addLectureByStudent(@PathVariable(value = "id") Long id,@PathVariable(value = "lectureId") Long lectureId) throws RuntimeException{
        Optional<Student> studentOptional = studentRepository.findById(id);
        studentOptional.ifPresent(student -> {
            Lecture lecture = lectureRepocitory.findById(lectureId).get();
            if (lecture.getCurrentStudentNumber()<lecture.getMaxStudentNumber()){
                lecture.setCurrentStudentNumber(lecture.getCurrentStudentNumber()+1);
                student.getLectures().add(lecture);
                studentRepository.save(student);
            }else{
                throw new RuntimeException("Boş Kontenjan kalmadı");
            }

        });
    }

    @GetMapping("/lecturesByStudents/{id}")
    public List<Lecture> getAllLectureByStudent(@PathVariable(value = "id") Long id) {
      Optional<Student>  student = studentRepository.findById(id);
      return student.map(student1 -> student1.getLectures()).get();

    }




 }


