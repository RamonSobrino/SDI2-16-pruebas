package com.sdi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_RegisterForm {

	public void rellenaEntrada(WebDriver driver, String plogin, String pemail,
			String ppassword, String ppasswordAgain) {
		WebElement nombre = driver.findElement(By.id("form-register:login"));
		nombre.click();
		nombre.clear();
		nombre.sendKeys(plogin);
		
		WebElement apellidos = driver.findElement(By
				.id("form-register:email"));
		apellidos.click();
		apellidos.clear();
		apellidos.sendKeys(pemail);
		
		WebElement password = driver.findElement(By
				.id("form-register:password"));
		password.click();
		password.clear();
		password.sendKeys(ppassword);
		
		WebElement password2 = driver.findElement(By
				.id("form-register:passwordAgain"));
		password2.click();
		password2.clear();
		password2.sendKeys(ppasswordAgain);

		// Pulsar el boton de Alta.
		By boton = By.id("form-register:register");
		driver.findElement(boton).click();
	}

}
