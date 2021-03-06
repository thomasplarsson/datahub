package com.linkedin.datahub.graphql.types.mappers;

import com.linkedin.datahub.graphql.generated.SchemaField;
import com.linkedin.datahub.graphql.generated.SchemaFieldDataType;

import javax.annotation.Nonnull;

public class SchemaFieldMapper implements ModelMapper<com.linkedin.schema.SchemaField, SchemaField> {

    public static final SchemaFieldMapper INSTANCE = new SchemaFieldMapper();

    public static SchemaField map(@Nonnull final com.linkedin.schema.SchemaField metadata) {
        return INSTANCE.apply(metadata);
    }

    @Override
    public SchemaField apply(@Nonnull final com.linkedin.schema.SchemaField input) {
        final SchemaField result = new SchemaField();
        result.setDescription(input.getDescription());
        result.setFieldPath(input.getFieldPath());
        result.setJsonPath(input.getJsonPath());
        result.setRecursive(input.isRecursive());
        result.setNullable(input.isNullable());
        result.setNativeDataType(input.getNativeDataType());
        result.setType(mapSchemaFieldDataType(input.getType()));
        return result;
    }

    private SchemaFieldDataType mapSchemaFieldDataType(@Nonnull final com.linkedin.schema.SchemaFieldDataType dataTypeUnion) {
        final com.linkedin.schema.SchemaFieldDataType.Type type = dataTypeUnion.getType();
        if (type.isBytesType()) {
            return SchemaFieldDataType.BYTES;
        } else if (type.isBooleanType()) {
            return SchemaFieldDataType.BOOLEAN;
        } else if (type.isStringType()) {
            return SchemaFieldDataType.STRING;
        } else if (type.isNumberType()) {
            return SchemaFieldDataType.NUMBER;
        } else if (type.isEnumType()) {
            return SchemaFieldDataType.ENUM;
        } else if (type.isNullType()) {
            return SchemaFieldDataType.NULL;
        } else if (type.isArrayType()) {
            return SchemaFieldDataType.ARRAY;
        } else if (type.isMapType()) {
            return SchemaFieldDataType.MAP;
        } else if (type.isRecordType()) {
            return SchemaFieldDataType.STRUCT;
        } else if (type.isUnionType()) {
            return SchemaFieldDataType.UNION;
        } else {
            throw new RuntimeException(String.format("Unrecognized SchemaFieldDataType provided %s", type.memberType().getType().name()));
        }
    }
}
