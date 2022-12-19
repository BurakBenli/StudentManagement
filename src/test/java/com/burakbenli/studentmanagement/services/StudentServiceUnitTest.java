package com.burakbenli.studentmanagement.services;


import com.burakbenli.studentmanagement.entity.Student;
import com.burakbenli.studentmanagement.repository.LectureRepocitory;
import com.burakbenli.studentmanagement.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class StudentServiceUnitTest {

    @Test
    void testGetStudentById_returnEmptyWhenThereIsNoStudent() {

        StudentRepository studentRepository = mock(StudentRepository.class);

        LectureRepocitory lectureRepocitory = mock(LectureRepocitory.class);

        StudentService underTest = new StudentService(studentRepository,lectureRepocitory);

        when(studentRepository.findById(5L)).thenReturn(Optional.empty());

        Optional<Student> stundentOptional = underTest.getStudentById(5L);

        assertThat(stundentOptional).isEmpty();
    }

    @Test
    void getAllStudent_SuccessfullAll() {

        StudentRepository studentRepository = mock(StudentRepository.class);

        LectureRepocitory lectureRepocitory = mock(LectureRepocitory.class);

        StudentService underTest = new StudentService(studentRepository,lectureRepocitory);

        Student student1 = new Student();
        student1.setId(80L);
        student1.setFirstName("Burak");

        Optional<Student> studentOptional = Optional.of(student1);
        when(studentRepository.findById(80L)).thenReturn(studentOptional);

        assertThat(studentOptional).isNotEmpty();
        assertThat(studentOptional.get().getId()).isEqualTo(80L);
        assertThat(studentOptional.get().getFirstName().equals("Burak"));

    }



}