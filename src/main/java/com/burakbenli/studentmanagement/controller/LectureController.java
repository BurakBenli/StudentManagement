package com.burakbenli.studentmanagement.controller;

import com.burakbenli.studentmanagement.entity.Lecture;
import com.burakbenli.studentmanagement.entity.Student;
import com.burakbenli.studentmanagement.repository.LectureRepocitory;
import com.burakbenli.studentmanagement.services.LectureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/lectures")
public class LectureController {

    private final LectureRepocitory lectureRepocitory;

    private final LectureService lectureService;

    private static final Logger logger = LoggerFactory.getLogger(LectureController.class);

    public LectureController(LectureRepocitory lectureRepocitory, LectureService lectureService) {
        this.lectureRepocitory = lectureRepocitory;
        this.lectureService = lectureService;
    }

    @GetMapping("/{id}")
    public Optional<Lecture> getLectureById(@PathVariable(value = "id") Long id) {
        logger.info(id+" li dersin bilgilerinin getirilmesi");
        return lectureService.getLectureById(id);
    }

    @GetMapping("/allLecture")
    public List<Lecture> getAllLecture() {
        logger.info("Bütün Derslerin Getirilmesi");
        return lectureService.getAllLecture();
    }

    @PostMapping("")
    public Lecture createLecture(@RequestBody Lecture lectureRequest) throws Exception {
        logger.info(lectureRequest.getLectureName() + " ders kaydı işlemi gerçekleşecektir");
        return lectureService.createLecture(lectureRequest);
    }

    @PutMapping("/{id}")
    public Lecture updateLecture(@PathVariable(value = "id") Long id, @RequestBody Lecture lectureRequest)
            throws Exception {
        logger.info(lectureRequest.getLectureName()+" dersin update işlemi yapılmaktadır");
        return lectureService.updateLecture(id,lectureRequest);

    }

    @GetMapping("/allStudentByAllLecture")
    public List<Student> getAllStudentByAllLecture() {
        logger.info("Derslere gore ogrencilerin getirilmesi");
        return lectureService.getAllStudentByAllLecture();
    }


}
