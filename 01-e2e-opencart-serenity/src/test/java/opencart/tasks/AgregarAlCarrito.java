package opencart.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import opencart.ui.PaginaInicio;
import opencart.ui.PaginaProducto;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.containsText;

/** Va a la página del producto desde el inicio y lo agrega al carrito. */
public class AgregarAlCarrito implements Task {

    private final String producto;

    public AgregarAlCarrito(String producto) {
        this.producto = producto;
    }

    public static AgregarAlCarrito elProducto(String producto) {
        return Tasks.instrumented(AgregarAlCarrito.class, producto);
    }

    @Override
    @Step("{0} agrega el producto #producto al carrito")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(PaginaInicio.LOGO),
                Click.on(PaginaInicio.PRODUCTO.of(producto)),
                Click.on(PaginaProducto.BOTON_AGREGAR),
                // El alta es por AJAX: se espera la confirmación antes de seguir
                WaitUntil.the(PaginaProducto.MENSAJE_EXITO, containsText(producto)).forNoMoreThan(15).seconds()
        );
    }
}
