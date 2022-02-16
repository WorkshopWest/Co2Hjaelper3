package Views;

import com.github.lgooddatepicker.components.DatePicker;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.VerticalAlignment;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ConsumptionView {
    private JButton btnSubmit;
    private JTable tblDistribution;
    private JTextField txtTotalConsumption;
    private JLabel lblTo;
    private JLabel lblFrom;
    private JLabel lblSearch;
    private DatePicker dpFrom;
    private DatePicker dpTo;
    private JLabel lblDistribution;
    private JLabel lblTotalConsumption;
    private JPanel ConsumptionPanel;
    private JScrollPane scrPane;
    private ChartPanel chartPanel1;
    private JTextField txtTotal;
    private JLabel lblTotal;
    private JTextField txtBudget;
    private JLabel lblBudget;
    private JButton btnBudget;
    private JTextField txtYearBudget;
    private JLabel lblYearBudget;
    private JTextField txtProjection;
    private JLabel lblProjection;

    public JTextField getTxtProjection() {
        return txtProjection;
    }

    public void setTxtProjection(JTextField txtProjection) {
        this.txtProjection = txtProjection;
    }

    public JLabel getLblProjection() {
        return lblProjection;
    }

    public void setLblProjection(JLabel lblProjection) {
        this.lblProjection = lblProjection;
    }

    public JTextField getTxtYearBudget() {
        return txtYearBudget;
    }

    public void setTxtYearBudget(JTextField txtYearBudget) {
        this.txtYearBudget = txtYearBudget;
    }

    public JLabel getLblYearBudget() {
        return lblYearBudget;
    }

    public void setLblYearBudget(JLabel lblYearBudget) {
        this.lblYearBudget = lblYearBudget;
    }

    public JTextField getTxtBudget() {
        return txtBudget;
    }

    public void setTxtBudget(JTextField txtBudget) {
        this.txtBudget = txtBudget;
    }

    public JLabel getLblBudget() {
        return lblBudget;
    }

    public void setLblBudget(JLabel lblBudget) {
        this.lblBudget = lblBudget;
    }

    public JButton getBtnBudget() {
        return btnBudget;
    }

    public void setBtnBudget(JButton btnBudget) {
        this.btnBudget = btnBudget;
    }

    public ChartPanel getChartPanel1() {
        return chartPanel1;
    }

    public void setChartPanel1(ChartPanel chartPanel1) {
        this.chartPanel1 = chartPanel1;
    }

    public JTextField getTxtTotal() {
        return txtTotal;
    }

    public void setTxtTotal(JTextField txtTotal) {
        this.txtTotal = txtTotal;
    }

    public JLabel getLblTotal() {
        return lblTotal;
    }

    public void setLblTotal(JLabel lblTotal) {
        this.lblTotal = lblTotal;
    }

    public JScrollPane getScrPane() {
        return scrPane;
    }

    public void setScrPane(JScrollPane scrPane) {
        this.scrPane = scrPane;
    }

    private JTableHeader header;

    public JTableHeader getHeader() {
        return header;
    }

    public void setHeader(JTableHeader header) {
        this.header = header;
    }

    public JPanel getConsumptionPanel() {
        return ConsumptionPanel;
    }

    public void setConsumptionPanel(JPanel consumptionPanel) {
        ConsumptionPanel = consumptionPanel;
    }

    public JButton getBtnSubmit() {
        return btnSubmit;
    }

    public void setBtnSubmit(JButton btnSubmit) {
        this.btnSubmit = btnSubmit;
    }

    public JTable getTblDistribution() {
        return tblDistribution;
    }

    public void setTblDistribution(JTable tblDistribution) {
        this.tblDistribution = tblDistribution;
    }

    public JTextField getTxtTotalConsumption() {
        return txtTotalConsumption;
    }

    public void setTxtTotalConsumption(JTextField txtTotalConsumption) {
        this.txtTotalConsumption = txtTotalConsumption;
    }

    public JLabel getLblTo() {
        return lblTo;
    }

    public void setLblTo(JLabel lblTo) {
        this.lblTo = lblTo;
    }

    public JLabel getLblFrom() {
        return lblFrom;
    }

    public void setLblFrom(JLabel lblFrom) {
        this.lblFrom = lblFrom;
    }

    public JLabel getLblSearch() {
        return lblSearch;
    }

    public void setLblSearch(JLabel lblSearch) {
        this.lblSearch = lblSearch;
    }

    public DatePicker getDpFrom() {
        return dpFrom;
    }

    public void setDpFrom(DatePicker dpFrom) {
        this.dpFrom = dpFrom;
    }

    public DatePicker getDpTo() {
        return dpTo;
    }

    public void setDpTo(DatePicker dpTo) {
        this.dpTo = dpTo;
    }

    public JLabel getLblDistribution() {
        return lblDistribution;
    }

    public void setLblDistribution(JLabel lblDistribution) {
        this.lblDistribution = lblDistribution;
    }

    public JLabel getLblTotalConsumption() {
        return lblTotalConsumption;
    }

    public void setLblTotalConsumption(JLabel lblTotalConsumption) {
        this.lblTotalConsumption = lblTotalConsumption;
    }

    private void createUIComponents() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(100, "KgCo2e", "Jan");
        dataset.setValue(200, "KgCo2e", "Feb");
        dataset.setValue(200, "KgCo2e", "Mar");
        dataset.setValue(200, "KgCo2e", "Apr");
        dataset.setValue(200, "KgCo2e", "Maj");
        dataset.setValue(200, "KgCo2e", "Juni");
        dataset.setValue(200, "KgCo2e", "Juli");
        dataset.setValue(200, "KgCo2e", "Aug");
        dataset.setValue(200, "KgCo2e", "Sep");
        dataset.setValue(200, "KgCo2e", "Okt");
        dataset.setValue(200, "KgCo2e", "Nov");
        dataset.setValue(200, "KgCo2e", "Dec");

        JFreeChart chart = ChartFactory.createBarChart("Årsoversigt", "Måneder", "KgCo2e", dataset, PlotOrientation.VERTICAL, false, true, false);
        chart.getTitle().setFont(new Font("Cantarell", 1, 16));
        chart.getTitle().setVerticalAlignment(VerticalAlignment.BOTTOM); // Doesnt do anything it seems

        chartPanel1 = new ChartPanel(chart);


    }
}
