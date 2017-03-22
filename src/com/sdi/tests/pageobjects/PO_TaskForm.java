package com.sdi.tests.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.seleniumhq.jetty9.util.StringUtil;

public class PO_TaskForm {

	public void rellenaEntrada(WebDriver driver, String titulo,
			String categoria, String comentario, String planeada,
			String finalizada) {

		WebElement tituloE = driver.findElement(By.id("form-alta:titulo"));
		tituloE.click();
		tituloE.clear();
		tituloE.sendKeys(titulo);

		if (StringUtil.isNotBlank(categoria)) {
			driver.findElement(By.id("form-alta:category")).click();
			driver.findElement(By.id("form-alta:" + categoria)).click();
		}

		if (StringUtil.isNotBlank(comentario)) {
			WebElement commentE = driver.findElement(By
					.id("form-alta:comentario"));
			commentE.click();
			commentE.clear();
			commentE.sendKeys(comentario);
		}

		if (StringUtil.isNotBlank(planeada)) {
			WebElement planeadaE = driver.findElement(By
					.id("form-alta:planned_input"));
			planeadaE.click();
			planeadaE.clear();
			planeadaE.sendKeys(planeada);
		}

		if(StringUtil.isNotBlank(finalizada)){
			// Pulsamos el titulo para que se quite el calendario
			tituloE.click();
			try {
				Thread.sleep(250L);
			} catch (InterruptedException e) {
			}

			WebElement finalizadaE = driver.findElement(By
					.id("form-alta:finished_input"));
			finalizadaE.click();
			finalizadaE.clear();
			finalizadaE.sendKeys(finalizada);
		}

		// Pulsar el boton de Alta.
		By boton = By.id("form-alta:send");
		driver.findElement(boton).click();
	}

}
