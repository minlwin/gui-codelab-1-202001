package com.jdc.accounting.views;

import java.util.Map;

import com.jdc.accounting.context.ApplicationContext;
import com.jdc.accounting.model.EmployeeModel;
import com.jdc.accounting.model.SummaryModel;
import com.jdc.accounting.model.entity.BalanceType;
import com.jdc.accounting.model.entity.Employee;
import com.jdc.accounting.model.entity.Employee.Role;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Label;

public class EmployeeHome {

    @FXML
    private Label code;

    @FXML
    private Label name;

    @FXML
    private Label role;

    @FXML
    private Label phone;

    @FXML
    private Label email;
    
    @FXML
    private PieChart pieChart;
    @FXML
    private BarChart<String, Integer> incomeChart;
    @FXML
    private BarChart<String, Integer> expenseChart;
    
    private SummaryModel model;
    
    @FXML
    private void initialize() {
    	model = new SummaryModel();
    	loadProfile();
    	
    	String code = null;
    	
    	Employee loginUser = ApplicationContext.getLoginUser();
    	if(loginUser.getRole() != Role.Admin) {
    		code = loginUser.getCode();
    	}
    	
    	pieChart.getData().add(new Data("Income", model.find(BalanceType.Incomes, code)));
    	pieChart.getData().add(new Data("Expense", model.find(BalanceType.Expenses, code)));
    
    	Map<String, Map<String, Integer>> incomes = model.findBarData(BalanceType.Incomes, code);
    	Map<String, Map<String, Integer>> expenses = model.findBarData(BalanceType.Expenses, code);
    	
    	loadCart(incomeChart, incomes, "Incomes");
    	loadCart(expenseChart, expenses, "Expenses");
    }

    private void loadCart(BarChart<String, Integer> chart, Map<String, Map<String, Integer>> data, String title) {
    	
    	for(String category : data.keySet()) {
    		Series<String, Integer>  series = new Series<String, Integer>();
    		series.setName(category);
    		
    		Map<String, Integer> seriesData = data.get(category);
    		
    		for(String date: seriesData.keySet()) {
    			XYChart.Data<String, Integer> chartData = new XYChart.Data<String, Integer>(date, seriesData.get(date));
    			series.getData().add(chartData);
    		}
    		
    		chart.getData().add(series);
    	}
    	
	}

	@FXML
    void changePass() {
    	ChangePass.show();
    }

    @FXML
    void editProfile() {
    	EmployeeEdit.show(ApplicationContext.getLoginUser(), this::save);
    }
    
    private void save(Employee emp) {
    	
    	// save employee
    	new EmployeeModel().save(emp);
    	
    	ApplicationContext.setLoginUser(emp);
    	
    	loadProfile();
    }
    
    private void loadProfile() {
    	Employee login = ApplicationContext.getLoginUser();
    	
    	code.setText(login.getCode());
    	name.setText(login.getName());
    	role.setText(login.getRole().name());
    	phone.setText(login.getPhone());
    	email.setText(login.getEmail());
    }

}
