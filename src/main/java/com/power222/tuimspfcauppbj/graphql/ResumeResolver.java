package com.power222.tuimspfcauppbj.graphql;

import com.power222.tuimspfcauppbj.model.Resume;
import com.power222.tuimspfcauppbj.service.ResumeService;
import com.power222.tuimspfcauppbj.util.ReviewState;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ResumeResolver implements GraphQLQueryResolver {
    private final ResumeService resumeService;
    private final StudentApplicationResolver studentApplicationResolver;

    public ResumeResolver(ResumeService resumeService, StudentApplicationResolver studentApplicationResolver) {
        this.resumeService = resumeService;
        this.studentApplicationResolver = studentApplicationResolver;
    }

    public ResumeSchema resume(long id) {
        Optional<Resume> resumeOpt = resumeService.getResumeById(id);
        if (resumeOpt.isEmpty())
            return null;
        Resume resume = resumeOpt.get();

        return new ResumeSchema(
                id,
                resume.getFile(),
                resume.getName(),
                resume.getReviewState(),
                resume.getReasonForRejection(),
                resume.getOwner().getId(),
                resume.getApplications().stream().map(application -> studentApplicationResolver.studentApplication(application.getId())).collect(Collectors.toList())
        );
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ResumeSchema {
        private long id;
        private String file;
        private String name;
        private ReviewState reviewState;
        private String reasonForRejection;
        private long ownerId;
        private List<StudentApplicationResolver.StudentApplicationSchema> applications;
    }
}
