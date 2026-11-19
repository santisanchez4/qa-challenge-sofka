package opencart.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class PaginaConfirmacion {

    public static final Target TITULO =
            Target.the("título de confirmación").located(By.cssSelector("#content h1"));

    // Solo existe en la página de éxito. El checkout también tiene un h1 ("Checkout"),
    // así que esperar por el título no garantiza que la redirección ya ocurrió.
    public static final Target INDICADOR_PAGINA_EXITO =
            Target.the("breadcrumb de la página de éxito")
                  .located(By.cssSelector("ul.breadcrumb a[href*='checkout/success']"));

    private PaginaConfirmacion() {
    }
}
