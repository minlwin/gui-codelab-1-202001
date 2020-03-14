package com.jdc.accounting.views;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.jdc.accounting.context.ApplicationContext;
import com.jdc.accounting.model.BalanceModel;
import com.jdc.accounting.model.CategoryModel;
import com.jdc.accounting.model.entity.Balance;
import com.jdc.accounting.model.entity.BalanceDetail;
import com.jdc.accounting.model.entity.BalanceType;
import com.jdc.accounting.model.entity.Category;
import com.jdc.accounting.utils.StringUtils;
import com.jdc.accounting.utils.ValidationUtils;
import com.jdc.commons.fx.controls.AutoCompleteUtils;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ListChangeListener.Change;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;

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
	
	@FXML
	private TableColumn<BalanceDetail, Integer> amountColumn;
	
	@FXML
	private Button delButton;

	private Balance balance;

	private CategoryModel categoryModel;
	private BalanceModel model;

	private IntegerProperty totalProperty;
	
	private List<BalanceDetail> deleteList;

	@FXML
	private void initialize() {
		this.deleteList = new ArrayList<>();
		this.categoryModel = new CategoryModel();
		this.model = new BalanceModel();
		this.totalProperty = new SimpleIntegerProperty();

		this.total.textProperty().bind(totalProperty.asString());
		this.businessDate.valueProperty().addListener((a,b,c) -> {
			if(null != balance) {
				balance.setDate(c);
			}
		});

		this.remark.textProperty().addListener((a, b, c) -> {
			if (null != balance) {
				balance.setRemark(c);
			}
		});

		table.getItems().addListener((Change<? extends BalanceDetail> c) -> calculate());
		
		amountColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {

			@Override
			public String toString(Integer object) {
				if(null != object) {
					return object.toString();
				}
				return null;
			}

			@Override
			public Integer fromString(String string) {
				if(!StringUtils.isEmpty(string)) {
					return Integer.parseInt(string);
				}
				return null;
			}
		}));
		
		amountColumn.setOnEditCommit(event -> {
			event.getRowValue().setAmount(event.getNewValue());
			calculate();
		});
		
		MenuItem delete = new MenuItem("Delete");
		delete.setOnAction(e  -> {
			BalanceDetail data = table.getSelectionModel().getSelectedItem();
			table.getItems().remove(data);
			
			if(data.getId() > 0) {
				data.setDelete(true);
				deleteList.add(data);
			}
		});
		
		table.setContextMenu(new ContextMenu(delete));
		
	}

	private void init(final Balance balance) {
		this.balance = balance;

		title.setText(balance.getType() == BalanceType.Incomes ? "Income Details" : "Expense Details");

		if (balance.getId() == 0) {
			balance.setEmployee(ApplicationContext.getLoginUser());
			balance.setDate(LocalDate.now());
			delButton.setVisible(false);
		}

		empCode.setText(balance.getEmployee().getCode());
		empName.setText(balance.getEmployee().getName());
		empRole.setText(balance.getEmployee().getRole().name());
		businessDate.setValue(balance.getDate());

		if (balance.getCategory() != null) {
			category.setText(balance.getCategory().getName());
		}
		
		if(balance.getId() > 0) {
			List<BalanceDetail> details = model.findDetails(balance.getId());
			table.getItems().addAll(details);
		}

		remark.setText(balance.getRemark());

		AutoCompleteUtils.attach(category, name -> categoryModel.search(this.balance.getType(), name),
				this.balance::setCategory);

	}

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
	
	@FXML
	private void save() {

		try {

			if (null == balance.getCategory() && !category.getText().isEmpty()) {
				Category c = categoryModel.create(balance.getType(), category.getText());
				balance.setCategory(c);
			}

			// save balance
			List<BalanceDetail> list = new ArrayList<>();
			list.addAll(deleteList);
			list.addAll(table.getItems());
			model.save(balance, list);

			AccountHome.getContentManager().loadBalance(balance.getType());

		} catch (Exception e) {
			e.getSuppressed();
		}

	}

	@FXML
	private void delete() {
		
		try {
			
			model.delete(balance);
			
			AccountHome.getContentManager().loadBalance(balance.getType());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void validate(BalanceDetail data) {

		ValidationUtils.notEmptyString(data.getTitle(), "Title of Balance Details");

		ValidationUtils.notZero(data.getAmount(), "Amount");
	}
	
	private void calculate() {
		int totalValue = table.getItems().stream().filter(a -> !a.isDelete()).mapToInt(details -> details.getAmount()).sum();

		this.totalProperty.set(totalValue);
		this.balance.setTotal(totalValue);
	}

	public static Parent getView(Balance balnce) {

		try {
			FXMLLoader loader = new FXMLLoader(BalanceDetails.class.getResource("BalanceDetails.fxml"));
			Parent view = loader.load();
			BalanceDetails controller = loader.getController();
			controller.init(balnce);

			return view;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
