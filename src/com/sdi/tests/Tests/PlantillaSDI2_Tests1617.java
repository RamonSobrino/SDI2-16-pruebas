package com.sdi.tests.Tests;

import static org.junit.Assert.*;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.seleniumhq.jetty9.util.StringUtil;

import com.sdi.tests.pageobjects.PO_LoginForm;
import com.sdi.tests.pageobjects.PO_RegisterForm;
import com.sdi.tests.pageobjects.PO_TaskForm;
import com.sdi.tests.utils.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PlantillaSDI2_Tests1617 {

	WebDriver driver;
	List<WebElement> elementos = null;
	private String baseURL = "http://localhost:8280/sdi2-16";

	public PlantillaSDI2_Tests1617() {
	}

	@Before
	public void run() {
		// Este código es para ejecutar con la versión portale de Firefox 46.0
		File pathToBinary = new File("S:\\firefox\\FirefoxPortable.exe");
		FirefoxBinary ffBinary = new FirefoxBinary(pathToBinary);
		FirefoxProfile firefoxProfile = new FirefoxProfile();
		driver = new FirefoxDriver(ffBinary, firefoxProfile);
		driver.get(baseURL);
		// Este código es para ejecutar con una versión instalada de Firex 46.0
		// driver = new FirefoxDriver();
		// driver.get("http://localhost:8180/sdi2-n");
	}

	@After
	public void end() {
		// Cerramos el navegador
		driver.quit();
	}

	// PRUEBAS
	// ADMINISTRADOR
	// PR01: Autentificar correctamente al administrador.
	@Test
	public void prueba01() {

		new PO_LoginForm().rellenaEntrada(driver, "admin1", "admin1");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);
		SeleniumUtils.textoPresentePagina(driver, "Opciones de administrador");
	}

	// PR02: Fallo en la autenticación del administrador por introducir mal el
	// login.
	@Test
	public void prueba02() {
		new PO_LoginForm().rellenaEntrada(driver, "admin", "admin1");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Error", 60);
		SeleniumUtils.textoPresentePagina(driver,
				"Usuario o contraseña incorrectos");
	}

	// PR03: Fallo en la autenticación del administrador por introducir mal la
	// password.
	@Test
	public void prueba03() {
		new PO_LoginForm().rellenaEntrada(driver, "admin1", "admin");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Error", 60);
		SeleniumUtils.textoPresentePagina(driver,
				"Usuario o contraseña incorrectos");
	}

	// PR04: Probar que la base de datos contiene los datos insertados con
	// conexión correcta a la base de datos.
	@Test
	public void prueba04() {
		new PO_LoginForm().rellenaEntrada(driver, "admin1", "admin1");
		WebElement restart = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"restartdb", 60).get(0);
		// Pulsamos iniciar base de datos
		restart.click();
		// Comprobamos que se reincia con exito
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Exito", 60);
		SeleniumUtils.textoPresentePagina(driver,
				"Base de datos reiniciada con exito");
		// Accedemos a la lista de usuarios
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:listar");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "user1", 60);
		// Comprobamos que exiten los usuarios
		SeleniumUtils.textoPresentePagina(driver, "user1");
		SeleniumUtils.textoPresentePagina(driver, "user2");
		SeleniumUtils.textoPresentePagina(driver, "user3");

		// Salimos de administrador
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:cerrar");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Exito", 60);

		// Comprobamos user1
		new PO_LoginForm().rellenaEntrada(driver, "user1", "user1");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones de usuario",
				60);
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:listar");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "1 de 3", 60);
		// Tareas inbox pagina 1
		for (int i = 1; i <= 8; i++) {
			SeleniumUtils.textoPresentePagina(driver, "Tarea " + i);
		}
		// Tareas inbox pagina 2
		WebElement pag2 = driver
				.findElements(By.className("ui-paginator-page")).get(1);
		pag2.click();
		SeleniumUtils.EsperaCargaPagina(driver, "text", "2 de 3", 60);

		for (int i = 9; i <= 16; i++) {
			SeleniumUtils.textoPresentePagina(driver, "Tarea " + i);
		}
		WebElement pag3 = driver
				.findElements(By.className("ui-paginator-page")).get(2);
		pag3.click();
		SeleniumUtils.EsperaCargaPagina(driver, "text", "3 de 3", 60);
		for (int i = 17; i <= 20; i++) {
			SeleniumUtils.textoPresentePagina(driver, "Tarea " + i);
		}

	}

	// PR05: Visualizar correctamente la lista de usuarios normales.
	@Test
	public void prueba05() {
		new PO_LoginForm().rellenaEntrada(driver, "admin1", "admin1");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:listar");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "user1", 60);

		// SeleniumUtils.textoPresentePagina(driver, "Lista de Usuarios");
		SeleniumUtils.textoPresentePagina(driver, "user1");
		SeleniumUtils.textoPresentePagina(driver, "user2");
		SeleniumUtils.textoPresentePagina(driver, "user3");

	}

	// PR06: Cambiar el estado de un usuario de ENABLED a DISABLED. Y tratar de
	// entrar con el usuario que se desactivado.
	@Test
	public void prueba06() {
		new PO_LoginForm().rellenaEntrada(driver, "admin1", "admin1");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:listar");

		// Deshabilitamos el usuario 3
		WebElement user3Disable = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"listado_form:user-table:3", 60).get(0);
		user3Disable.click();
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Exito", 60);
		SeleniumUtils.textoPresentePagina(driver, "Usuario user3 desactivado");
		SeleniumUtils.textoPresentePagina(driver, "DISABLED");

		// Cerramos sesion con el administrador e intentamos entrar con user3
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:cerrar");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Login", 60);
		new PO_LoginForm().rellenaEntrada(driver, "user3", "user3");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Error", 60);
		SeleniumUtils.textoPresentePagina(driver,
				"Usuario o contraseña incorrectos");
	}

	// PR07: Cambiar el estado de un usuario a DISABLED a ENABLED. Y Y tratar de
	// entrar con el usuario que se ha activado.
	@Test
	public void prueba07() {
		new PO_LoginForm().rellenaEntrada(driver, "admin1", "admin1");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:listar");

		// Habilitamos el usuario 3
		WebElement user3Disable = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"listado_form:user-table:3", 60).get(0);
		user3Disable.click();
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Exito", 60);
		SeleniumUtils.textoPresentePagina(driver, "Usuario user3 activado");
		SeleniumUtils.textoNoPresentePagina(driver, "DISABLED");

		// Cerramos sesion con el administrador e intentamos entrar con user3
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:cerrar");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Login", 60);
		new PO_LoginForm().rellenaEntrada(driver, "user3", "user3");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);
		SeleniumUtils.textoPresentePagina(driver, "Opciones de usuario");
	}

	// PR08: Ordenar por Login
	@Test
	public void prueba08() {
		new PO_LoginForm().rellenaEntrada(driver, "admin1", "admin1");

		SeleniumUtils.EsperaCargaPagina(driver, "text",
				"Opciones de administrador", 60);
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:listar");

		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver,
				"id", "user-table:order_login", 60);

		// Comprobar al derechas
		WebElement loginHead = elementos.get(0);
		loginHead.click();
		// Esperar a que se ordene
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
		}
		By loginXpath = By.xpath("//table/tbody/tr/td[2]");
		List<WebElement> logins = driver.findElements(loginXpath);
		List<String> loginsStr = new ArrayList<>();
		for (WebElement webElement : logins) {
			loginsStr.add(webElement.getText());
		}
		List<String> expected = new ArrayList<>(loginsStr);
		Collections.sort(expected);
		for (int i = 0; i < loginsStr.size(); i++) {
			assertEquals(expected.get(i), loginsStr.get(i));
		}

		// Comprobar al reves
		loginHead.click();
		// Esperar a que se ordene
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
		}
		logins = driver.findElements(loginXpath);
		loginsStr = new ArrayList<>();
		for (WebElement webElement : logins) {
			loginsStr.add(webElement.getText());
		}
		expected = new ArrayList<>(loginsStr);
		Collections.sort(expected);
		Collections.reverse(expected);
		for (int i = 0; i < loginsStr.size(); i++) {
			assertEquals(expected.get(i), loginsStr.get(i));
		}

	}

	// PR09: Ordenar por Email
	@Test
	public void prueba09() {
		new PO_LoginForm().rellenaEntrada(driver, "admin1", "admin1");

		SeleniumUtils.EsperaCargaPagina(driver, "text",
				"Opciones de administrador", 60);
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:listar");

		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver,
				"id", "user-table:order_email", 60);

		By emailXpath = By.xpath("//table/tbody/tr/td[3]");
		// Comprobar al derechas
		WebElement emailHead = elementos.get(0);
		emailHead.click();
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e1) {
		}
		List<WebElement> emails = driver.findElements(emailXpath);
		List<String> emailsStr = new ArrayList<>();
		for (WebElement webElement : emails) {
			emailsStr.add(webElement.getText());
		}
		List<String> expected = new ArrayList<>(emailsStr);
		Collections.sort(expected);
		for (int i = 0; i < emailsStr.size(); i++) {
			assertEquals(expected.get(i), emailsStr.get(i));
		}

		// Comprobar al reves
		emailHead.click();
		// Esperar a que se ordene
		try {
			Thread.sleep(500L);
		} catch (InterruptedException e) {
		}
		emails = driver.findElements(emailXpath);
		emailsStr = new ArrayList<>();
		for (WebElement webElement : emails) {
			emailsStr.add(webElement.getText());
		}
		expected = new ArrayList<>(emailsStr);
		Collections.sort(expected);
		Collections.reverse(expected);
		for (int i = 0; i < emailsStr.size(); i++) {
			assertEquals(expected.get(i), emailsStr.get(i));
		}

	}

	// PR10: Ordenar por Status
	@Test
	public void prueba10() {
		new PO_LoginForm().rellenaEntrada(driver, "admin1", "admin1");

		SeleniumUtils.EsperaCargaPagina(driver, "text",
				"Opciones de administrador", 60);
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:listar");

		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver,
				"id", "listado_form:user-table:order_status", 60);

		By statusXpath = By.xpath("//table/tbody/tr/td[4]");
		// Comprobar al derechas
		WebElement statusHead = elementos.get(0);
		statusHead.click();
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e1) {
		}
		List<WebElement> statuses = driver.findElements(statusXpath);
		List<String> statusStr = new ArrayList<>();
		for (WebElement webElement : statuses) {
			statusStr.add(webElement.getText());
		}
		List<String> expected = new ArrayList<>(statusStr);
		Collections.sort(expected);
		for (int i = 0; i < statusStr.size(); i++) {
			assertEquals(expected.get(i), statusStr.get(i));
		}

		// Comprobar al reves
		statusHead.click();
		// Esperar a que se ordene
		try {
			Thread.sleep(500L);
		} catch (InterruptedException e) {
		}
		statuses = driver.findElements(statusXpath);
		statusStr = new ArrayList<>();
		for (WebElement webElement : statuses) {
			statusStr.add(webElement.getText());
		}
		expected = new ArrayList<>(statusStr);
		Collections.sort(expected);
		Collections.reverse(expected);
		for (int i = 0; i < statusStr.size(); i++) {
			assertEquals(expected.get(i), statusStr.get(i));
		}

	}

	// PR11: Borrar una cuenta de usuario normal y datos relacionados.
	@Test
	public void prueba11() {
		// Registramos un usuario para borrar
		WebElement registerLink = driver.findElement(By
				.id("form-principal:register"));
		registerLink.click();
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				"Registrar nuevo usuario", 60);
		new PO_RegisterForm().rellenaEntrada(driver, "borrame",
				"borrame@example.org", "borrame1", "borrame1");

		// Entramos como administrador
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Login", 60);
		new PO_LoginForm().rellenaEntrada(driver, "admin1", "admin1");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:listar");

		// Borramos usuario
		WebElement borrame = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"listado_form:user-table:4", 60).get(1);
		borrame.click();
		WebElement confirm = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"4:comfirm-delete", 60).get(0);
		confirm.click();

		// Comprobamos borrado
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Exito", 60);
		SeleniumUtils.textoNoPresentePagina(driver, "borrame");
	}

	// PR12: Crear una cuenta de usuario normal con datos válidos.
	@Test
	public void prueba12() {
		WebElement registerLink = driver.findElement(By
				.id("form-principal:register"));
		registerLink.click();
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				"Registrar nuevo usuario", 60);
		new PO_RegisterForm().rellenaEntrada(driver, "testuser",
				"test@example.org", "testus3r", "testus3r");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Exito", 60);
		SeleniumUtils.textoPresentePagina(driver, "Registrado correctamente");

		// Comprobar que te puedes loguear correctamente
		new PO_LoginForm().rellenaEntrada(driver, "testuser", "testus3r");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);
		SeleniumUtils.textoPresentePagina(driver, "Opciones de usuario");
	}

	// PR13: Crear una cuenta de usuario normal con login repetido.
	@Test
	public void prueba13() {
		WebElement registerLink = driver.findElement(By
				.id("form-principal:register"));
		registerLink.click();
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				"Registrar nuevo usuario", 60);
		new PO_RegisterForm().rellenaEntrada(driver, "admin1",
				"test@example.org", "testus3r", "testus3r");

		SeleniumUtils.EsperaCargaPagina(driver, "text",
				"The login is already used", 60);
		SeleniumUtils.textoPresentePagina(driver, "The login is already used");
	}

	// PR14: Crear una cuenta de usuario normal con Email incorrecto.
	@Test
	public void prueba14() {
		WebElement registerLink = driver.findElement(By
				.id("form-principal:register"));
		registerLink.click();
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				"Registrar nuevo usuario", 60);
		new PO_RegisterForm().rellenaEntrada(driver, "wrongemail",
				"example.org", "testus3r", "testus3r");

		SeleniumUtils.EsperaCargaPagina(driver, "text",
				"El email tiene que ser <nombre>@<dominio>", 60);
		SeleniumUtils.textoPresentePagina(driver,
				"El email tiene que ser <nombre>@<dominio>");
	}

	// PR15: Crear una cuenta de usuario normal con Password incorrecta.
	@Test
	public void prueba15() {
		WebElement registerLink = driver.findElement(By
				.id("form-principal:register"));
		registerLink.click();
		SeleniumUtils.EsperaCargaPagina(driver, "text",
				"Registrar nuevo usuario", 60);
		new PO_RegisterForm().rellenaEntrada(driver, "wrongpass",
				"wrong@example.org", "con", "con");

		SeleniumUtils
				.EsperaCargaPagina(
						driver,
						"text",
						"La contraseña tiene que contener como minimo 8 caracteres y al menos una letra o un numero",
						60);
		SeleniumUtils
				.textoPresentePagina(
						driver,
						"La contraseña tiene que contener como minimo 8 caracteres y al menos una letra o un numero");
	}

	// USUARIO
	// PR16: Comprobar que en Inbox sólo aparecen listadas las tareas sin
	// categoría y que son las que tienen que ser. Usar paginación navegando por
	// las tres páginas.
	@Test
	public void prueba16() {
		new PO_LoginForm().rellenaEntrada(driver, "user1", "user1");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:listar");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Volver", 60);
		SeleniumUtils.textoPresentePagina(driver, "1 de 3");
		SeleniumUtils.textoNoPresentePagina(driver, "categoria");
		for (int i = 1; i <= 8; i++) {
			SeleniumUtils.textoPresentePagina(driver, "Tarea " + i);
		}
		WebElement pag2 = driver
				.findElements(By.className("ui-paginator-page")).get(1);
		pag2.click();
		SeleniumUtils.EsperaCargaPagina(driver, "text", "2 de", 60);
		SeleniumUtils.textoPresentePagina(driver, "2 de 3");
		SeleniumUtils.textoNoPresentePagina(driver, "categoria");
		for (int i = 9; i <= 16; i++) {
			SeleniumUtils.textoPresentePagina(driver, "Tarea " + i);
		}
		WebElement pag3 = driver
				.findElements(By.className("ui-paginator-page")).get(2);
		pag3.click();
		SeleniumUtils.EsperaCargaPagina(driver, "text", "3 de", 60);
		SeleniumUtils.textoPresentePagina(driver, "3 de 3");
		SeleniumUtils.textoNoPresentePagina(driver, "categoria");
		for (int i = 17; i <= 20; i++) {
			SeleniumUtils.textoPresentePagina(driver, "Tarea " + i);
		}
	}

	// PR17: Funcionamiento correcto de la ordenación por fecha planeada.
	@Test
	public void prueba17() {
		new PO_LoginForm().rellenaEntrada(driver, "user1", "user1");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:listar");

		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver,
				"id", "task_planned", 60);

		// Comprobar al derechas
		WebElement plannedHead = elementos.get(0);
		plannedHead.click();
		// Esperar a que se ordene
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
		}
		By plannedXpath = By.xpath("//table/tbody/tr/td[6]");
		List<WebElement> planned = driver.findElements(plannedXpath);
		List<String> plannedStr = new ArrayList<>();
		for (WebElement webElement : planned) {
			plannedStr.add(webElement.getText());
		}
		List<String> expected = new ArrayList<>(plannedStr);
		Collections.sort(expected);
		for (int i = 0; i < plannedStr.size(); i++) {
			assertEquals(expected.get(i), plannedStr.get(i));
		}

		// Comprobar al reves
		plannedHead.click();
		// Esperar a que se ordene
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
		}
		planned = driver.findElements(plannedXpath);
		plannedStr = new ArrayList<>();
		for (WebElement webElement : planned) {
			plannedStr.add(webElement.getText());
		}
		expected = new ArrayList<>(plannedStr);
		Collections.sort(expected);
		Collections.reverse(expected);
		for (int i = 0; i < plannedStr.size(); i++) {
			assertEquals(expected.get(i), plannedStr.get(i));
		}
	}

	// PR18: Funcionamiento correcto del filtrado.
	@Test
	public void prueba18() {
		new PO_LoginForm().rellenaEntrada(driver, "user1", "user1");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:listar");

		WebElement filter = SeleniumUtils.EsperaCargaPagina(driver, "class",
				"ui-column-filter", 60).get(0);
		filter.sendKeys("20");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Tarea 20", 20);
		SeleniumUtils.textoPresentePagina(driver, "Tarea 20");

		filter.clear();
		filter.sendKeys("15");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Tarea 15", 20);
		SeleniumUtils.textoPresentePagina(driver, "Tarea 15");

		filter.clear();
		filter.sendKeys("No existe esta tarea");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "No hay tareas", 20);
		SeleniumUtils.textoPresentePagina(driver, "No hay tareas");
	}

	// PR19: Funcionamiento correcto de la ordenación por categoría.
	@Test
	public void prueba19() {
		new PO_LoginForm().rellenaEntrada(driver, "user1", "user1");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:listar");

		SeleniumUtils.EsperaCargaPagina(driver, "id", "listDia", 60).get(0)
				.click();
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
		}

		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver,
				"id", "task_category", 60);

		// Comprobar al derechas
		WebElement categoryHead = elementos.get(0);
		categoryHead.click();
		// Esperar a que se ordene
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
		}
		By categoryXpath = By.xpath("//table/tbody/tr/td[3]");
		List<WebElement> categories = driver.findElements(categoryXpath);
		List<String> categoriesStr = new ArrayList<>();
		for (WebElement webElement : categories) {
			categoriesStr.add(webElement.getText());
		}
		List<String> expected = new ArrayList<>(categoriesStr);
		Collections.sort(expected);
		for (int i = 0; i < categoriesStr.size(); i++) {
			assertEquals(expected.get(i), categoriesStr.get(i));
		}

		// Comprobar al reves
		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"task_category", 60);
		categoryHead = elementos.get(0);
		categoryHead.click();

		// Esperar a que se ordene
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
		}
		categories = driver.findElements(categoryXpath);
		categoriesStr = new ArrayList<>();
		for (WebElement webElement : categories) {
			if (StringUtil.isNotBlank(webElement.getText())) {
				categoriesStr.add(webElement.getText());
			}
		}
		expected = new ArrayList<>(categoriesStr);
		Collections.sort(expected);
		Collections.reverse(expected);
		for (int i = 0; i < categoriesStr.size(); i++) {
			assertEquals(expected.get(i), categoriesStr.get(i));
		}
	}

	// PR20: Funcionamiento correcto de la ordenación por fecha planeada.
	@Test
	public void prueba20() {
		new PO_LoginForm().rellenaEntrada(driver, "user1", "user1");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:listar");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Volver", 60);
		WebElement bDia = driver.findElement(By.id("listDia"));
		bDia.click();
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
		}
		List<WebElement> elementos = SeleniumUtils.EsperaCargaPagina(driver,
				"id", "task_planned", 60);

		// Comprobar al derechas
		WebElement plannedHead = elementos.get(0);
		plannedHead.click();
		// Esperar a que se ordene
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
		}
		By plannedXpath = By.xpath("//table/tbody/tr/td[6]");
		List<WebElement> planned = driver.findElements(plannedXpath);
		List<String> plannedStr = new ArrayList<>();
		for (WebElement webElement : planned) {
			plannedStr.add(webElement.getText());
		}
		List<String> expected = new ArrayList<>(plannedStr);
		Collections.sort(expected);
		for (int i = 0; i < plannedStr.size(); i++) {
			assertEquals(expected.get(i), plannedStr.get(i));
		}

		elementos = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"task_planned", 60);

		// Comprobar al derechas
		plannedHead = elementos.get(0);
		// Comprobar al reves
		plannedHead.click();
		// Esperar a que se ordene
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
		}
		planned = driver.findElements(plannedXpath);
		plannedStr = new ArrayList<>();
		for (WebElement webElement : planned) {
			plannedStr.add(webElement.getText());
		}
		expected = new ArrayList<>(plannedStr);
		Collections.sort(expected);
		Collections.reverse(expected);
		for (int i = 0; i < plannedStr.size(); i++) {
			assertEquals(expected.get(i), plannedStr.get(i));
		}
	}

	// PR21: Comprobar que las tareas que no están en rojo son las de hoy y
	// además las que deben ser.
	@Test
	public void prueba21() {
		assertTrue(false);
		// TODO
	}

	// PR22: Comprobar que las tareas retrasadas están en rojo y son las que
	// deben ser.
	@Test
	public void prueba22() {
		new PO_LoginForm().rellenaEntrada(driver, "user1", "user1");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:listar");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Volver", 60);
		WebElement bDia = driver.findElement(By.id("listDia"));
		bDia.click();
		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie", 60);
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e1) {
		}

		By plannedXpath = By.xpath("//table/tbody/tr/td[6]/span");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		List<WebElement> planned = driver.findElements(plannedXpath);
		for (WebElement webElement : planned) {
			assertEquals("rgba(255, 0, 0, 1)", webElement.getCssValue("color"));
			Date actual = null;
			try {
				actual = sdf.parse(webElement.getText());
			} catch (ParseException e) {
				fail("Error parseando la fecha");
			}
			assertTrue(actual.before(new Date()));
		}
		// TODO: igual que el anterior
	}

	// PR23: Comprobar que las tareas de hoy y futuras no están en rojo y que
	// son las que deben ser.
	@Test
	public void prueba23() {
		new PO_LoginForm().rellenaEntrada(driver, "user1", "user1");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:listar");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Volver", 60);
		WebElement bDia = driver.findElement(By.id("listSemana"));
		bDia.click();
		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-pie", 60);
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e1) {
		}
		for (int i = 3; i >= 0; i--) {
			WebElement pag3 = driver.findElements(
					By.className("ui-paginator-page")).get(i);
			pag3.click();
			SeleniumUtils.EsperaCargaPagina(driver, "text", (i + 1) + " de 4",
					60);
			// Sacar planeados
			By trXpath = By.xpath("//table/tbody/tr");
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			List<WebElement> planned = driver.findElements(trXpath);
			By plannedXpath = By.xpath("//td[6]");
			By catXpath = By.xpath("//td[3]/span");
			for (WebElement webElement : planned) {
				Date actual = null;
				try {
					actual = sdf.parse(webElement.findElement(plannedXpath)
							.getText());
				} catch (ParseException e) {
					fail("Error parseando la fecha");
				}
				if (!actual.before(new Date())) {
					assertEquals(
							"rgba(255, 0, 0, 1)",
							webElement.findElement(catXpath).getCssValue(
									"color"));
				}
			}
		}
	}

	// PR24: Funcionamiento correcto de la ordenación por día.
	@Test
	public void prueba24() {
		assertTrue(false);
		// TODO
	}

	// PR25: Funcionamiento correcto de la ordenación por nombre.
	@Test
	public void prueba25() {
		new PO_LoginForm().rellenaEntrada(driver, "user1", "user1");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:listar");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Volver", 60);
		WebElement bSemana = driver.findElement(By.id("listSemana"));
		bSemana.click();
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
		}
		SeleniumUtils.EsperaCargaPagina(driver, "id", "task_title", 60);

		// Comprobar al derechas
		WebElement titleHead = driver.findElements(
				By.xpath("//*[contains(@class, 'ui-column-title')]")).get(1);
		titleHead.click();
		// Esperar a que se ordene
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
		}
		By titleXpath = By.xpath("//table/tbody/tr/td[2]");
		List<WebElement> title = driver.findElements(titleXpath);
		List<String> titleStr = new ArrayList<>();
		for (WebElement webElement : title) {
			titleStr.add(webElement.getText());
		}
		List<String> expected = new ArrayList<>(titleStr);
		Collections.sort(expected);
		for (int i = 0; i < titleStr.size(); i++) {
			assertEquals(expected.get(i), titleStr.get(i));
		}

		// Comprobar al reves
		SeleniumUtils.EsperaCargaPagina(driver, "id", "task_title", 60);
		titleHead = driver.findElements(
				By.xpath("//*[contains(@class, 'ui-column-title')]")).get(1);
		titleHead.click();

		// Esperar a que se ordene
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
		}

		title = driver.findElements(titleXpath);
		titleStr = new ArrayList<>();
		for (WebElement webElement : title) {
			titleStr.add(webElement.getText());
		}
		expected = new ArrayList<>(titleStr);
		Collections.sort(expected);
		Collections.reverse(expected);
		for (int i = 0; i < titleStr.size(); i++) {
			assertEquals(expected.get(i), titleStr.get(i));
		}
	}

	// PR26: Confirmar una tarea, inhabilitar el filtro de tareas terminadas, ir
	// a la pagina donde está la tarea terminada y comprobar que se muestra.
	@Test
	public void prueba26() {
		assertTrue(false);
		// TODO
	}

	// PR27: Crear una tarea sin categoría y comprobar que se muestra en la
	// lista Inbox.
	@Test
	public void prueba27() {
		new PO_LoginForm().rellenaEntrada(driver, "user1", "user1");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:addTarea");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Finalizada", 60);

		new PO_TaskForm().rellenaEntrada(driver, "Esta va para inbox", "",
				"Un comentario", "", "");

		WebElement title = SeleniumUtils.EsperaCargaPagina(driver, "class",
				"title", 60).get(0);
		assertEquals("Inbox", title.getText());

		WebElement filter = SeleniumUtils.EsperaCargaPagina(driver, "class",
				"ui-column-filter", 60).get(0);
		filter.sendKeys("para inbox");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Esta va", 20);
		SeleniumUtils.textoPresentePagina(driver, "Esta va para inbox");
		SeleniumUtils.textoNoPresentePagina(driver, "No hay tareas");
	}

	// PR28: Crear una tarea con categoría categoria1 y fecha planeada Hoy y
	// comprobar que se muestra en la lista Hoy.
	@Test
	public void prueba28() {
		new PO_LoginForm().rellenaEntrada(driver, "user1", "user1");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:addTarea");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Finalizada", 60);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		String hoy = sdf.format(new Date());

		new PO_TaskForm().rellenaEntrada(driver, "Prueba list hoy",
				"category_1", "Un comentario", hoy, "");

		WebElement title = SeleniumUtils.EsperaCargaPagina(driver, "class",
				"title", 60).get(0);
		assertEquals("Dia", title.getText());

		WebElement filter = SeleniumUtils.EsperaCargaPagina(driver, "class",
				"ui-column-filter", 60).get(0);
		filter.sendKeys("Prueba");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "list hoy", 20);
		SeleniumUtils.textoPresentePagina(driver, "Prueba list hoy");
		SeleniumUtils.textoNoPresentePagina(driver, "No hay tareas");
	}

	// PR29: Crear una tarea con categoría categoria1 y fecha planeada posterior
	// a Hoy y comprobar que se muestra en la lista Semana.
	@Test
	public void prueba29() {
		new PO_LoginForm().rellenaEntrada(driver, "user1", "user1");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:addTarea");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Finalizada", 60);

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_MONTH, 3);

		String thisWeek = sdf.format(c.getTime());

		new PO_TaskForm().rellenaEntrada(driver, "Para la semana",
				"category_1", "Un comentario", thisWeek, "");

		WebElement title = SeleniumUtils.EsperaCargaPagina(driver, "class",
				"title", 60).get(0);
		assertEquals("Semana", title.getText());

		WebElement filter = SeleniumUtils.EsperaCargaPagina(driver, "class",
				"ui-column-filter", 60).get(0);
		filter.sendKeys("Para la");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "la semana", 20);
		SeleniumUtils.textoPresentePagina(driver, "Para la semana");
		SeleniumUtils.textoNoPresentePagina(driver, "No hay tareas");
	}

	// PR30: Editar el nombre, y categoría de una tarea (se le cambia a
	// categoría1) de la lista Inbox y comprobar que las tres pseudolista se
	// refresca correctamente.
	@Test
	public void prueba30() {
		new PO_LoginForm().rellenaEntrada(driver, "user1", "user1");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:listar");

		WebElement el = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"task_edit", 60).get(0);

		el.click();

		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-alta:send", 60);

		new PO_TaskForm().rellenaEntrada(driver, "FueraInbox", "category_1",
				"", "", "");

		WebElement filter = SeleniumUtils.EsperaCargaPagina(driver, "class",
				"ui-column-filter", 60).get(0);
		filter.sendKeys("Inbox");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Fuera", 60);
		SeleniumUtils.textoNoPresentePagina(driver, "No hay tareas");
		SeleniumUtils.textoPresentePagina(driver, "FueraInbox");

		WebElement bDia = driver.findElement(By.id("listInbox"));
		bDia.click();
		try {
			Thread.sleep(500L);
		} catch (InterruptedException e) {
		}
		filter = SeleniumUtils.EsperaCargaPagina(driver, "class",
				"ui-column-filter", 60).get(0);
		filter.sendKeys("FueraInbox");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "No hay", 60);
		SeleniumUtils.textoPresentePagina(driver, "No hay tareas");
		SeleniumUtils.textoNoPresentePagina(driver, "FueraInbox");

		WebElement bSemana = driver.findElement(By.id("listSemana"));
		bSemana.click();
		try {
			Thread.sleep(500L);
		} catch (InterruptedException e) {
		}
		filter = SeleniumUtils.EsperaCargaPagina(driver, "class",
				"ui-column-filter", 60).get(0);
		filter.sendKeys("Inbox");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Fuera", 60);
		SeleniumUtils.textoNoPresentePagina(driver, "No hay tareas");
		SeleniumUtils.textoPresentePagina(driver, "FueraInbox");

	}

	// PR31: Editar el nombre, y categoría (Se cambia a sin categoría) de una
	// tarea de la lista Hoy y comprobar que las tres pseudolistas se refrescan
	// correctamente.
	@Test
	public void prueba31() {
		new PO_LoginForm().rellenaEntrada(driver, "user1", "user1");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:listar");

		SeleniumUtils.EsperaCargaPagina(driver, "id", "task_edit", 60).get(0);

		WebElement bDia = driver.findElement(By.id("listDia"));
		bDia.click();
		try {
			Thread.sleep(500L);
		} catch (InterruptedException e) {
		}
		WebElement pag3 = driver
				.findElements(By.className("ui-paginator-page")).get(2);
		pag3.click();
		SeleniumUtils.EsperaCargaPagina(driver, "text", "3 de 3", 60);
		WebElement el = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"task_edit", 60).get(0);

		el.click();

		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-alta:send", 60);

		new PO_TaskForm().rellenaEntrada(driver, "QuitarCategory",
				"category_0", "", "", "");

		WebElement filter = SeleniumUtils.EsperaCargaPagina(driver, "class",
				"ui-column-filter", 60).get(0);
		filter.sendKeys("Quitar");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Category", 60);
		SeleniumUtils.textoNoPresentePagina(driver, "No hay tareas");
		SeleniumUtils.textoPresentePagina(driver, "QuitarCategory");

		WebElement bInbox = driver.findElement(By.id("listInbox"));
		bInbox.click();
		try {
			Thread.sleep(500L);
		} catch (InterruptedException e) {
		}
		filter = SeleniumUtils.EsperaCargaPagina(driver, "class",
				"ui-column-filter", 60).get(0);
		filter.sendKeys("Quitar");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Category", 60);
		SeleniumUtils.textoNoPresentePagina(driver, "No hay tareas");
		SeleniumUtils.textoPresentePagina(driver, "QuitarCategory");

		WebElement bSemana = driver.findElement(By.id("listSemana"));
		bSemana.click();
		try {
			Thread.sleep(500L);
		} catch (InterruptedException e) {
		}
		filter = SeleniumUtils.EsperaCargaPagina(driver, "class",
				"ui-column-filter", 60).get(0);
		filter.sendKeys("Quitar");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Category", 60);
		SeleniumUtils.textoNoPresentePagina(driver, "No hay tareas");
		SeleniumUtils.textoPresentePagina(driver, "QuitarCategory");
	}

	// PR32: Marcar una tarea como finalizada. Comprobar que desaparece de las
	// tres pseudolistas.
	@Test
	public void prueba32() {
		new PO_LoginForm().rellenaEntrada(driver, "user1", "user1");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:listar");

		WebElement el = SeleniumUtils.EsperaCargaPagina(driver, "id",
				"task_edit", 60).get(0);

		el.click();

		SeleniumUtils.EsperaCargaPagina(driver, "id", "form-alta:send", 60);

		WebElement titleE = driver.findElement(By.id("form-alta:titulo"));
		titleE.click();
		titleE.clear();
		titleE.sendKeys("TFinalizada");

		WebElement finalizadaE = driver.findElement(By
				.id("form-alta:finished_input"));
		finalizadaE.click();
		finalizadaE.clear();
		finalizadaE.sendKeys("22/03/2017");

		WebElement send = driver.findElement(By.id("form-alta:send"));
		send.click();

		WebElement filter = SeleniumUtils.EsperaCargaPagina(driver, "class",
				"ui-column-filter", 60).get(0);
		filter.sendKeys("TFinalizada");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "No hay", 60);
		SeleniumUtils.textoPresentePagina(driver, "No hay tareas");
		SeleniumUtils.textoNoPresentePagina(driver, "TFinalizada");

		WebElement bDia = driver.findElement(By.id("listDia"));
		bDia.click();
		try {
			Thread.sleep(500L);
		} catch (InterruptedException e) {
		}
		filter = SeleniumUtils.EsperaCargaPagina(driver, "class",
				"ui-column-filter", 60).get(0);
		filter.sendKeys("TFinalizada");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "No hay", 60);
		SeleniumUtils.textoPresentePagina(driver, "No hay tareas");
		SeleniumUtils.textoNoPresentePagina(driver, "TFinalizada");

		WebElement bSemana = driver.findElement(By.id("listSemana"));
		bSemana.click();
		try {
			Thread.sleep(500L);
		} catch (InterruptedException e) {
		}
		filter = SeleniumUtils.EsperaCargaPagina(driver, "class",
				"ui-column-filter", 60).get(0);
		filter.sendKeys("TFinalizada");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "No hay", 60);
		SeleniumUtils.textoPresentePagina(driver, "No hay tareas");
		SeleniumUtils.textoNoPresentePagina(driver, "TFinalizada");
	}

	// PR33: Salir de sesión desde cuenta de administrador.
	@Test
	public void prueba33() {
		new PO_LoginForm().rellenaEntrada(driver, "admin1", "admin1");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:cerrar");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Exito", 60);
		SeleniumUtils.textoPresentePagina(driver, "Sesion cerrada con exito");
		SeleniumUtils.textoPresentePagina(driver, "Login");
		SeleniumUtils.textoPresentePagina(driver, "Registrarse");
	}

	// PR34: Salir de sesión desde cuenta de usuario normal.
	@Test
	public void prueba34() {
		new PO_LoginForm().rellenaEntrada(driver, "user2", "user2");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:cerrar");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Exito", 60);
		SeleniumUtils.textoPresentePagina(driver, "Sesion cerrada con exito");
		SeleniumUtils.textoPresentePagina(driver, "Login");
		SeleniumUtils.textoPresentePagina(driver, "Registrarse");
	}

	// PR35: Cambio del idioma por defecto a un segundo idioma. (Probar algunas
	// vistas)
	@Test
	public void prueba35() {
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuIdiomas", "form-cabecera:english");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Register", 60);

		SeleniumUtils.textoPresentePagina(driver, "Register");
		SeleniumUtils.textoPresentePagina(driver, "Create notes");
		SeleniumUtils.textoPresentePagina(driver, "User");
		SeleniumUtils.textoPresentePagina(driver, "Password");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Register", 60);
		new PO_LoginForm().rellenaEntrada(driver, "user1", "user1");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Create notes", 60);

		SeleniumUtils.textoPresentePagina(driver, "User Options");

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:listar");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Delete", 60);

		SeleniumUtils.textoPresentePagina(driver, "Day");
		SeleniumUtils.textoPresentePagina(driver, "Week");
		SeleniumUtils.textoPresentePagina(driver, "Category");
		SeleniumUtils.textoPresentePagina(driver, "Created");
	}

	// PR36: Cambio del idioma por defecto a un segundo idioma y vuelta al
	// idioma por defecto. (Probar algunas vistas)
	@Test
	public void prueba36() {
		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuIdiomas", "form-cabecera:english");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Register", 60);

		SeleniumUtils.textoPresentePagina(driver, "Register");
		SeleniumUtils.textoPresentePagina(driver, "Create notes");
		SeleniumUtils.textoPresentePagina(driver, "User");
		SeleniumUtils.textoPresentePagina(driver, "Password");

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuIdiomas", "form-cabecera:spanish");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Registrarse", 60);
		SeleniumUtils.textoPresentePagina(driver, "Registrarse");
		SeleniumUtils.textoPresentePagina(driver, "Crea notas");
		SeleniumUtils.textoPresentePagina(driver, "Usuario");
		SeleniumUtils.textoPresentePagina(driver, "Contraseña");

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuIdiomas", "form-cabecera:english");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Register", 60);
		new PO_LoginForm().rellenaEntrada(driver, "user1", "user1");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Create notes", 60);

		SeleniumUtils.textoPresentePagina(driver, "User Options");

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuIdiomas", "form-cabecera:spanish");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);
		SeleniumUtils.textoPresentePagina(driver, "Opciones de usuario");

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuIdiomas", "form-cabecera:english");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Create notes", 60);

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuOpciones", "form-cabecera:listar");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Delete", 60);

		SeleniumUtils.textoPresentePagina(driver, "Day");
		SeleniumUtils.textoPresentePagina(driver, "Week");
		SeleniumUtils.textoPresentePagina(driver, "Category");
		SeleniumUtils.textoPresentePagina(driver, "Created");

		SeleniumUtils.ClickSubopcionMenuHover(driver,
				"form-cabecera:MenuIdiomas", "form-cabecera:spanish");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Borrar", 60);

		SeleniumUtils.textoPresentePagina(driver, "Dia");
		SeleniumUtils.textoPresentePagina(driver, "Semana");
		SeleniumUtils.textoPresentePagina(driver, "Categoria");
		SeleniumUtils.textoPresentePagina(driver, "Creada");

	}

	// PR37: Intento de acceso a un URL privado de administrador con un usuario
	// autenticado como usuario normal.
	@Test
	public void prueba37() {
		new PO_LoginForm().rellenaEntrada(driver, "user1", "user1");

		SeleniumUtils.EsperaCargaPagina(driver, "text", "Opciones", 60);

		driver.get(baseURL + "/admin/opciones.xhtml");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Login", 60);
		SeleniumUtils.textoPresentePagina(driver, "GTD Task Manager");
		SeleniumUtils.textoPresentePagina(driver, "Login");
	}

	// PR38: Intento de acceso a un URL privado de usuario normal con un usuario
	// no autenticado.
	@Test
	public void prueba38() {
		driver.get(baseURL + "/user/opciones.xhtml");
		SeleniumUtils.EsperaCargaPagina(driver, "text", "Login", 60);
		SeleniumUtils.textoPresentePagina(driver, "GTD Task Manager");
		SeleniumUtils.textoPresentePagina(driver, "Login");
	}

}