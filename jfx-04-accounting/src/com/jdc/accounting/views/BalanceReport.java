package com.jdc.accounting.views;

import java.time.LocalDate;
import java.util.List;

import com.jdc.accounting.model.BalanceModel;
import com.jdc.accounting.model.entity.Balance;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;

public class BalanceReport {

    @FXML
    private DatePicker from;

    @FXML
    private DatePicker to;

    @FXML
    private TableView<Balance> table;
    
    private BalanceModel model;
    
    @FXML
    private void initialize() {
    	model = new BalanceModel();
    	from.setValue(LocalDate.now().withDayOfMonth(1));
    }

    @FXML
    private void search() {
    	try {
			List<Balance> list = model.search(from.getValue(), to.getValue());
			table.getItems().clear();
			table.getItems().addAll(list);
    	} catch (Exception e) {
			e.printStackTrace();
		}
    }

}
