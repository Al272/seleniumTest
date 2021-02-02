import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class testRGS {
    private WebDriver driver;
    private WebDriverWait wait;
    private ChromeOptions options;

    @Before

    public void before() {
        options = new ChromeOptions();
        options.addArguments("--disable-notifications");
        System.setProperty("webdriver.chrome.driver", "webDriver/chromedriver.exe");
        driver =new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10, 2000);

        String baseUrl = "https://www.rgs.ru/";
        driver.get(baseUrl);
    }

    @Test
    public void exampleScenario() throws InterruptedException {

        // выбрать пункт "Меню"
        String MenuButtonXPath = "//a[contains (text(), 'Меню') and @data-toggle=\"dropdown\"]";
        WebElement menuButton = driver.findElement(By.xpath(MenuButtonXPath));
        waitUtilElementToBeClickable(menuButton);
        menuButton.click();

        // выбрать пункт "Компаниям"
        String forCompaniesButtonXPath = "//a[contains(text(), 'Компаниям')]";
        WebElement forCompaniesButton = driver.findElement(By.xpath(forCompaniesButtonXPath));
        waitUtilElementToBeClickable(forCompaniesButton);
        forCompaniesButton.click();
        // проверка открытия страницы "Компаниям"
        Assert.assertEquals("Заголовок отсутствует/не соответствует требуемому",
                "Корпоративное страхование для компаний | Росгосстрах", driver.getTitle());
        System.out.println(driver.getTitle());

        // выбрать пункт "Здоровье"
        String HealthButtonXPath = "//a[contains (text(), 'Здоровье') and @class= \"list-group-item adv-analytics-navigation-line4-link\"]";
        WebElement HealthButton = driver.findElement(By.xpath(HealthButtonXPath));
        waitUtilElementToBeClickable(HealthButton);
        HealthButton.click();
        // проверка открытия страницы "Здоровье"
        Assert.assertEquals("Заголовок отсутствует/не соответствует требуемому",
                "ДМС для сотрудников - добровольное медицинское страхование от Росгосстраха", driver.getTitle());
        System.out.println(driver.getTitle());

        // выбрать пункт "Добровольное медицинское страхование"
        String DMSButtonXPath = "//a[contains(text(), 'Добровольное медицинское страхование')]";
        WebElement DMSButton = driver.findElement(By.xpath(DMSButtonXPath));
        waitUtilElementToBeClickable(DMSButton);
        DMSButton.click();
        // проверка открытия страницы "Добровольное медицинское страхование"
        Assert.assertEquals("Заголовок отсутствует/не соответствует требуемому",
                "Добровольное медицинское страхование в Росгосстрахе", driver.getTitle());
        System.out.println(driver.getTitle());

        // нажать кнопку "Отправить заявку"
        String ApplicationButtonXPath = "//a[contains(text(), 'Отправить заявку')]";
        WebElement ApplicationButton = driver.findElement(By.xpath(ApplicationButtonXPath));
        waitUtilElementToBeClickable(ApplicationButton);
        ApplicationButton.click();

        // проверка открытия страницы "Заявка на добровольное медицинское страховане"
        String pageTitleXPath = "//b[contains(text(), 'Заявка на добровольное медицинское страхование')]";
        waitUtilElementToBeVisible(By.xpath(pageTitleXPath));
        WebElement pageTitle = driver.findElement(By.xpath(pageTitleXPath));
        Assert.assertEquals("Заголовок отсутствует/не соответствует требуемому",
                "Заявка на добровольное медицинское страхование", pageTitle.getText());
        System.out.println(pageTitle.getText());

        // заполнить поля данными
        String fieldXPath = "//input[@name='%s']";
        fillInputField(driver.findElement(By.xpath(String.format(fieldXPath, "LastName"))), "Сидоров");
        fillInputField(driver.findElement(By.xpath(String.format(fieldXPath, "FirstName"))), "Степан");
        fillInputField(driver.findElement(By.xpath(String.format(fieldXPath, "MiddleName"))), "Иванович");

        String regionButtonXPath = "//select[@name=\"Region\"]";
        WebElement regionButton = driver.findElement(By.xpath(regionButtonXPath));
        regionButton.click();
        Select select = new Select(driver.findElement(By.xpath(regionButtonXPath)));
        select.selectByVisibleText("Москва");
        regionButton.click();

        fillInputField(driver.findElement(By.xpath(String.format("//label[contains(text(), 'Телефон')]/following-sibling::input"))), "+7 (777) 777-77-77");
        fillInputField(driver.findElement(By.xpath(String.format(fieldXPath, "Email"))), "qwertyqwerty");
        fillInputField(driver.findElement(By.xpath(String.format(fieldXPath, "ContactDate"))), "19.02.2021");

        driver.findElement(By.xpath("//label[contains(text(), 'Предпочитаемая дата контакта')]")).click();

        fillInputField(driver.findElement(By.xpath("//textarea[@name=\"Comment\"]")), "Какие-то комментарии");

        String checkBoxXPath = "//input[@class=\"checkbox\"]";
        WebElement checkBox = driver.findElement(By.xpath(checkBoxXPath));
        checkBox.click();

        String passButtonXPath = "//button[@id=\"button-m\"]";
        WebElement passButton = driver.findElement(By.xpath(passButtonXPath));
        waitUtilElementToBeClickable(passButton);
        passButton.click();

        // проверить сообщение об ошибке
        checkErrorMessageAtField(driver.findElement(By.xpath(String.format(fieldXPath, "Email"))), "Введите адрес электронной почты");


        // проверить сообщение об ошибке
        String errorAlertXPath = "//span[@class=\"validation-error-text\"]";
        WebElement errorAlert = driver.findElement(By.xpath(errorAlertXPath));
        scrollToElementJs(errorAlert);
        waitUtilElementToBeVisible(errorAlert);
        Assert.assertEquals("Проверка ошибки у alert на странице не была пройдено",
                "Введите адрес электронной почты", errorAlert.getText());
    }

    @After
    public void after(){
        driver.quit();
    }

   private void scrollToElementJs(WebElement element) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    private void waitUtilElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    private void waitUtilElementToBeVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private void waitUtilElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

    private void fillInputField(WebElement element, String value) {
        scrollToElementJs(element);
        waitUtilElementToBeClickable(element);
        element.click();
        element.sendKeys(value);
        Assert.assertEquals("Поле было заполнено некорректно",
                value, element.getAttribute("value"));
    }

    private void checkErrorMessageAtField(WebElement element, String errorMessage) {
        element = element.findElement(By.xpath("./..//span"));
        Assert.assertEquals("Проверка ошибки у поля не была пройдена",
                errorMessage, element.getText());
    }
}
