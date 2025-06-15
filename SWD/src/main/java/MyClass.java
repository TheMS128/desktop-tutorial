import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;

public class MyClass {
    public static void main(String[] args) {
        WebDriver webDriver = new ChromeDriver();

        webDriver.get("https://sb2clientstatic-altenar2-stage.biahosted.com/?integration=skintest&culture=en-en#/");

        WebElement element = webDriver.findElement(By.xpath("//*[@id=\"sb\"]/div[2]/div[2]/div[2]/div[5]/div[2]/div/div/div[2]/div[1]/div"));
        System.out.println(element);
        webDriver.quit();
    }
}
