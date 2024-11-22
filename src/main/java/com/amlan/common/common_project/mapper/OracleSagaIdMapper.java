package com.amlan.common.common_project.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.amlan.common.common_project.entity.OracleSagaId;

public class OracleSagaIdMapper implements RowMapper<OracleSagaId>{

    @Override
    public OracleSagaId mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        if(resultSet!=null){
            OracleSagaId sagaId = new OracleSagaId();
            sagaId.setEventUuid(resultSet.getString("EVENT_UUID"));
            sagaId.setSagaState(resultSet.getString("SAGA_SATE"));
            return sagaId;
        }else{
            return null;
        }
    }

}
