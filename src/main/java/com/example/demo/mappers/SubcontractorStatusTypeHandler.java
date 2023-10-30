package com.example.demo.mappers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.example.demo.entity.SubcontractorStatus;

public class SubcontractorStatusTypeHandler extends BaseTypeHandler<SubcontractorStatus> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, SubcontractorStatus parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.name());
    }

    @Override
    public SubcontractorStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return SubcontractorStatus.valueOf(rs.getString(columnName));
    }

    @Override
    public SubcontractorStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return SubcontractorStatus.valueOf(rs.getString(columnIndex));
    }

    @Override
    public SubcontractorStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return SubcontractorStatus.valueOf(cs.getString(columnIndex));
    }
}
