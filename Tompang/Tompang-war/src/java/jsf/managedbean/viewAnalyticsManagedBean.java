/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;


import ejb.stateless.TransactionSessionBeanLocal;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javafx.animation.Animation;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearTicks;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.optionconfig.legend.LegendLabel;
import org.primefaces.model.charts.optionconfig.title.Title;

/**
 *
 * @author seanang
 */
@Named(value = "viewAnalyticsManagedBean")
@ViewScoped
public class viewAnalyticsManagedBean implements Serializable {

    @EJB
    private TransactionSessionBeanLocal transactionSessionBean;

    

    /**
     * Creates a new instance of viewAnalyticsManagedBean
     */
    
    private BarChartModel barModel;
    private Double totalRevenue;
    
    public viewAnalyticsManagedBean() {
    }
    
    @PostConstruct
    public void postConstruct(){
        this.totalRevenue = 0.0;
        createBarModel();
        
    }
    
    public void createBarModel() {
        setBarModel(new BarChartModel());
        ChartData data = new ChartData();

        BarChartDataSet barDataSet = new BarChartDataSet();
        barDataSet.setLabel("Tompang Revenue");

        List<Number> values = new ArrayList<>();
        List<Double> list = transactionSessionBean.getList();
        for(int i = 0; i < 12; i++){
            values.add(list.get(i));
        }
//        values.add(10000);
//        values.add(40000);
//        values.add(20000);
//        values.add(30000);
//        values.add(15000);
//        values.add(70000);
//        values.add(40000);
//        values.add(20000);
//        values.add(25000);
//        values.add(40000);
//        values.add(70000);
//        values.add(90000);
        
        for(Number amount: values){
            this.setTotalRevenue((Double) (this.getTotalRevenue() + amount.doubleValue()));
        }
        
        
        
       
        barDataSet.setData(values);

        List<String> bgColor = new ArrayList<>();
        bgColor.add("rgb(25, 25, 112)");
        bgColor.add("rgb(0, 0, 128)");
        bgColor.add("rgb(25, 25, 112)");
        bgColor.add("rgb(0, 71, 171)");
        bgColor.add("rgb(65, 105, 225)");
        bgColor.add("rgb(0, 0, 255)");
        bgColor.add("rgb(100, 149, 237)");  
        bgColor.add("rgb(167, 199, 231)");
        bgColor.add("rgb(173, 216, 230)");
        bgColor.add("rgb(0, 150, 255)");
        bgColor.add("rgb(137, 207, 240)");
        bgColor.add("rgb(0, 255, 255)");
        barDataSet.setBackgroundColor(bgColor);

        List<String> borderColor = new ArrayList<>();
        borderColor.add("rgb(25, 25, 112)");
        borderColor.add("rgb(0, 0, 128)");
        borderColor.add("rgb(25, 25, 112)");
        borderColor.add("rgb(0, 71, 171)");
        borderColor.add("rgb(100, 149, 237)");
        borderColor.add("rgb(0, 0, 255)");
        borderColor.add("rgb(65, 105, 225)");  
        borderColor.add("rgb(167, 199, 231)");
        borderColor.add("rgb(173, 216, 230)");
        borderColor.add("rgb(0, 150, 255)");
        borderColor.add("rgb(137, 207, 240)");
        borderColor.add("rgb(0, 255, 255)");
        barDataSet.setBorderColor(borderColor);
        barDataSet.setBorderWidth(1);

        data.addChartDataSet(barDataSet);

        List<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        labels.add("July");
        labels.add("August");
        labels.add("September");
        labels.add("October");
        labels.add("November");
        labels.add("December");
        data.setLabels(labels);
        getBarModel().setData(data);

        //Options
        BarChartOptions options = new BarChartOptions();
        CartesianScales cScales = new CartesianScales();
        CartesianLinearAxes linearAxes = new CartesianLinearAxes();
        linearAxes.setOffset(true);
        CartesianLinearTicks ticks = new CartesianLinearTicks();
        ticks.setBeginAtZero(true);
        linearAxes.setTicks(ticks);
        cScales.addYAxesData(linearAxes);
        options.setScales(cScales);

        Title title = new Title();
        title.setDisplay(true);
        title.setText("Bar Chart");
        options.setTitle(title);

        Legend legend = new Legend();
        legend.setDisplay(true);
        legend.setPosition("top");
        LegendLabel legendLabels = new LegendLabel();
        legendLabels.setFontStyle("bold");
        legendLabels.setFontColor("#2980B9");
        legendLabels.setFontSize(24);
        legend.setLabels(legendLabels);
        options.setLegend(legend);

      
        getBarModel().setOptions(options);
    }

    /**
     * @return the barModel
     */
    public BarChartModel getBarModel() {
        return barModel;
    }

    /**
     * @param barModel the barModel to set
     */
    public void setBarModel(BarChartModel barModel) {
        this.barModel = barModel;
    }

    /**
     * @return the totalRevenue
     */
    public Double getTotalRevenue() {
        return totalRevenue;
    }

    /**
     * @param totalRevenue the totalRevenue to set
     */
    public void setTotalRevenue(Double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
