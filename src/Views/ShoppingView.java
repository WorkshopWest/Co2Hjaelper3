package Views;

import com.github.lgooddatepicker.components.DatePicker;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.ui.VerticalAlignment;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;

public class ShoppingView {
    private JPanel panelMain;
    private JComboBox cmbBoxCategory;
    private JComboBox cmbBoxQuantity;
    private JComboBox cmbBoxGrocery;
    private JList lstSelection;
    private JButton btnRemove;
    private JButton btnClear;
    private JButton btnSave;
    private JButton btnAdd;

    private JLabel labelTop;
    private JComboBox cmbBoxDate;
    private JTextField txtAmount;
    private DatePicker datePicker;
    private JLabel lblList;
    private ChartPanel pnlChart;


    // Getters and setters


    public JLabel getLblList() {
        return lblList;
    }

    public void setLblList(JLabel lblList) {
        this.lblList = lblList;
    }

    public ChartPanel getPnlChart() {
        return pnlChart;
    }

    public void setPnlChart(ChartPanel pnlChart) {
        this.pnlChart = pnlChart;
    }

    public JTextField getTxtAmount() {
        return txtAmount;
    }

    public void setTxtAmount(JTextField txtAmount) {
        this.txtAmount = txtAmount;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public void setDatePicker(DatePicker datePicker) {
        this.datePicker = datePicker;
    }

    public JPanel getPanelMain() {
        return panelMain;
    }

    public void setPanelMain(JPanel panelMain) {
        this.panelMain = panelMain;
    }

    public JComboBox getCmbBoxCategory() {
        return cmbBoxCategory;
    }

    public void setCmbBoxCategory(JComboBox cmbBoxCategory) {
        this.cmbBoxCategory = cmbBoxCategory;
    }

    public JComboBox getCmbBoxQuantity() {
        return cmbBoxQuantity;
    }

    public void setCmbBoxQuantity(JComboBox cmbBoxQuantity) {
        this.cmbBoxQuantity = cmbBoxQuantity;
    }

    public JComboBox getCmbBoxGrocery() {
        return cmbBoxGrocery;
    }

    public void setCmbBoxGrocery(JComboBox cmbBoxGrocery) {
        this.cmbBoxGrocery = cmbBoxGrocery;
    }

    public JList getLstSelection() {
        return lstSelection;
    }

    public void setLstSelection(JList lstSelection) {
        this.lstSelection = lstSelection;
    }

    public JButton getBtnRemove() {
        return btnRemove;
    }

    public void setBtnRemove(JButton btnRemove) {
        this.btnRemove = btnRemove;
    }

    public JButton getBtnClear() {
        return btnClear;
    }

    public void setBtnClear(JButton btnClear) {
        this.btnClear = btnClear;
    }

    public JButton getBtnSave() {
        return btnSave;
    }

    public void setBtnSave(JButton btnSave) {
        this.btnSave = btnSave;
    }

    public JButton getBtnAdd() {
        return btnAdd;
    }

    public void setBtnAdd(JButton btnAdd) {
        this.btnAdd = btnAdd;
    }


    public JLabel getLabelTop() {
        return labelTop;
    }

    public void setLabelTop(JLabel labelTop) {
        this.labelTop = labelTop;
    }

    public JComboBox getCmbBoxDate() {
        return cmbBoxDate;
    }

    public void setCmbBoxDate(JComboBox cmbBoxDate) {
        this.cmbBoxDate = cmbBoxDate;
    }

    private void createUIComponents() {
        DefaultPieDataset dataset = new DefaultPieDataset();

        JFreeChart chart = ChartFactory.createPieChart("Fordeling", dataset);
        chart.getTitle().setFont(new Font("Cantarell", 1, 16));
        chart.getTitle().setVerticalAlignment(VerticalAlignment.BOTTOM); // Doesnt do anything it seems

        pnlChart = new ChartPanel(chart);
    }
}
