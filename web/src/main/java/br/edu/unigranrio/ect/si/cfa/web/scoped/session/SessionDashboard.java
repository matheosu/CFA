package br.edu.unigranrio.ect.si.cfa.web.scoped.session;

import br.edu.unigranrio.ect.si.cfa.commons.util.Constants;
import br.edu.unigranrio.ect.si.cfa.model.*;
import br.edu.unigranrio.ect.si.cfa.service.FlowService;
import br.edu.unigranrio.ect.si.cfa.service.UserService;
import br.edu.unigranrio.ect.si.cfa.web.annotation.LoggedRoleId;
import br.edu.unigranrio.ect.si.cfa.commons.annotation.LoggedUserId;
import br.edu.unigranrio.ect.si.cfa.web.dashboard.Dashboard;
import br.edu.unigranrio.ect.si.cfa.web.message.Message;
import org.primefaces.model.DashboardModel;
import org.primefaces.model.DefaultDashboardColumn;
import org.primefaces.model.DefaultDashboardModel;
import org.primefaces.model.chart.*;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@ViewScoped
@Named("dashboard")
public class SessionDashboard implements Dashboard {

    private static final long serialVersionUID = -16769239285608522L;

    /* Box Constants */
    public static final String DASH_LOCALIZATIONS_ID = "locatins";
    public static final String DASH_CONTROLLERS_ID = "controllers";
    public static final String DASH_ACTIVE_USERS_ID = "active_users";
    public static final String DASH_AVERAGE_FLOW_USERS_ID = "avg_flow_users";
    public static final String DASH_AVERAGE_TIME_USERS_ID = "avg_time_users";

    /* Charts Constants */
    public static final String DASH_TOTAL_EXPENDITURE_ID = "total_expenditure";
    public static final String DASH_RAKING_USERS_ID = "raking_users";
    public static final String DASH_FLOW_RESTRICT_ID = "flow_restriction";
    public static final String CHART = "_chart";
    public static final String DASH_TOTAL_EXPENDITURE_CHART_ID = DASH_TOTAL_EXPENDITURE_ID + CHART;
    public static final String DASH_RAKING_USERS_CHART_ID = DASH_RAKING_USERS_ID + CHART;
    public static final String DASH_FLOW_RESTRICT_CHART_ID = DASH_FLOW_RESTRICT_ID + CHART;

    @Inject @LoggedRoleId
    private Long loggedRoleId;

    @Inject @LoggedUserId
    private Long loggedUserId;

    @Inject
    private UserService userService;

    @Inject
    private FlowService flowService;

    @Inject
    private Message message;

    private DashboardModel chartModel;

    private DashboardModel boxModel;

    @PostConstruct
    public void init() {
        if (isAdmin())
            adminDashboard();

        if (isManager())
            managerDashboard();

        if (isUser())
            userDashboard();

    }

    private void adminDashboard() {
        adminBox();
        adminCharts();
    }

    private void managerDashboard() {
        managerBox();
        managerCharts();
    }

    private void userDashboard() {
        userBox();
        userCharts();
    }

    private void userCharts() {
        createCharts(DASH_FLOW_RESTRICT_ID);
    }

    private void userBox() {
        createBoxs(DASH_AVERAGE_FLOW_USERS_ID,DASH_AVERAGE_TIME_USERS_ID);
    }

    private void managerBox() {
        createBoxs(DASH_ACTIVE_USERS_ID,DASH_AVERAGE_FLOW_USERS_ID,DASH_AVERAGE_TIME_USERS_ID);
    }

    private void managerCharts() {
        createCharts(DASH_FLOW_RESTRICT_ID, DASH_RAKING_USERS_ID);
    }

    private void adminBox() {
        createBoxs(DASH_LOCALIZATIONS_ID, DASH_CONTROLLERS_ID,
                DASH_ACTIVE_USERS_ID, DASH_AVERAGE_FLOW_USERS_ID, DASH_AVERAGE_TIME_USERS_ID);
    }

    private void adminCharts() {
        createCharts(DASH_TOTAL_EXPENDITURE_ID, DASH_RAKING_USERS_ID);
    }

    private void createBoxs(String... boxs) {
        boxModel = new DefaultDashboardModel();
        for (String box : boxs) {
            DefaultDashboardColumn column = new DefaultDashboardColumn();
            column.addWidget(box);
            boxModel.addColumn(column);
        }
    }

    private void createCharts(String... charts) {
        chartModel = new DefaultDashboardModel();
        for (String chart : charts) {
            DefaultDashboardColumn column = new DefaultDashboardColumn();
            column.addWidget(chart);
            chartModel.addColumn(column);
        }

    }

    @Override
    public boolean isAdmin() {
        return Constants.ROLE_ADMIN_ID.equals(loggedRoleId);
    }

    @Override
    public boolean isManager() {
        return Constants.ROLE_MANAGER_ID.equals(loggedRoleId);
    }

    @Override
    public boolean isUser() {
        return Constants.ROLE_USER_ID.equals(loggedRoleId);
    }

    public DashboardModel getChartModel() {
        return chartModel;
    }

    public DashboardModel getBoxModel() {
        return boxModel;
    }

    public Long getTotalControllers() {
        return userService.count(Controller.class);
    }

    public Long getTotalActiveUsers() {
        return userService.getTotalActiveUsers();
    }

    public Long getTotalLocalizations() {
        return userService.count(Localization.class);
    }

    public Double getAverageFlowUsers() {
        List<Flow> flowByPeriod;
        if (loggedRoleId.equals(Constants.ROLE_USER_ID)) {
            flowByPeriod = flowService.findFlowByPeriod(loggedUserId, Period.MONTH);
        } else {
            flowByPeriod = flowService.findFlowByPeriod(Period.MONTH);
        }

        Map<User, Double> collect = flowByPeriod.stream()
                .collect(Collectors.groupingBy(Flow::getUser,
                        Collectors.summingDouble(Flow::getMeasure)));
        Double value = collect.keySet().stream().map(collect::get)
                        .collect(Collectors.averagingDouble(Double::doubleValue));
        return value != null ? value : 0D;
    }

    public Double getAverageTimeUsers() {
        List<Flow> flowByPeriod;
        if (loggedRoleId.equals(Constants.ROLE_USER_ID)) {
            flowByPeriod = flowService.findFlowByPeriod(loggedUserId, Period.MONTH);
        } else {
            flowByPeriod = flowService.findFlowByPeriod(Period.MONTH);
        }

        Map<User, Set<Calendar>> collect = flowByPeriod.stream()
                .filter(f -> f.getMeasure() != null)
                .filter(f -> f.getMeasure() != 0L)
                .collect(Collectors.groupingBy(Flow::getUser,
                        Collectors.mapping(Flow::getRegistrantion, Collectors.toSet())));
        Long totalValue = 0L;
        for (User user : collect.keySet()) {
            Long lastTimeMillis = 0L;
            for (Calendar calendar : collect.get(user)) {
                if (lastTimeMillis != 0L)
                    totalValue += TimeUnit.MILLISECONDS.toMinutes(Math.abs(lastTimeMillis - calendar.getTimeInMillis()));
                lastTimeMillis = calendar.getTimeInMillis();
            }
        }

        return totalValue != null && totalValue != 0L ? (double) (totalValue / collect.keySet().size()) : 0D;
    }


    public LineChartModel getTotalExpenditureModel() {
        LineChartModel model = new LineChartModel();

        final LineChartSeries unique = new LineChartSeries();
        unique.setFill(true);
        unique.setLabel(message.getString("total"));

//        List<Flow> flows = flowService.findFlowByPeriod(Period.MONTH);
//        flows.forEach(f -> unique.set(
//                String.valueOf(f.getRegistrantion().get(Calendar.MONTH) + 1),
//                f.getMeasure()
//        ));
        unique.set("Janeiro", 110);
        unique.set("Fevereiro", 90);

        model.addSeries(unique);

        model.setTitle(message.getString("total.expenditure"));
        model.setLegendPosition("ne");
        model.setStacked(true);
        model.setShowPointLabels(true);

        Axis xAxis = new CategoryAxis(message.getString("months"));
        model.getAxes().put(AxisType.X, xAxis);
        Axis yAxis = model.getAxis(AxisType.Y);
        yAxis.setLabel(message.getString("liter"));
        yAxis.setMin(0);
        yAxis.setMax(300);

        return model;
    }

    public BarChartModel getRakingUsersModel() {
        BarChartModel barModel = new BarChartModel();

        barModel.setTitle(message.getString("raking.users"));
        barModel.setLegendPosition("ne");

        Axis xAxis = barModel.getAxis(AxisType.X);
        xAxis.setLabel(message.getString("months"));

        Axis yAxis = barModel.getAxis(AxisType.Y);
        yAxis.setLabel(message.getString("liter"));
        yAxis.setMin(0);
        yAxis.setMax(200);

//        List<Flow> flows = flowService.findFlowByPeriod(Period.MONTH);
//        Map<User, Double> collect = flows.stream()
//                                    .collect(Collectors.groupingBy(Flow::getUser,
//                                            Collectors.summingDouble(Flow::getMeasure)));
//
//        for (User user : collect.keySet()) {
//            ChartSeries chartSeries = new ChartSeries();
//            chartSeries.set(Calendar.getInstance().get(Calendar.MONTH) + 1, collect.get(user));
//            barModel.addSeries(chartSeries);
//        }
        ChartSeries series = new ChartSeries();
        series.setLabel("André");
        series.set("Janeiro", 110);

        ChartSeries series2 = new ChartSeries();
        series2.setLabel("Matheus");
        series2.set("Fevereiro", 90);

        barModel.addSeries(series);
        barModel.addSeries(series2);

        return barModel;
    }

    public PieChartModel getAvailableFlow() {
        PieChartModel pie = new PieChartModel();
        User user = userService.find(User.class, loggedUserId);
        Float flow = flowService.availableFlow(loggedUserId);

        pie.getData().put(user.getFlowRestriction().getName(), user.getFlowRestriction().getValue());
        pie.getData().put(message.getString("available"), flow);

        pie.setTitle("Votes");
        pie.setLegendPosition("ne");

        return pie;
    }
}
