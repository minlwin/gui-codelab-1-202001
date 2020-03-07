package com.jdc.accounting.views;

import java.io.IOException;
import java.util.Iterator;

import com.jdc.accounting.context.ApplicationContext;
import com.jdc.accounting.model.entity.BalanceType;
import com.jdc.accounting.utils.ViewId;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AccountHome implements ContentManager{
	
	private static ContentManager contentManager;
	
	public static ContentManager getContentManager() {
		return contentManager;
	}
	
	@FXML
	private StackPane content;
	
	@FXML
	private HBox header;
	
	public static void show() {
		try {
			
			Parent root = FXMLLoader.load(AccountHome.class.getResource("AccountHome.fxml"));
			Stage stage = new Stage();
			stage.setScene(new Scene(root));
			stage.show();
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	@FXML
	private void initialize() throws IOException {
		// filter menu
		if(!ApplicationContext.isAdamin()) {
			Iterator<Node> itr = header.getChildren().iterator();
			
			while(itr.hasNext()) {
				Node node = itr.next();
				
				if(node.getStyleClass().contains("icon-menu")) {
					
					ViewId viewId = ViewId.valueOf(node.getId());
					
					if(viewId.isOnlyAdmin()) {
						itr.remove();
					}
				}
			}
		}
		
		loadView(ViewId.Home.getView());
		
		contentManager = this;
	}

	@FXML
	private void loadView(MouseEvent event) {
		
		try {
			Object source = event.getSource();
			if(source instanceof VBox) {
				VBox iconMenu = (VBox) source;
				String menuId = iconMenu.getId();
				
				ViewId viewId = ViewId.valueOf(menuId);
				
				switch (viewId) {
				case Incomes:
				case Expenses:
					loadBalances(viewId);
					break;

				default:
					loadView(viewId.getView());
					break;
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	private void loadBalances(ViewId viewId) throws IOException {

		FXMLLoader loader = new FXMLLoader(getClass().getResource(viewId.getView()));
		Parent view = loader.load();
		BalanceManagement controller = loader.getController();
		controller.init(viewId == ViewId.Incomes? BalanceType.Incomes : BalanceType.Expenses);
		loadView(view);
	}

	private void loadView(String viewName) throws IOException {
		Parent view = FXMLLoader.load(getClass().getResource(viewName));
		loadView(view);
	}

	private void loadView(Parent view) {
		content.getChildren().clear();
		content.getChildren().add(view);
	}

	@Override
	public void setContentView(Parent view) {
		loadView(view);
	}
}
