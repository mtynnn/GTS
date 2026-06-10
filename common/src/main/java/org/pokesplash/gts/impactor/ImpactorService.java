package org.pokesplash.gts.impactor;

import net.impactdev.impactor.api.economy.EconomyService;
import net.impactdev.impactor.api.economy.accounts.Account;
import net.impactdev.impactor.api.economy.currency.Currency;
import net.impactdev.impactor.api.economy.transactions.EconomyTransaction;
import org.pokesplash.gts.api.economy.GtsEconomy;

import java.math.BigDecimal;
import java.util.UUID;

public class ImpactorService implements GtsEconomy {

	private static EconomyService service = EconomyService.instance();
	private static Currency currency = service.currencies().primary();

	private Account getAccount(UUID uuid) {
		if (!service.hasAccount(uuid).join()) {
			return service.account(uuid).join();
		}
		return service.account(currency, uuid).join();
	}

	@Override
	public boolean add(UUID account, double amount) {
		Account acc = getAccount(account);
		EconomyTransaction transaction = acc.deposit(new BigDecimal(amount));
		return transaction.successful();
	}

	@Override
	public boolean remove(UUID account, double amount) {
		Account acc = getAccount(account);
		EconomyTransaction transaction = acc.withdraw(new BigDecimal(amount));
		return transaction.successful();
	}

	@Override
	public double balance(UUID player) {
		Account acc = getAccount(player);
		return acc.balance().doubleValue();
	}
}
