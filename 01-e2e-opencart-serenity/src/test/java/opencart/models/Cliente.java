package opencart.models;

import java.util.Map;

/** Datos del cliente invitado que completa el checkout. */
public record Cliente(
        String nombre,
        String apellido,
        String email,
        String telefono,
        String direccion,
        String ciudad,
        String codigoPostal,
        String pais,
        String departamento) {

    public static Cliente desde(Map<String, String> fila) {
        return new Cliente(
                fila.get("nombre"),
                fila.get("apellido"),
                fila.get("email"),
                fila.get("telefono"),
                fila.get("direccion"),
                fila.get("ciudad"),
                fila.get("codigoPostal"),
                fila.get("pais"),
                fila.get("departamento"));
    }
}
