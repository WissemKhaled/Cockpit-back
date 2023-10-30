package com.example.demo.mappers;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import com.example.demo.entity.EStatus;

public class EStatusTypeHandler extends BaseTypeHandler<EStatus> {
	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, EStatus parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, parameter.name());
	}

	@Override
	public EStatus getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return EStatus.valueOf(rs.getString(columnName));
	}

	@Override
	public EStatus getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return EStatus.valueOf(rs.getString(columnIndex));
	}

	@Override
	public EStatus getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return EStatus.valueOf(cs.getString(columnIndex));
	}
}
