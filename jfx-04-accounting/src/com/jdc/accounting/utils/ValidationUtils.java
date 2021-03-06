package com.jdc.accounting.utils;

import java.util.List;

import com.jdc.accounting.model.BalanceException;
import com.jdc.accounting.model.entity.BalanceDetail;

public class ValidationUtils {

	public static void notEmptyStringInput(String str, String field) {
		if(StringUtils.isEmpty(str)) {
			throw new BalanceException(String.format("Please enter %s!", field));
		}
	}

	public static void notZero(int value, String field) {
		if(value <= 0) {
			throw new BalanceException(String.format("Please enter %s more than Zero!", field));
		}
	}

	public static<T> void notNullSelect(T role, String field) {
		if(null == role) {
			throw new BalanceException(String.format("Please select %s!", field));
		}
	}

	public static void notEmptyString(String str, String message) {
		if(StringUtils.isEmpty(str)) {
			throw new BalanceException(message);
		}
	}

	public static void notEmptyList(List<BalanceDetail> items, String string) {

		if(null == items || items.isEmpty()) {
			throw new BalanceException(String.format("Please add %s!", string));
		}
	}

	public static<T> void notNull(T d, String string) {
		if(null == d) {
			throw new BalanceException(String.format("%s must not be null!", string));
		}
	}
}
