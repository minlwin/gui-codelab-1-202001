package com.jdc.accounting.views;

import java.io.IOException;
import java.time.LocalDate;

import com.jdc.accounting.context.ApplicationContext;
import com.jdc.accounting.model.CategoryModel;
import com.jdc.accounting.model.entity.Balance;
import com.jdc.accounting.model.entity.BalanceDetail;
import com.jdc.accounting.model.entity.BalanceType;
import com.jdc.accounting.model.entity.Category;
import com.jdc.accounting.utils.ValidationUtils;
import com.jdc.commons.fx.controls.AutoCompleteUtils;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class BalanceDetails {
	
	@FXML
	private Label title;

    @FXML
    private TextField empCode;

    @FXML
    private TextField empName;

    @FXML
    private TextField empRole;

    @FXML
    private DatePicker businessDate;

    @FXML
    private TextField category;

    @FXML
    private TextField total;

    @FXML
    private TextArea remark;

    @FXML
    private TextField inputTitle;

    @FXML
    private TextField inputAmount;

    @FXML
    private TextField inputRemark;

    @FXML
    private TableView<BalanceDetail> table;
    
    private Balance balance;
    
    private CategoryModel categoryModel;
    
    @FXML
    private void add() {
    	
    	try {
        	BalanceDetail data = new BalanceDetail();
        	data.setTitle(inputTitle.getText());
        	data.setAmount(Integer.parseInt(inputAmount.getText()));
        	data.setRemark(inputRemark.getText());
        	
        	validate(data);
        	data.setNo(table.getItems().size() + 1);
        	table.getItems().add(data);
        	
        	
        	inputTitle.clear();
        	inputAmount.clear();
        	inputRemark.clear();
        	
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
    }

    private void validate(BalanceDetail data) {

    	ValidationUtils.notEmptyString(data.getTitle(), "Title of Balance Details");
    	
    	ValidationUtils.notZero(data.getAmount(), "Amount");
	}

	@FXML
    private void save() {
    	
    	if(null == balance.getCategory() && !category.getText().isEmpty()) {
    		Category c = categoryModel.create(balance.getType(), category.getText());
    		balance.setCategory(c);
    	}
    	
    	// save balance
    }
    
    private void init(final Balance balance) {
    	this.balance = balance;
    	this.categoryModel = new CategoryModel();
    	
    	title.setText(balance.getType() == BalanceType.Incomes ? "Income Details" : "Expense Details");
    	
    	if(balance.getId() == 0) {
    		balance.setEmployee(ApplicationContext.getLoginUser());
    		balance.setDate(LocalDate.now());
    	} 
    	
    	empCode.setText(balance.getEmployee().getCode());
    	empName.setText(balance.getEmployee().getName());
    	empRole.setText(balance.getEmployee().getRole().name());
    	businessDate.setValue(balance.getDate());
    	
    	if(balance.getCategory() != null) {
    		category.setText(balance.getCategory().getName());
    	}
    	
    	inputAmount.setText(String.valueOf(balance.getTotal()));
    	remark.setText(balance.getRemark());
    	
    	AutoCompleteUtils.attach(category, 
    			name -> categoryModel.search(this.balance.getType(), name),
    			this.balance::setCategory);
    }

	public static Parent getView(Balance balnce) throws IOException {
		
		FXMLLoader loader = new FXMLLoader(BalanceDetails.class.getResource("BalanceDetails.fxml"));
		Parent view = loader.load();
		BalanceDetails controller = loader.getController();
		controller.init(balnce);
		
		return view;
	}


}
