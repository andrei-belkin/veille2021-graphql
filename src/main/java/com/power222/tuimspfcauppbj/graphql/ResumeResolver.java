package com.power222.tuimspfcauppbj.graphql;

import com.power222.tuimspfcauppbj.dao.StudentApplicationRepository;
import com.power222.tuimspfcauppbj.model.Resume;
import com.power222.tuimspfcauppbj.model.StudentApplication;
import com.power222.tuimspfcauppbj.service.ResumeService;
import com.power222.tuimspfcauppbj.graphql.StudentApplicationResolver.StudentApplicationSchema;
import com.power222.tuimspfcauppbj.util.ReviewState;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ResumeResolver implements GraphQLQueryResolver {
    private final ResumeService resumeService;
    private final StudentApplicationResolver studentApplicationResolver;
    private final StudentApplicationRepository studentApplicationRepository;

    public ResumeResolver(ResumeService resumeService, StudentApplicationResolver studentApplicationResolver, StudentApplicationRepository studentApplicationRepository) {
        this.resumeService = resumeService;
        this.studentApplicationResolver = studentApplicationResolver;
        this.studentApplicationRepository = studentApplicationRepository;
    }

    public ResumeSchema resume(long id) {
        Optional<Resume> resumeOpt = resumeService.getResumeById(id);
        if (resumeOpt.isEmpty())
            return null;
        Resume resume = resumeOpt.get();

        List<StudentApplication> studentApplications = studentApplicationRepository.findAllByResume_Id(resume.getId());
        List<StudentApplicationSchema> studentApplicationSchemas = new ArrayList<>();
        for (StudentApplication application : studentApplications)
            studentApplicationSchemas.add(studentApplicationResolver.studentApplication(application.getId()));

        return new ResumeSchema(
                id,
                resume.getFile(),
                resume.getName(),
                resume.getReviewState(),
                resume.getReasonForRejection(),
                resume.getOwner().getId(),
                studentApplicationSchemas
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
