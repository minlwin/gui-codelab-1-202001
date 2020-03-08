package com.jdc.accounting.views;

import com.jdc.accounting.model.entity.BalanceType;

import javafx.scene.Parent;

public interface ContentManager {

	void setContentView(Parent view);
	
	void loadBalance(BalanceType type);
}
