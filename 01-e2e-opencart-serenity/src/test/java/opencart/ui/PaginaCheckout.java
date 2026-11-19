package opencart.ui;

import net.serenitybdd.screenplay.targets.Target;
import org.openqa.selenium.By;

public class PaginaCheckout {

    // Paso 1: opciones de checkout
    public static final Target OPCION_INVITADO =
            Target.the("opción Guest Checkout").located(By.cssSelector("input[name='account'][value='guest']"));
    public static final Target CONTINUAR_CUENTA =
            Target.the("botón Continue (opciones)").located(By.cssSelector("#button-account"));

    // Paso 2: datos de facturación
    public static final Target NOMBRE = Target.the("campo First Name").located(By.cssSelector("#input-payment-firstname"));
    public static final Target APELLIDO = Target.the("campo Last Name").located(By.cssSelector("#input-payment-lastname"));
    public static final Target EMAIL = Target.the("campo E-Mail").located(By.cssSelector("#input-payment-email"));
    public static final Target TELEFONO = Target.the("campo Telephone").located(By.cssSelector("#input-payment-telephone"));
    public static final Target DIRECCION = Target.the("campo Address 1").located(By.cssSelector("#input-payment-address-1"));
    public static final Target CIUDAD = Target.the("campo City").located(By.cssSelector("#input-payment-city"));
    public static final Target CODIGO_POSTAL = Target.the("campo Post Code").located(By.cssSelector("#input-payment-postcode"));
    public static final Target PAIS = Target.the("lista Country").located(By.cssSelector("#input-payment-country"));
    public static final Target DEPARTAMENTO = Target.the("lista Region / State").located(By.cssSelector("#input-payment-zone"));
    public static final Target OPCION_DEPARTAMENTO =
            Target.the("departamento {0}")
                  .locatedBy("//select[@id='input-payment-zone']/option[normalize-space()='{0}']");
    public static final Target CONTINUAR_INVITADO =
            Target.the("botón Continue (datos)").located(By.cssSelector("#button-guest"));

    // Paso 4: método de envío
    public static final Target CONTINUAR_ENVIO =
            Target.the("botón Continue (envío)").located(By.cssSelector("#button-shipping-method"));

    // Paso 5: método de pago
    public static final Target ACEPTAR_TERMINOS =
            Target.the("checkbox Terms & Conditions").located(By.cssSelector("input[name='agree']"));
    public static final Target CONTINUAR_PAGO =
            Target.the("botón Continue (pago)").located(By.cssSelector("#button-payment-method"));

    // Paso 6: confirmación
    public static final Target CONFIRMAR_ORDEN =
            Target.the("botón Confirm Order").located(By.cssSelector("#button-confirm"));

    private PaginaCheckout() {
    }
}
