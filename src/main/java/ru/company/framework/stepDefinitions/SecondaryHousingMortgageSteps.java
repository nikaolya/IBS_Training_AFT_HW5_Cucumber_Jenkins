package ru.company.framework.stepDefinitions;

import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Пусть;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.company.framework.pages.SecondaryHousingMortgagePage;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SecondaryHousingMortgageSteps extends BaseSteps {
	private static final Logger LOGGER = LoggerFactory.getLogger(SecondaryHousingMortgageSteps.class);
	SecondaryHousingMortgagePage mortgagePage = pageManager.getPage(SecondaryHousingMortgagePage.class);

	@И("^скроллит страницу (?:вниз|вверх) до раздела \"([^\"]*)\"$")
	public void scrollToSection(String section) {
		LOGGER.info("Скроллим страницу до раздела {}", section);
		Assertions.assertNotNull(mortgagePage.getH2Header(section), String.format("Заголовка %s нет на странице", section));
		mortgagePage.scrollToElementJs(mortgagePage.getH2Header(section));
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	@Пусть("^пользователь заполняет поля значениями$")
	public void fillInputFields(Map<String, Integer> input) {
		LOGGER.info("Заполняем поля значениями");
		input.entrySet().forEach(i -> {
			WebElement inputField = mortgagePage.getInputField(i.getKey());
			Assertions.assertNotNull(inputField, "Поле для заполнения не найдено");
			mortgagePage.scrollToElementJsInFrame(mortgagePage.getFrameInput(), inputField);
			mortgagePage.fillInputField(inputField, String.valueOf(i.getValue()));
			assertThat("Введенное значение неверно",
					mortgagePage.getInputFieldValue(inputField), is(i.getValue()));
		});
	}

	@И("^(?:убирает|ставит) галочку \"([^\"]*)\"$")
	public void setCheckbox(String option) {
		LOGGER.info("Убираем/ставим галочку {}", option);
		WebElement checkbox = mortgagePage.getCheckbox(option);
		Assertions.assertNotNull(checkbox, String.format("На странице нет чекбокса %s", option));
		mortgagePage.scrollToElementJsInFrame(mortgagePage.getFrameInput(), checkbox);
		mortgagePage.setCheckbox(checkbox);
	}

	@Затем("^проверяет правильность расчета условий по ипотеке$")
	public void checkCalculationCorrectness(Map<String, String> result) {
		LOGGER.info("Проверяем правильность расчета условий по ипотеке");
		result.entrySet().forEach(i -> {
			WebElement resultField = mortgagePage.getResult(i.getKey());
			Assertions.assertNotNull(resultField, String.format("Поле %s не найдено", i.getKey()));
			mortgagePage.scrollToElementJsInFrame(mortgagePage.getFrameInput(), resultField);
			assertThat(String.format("Введенное значение %s неверно", i.getKey()),
					mortgagePage.getMainResultValue(resultField), is(i.getValue()));
		});
	}

	@И("^проверяет, что необходимый доход составляет ([^\"]*)$")
	public void checkRequiredIncome(String expectedResult) {
		LOGGER.info("Проверяем, что необходимый доход составляет {}", expectedResult);
		assertThat("Значение необходимого дохода посчитано не верно", mortgagePage.getRequiredIncomeValue(), is(expectedResult));
	}
}
