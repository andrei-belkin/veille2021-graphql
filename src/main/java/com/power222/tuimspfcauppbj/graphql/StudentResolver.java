package com.power222.tuimspfcauppbj.graphql;

import com.power222.tuimspfcauppbj.dao.InternshipOfferRepository;
import com.power222.tuimspfcauppbj.graphql.StudentApplicationResolver.StudentApplicationSchema;
import com.power222.tuimspfcauppbj.graphql.ResumeResolver.ResumeSchema;
import com.power222.tuimspfcauppbj.model.Student;
import com.power222.tuimspfcauppbj.service.StudentService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class StudentResolver implements GraphQLQueryResolver {
    private final StudentService studentService;
    private final ResumeResolver resumeResolver;
    private final StudentApplicationResolver studentApplicationResolver;
    private final InternshipOfferRepository internshipOfferRepository;

    public StudentResolver(StudentService studentService, ResumeResolver resumeResolver, StudentApplicationResolver studentApplicationResolver, InternshipOfferRepository internshipOfferRepository) {
        this.studentService = studentService;
        this.resumeResolver = resumeResolver;
        this.studentApplicationResolver = studentApplicationResolver;
        this.internshipOfferRepository = internshipOfferRepository;
    }

    public StudentSchema student(long id) {
        Optional<Student> studentOpt = studentService.getStudentById(id);
        if (studentOpt.isEmpty())
            return null;
        Student student = studentOpt.get();

        List<Long> allowedOffers = new ArrayList<>();
        internshipOfferRepository.findAllByAllowedStudentsId(id).forEach(offer -> allowedOffers.add(offer.getId()));

        return new StudentSchema(
                student.getId(),
                student.getFirstName(),
                student.getLastName(),
                student.getFullName(),
                student.getStudentId(),
                student.getEmail(),
                student.isPasswordExpired(),
                student.isDisabled(),
                student.getPhoneNumber(),
                student.getAddress(),
                student.getSemesters(),
                student.getResumes().stream().map(resume -> resumeResolver.resume(resume.getId())).collect(Collectors.toList()),
                allowedOffers,
                student.getApplications().stream().map(studentApplication -> studentApplicationResolver.studentApplication(studentApplication.getId())).collect(Collectors.toList())
        );
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudentSchema {
        private long id;
        private String firstName;
        private String lastName;
        private String fullName;
        private String studentId;
        private String email;
        private boolean passwordExpired;
        private boolean disabled;
        private String phoneNumber;
        private String address;
        private List<String> semesters;
        private List<ResumeSchema> resumes;
        private List<Long> allowedOffers;
        private List<StudentApplicationSchema> applications;
    }
}
