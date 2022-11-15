package ru.company.framework.managers;

import ru.company.framework.pages.SecondaryHousingMortgagePage;
import ru.company.framework.pages.StartPage;

public class PageManager {
	private static PageManager pageManager = null;
	private PageManager() {
	}

	public static PageManager getPageManager() {
		if (pageManager == null) {
			pageManager = new PageManager();
		}
		return pageManager;
	}

	private StartPage startPage;
	private SecondaryHousingMortgagePage mortgagePage;

	public StartPage getStartPage() {
		if (startPage == null) {
			startPage = new StartPage();
		}
		return startPage;
	}

	public SecondaryHousingMortgagePage getMortgagePage() {
		if (mortgagePage == null) {
			mortgagePage = new SecondaryHousingMortgagePage();
		}
		return mortgagePage;
	}
}
