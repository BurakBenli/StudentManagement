package com.burakbenli.studentmanagement.services;

import com.burakbenli.studentmanagement.entity.Lecture;
import com.burakbenli.studentmanagement.entity.Student;
import com.burakbenli.studentmanagement.repository.LectureRepocitory;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class LectureServiceUnitTest {

    @Test
    void testGetLectureById_returnEmptyWhenThereIsNoLecture() {

        LectureRepocitory lectureRepocitory = mock(LectureRepocitory.class);
        LectureService underTest = new LectureService(lectureRepocitory);

        when(lectureRepocitory.findById(5L)).thenReturn(Optional.empty());

        Optional<Lecture> lectureOptional = underTest.getLectureById(5L);

        assertThat(lectureOptional).isEmpty();
    }

    @Test
    void testGetLectureById_successfullPath() {
        LectureRepocitory lectureRepocitory = mock(LectureRepocitory.class);
        LectureService underTest = new LectureService(lectureRepocitory);

        Lecture lecture = new Lecture();
        lecture.setId(1L);
        lecture.setLectureName("Fizik101");

        Optional<Lecture> lectureOptional = Optional.of(lecture);

        when(lectureRepocitory.findById(1L)).thenReturn(lectureOptional);

        //Test
        Optional<Lecture> actualOptional = underTest.getLectureById(5L);

        assertThat(lectureOptional).isNotEmpty();
        assertThat(lectureOptional.get().getId()).isEqualTo(1L);
        assertThat(lectureOptional.get().getLectureName().equals("Fizik101"));
    }


    @Test
    void testGetAllStudentByAllLecture_whenThereIsNoStudentInAnyLecture() {
        LectureRepocitory lectureRepocitory = mock(LectureRepocitory.class);
        LectureService underTest = new LectureService(lectureRepocitory);

        Lecture lecture1 = new Lecture();
        lecture1.setLectureName("Kımya101");
        lecture1.setStudents(Collections.emptyList());

        Lecture lecture2 = new Lecture();
        lecture2.setLectureName("Matematık101");
        lecture2.setStudents(Collections.emptyList());
        when(lectureRepocitory.findAll()).thenReturn(Arrays.asList(lecture1, lecture2));

        //Test
        List<Student> students = underTest.getAllStudentByAllLecture();

        assertThat(students).isNotNull();

        assertThat(students).isEmpty();

    }

    @Test
    void testGetAllStudentByAllLecture_whenThereAreStudentsinDifferentLectures() {

        LectureRepocitory lectureRepocitory = mock(LectureRepocitory.class);
        LectureService underTest = new LectureService(lectureRepocitory);


        Lecture lecture1 = new Lecture();
        lecture1.setLectureName("Kımya101");
        lecture1.setLectureCode("100");

        Lecture lecture2 = new Lecture();
        lecture2.setLectureName("Matematık101");
        lecture2.setLectureCode("101");


        Student student1 = new Student();
        student1.setId(12L);
        student1.setFirstName("Burak");
        student1.setLastName("Benli");
        student1.setLectures(Arrays.asList(lecture1,lecture2));

        Student student2 = new Student();
        student2.setId(13L);
        student2.setFirstName("Hüseyin");
        student2.setLastName("Benli");
        student2.setLectures(Arrays.asList(lecture2));

        lecture1.setStudents(Arrays.asList(student1));
        lecture2.setStudents(Arrays.asList(student1, student2));
        when(lectureRepocitory.findAll()).thenReturn(Arrays.asList(lecture1, lecture2));


        List<Student> students = underTest.getAllStudentByAllLecture();

        assertThat(students).isNotNull();
        assertThat(students).isNotEmpty();
        assertThat(students.size()).isEqualTo(2);
        assertThat(lecture1.getStudents()).contains(student1);
        assertThat(lecture2.getStudents()).containsAll(Arrays.asList(student1,student2));
        assertThat(students.get(0).getId()).isEqualTo(12L);
        assertThat(students.get(1).getId()).isEqualTo(13L);



    }

    @Test
    void testGetAllLecture() {
    }

    @Test
    void testCreateLecture() {
    }

    @Test
    void testUpdateLecture() {
    }
}