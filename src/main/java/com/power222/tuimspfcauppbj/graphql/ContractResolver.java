package com.power222.tuimspfcauppbj.graphql;

import com.power222.tuimspfcauppbj.graphql.StudentApplicationResolver.StudentApplicationSchema;
import com.power222.tuimspfcauppbj.model.Contract;
import com.power222.tuimspfcauppbj.service.ContractService;
import com.power222.tuimspfcauppbj.util.ContractSignatureState;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ContractResolver implements GraphQLQueryResolver {
    private final ContractService contractService;
    private final StudentApplicationResolver studentApplicationResolver;

    public ContractResolver(ContractService contractService, StudentApplicationResolver studentApplicationResolver) {
        this.contractService = contractService;
        this.studentApplicationResolver = studentApplicationResolver;
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
                studentApplicationResolver.studentApplication(contract.getStudentApplication() != null ? contract.getStudentApplication().getId() : -1L),
                contract.getInternEvaluation() != null ? contract.getInternEvaluation().getId() : -1L,
                contract.getAdmin() != null ? contract.getAdmin().getId() : -1L,
                contract.getBusinessEvaluation() != null ? contract.getBusinessEvaluation().getId() : -1L
        );
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
