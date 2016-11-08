package br.edu.unigranrio.ect.si.cfa.ws.rs.vo;

import br.edu.unigranrio.ect.si.cfa.commons.model.Auditable;
import br.edu.unigranrio.ect.si.cfa.ws.rs.serializer.CalendarDeserializer;
import br.edu.unigranrio.ect.si.cfa.ws.rs.serializer.CalendarSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Calendar;

public class AuditedInfo implements TranscribeValues<Auditable> {

    private Long userCreatedId;

    @JsonSerialize(using = CalendarSerializer.class)
    @JsonDeserialize(using = CalendarDeserializer.class)
    private Calendar dateCreated;

    private Long userUpdatedId;

    @JsonSerialize(using = CalendarSerializer.class)
    @JsonDeserialize(using = CalendarDeserializer.class)
    private Calendar dateUpdated;

    public AuditedInfo(Auditable auditable) {
        this.copy(auditable);
    }

    public Long getUserCreatedId() {
        return userCreatedId;
    }

    public void setUserCreatedId(Long userCreatedId) {
        this.userCreatedId = userCreatedId;
    }

    public Long getUserUpdatedId() {
        return userUpdatedId;
    }

    public void setUserUpdatedId(Long userUpdatedId) {
        this.userUpdatedId = userUpdatedId;
    }

    public Calendar getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Calendar dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Calendar getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Calendar dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @Override
    public void copy(Auditable entity) {
        this.setUserCreatedId(entity.getUserCreatedId());
        this.setUserUpdatedId(entity.getUserUpdatedId());
        this.setDateCreated(entity.getDateCreated());
        this.setDateUpdated(entity.getDateUpdated());
    }
}