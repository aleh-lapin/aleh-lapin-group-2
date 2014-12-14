package com.epam.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.epam.ifaces.IAccount;
import com.epam.ifaces.ICurrencyRate;
import com.epam.model.Account;
import com.epam.model.Currency;
import com.epam.model.CurrencyRate;


@WebServlet("/manager.do")
@RolesAllowed(value = { "user_role" })
public class ManagerController extends AbstractServlet {
	private static final long serialVersionUID = 1L;
	Logger LOG = Logger.getLogger(ManagerController.class);
	
	@Inject
	IAccount iAccount;
	
	@Inject
	ICurrencyRate iCurrencyRate;
    
	private static Map<Long, String> currencies = new HashMap<Long, String>();
	static {
		for (Currency currency : Currency.values()) {
			currencies.put(new Long(currency.getId()), currency.toString());
		}
	}
	
    public ManagerController() {
        super();
    }

	@Override
	void doPostGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Account account = null;
		CurrencyRate rate = null;
		account = iAccount.findAccont(0);
		if (account == null) {
			account = new Account(0, "Main Account", Currency.USD.getId(), 1000);
			iAccount.addOrUpdateAccount(account);
		}
		LOG.info("Acount loaded");
		rate = iCurrencyRate.findCurrencyRate(account.getCurrency());
		LOG.info("Rates loaded");
		request.setAttribute("account", account);
		request.setAttribute("rate", rate);
		request.setAttribute("currencies", currencies);
		getServletContext().getRequestDispatcher("/manager.jsp").forward(request, response);
		
	}

}
