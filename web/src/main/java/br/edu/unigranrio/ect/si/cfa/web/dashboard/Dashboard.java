package br.edu.unigranrio.ect.si.cfa.web.dashboard;

import org.primefaces.model.DashboardModel;

import java.io.Serializable;

public interface Dashboard extends Serializable {

    boolean isAdmin();

    boolean isManager();

    boolean isUser();

    DashboardModel getChartModel();

}
