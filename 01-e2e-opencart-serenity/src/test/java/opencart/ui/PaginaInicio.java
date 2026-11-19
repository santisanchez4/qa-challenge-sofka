package opencart.ui;

import net.serenitybdd.annotations.DefaultUrl;
import net.serenitybdd.core.pages.PageObject;
import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

@DefaultUrl("http://opencart.abstracta.us/")
public class PaginaInicio extends PageObject {

    public static final Target LOGO =
            Target.the("logo de la tienda").located(By.cssSelector("#logo a"));

    public static final Target PRODUCTO =
            Target.the("producto {0}")
                  .locatedBy("//div[contains(@class,'product-thumb')]//h4/a[normalize-space()='{0}']");

    public static final Target ENLACE_CARRITO =
            Target.the("enlace Shopping Cart").located(By.cssSelector("a[title='Shopping Cart']"));
}
