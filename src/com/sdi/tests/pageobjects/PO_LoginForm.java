package com.sdi.tests.pageobjects;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class PO_LoginForm {

	
	
   public void rellenaEntrada(WebDriver driver, String plogin, String ppassword)
   {
		WebElement nombre = driver.findElement(By.id("form-principal:login"));
		nombre.click();
		nombre.clear();
		nombre.sendKeys(plogin);
		WebElement apellidos = driver.findElement(By.id("form-principal:password"));
		apellidos.click();
		apellidos.clear();
		apellidos.sendKeys(ppassword);
			
		//Pulsar el boton de Alta.
		By boton = By.id("form-principal:aceppt");
		driver.findElement(boton).click();	   
   }
	
	
	
}
