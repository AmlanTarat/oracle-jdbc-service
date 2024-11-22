package com.amlan.common.common_project.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import com.amlan.common.common_project.entity.OracleSagaId;
import com.amlan.common.common_project.exception.DAOException;
import com.amlan.common.common_project.mapper.OracleSagaIdMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OracleSagaIdImpl {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Retryable(value={DataAccessException.class}, maxAttemptsExpression = "#{@retryConfiguration.getMaxAttempts()}",
                        backoff = @Backoff(delayExpression = "#{@retryConfiguration.getDelay()}", 
                        multiplierExpression = "#{@retryConfiguration.getMultiplier()}",
                        maxDelayExpression = "#{@retryConfiguration.getMaxDelay()}"))
    public int saveSagaContext(OracleSagaId sagaId){
        String insertQuery = "INSERT INTO" + "SAGA_TABLE" + " (" +"EVENT_UUID"+","+"SAGA_STATE"+" VALUES (?,?)";
        try{
            return jdbcTemplate.update(insertQuery, sagaId.getEventUuid().toString(),sagaId.getSagaState());
        }catch(DuplicateKeyException e){
            log.info("updating the event for event uuid"+ sagaId.getEventUuid());
            String updateQuery = "UPDATE" + "SAGA_TABLE" + "SET"+" (" +"SAGA_STATE"+"=? WHERE"+" EVENTUUID = ?)";
            return jdbcTemplate.update(updateQuery, sagaId.getSagaState(), sagaId.getEventUuid().toString());
        }
    }

    @Retryable(value={DataAccessException.class}, maxAttemptsExpression = "#{@retryConfiguration.getMaxAttempts()}",
                        backoff = @Backoff(delayExpression = "#{@retryConfiguration.getDelay()}", 
                        multiplierExpression = "#{@retryConfiguration.getMultiplier()}",
                        maxDelayExpression = "#{@retryConfiguration.getMaxDelay()}"))
    public List<OracleSagaId> fetchSagaContext(OracleSagaId sagaId){
        
        String query = "SELECT STATEMENT";
        
        return jdbcTemplate.query(query, new OracleSagaIdMapper());
      
    }

    @Recover
    public int handleDataAccessException(DataAccessException exception) throws DAOException{
        throw new DAOException("FAILURE_SYSTEM_ERROR", exception.getMessage(), exception.getCause());
    }
}
