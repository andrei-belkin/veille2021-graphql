package com.power222.tuimspfcauppbj.graphql.resolver;

import com.power222.tuimspfcauppbj.dao.StudentApplicationRepository;
import com.power222.tuimspfcauppbj.model.Resume;
import com.power222.tuimspfcauppbj.model.StudentApplication;
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
    private final StudentApplicationRepository studentApplicationRepository;

    public ResumeResolver(ResumeService resumeService, StudentApplicationRepository studentApplicationRepository) {
        this.resumeService = resumeService;
        this.studentApplicationRepository = studentApplicationRepository;
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
                studentApplicationRepository
                        .findAllByResume_Id(resume.getId())
                        .stream()
                        .map(StudentApplication::getId)
                        .collect(Collectors.toList())
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
        private List<Long> applicationIds;
    }
}
