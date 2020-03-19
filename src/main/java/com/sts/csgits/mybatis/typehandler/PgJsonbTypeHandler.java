package com.sts.csgits.mybatis.typehandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sts.csgits.utils.PgJsonb;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * PgJsonbTypeHandler
 * @author gaobin
 */
@Component
public class PgJsonbTypeHandler extends BaseTypeHandler<PgJsonb> {

    private ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, PgJsonb parameter, JdbcType jdbcType)
        throws SQLException {
        String jsonText;
        try {
            jsonText = objectMapper.writeValueAsString(parameter);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        
        ps.setString(i, jsonText);
    }

    @Override
    public PgJsonb getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String jsonText = rs.getString(columnName);
        return readToMap(jsonText);
    }

    @Override
    public PgJsonb getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String jsonText = rs.getString(columnIndex);
        return readToMap(jsonText);
    }

    @Override
    public PgJsonb getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String jsonText = cs.getString(columnIndex);
        return readToMap(jsonText);
    }
    
    private PgJsonb readToMap(String jsonText) {
        if(StringUtils.isEmpty(jsonText)) {
            return null;
        }
        
        try {
            return objectMapper.readValue(jsonText, PgJsonb.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}