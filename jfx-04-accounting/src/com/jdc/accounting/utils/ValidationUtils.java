package com.jdc.accounting.utils;

import com.jdc.accounting.model.BalanceException;

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
}
