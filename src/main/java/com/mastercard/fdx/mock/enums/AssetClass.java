package com.mastercard.fdx.mock.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@AllArgsConstructor
@Slf4j
public enum AssetClass {

	DOMESTIC_BOND("DOMESTICBOND"),
	INTL_BOND("INTLBOND"),
	INTL_STOCK("INTLSTOCK"),
	LARGE_STOCK("LARGESTOCK"),
	MONEY_MARKET("MONEYMARKET"),
	OTHER("OTHER"),
	OTHER_ASSET("Other"),
	SMALL_STOCK("SMALLSTOCK"),
	
	CASH("Cash"),
	FIXEDINCOME("FixedIncome"),
	STOCK("Stock"),
	MUTUALFUND("MutualFund"),
	EQUITY("Equity"),
	MONEY_MARKET_FUND("MoneyMarketFund"),
	PREFERRED_STOCK("PreferredStock"),
	STOCK_FUND("StockFund"),
	BALANCED_FUND("BalancedFund"),
	TAXABLE_BOND_FUND("TaxableBondFund"),
	TAX_EXEMPT_BOND_FUND("TaxExemptBondFund"),
	CLOSE_END_MUTUAL_FUND("CloseEndMutualFund"),
	OPTION("Option"),
	LISTED_OPTION("ListedOption"),
	INDEX_OPTION("IndexOption"),
	CURRENCY_OPTION("CurrencyOption"),
	OTC_OPTION("OTCOption"),
	FUTURE("Future"),
	RIGHT("Right"),
	WARRANT("Warrant"),
	BOND("Bond"),
	COMMERCIAL_PAPER("CommercialPaper"),
	CD("CD"),
	BANK_NOTE("BankNote"),
	TAX_EXEMPT_BOND("TaxExemptBond"),
	TAXABLE_BOND("TaxableBond"),
	UNIT_INVESTMENT_TRUST("UnitInvestmentTrust"),
	INSURED_BANK_DEPOSIT("InsuredBankDeposit"),
	INSURANCE("Insurance"),
	UNKNOWN("Unknown"),
	CORPORATE_BOND("CorporateBond"),
	MUNICIPAL_BOND("MunicipalBond"),
	GOVERNMENT_BOND("GovernmentBond"),
	LIMITED_PARTNERSHIP("LimitedPartnership"),
	MORTGAGE_BACKED_SECURITY("MortgageBackedSecurity"),
	REAL_ESTATE("RealEstate"),
	REAL_ASSETS("RealAssets"),
	COMMODITIES("Commodities"),
	ALTERNATIVE_INVESTMENT("AlternativeInvestment"),
	HELD_AWAY_ASSETS("HeldAwayAssets");
	
	private String type;
	
	@JsonValue
	public String forJackson() {
		return type;
	}
}
