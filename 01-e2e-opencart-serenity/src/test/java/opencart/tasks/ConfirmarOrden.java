package opencart.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.waits.WaitUntil;
import opencart.ui.PaginaCheckout;
import opencart.ui.PaginaConfirmacion;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ConfirmarOrden implements Task {

    public static ConfirmarOrden deCompra() {
        return Tasks.instrumented(ConfirmarOrden.class);
    }

    @Override
    @Step("{0} confirma la orden")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Click.on(PaginaCheckout.CONFIRMAR_ORDEN),
                WaitUntil.the(PaginaConfirmacion.INDICADOR_PAGINA_EXITO, isVisible()).forNoMoreThan(20).seconds()
        );
    }
}
