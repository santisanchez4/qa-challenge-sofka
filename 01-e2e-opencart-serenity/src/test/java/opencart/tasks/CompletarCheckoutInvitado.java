package opencart.tasks;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.actions.SelectFromOptions;
import net.serenitybdd.screenplay.waits.WaitUntil;
import opencart.models.Cliente;
import opencart.ui.PaginaCarrito;
import opencart.ui.PaginaCheckout;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isClickable;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isPresent;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

/**
 * Recorre el checkout de invitado hasta dejar la orden lista para confirmar.
 * Cada paso del checkout se carga por AJAX, por eso se espera a que el siguiente
 * panel esté visible antes de interactuar.
 */
public class CompletarCheckoutInvitado implements Task {

    private final Cliente cliente;

    public CompletarCheckoutInvitado(Cliente cliente) {
        this.cliente = cliente;
    }

    public static CompletarCheckoutInvitado conLosDatos(Cliente cliente) {
        return Tasks.instrumented(CompletarCheckoutInvitado.class, cliente);
    }

    @Override
    @Step("{0} completa el checkout como invitado")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                // Paso 1: Checkout Options -> Guest Checkout
                Click.on(PaginaCarrito.BOTON_CHECKOUT),
                WaitUntil.the(PaginaCheckout.OPCION_INVITADO, isClickable()).forNoMoreThan(15).seconds(),
                Click.on(PaginaCheckout.OPCION_INVITADO),
                Click.on(PaginaCheckout.CONTINUAR_CUENTA),

                // Paso 2: Billing Details
                WaitUntil.the(PaginaCheckout.NOMBRE, isVisible()).forNoMoreThan(15).seconds(),
                Enter.theValue(cliente.nombre()).into(PaginaCheckout.NOMBRE),
                Enter.theValue(cliente.apellido()).into(PaginaCheckout.APELLIDO),
                Enter.theValue(cliente.email()).into(PaginaCheckout.EMAIL),
                Enter.theValue(cliente.telefono()).into(PaginaCheckout.TELEFONO),
                Enter.theValue(cliente.direccion()).into(PaginaCheckout.DIRECCION),
                Enter.theValue(cliente.ciudad()).into(PaginaCheckout.CIUDAD),
                Enter.theValue(cliente.codigoPostal()).into(PaginaCheckout.CODIGO_POSTAL),
                SelectFromOptions.byVisibleText(cliente.pais()).from(PaginaCheckout.PAIS),
                // Los departamentos se recargan por AJAX al cambiar el país
                WaitUntil.the(PaginaCheckout.OPCION_DEPARTAMENTO.of(cliente.departamento()), isPresent())
                         .forNoMoreThan(15).seconds(),
                SelectFromOptions.byVisibleText(cliente.departamento()).from(PaginaCheckout.DEPARTAMENTO),
                Click.on(PaginaCheckout.CONTINUAR_INVITADO),

                // Paso 3 (Delivery Details) se omite: la dirección de envío es la misma que la de facturación
                // Paso 4: Delivery Method
                WaitUntil.the(PaginaCheckout.CONTINUAR_ENVIO, isClickable()).forNoMoreThan(15).seconds(),
                Click.on(PaginaCheckout.CONTINUAR_ENVIO),

                // Paso 5: Payment Method
                WaitUntil.the(PaginaCheckout.ACEPTAR_TERMINOS, isClickable()).forNoMoreThan(15).seconds(),
                Click.on(PaginaCheckout.ACEPTAR_TERMINOS),
                Click.on(PaginaCheckout.CONTINUAR_PAGO),

                // Paso 6: Confirm Order (queda listo para confirmar)
                WaitUntil.the(PaginaCheckout.CONFIRMAR_ORDEN, isClickable()).forNoMoreThan(15).seconds()
        );
    }
}
