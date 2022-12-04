package ru.company.framework.stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hooks extends BaseSteps{
	private static final Logger LOGGER = LoggerFactory.getLogger(Hooks.class);

	@After
	public void tearDown(Scenario scenario){
		if (scenario.isFailed()) {
			LOGGER.info("Сохраняем скриншот страницы");
			Allure.addAttachment("failureScreenshot", "image/png", takeScreenshot(), "png");
		}
		pageManager.clearMapPages();
		driverManager.quitDriver();
	}
}
