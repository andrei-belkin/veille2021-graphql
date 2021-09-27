package com.power222.tuimspfcauppbj.graphql;

import com.power222.tuimspfcauppbj.service.ContractGenerationService;
import graphql.language.StringValue;
import graphql.schema.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class Scalar {
    public static GraphQLScalarType LOCAL_DATE = GraphQLScalarType.newScalar()
            .name("localDate")
            .description("Basic implementation of Java's LocalDate")
            .coercing(new Coercing<LocalDate, String>() {
                @Override
                public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
                    assert dataFetcherResult instanceof LocalDate : "Serialized Java object must be of type LocalDate";
                    return dataFetcherResult.toString();
                }

                @Override
                public LocalDate parseValue(Object input) throws CoercingParseValueException {
                    assert input instanceof String : "Input is not of type String";
                    return LocalDate.parse(input.toString(), ContractGenerationService.DTF_WITHOUT_TIME);
                }

                @Override
                public LocalDate parseLiteral(Object input) throws CoercingParseLiteralException {
                    assert input instanceof StringValue : "Input is not of type String";
                    return LocalDate.parse(((StringValue) input).getValue(), ContractGenerationService.DTF_WITHOUT_TIME);
                }
            })
            .build();

    public static GraphQLScalarType LOCAL_TIME = GraphQLScalarType.newScalar()
            .name("localTime")
            .description("Basic implementation of Java's LocalTime")
            .coercing(new Coercing<LocalTime, String>() {
                @Override
                public String serialize(Object dataFetcherResult) throws CoercingSerializeException {
                    assert dataFetcherResult instanceof LocalTime : "Serialized Java object must be of type LocalTime";
                    return dataFetcherResult.toString();
                }

                @Override
                public LocalTime parseValue(Object input) throws CoercingParseValueException {
                    assert input instanceof String : "Input is not of type String";
                    return LocalTime.parse(input.toString(), ContractGenerationService.DTF_WITH_TIME);
                }

                @Override
                public LocalTime parseLiteral(Object input) throws CoercingParseLiteralException {
                    assert input instanceof StringValue : "Input is not of type String";
                    return LocalTime.parse(((StringValue) input).getValue(), ContractGenerationService.DTF_WITH_TIME);
                }
            })
            .build();
}
