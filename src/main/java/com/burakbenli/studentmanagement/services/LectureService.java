package com.burakbenli.studentmanagement.services;


import com.burakbenli.studentmanagement.entity.Lecture;
import com.burakbenli.studentmanagement.entity.Student;
import com.burakbenli.studentmanagement.repository.LectureRepocitory;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class LectureService {

    private final LectureRepocitory lectureRepocitory;

    private static final Logger logger = LoggerFactory.getLogger(LectureService.class);

    public Optional<Lecture> getLectureById(Long id){
        logger.info("Dersler id göre bulunması");
        return lectureRepocitory.findById(id);

    }

    public List<Lecture> getAllLecture(){
        logger.info("Bütün Derslerin Getirilmesi");
        return lectureRepocitory.findAll();

    }

    public Lecture createLecture(@RequestBody Lecture lectureRequest) throws Exception {
        Lecture lecture = lectureRepocitory.findLectureByLectureCode(lectureRequest.getLectureCode());
        if (lecture != null){
            logger.error(lectureRequest.getLectureName()+" adında kayıt mevcuttur "+lectureRequest.getId() + "" + lectureRequest.getLectureCode());
            throw new Exception("Ders sistemde kayıtlıdır");
        }
        lectureRequest.setCreatedAt(OffsetDateTime.now());
        lectureRequest.setUpdatedAt(OffsetDateTime.now());
        logger.info(lectureRequest.getLectureName() + " ders kaydı işlemi gerçekleşecektir");
        return lectureRepocitory.save(lectureRequest);
    }


    public Lecture updateLecture(@PathVariable(value = "id") Long id, @RequestBody Lecture lectureRequest)
            throws Exception {
        Optional<Lecture> lectureOption = lectureRepocitory.findById(id);
        if (!lectureOption.isPresent()) {
            logger.error("Kayıt Bulunamadı");
            throw new Exception("Kayıt Bulunamadı");
        }
        OffsetDateTime offsetdatetime = OffsetDateTime.now();
        logger.info(lectureRequest.getLectureName()+" dersin update işlemi yapılmaktadır");
        return lectureOption.map(lecture ->{
            lecture.setLectureName(lectureRequest.getLectureName());
            lecture.setLectureCode(lectureRequest.getLectureCode());
            lecture.setUpdatedAt(offsetdatetime);
            return lectureRepocitory.save(lecture);
        }).get();

    }

    public List<Student> getAllStudentByAllLecture(){
        logger.info("Ogrencilere göre dersler sıralanması ");
        return  lectureRepocitory.findAll().stream().flatMap(lecture -> {
            List<Student> students = lecture.getStudents();
            return students.stream();
        }).distinct().collect(Collectors.toList());
    }
}
