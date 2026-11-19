package opencart.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import opencart.ui.PaginaCarrito;
import opencart.ui.PaginaInicio;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class VerCarrito implements Task {

    public static VerCarrito completo() {
        return Tasks.instrumented(VerCarrito.class);
    }

    @Override
    @Step("{0} abre el carrito de compras")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(PaginaInicio.ENLACE_CARRITO),
                WaitUntil.the(PaginaCarrito.BOTON_CHECKOUT, isVisible()).forNoMoreThan(15).seconds()
        );
    }
}
