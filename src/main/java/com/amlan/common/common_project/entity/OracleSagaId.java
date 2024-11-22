package com.amlan.common.common_project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="SAGA_TABLE")
@Getter
@Setter
public class OracleSagaId {

    @Id
    @Column(name="event_uuid")
    private String eventUuid;

    @Column(name="saga_state")
    private String sagaState;

}
