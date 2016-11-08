package br.edu.unigranrio.ect.si.cfa.ws.rs.vo;

import br.edu.unigranrio.ect.si.cfa.model.Flow;
import br.edu.unigranrio.ect.si.cfa.ws.rs.serializer.CalendarDeserializer;
import br.edu.unigranrio.ect.si.cfa.ws.rs.serializer.CalendarSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Calendar;

public class FlowVO extends ValidObject<Flow> {

    private static final long serialVersionUID = -8192311374327332257L;

    private Float measure;

    @JsonSerialize(using = CalendarSerializer.class)
    @JsonDeserialize(using = CalendarDeserializer.class)
    private Calendar registration;

    private UserVO user;
    private ControllerVO controller;

    public FlowVO() {
    }

    public FlowVO(Flow flow) {
        copy(flow);
    }

    public Float getMeasure() {
        return measure;
    }

    public void setMeasure(Float measure) {
        this.measure = measure;
    }

    public Calendar getRegistration() {
        return registration;
    }

    public void setRegistration(Calendar registration) {
        this.registration = registration;
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    public ControllerVO getController() {
        return controller;
    }

    public void setController(ControllerVO controller) {
        this.controller = controller;
    }

    @Override
    public void copy(Flow entity) {
        super.copy(entity);
        if (entity != null) {
            this.setRegistration(entity.getRegistrantion());
            this.setMeasure(entity.getMeasure());
            this.setController(new ControllerVO(entity.getController()));
            this.setUser(new UserVO(entity.getUser()));
        }
    }
}
