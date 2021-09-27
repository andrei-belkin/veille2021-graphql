package com.power222.tuimspfcauppbj.graphql;

import com.power222.tuimspfcauppbj.model.StudentApplication;
import com.power222.tuimspfcauppbj.service.StudentApplicationService;
import com.power222.tuimspfcauppbj.util.StudentApplicationState;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StudentApplicationResolver implements GraphQLQueryResolver {
    private final StudentApplicationService studentApplicationService;

    public StudentApplicationResolver(StudentApplicationService studentApplicationService) {
        this.studentApplicationService = studentApplicationService;
    }

    public StudentApplicationSchema studentApplication(long id) {
        Optional<StudentApplication> studentApplicationOpt = studentApplicationService.getApplicationById(id);
        if (studentApplicationOpt.isEmpty())
            return null;
        StudentApplication studentApplication = studentApplicationOpt.get();

        return new StudentApplicationSchema(
                id,
                studentApplication.getState(),
                studentApplication.getReasonForRejection(),
                studentApplication.getOffer() != null ? studentApplication.getOffer().getId() : -1L,
                studentApplication.getStudent() != null ? studentApplication.getStudent().getId() : -1L,
                studentApplication.getResume() != null ? studentApplication.getResume().getId() : -1L,
                studentApplication.getInterview() != null ? studentApplication.getInterview().getId() : -1L,
                studentApplication.getContract() != null ? studentApplication.getContract().getId() : -1L
        );
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StudentApplicationSchema {
        private long id;
        private StudentApplicationState state;
        private String reasonForRejection;
        private long offerId;
        private long studentId;
        private long resumeId;
        private long interviewId;
        private long contractId;
    }
}
