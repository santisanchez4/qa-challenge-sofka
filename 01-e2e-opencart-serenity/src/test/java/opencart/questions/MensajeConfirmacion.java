package opencart.questions;

import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import opencart.ui.PaginaConfirmacion;

/** Título que muestra la tienda al finalizar la compra. */
public class MensajeConfirmacion {

    public static Question<String> mostrado() {
        return Text.of(PaginaConfirmacion.TITULO).describedAs("el mensaje de confirmación");
    }

    private MensajeConfirmacion() {
    }
}
