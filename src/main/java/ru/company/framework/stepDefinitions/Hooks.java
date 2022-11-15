package ru.company.framework.stepDefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;

public class Hooks extends BaseSteps{

	@After
	public void tearDown(Scenario scenario){
		if (scenario.isFailed()) {
			Allure.addAttachment("failureScreenshot", "image/png", takeScreenshot(), "png");
		}
		driverManager.getDriver().quit();
	}
}
