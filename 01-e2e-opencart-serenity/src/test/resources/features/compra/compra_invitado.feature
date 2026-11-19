# language: es
@compra
Característica: Compra de productos como invitado en OpenCart
  Como cliente sin cuenta registrada
  Quiero comprar productos usando el checkout de invitado
  Para recibir mi pedido sin tener que crear una cuenta

  Escenario: Compra de dos productos con Guest Checkout hasta la confirmación
    Dado que el cliente navega en la tienda OpenCart
    Cuando agrega los siguientes productos al carrito:
      | producto |
      | MacBook  |
      | iPhone   |
    Y visualiza el carrito
    Entonces el carrito contiene los productos:
      | producto |
      | MacBook  |
      | iPhone   |
    Cuando completa el checkout como invitado con los datos:
      | nombre   | apellido | email                | telefono  | direccion       | ciudad     | codigoPostal | pais    | departamento |
      | Santiago | Sanchez  | qa.invitado@test.com | 099123456 | Av. 18 de Julio | Montevideo | 11000        | Uruguay | Montevideo   |
    Y confirma la orden
    Entonces debería ver el mensaje "Your order has been placed!"
