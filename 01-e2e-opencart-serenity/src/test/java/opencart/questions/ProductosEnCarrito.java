package opencart.questions;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Question;
import opencart.ui.PaginaCarrito;

import java.util.List;

/** Nombres de los productos que se muestran en el carrito. */
public class ProductosEnCarrito {

    public static Question<List<String>> mostrados() {
        return Question.about("los productos del carrito").answeredBy(actor ->
                PaginaCarrito.NOMBRES_PRODUCTOS.resolveAllFor(actor).stream()
                        .map(WebElementFacade::getText)
                        .map(String::trim)
                        .toList());
    }

    private ProductosEnCarrito() {
    }
}
