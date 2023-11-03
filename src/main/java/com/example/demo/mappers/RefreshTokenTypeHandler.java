package com.example.demo.mappers;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import com.example.demo.entity.RefreshToken;
import com.example.demo.entity.UUser;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;

public class RefreshTokenTypeHandler extends BaseTypeHandler<RefreshToken> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, RefreshToken parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, parameter.getRtToken());
    }

    @Override
    public RefreshToken getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return buildRefreshToken(rs, columnName);
    }

    @Override
    public RefreshToken getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return buildRefreshToken(rs, "rt_" + columnIndex);
    }

    @Override
    public RefreshToken getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return (RefreshToken) cs.getObject(columnIndex);
    }

    private RefreshToken buildRefreshToken(ResultSet rs, String prefix) throws SQLException {
        int rtId = rs.getInt("rt_id");
        String rtToken = rs.getString("rt_token");
        Instant rtExpiryDate = rs.getTimestamp("rt_expiry_date").toInstant();
        int uId = rs.getInt("uId");
        String uEmail = rs.getString("u_email");
        String uPassword = rs.getString("u_password");
        String uFirstName = rs.getString("u_first_name");
        String uLastName = rs.getString("u_last_name");
        boolean uStatus = rs.getBoolean("u_status");

        UUser uUser = new UUser(uId, uEmail, uPassword, uFirstName, uLastName, uStatus, null, null);

        return RefreshToken.builder()
                .rtId(rtId)
                .rtToken(rtToken)
                .rtExpiryDate(rtExpiryDate)
                .uUser(uUser)
                .build();
    }


}
