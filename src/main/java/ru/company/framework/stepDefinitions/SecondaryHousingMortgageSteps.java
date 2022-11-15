package ru.company.framework.stepDefinitions;

import io.cucumber.java.ru.Затем;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Пусть;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import ru.company.framework.pages.SecondaryHousingMortgagePage;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class SecondaryHousingMortgageSteps extends BaseSteps {
	SecondaryHousingMortgagePage mortgagePage = pageManager.getMortgagePage();

	@И("^скроллит страницу (?:вниз|вверх) до раздела \"([^\"]*)\"$")
	public void scrollToSection(String section) {
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
		input.entrySet().forEach(i -> {
			WebElement inputField = mortgagePage.getInputField(i.getKey());
			mortgagePage.scrollToElementJsInFrame(mortgagePage.getFrameInput(), inputField);
			Assertions.assertNotNull(inputField, "Поле для заполнения не найдено");
			mortgagePage.fillInputField(inputField, String.valueOf(i.getValue()));
			assertThat("Введенное значение неверно",
					mortgagePage.getInputFieldValue(inputField), is(i.getValue()));
		});
	}

	@И("^(?:убирает|ставит) галочку \"([^\"]*)\"$")
	public void setCheckbox(String option) {
		WebElement checkbox = mortgagePage.getCheckbox(option);
		mortgagePage.scrollToElementJsInFrame(mortgagePage.getFrameInput(), checkbox);
		Assertions.assertNotNull(checkbox, String.format("На странице нет чекбокса %s", option));
		mortgagePage.setCheckbox(checkbox);
	}

	@Затем("^проверяет правильность расчета условий по ипотеке$")
	public void checkCalculationCorrectness(Map<String, String> result) {
		result.entrySet().forEach(i -> {
			WebElement resultField = mortgagePage.getResult(i.getKey());
			mortgagePage.scrollToElementJsInFrame(mortgagePage.getFrameInput(), resultField);
			Assertions.assertNotNull(resultField, String.format("Поле %s не найдено", i.getKey()));
			assertThat(String.format("Введенное значение %s неверно", i.getKey()),
					mortgagePage.getMainResultValue(resultField), is(i.getValue()));
		});
	}

	@И("^проверяет, что необходимый доход составляет ([^\"]*)$")
	public void checkRequiredIncome(String expectedResult) {
		assertThat("Значение необходимого дохода посчитано не верно", mortgagePage.getRequiredIncomeValue(), is(expectedResult));
	}
}
