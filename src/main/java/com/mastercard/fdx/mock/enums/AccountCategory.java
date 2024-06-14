package com.mastercard.fdx.mock.enums;

public enum AccountCategory {

	DEPOSIT_ACCOUNT,
	LOAN_ACCOUNT,
	INVESTMENT_ACCOUNT,
	LOC_ACCOUNT;
	
	public static boolean validate(String accCategory) {
		AccountCategory[] accountCategories = AccountCategory.values();
		for(AccountCategory acc: accountCategories) {
			if(acc.name().equalsIgnoreCase(accCategory))
				return true;
		}
		return false;
	}
}
