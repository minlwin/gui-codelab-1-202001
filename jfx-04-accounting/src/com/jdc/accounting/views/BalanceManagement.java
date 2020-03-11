package com.jdc.accounting.views;

import java.time.LocalDate;
import java.util.List;

import com.jdc.accounting.context.ApplicationContext;
import com.jdc.accounting.model.BalanceModel;
import com.jdc.accounting.model.CategoryModel;
import com.jdc.accounting.model.entity.Balance;
import com.jdc.accounting.model.entity.BalanceType;
import com.jdc.accounting.model.entity.Category;
import com.jdc.accounting.model.entity.Employee;
import com.jdc.accounting.model.entity.Employee.Role;
import com.jdc.commons.fx.controls.AutoCompleteUtils;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class BalanceManagement {

	@FXML
	private Label title;

	@FXML
	private TextField schCategory;

	@FXML
	private TextField schEmployee;

	@FXML
	private DatePicker dateFrom;

	@FXML
	private DatePicker dateTo;

	@FXML
	private TableView<Balance> table;

	private BalanceType type;

	private ObjectProperty<Category> selectedCategory;

	private CategoryModel categoryModel;

	private BalanceModel balanceModel;

	@FXML
	private void initialize() {

		this.categoryModel = new CategoryModel();
		this.balanceModel = new BalanceModel();
		this.selectedCategory = new SimpleObjectProperty<Category>();
		
		Employee loginUser = ApplicationContext.getLoginUser();
		
		if(loginUser.getRole() != Role.Admin) {
			schEmployee.setText(loginUser.getCode());
			schEmployee.setEditable(false);
		}
		
		AutoCompleteUtils.attach(schCategory, name -> categoryModel.search(type, name), this.selectedCategory::set);

		schCategory.textProperty().addListener((a, b, c) -> selectedCategory.set(null));
		schEmployee.textProperty().addListener((a, b, c) -> search());

		dateFrom.valueProperty().addListener((a, b, c) -> search());
		dateTo.valueProperty().addListener((a, b, c) -> search());

		selectedCategory.addListener((a, b, c) -> search());

		table.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				try {
					Balance balance = table.getSelectionModel().getSelectedItem();
					if (null != balance) {
						Parent view = BalanceDetails.getView(balance);
						AccountHome.getContentManager().setContentView(view);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

	}

	@FXML
	private void addNew() {
		try {
			Balance balance = new Balance();
			balance.setType(type);
			Parent view = BalanceDetails.getView(balance);
			AccountHome.getContentManager().setContentView(view);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void clear() {

		schCategory.clear();
		schEmployee.clear();
		dateFrom.setValue(null);
		dateTo.setValue(null);
		table.getItems().clear();
	}

	@FXML
	private void search() {

		List<Balance> list = balanceModel.search(type, selectedCategory.get(), dateFrom.getValue(), dateTo.getValue(),
				schEmployee.getText());

		table.getItems().clear();
		table.getItems().addAll(list);
	}

	public void init(BalanceType type) {
		this.type = type;
		title.setText(type == BalanceType.Incomes ? "Incomes Management" : "Expenses Management");
		dateFrom.setValue(LocalDate.now().withDayOfMonth(1));
	}

}
