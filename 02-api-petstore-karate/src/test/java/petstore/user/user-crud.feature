@user
Feature: CRUD de usuario en PetStore API
  Como consumidor de la API de PetStore
  Quiero crear, consultar, actualizar y eliminar un usuario
  Para validar el ciclo de vida completo del recurso /user

  Background:
    * url baseUrl
    # PetStore es una API publica con varias instancias: un GET justo despues de
    # un POST/PUT puede devolver datos viejos o 404. Se reintenta en lugar de fallar.
    * configure retry = { count: 5, interval: 2000 }

    # Variables: datos unicos por ejecucion para no chocar con otros usuarios de la API
    * def ts = java.lang.System.currentTimeMillis()
    * def userId = ts
    * def username = 'qa_santiago_' + ts
    * def newUser = read('data/user.json')

  @crud
  Scenario: Ciclo de vida completo de un usuario (crear, buscar, actualizar, buscar, eliminar)

    # ---------------------------------------------------------------
    # CASO 1: Crear un usuario
    #   Entrada: POST /user con el JSON del usuario
    #   Salida esperada: 200 y message = id del usuario creado
    # ---------------------------------------------------------------
    * print 'CASO 1 - ENTRADA:', newUser
    Given path 'user'
    And request newUser
    When method post
    Then status 200
    And match response == { code: 200, type: 'unknown', message: '#string' }
    And match response.message == '' + userId
    * print 'CASO 1 - SALIDA:', response

    # ---------------------------------------------------------------
    # CASO 2: Buscar el usuario creado
    #   Entrada: GET /user/{username}
    #   Salida esperada: 200 y el usuario con los mismos datos enviados
    # ---------------------------------------------------------------
    Given path 'user', username
    And retry until responseStatus == 200
    When method get
    Then status 200
    And match response == newUser
    * print 'CASO 2 - SALIDA:', response

    # ---------------------------------------------------------------
    # CASO 3: Actualizar el nombre y el correo del usuario
    #   Entrada: PUT /user/{username} con firstName y email nuevos
    #   Salida esperada: 200 y message = id del usuario
    # ---------------------------------------------------------------
    * copy updatedUser = newUser
    * set updatedUser.firstName = 'Santiago Actualizado'
    * set updatedUser.email = 'santiago.actualizado.' + ts + '@test.com'
    * print 'CASO 3 - ENTRADA:', updatedUser
    Given path 'user', username
    And request updatedUser
    When method put
    Then status 200
    And match response == { code: 200, type: 'unknown', message: '#string' }
    And match response.message == '' + userId
    * print 'CASO 3 - SALIDA:', response

    # ---------------------------------------------------------------
    # CASO 4: Buscar el usuario actualizado
    #   Entrada: GET /user/{username}
    #   Salida esperada: 200 con firstName y email nuevos; el resto sin cambios
    # ---------------------------------------------------------------
    Given path 'user', username
    And retry until responseStatus == 200 && response.email == updatedUser.email
    When method get
    Then status 200
    And match response.firstName == 'Santiago Actualizado'
    And match response.email == updatedUser.email
    And match response == updatedUser
    * print 'CASO 4 - SALIDA:', response

    # ---------------------------------------------------------------
    # CASO 5: Eliminar el usuario
    #   Entrada: DELETE /user/{username}
    #   Salida esperada: 200 y message = username eliminado
    # ---------------------------------------------------------------
    Given path 'user', username
    And retry until responseStatus == 200
    When method delete
    Then status 200
    And match response == { code: 200, type: 'unknown', message: '#(username)' }
    * print 'CASO 5 - SALIDA:', response

    # ---------------------------------------------------------------
    # Verificacion adicional: el usuario ya no existe
    #   Entrada: GET /user/{username}
    #   Salida esperada: 404 "User not found"
    # ---------------------------------------------------------------
    Given path 'user', username
    And retry until responseStatus == 404
    When method get
    Then status 404
    And match response == { code: 1, type: 'error', message: 'User not found' }

  @negative
  Scenario: Buscar un usuario inexistente devuelve 404
    Given path 'user', 'no_existe_' + ts
    When method get
    Then status 404
    And match response == { code: 1, type: 'error', message: 'User not found' }
