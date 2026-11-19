package opencart.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class PaginaProducto {

    public static final Target BOTON_AGREGAR =
            Target.the("botón Add to Cart").located(By.cssSelector("#button-cart"));

    public static final Target MENSAJE_EXITO =
            Target.the("mensaje de producto agregado").located(By.cssSelector(".alert-success"));

    private PaginaProducto() {
    }
}
