package ru.company.framework.stepDefinitions;

import io.cucumber.java.ru.Допустим;
import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.И;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.company.framework.managers.PropManager;
import ru.company.framework.pages.StartPage;
import ru.company.framework.utils.ConstProp;

public class StartPageSteps extends BaseSteps{
	private static final Logger LOGGER = LoggerFactory.getLogger(StartPageSteps.class);
	private StartPage startPage = pageManager.getPage(StartPage.class);

	@Допустим("^пользователь открывает стартовую страницу сбербанк$")
	public void openStartPage(){
		LOGGER.info("Открываем стартовую страницу сбербанк");
		driverManager.getDriver().get(PropManager.getPropManager().getProperty(ConstProp.BASE_URL));
	}


	@И("^переходит в меню \"([^\"]*)\"$")
	public void openMenu(String menuTitle) {
		LOGGER.info("Переходим в раздел {}", menuTitle);
		WebElement menu = startPage.selectMenu(menuTitle);
		Assertions.assertNotNull(menu, String.format("Меню %s не найдено на странице", menuTitle));
		wait.until(ExpectedConditions.elementToBeClickable(menu));
		menu.click();
	}

	@Затем("^выбирает подменю \"([^\"]*)\"$")
	public void openSubMenu(String subMenuTitle) {
		LOGGER.info("Переходим в раздел {}", subMenuTitle);
		WebElement subMenu = startPage.selectSubMenu(subMenuTitle);
		Assertions.assertNotNull(subMenu, String.format("Меню %s не найдено на странице", subMenuTitle));
		wait.until(ExpectedConditions.elementToBeClickable(subMenu));
		subMenu.click();
	}
}
