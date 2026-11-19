package opencart.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.actions.Open;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import opencart.models.Cliente;
import opencart.questions.MensajeConfirmacion;
import opencart.questions.ProductosEnCarrito;
import opencart.tasks.AgregarAlCarrito;
import opencart.tasks.CompletarCheckoutInvitado;
import opencart.tasks.ConfirmarOrden;
import opencart.tasks.VerCarrito;
import opencart.ui.PaginaInicio;

import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.actors.OnStage.theActorCalled;
import static net.serenitybdd.screenplay.actors.OnStage.theActorInTheSpotlight;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

public class CompraStepDefinitions {

    @Before
    public void prepararEscenario() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Dado("que el cliente navega en la tienda OpenCart")
    public void navegaEnLaTienda() {
        theActorCalled("Cliente").attemptsTo(Open.browserOn().the(PaginaInicio.class));
    }

    @Cuando("agrega los siguientes productos al carrito:")
    public void agregaProductos(DataTable tabla) {
        for (String producto : productosDe(tabla)) {
            theActorInTheSpotlight().attemptsTo(AgregarAlCarrito.elProducto(producto));
        }
    }

    @Y("visualiza el carrito")
    public void visualizaElCarrito() {
        theActorInTheSpotlight().attemptsTo(VerCarrito.completo());
    }

    @Entonces("el carrito contiene los productos:")
    public void elCarritoContiene(DataTable tabla) {
        String[] esperados = productosDe(tabla).toArray(String[]::new);
        theActorInTheSpotlight().should(
                seeThat(ProductosEnCarrito.mostrados(), containsInAnyOrder(esperados)));
    }

    @Cuando("completa el checkout como invitado con los datos:")
    public void completaCheckout(DataTable tabla) {
        Cliente cliente = Cliente.desde(tabla.asMaps().get(0));
        theActorInTheSpotlight().attemptsTo(CompletarCheckoutInvitado.conLosDatos(cliente));
    }

    @Y("confirma la orden")
    public void confirmaLaOrden() {
        theActorInTheSpotlight().attemptsTo(ConfirmarOrden.deCompra());
    }

    @Entonces("debería ver el mensaje {string}")
    public void deberiaVerElMensaje(String mensaje) {
        theActorInTheSpotlight().should(seeThat(MensajeConfirmacion.mostrado(), equalTo(mensaje)));
    }

    private static List<String> productosDe(DataTable tabla) {
        return tabla.asMaps().stream().map((Map<String, String> fila) -> fila.get("producto")).toList();
    }
}
