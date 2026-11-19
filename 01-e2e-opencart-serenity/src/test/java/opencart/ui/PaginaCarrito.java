package opencart.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class PaginaCarrito {

    public static final Target NOMBRES_PRODUCTOS =
            Target.the("productos del carrito")
                  .located(By.cssSelector("#content form .table-responsive tbody td.text-left > a"));

    public static final Target BOTON_CHECKOUT =
            Target.the("botón Checkout")
                  .locatedBy("//div[contains(@class,'buttons')]//a[normalize-space()='Checkout']");

    private PaginaCarrito() {
    }
}
