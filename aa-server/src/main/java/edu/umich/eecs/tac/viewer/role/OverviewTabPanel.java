package edu.umich.eecs.tac.viewer.role;

import edu.umich.eecs.tac.viewer.TACAASimulationPanel;
import edu.umich.eecs.tac.viewer.ViewListener;
import edu.umich.eecs.tac.props.BankStatus;
import edu.umich.eecs.tac.TACAAConstants;

import javax.swing.*;
import java.awt.*;

import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.Day;
import org.jfree.data.xy.XYDataset;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.plot.XYPlot;
import org.jfree.ui.RectangleInsets;
import com.botbox.util.ArrayUtils;
import se.sics.isl.transport.Transportable;
import se.sics.tasim.viewer.TickListener;

/**
 * @author Patrick Jordan
 */
public class OverviewTabPanel extends SimulationTabPanel {
    private TimeSeriesCollection timeseriescollection;

    private int[] agents;
    private int[] roles;
    private int[] participants;
    private TimeSeries[] timeSeries;
    private String[] names;
    private int agentCount;
    private Day currentDay;

    public OverviewTabPanel(TACAASimulationPanel simulationPanel) {
        super(simulationPanel);

        agents = new int[0];
        roles = new int[0];
        participants = new int[0];
        timeSeries = new TimeSeries[0];
        names = new String[0];
        currentDay = new Day();
        initialize();

        getSimulationPanel().addTickListener(new DayListener());
        getSimulationPanel().addViewListener(new BankStatusListener());
    }

    protected void initialize() {
        setLayout(new GridLayout(1, 1));

        timeseriescollection = new TimeSeriesCollection();
        JFreeChart chart = createChart(timeseriescollection);
        ChartPanel chartpanel = new ChartPanel(chart, false);
        chartpanel.setMouseZoomable(true, false);

        add(chartpanel);
    }


    private JFreeChart createChart(XYDataset xydataset) {
        JFreeChart jfreechart = ChartFactory.createTimeSeriesChart("Advertiser Profits", "Day", "$", xydataset, true, true, false);
        jfreechart.setBackgroundPaint(Color.white);
        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        xyplot.setBackgroundPaint(Color.lightGray);
        xyplot.setDomainGridlinePaint(Color.white);
        xyplot.setRangeGridlinePaint(Color.white);
        xyplot.setAxisOffset(new RectangleInsets(5D, 5D, 5D, 5D));
        xyplot.setDomainCrosshairVisible(true);
        xyplot.setRangeCrosshairVisible(true);
        xyplot.getDomainAxis().setVisible(false);
        
        org.jfree.chart.renderer.xy.XYItemRenderer xyitemrenderer = xyplot.getRenderer();
        if (xyitemrenderer instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyitemrenderer;
            xylineandshaperenderer.setBaseShapesVisible(false);
        }
        return jfreechart;
    }


    protected void addAgent(int agent) {
        int index = ArrayUtils.indexOf(agents, 0, agentCount, agent);
        if (index < 0) {
            doAddAgent(agent);
        }
    }

    private int doAddAgent(int agent) {
        if (agentCount == participants.length) {
            int newSize = agentCount + 8;
            agents = ArrayUtils.setSize(agents, newSize);
            roles = ArrayUtils.setSize(roles, newSize);
            participants = ArrayUtils.setSize(participants, newSize);
            timeSeries = (TimeSeries[]) ArrayUtils.setSize(timeSeries, newSize);
            names = (String[]) ArrayUtils.setSize(names, newSize);
        }

        agents[agentCount] = agent;

        return agentCount++;
    }

    private void setAgent(int agent, int role, String name, int participantID) {
        addAgent(agent);

        int index = ArrayUtils.indexOf(agents, 0, agentCount, agent);
        roles[index] = role;
        names[index] = name;
        participants[index] = participantID;

        if(timeSeries[index]==null && TACAAConstants.ADVERTISER==roles[index]) {
            timeSeries[index] = new TimeSeries(name,org.jfree.data.time.Day.class);
            timeseriescollection.addSeries(timeSeries[index]);
        }
    }

    protected void participant(int agent, int role, String name, int participantID) {
        setAgent(agent,role,name,participantID);
    }

    protected void dataUpdated(int agent, int type, double value) {
        int index = ArrayUtils.indexOf(agents, 0, agentCount, agent);
        if(index < 0 || timeSeries[index]==null || type != TACAAConstants.DU_BANK_ACCOUNT) {
            return;
        }

        timeSeries[index].add(currentDay, value);
    }

    protected void tick(long serverTime) {
    }

    protected void simulationTick(long serverTime, int simulationDate) {
        currentDay = (Day)currentDay.next();
    }

    protected class DayListener implements TickListener {

        public void tick(long serverTime) {
            OverviewTabPanel.this.tick(serverTime);
        }

        public void simulationTick(long serverTime, int simulationDate) {
             OverviewTabPanel.this.simulationTick(serverTime,simulationDate);
        }
    }

    protected class BankStatusListener implements ViewListener {

        public void dataUpdated(int agent, int type, int value) {

        }

        public void dataUpdated(int agent, int type, long value) {

        }

        public void dataUpdated(int agent, int type, float value) {

        }

        public void dataUpdated(int agent, int type, double value) {
            OverviewTabPanel.this.dataUpdated(agent,type,value);
        }

        public void dataUpdated(int agent, int type, String value) {

        }

        public void dataUpdated(int agent, int type, Transportable value) {

        }

        public void dataUpdated(int type, Transportable value) {

        }

        public void participant(int agent, int role, String name, int participantID) {
            OverviewTabPanel.this.participant(agent,role,name,participantID);
        }
    }
}