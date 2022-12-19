package com.burakbenli.studentmanagement.services;


import com.burakbenli.studentmanagement.entity.Lecture;
import com.burakbenli.studentmanagement.entity.Student;
import com.burakbenli.studentmanagement.repository.LectureRepocitory;
import com.burakbenli.studentmanagement.repository.StudentRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service

public class StudentService {

    private StudentRepository studentRepository;

    private final LectureRepocitory lectureRepocitory;

    private static final Logger logger = LoggerFactory.getLogger(LectureService.class);

    public StudentService(StudentRepository studentRepository,LectureRepocitory lectureRepocitory) {
        this.studentRepository = studentRepository;
        this.lectureRepocitory = lectureRepocitory;
    }


    public Optional<Student> getStudentById(Long id) {
        logger.info(id+" li ogrencinin bilgilerinin getirilmesi");
        return studentRepository.findById(id);
    }

    public List<Student> getAllStudent() {
        logger.info("Bütün Ogrencilerin Getirilmesi");
        return studentRepository.findAll();
    }


    public Student createStudent(@RequestBody Student studentRequest) throws Exception {
        Student student = studentRepository.findStudentBySecurityNumber(studentRequest.getSecurityNumber());
        if (student != null){
            logger.error(studentRequest.getId()+" li ogrencinin kaydı mevcuttur"+studentRequest.getFirstName() + "  " + studentRequest.getLastName());
            throw new Exception("Ogrenci sistemde kayıtlıdır");
        }
        studentRequest.setCreatedAt(OffsetDateTime.now());
        studentRequest.setUpdatedAt(OffsetDateTime.now());
        logger.info(studentRequest.getFirstName()+" " + studentRequest.getLastName() + "adlı ogrencinin kayıt işlemi yapılmaktadır");
        return studentRepository.save(studentRequest);
    }


    public Student updateStudent(@PathVariable(value = "id") Long id, @RequestBody Student studentRequest)
            throws Exception {
        Optional<Student> studentOption = studentRepository.findById(id);
        if (!studentOption.isPresent()) {
            logger.error(studentRequest.getId()+" li ogrencinin kayıtı mevcut değildir");
            throw new Exception("Kayıt Bulunamadı");
        }
        OffsetDateTime offsetdatetime = OffsetDateTime.now();
        return studentOption.map(student ->{
            student.setFirstName(studentRequest.getFirstName());
            student.setLastName(studentRequest.getLastName());
            student.setUpdatedAt(offsetdatetime);
            logger.info(studentRequest.getFirstName() + " " + studentRequest.getLastName()+" bilgileri güncelemesi yapılmaktadır");
            return studentRepository.save(student);
        }).get();

    }


    public void deleteStudent(@PathVariable(value = "id") Long id) throws Exception {
        logger.info(id+" kayıt silme isleminin yapılması");
        studentRepository.deleteById(id);
    }


    public void deleteLectureByStudent(@PathVariable(value = "id") Long id,@PathVariable(value = "lectureId") Long lectureId) throws Exception {
        Optional<Student> studentOptional = studentRepository.findById(id);
        studentOptional.ifPresent(student -> {
            Optional<Lecture> foundLecture =  student.getLectures().stream().filter(lecture -> lecture.getId().equals(lectureId)).findFirst();
            student.getLectures().remove(foundLecture);
            logger.info(id+" li ogrencinin " + lectureId +" derse kayıt işlemi gerceklesecektir");
            studentRepository.save(student);
        });

    }

    public void addLectureByStudent(@PathVariable(value = "id") Long id,@PathVariable(value = "lectureId") Long lectureId) throws RuntimeException{
        Optional<Student> studentOptional = studentRepository.findById(id);
        studentOptional.ifPresent(student -> {
            Lecture lecture = lectureRepocitory.findById(lectureId).get();
            if (lecture.getCurrentStudentNumber()<lecture.getMaxStudentNumber()){
                lecture.setCurrentStudentNumber(lecture.getCurrentStudentNumber()+1);
                student.getLectures().add(lecture);
                lecture.setStudents(new ArrayList<>(List.of(student)));
                lectureRepocitory.save(lecture);
            }else{
                logger.info(" Boş Kontenjan kalmadı ");
                throw new RuntimeException("Boş Kontenjan kalmadı");
            }

        });
    }

    public List<Lecture> getAllLectureByStudent(@PathVariable(value = "id") Long id) {
        Optional<Student>  student = studentRepository.findById(id);
        logger.info(" Ogrenci idlerine göre derslerin getirilmesi islemi gerceklesecektir ");
        return student.map(student1 -> student1.getLectures()).get();
    }


}
