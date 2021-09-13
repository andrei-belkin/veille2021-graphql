package com.power222.tuimspfcauppbj.graphql;

import com.power222.tuimspfcauppbj.util.ContractSignatureState;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
public class ContractResolver implements GraphQLQueryResolver {
    public ContractSchema contract(long id) {
        return new ContractSchema(id, "file ici", "eng college ici", "eng compagnie ici", "eng étudiant ici", 37.5F, "reasonForRejection ici", ContractSignatureState.SIGNED, 0L, 0L, 0L, 0L);
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
        private long studentApplicationId;
        private long interEvaluationId;
        private long adminId;
        private long businessEvaluationId;
    }
}
