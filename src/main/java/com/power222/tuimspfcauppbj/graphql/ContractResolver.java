package com.power222.tuimspfcauppbj.graphql;

import com.power222.tuimspfcauppbj.model.Contract;
import com.power222.tuimspfcauppbj.model.StudentApplication;
import com.power222.tuimspfcauppbj.service.ContractService;
import com.power222.tuimspfcauppbj.service.StudentApplicationService;
import com.power222.tuimspfcauppbj.util.ContractSignatureState;
import com.power222.tuimspfcauppbj.util.StudentApplicationState;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ContractResolver implements GraphQLQueryResolver {
    private final ContractService contractService;
    private final StudentApplicationService studentApplicationService;

    public ContractResolver(ContractService contractService, StudentApplicationService studentApplicationService) {
        this.contractService = contractService;
        this.studentApplicationService = studentApplicationService;
    }

    public ContractSchema contract(long id) {
        Optional<Contract> contractOpt = contractService.getContractById(id);
        if (contractOpt.isEmpty())
            return null;
        Contract contract = contractOpt.get();

        return new ContractSchema(
                id,
                contract.getFile(),
                contract.getEngagementCollege(),
                contract.getEngagementCompany(),
                contract.getEngagementStudent(),
                contract.getTotalHoursPerWeek(),
                contract.getReasonForRejection(),
                contract.getSignatureState(),
                studentApplication(contract.getStudentApplication() != null ? contract.getStudentApplication().getId() : -1L),
                contract.getInternEvaluation() != null ? contract.getInternEvaluation().getId() : -1L,
                contract.getAdmin() != null ? contract.getAdmin().getId() : -1L,
                contract.getBusinessEvaluation() != null ? contract.getBusinessEvaluation().getId() : -1L
        );
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
    private static class StudentApplicationSchema {
        private long id;
        private StudentApplicationState state;
        private String reasonForRejection;
        private long offerId;
        private long studentId;
        private long resumeId;
        private long interviewId;
        private long contractId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    private static class ContractSchema {
        private long id;
        private String file;
        private String engagementCollege;
        private String engagementCompany;
        private String engagementStudent;
        private float totalHoursPerWeek;
        private String reasonForRejection;
        private ContractSignatureState signatureState;
        private StudentApplicationSchema studentApplication;
        private long internEvaluationId;
        private long adminId;
        private long businessEvaluationId;
    }
}
