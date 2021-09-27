package com.power222.tuimspfcauppbj.graphql;

import com.power222.tuimspfcauppbj.dao.InternshipOfferRepository;
import com.power222.tuimspfcauppbj.model.InternshipOffer;
import com.power222.tuimspfcauppbj.model.Resume;
import com.power222.tuimspfcauppbj.model.Student;
import com.power222.tuimspfcauppbj.model.StudentApplication;
import com.power222.tuimspfcauppbj.service.StudentService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentResolver implements GraphQLQueryResolver {
    private final StudentService studentService;
    private final InternshipOfferRepository internshipOfferRepository;

    public StudentResolver(StudentService studentService, InternshipOfferRepository internshipOfferRepository) {
        this.studentService = studentService;
        this.internshipOfferRepository = internshipOfferRepository;
    }

    public StudentSchema student(long id) {
        Optional<Student> studentOpt = studentService.getStudentById(id);
        if (studentOpt.isEmpty())
            return null;
        Student student = studentOpt.get();

        return new StudentSchema(
                student.getId(),
                student.getStudentId(),
                student.getFirstName(),
                student.getLastName(),
                student.getFullName(),
                student.getEmail(),
                student.getPhoneNumber(),
                student.getAddress(),
                student.isPasswordExpired(),
                student.isDisabled(),
                student.getSemesters(),
                student.getResumes().stream().map(Resume::getId).collect(Collectors.toList()),
                internshipOfferRepository.findAllByAllowedStudentsId(id).stream().map(InternshipOffer::getId).collect(Collectors.toList()),
                student.getApplications().stream().map(StudentApplication::getId).collect(Collectors.toList())
        );
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudentSchema {
        private long id;
        private String studentId;
        private String firstName, lastName, fullName;
        private String email, phoneNumber, address;
        private boolean passwordExpired, disabled;
        private List<String> semesters;
        private List<Long> resumeIds, allowedOfferIds, applicationIds;
    }
}
