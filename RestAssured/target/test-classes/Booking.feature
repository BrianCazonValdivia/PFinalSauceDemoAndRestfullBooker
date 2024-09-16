Feature: Booking enpoint
  Background: Los endpoints de reservas permiten obtener, crear, actualizar y eliminar reservas.

  @run
  Scenario: /booking Obtener todas las reservas
      Given Realizo una llamada GET al endpoint de las reservas
      Then Verifico que el estado sea 200
      And Verifico que el cuerpo no tenga tamaño 0
  @rune
  Scenario: Obtener una reserva por ID
    Given Realizo una llamada GET al endpoint de las reservas con id 7
    Then Verifico que el estado sea 200
    And El nombre del cliente es "Sally"
    And El apellido del cliente es "Brown"
    And El precio total es 994
    And La reserva fue pagada "true"
    And Las fechas de la reserva son check-in "2015-07-20" y check-out "2018-10-19"
  @runa
  Scenario: Obtener una reserva inexistente
    Given Realizo una llamada GET con id inexistente 999999999
    Then Verifico que el estado sea 404
    And Verifico que el mensaje de error sea "Not Found"
  @rut
  Scenario: Crear una reserva con datos vacíos
    Given Realizo una llamada POST al endpoint de crear reserva con datos vacíos
    Then Verifico que el estado sea 400
    And Verifico que el mensaje de error sea "Bad Request"
  @test
  Scenario: Crear una nueva reserva
    Given Realizo una llamada POST al endpoint de crear reserva con los siguientes datos
      | Kevinnn   | Valdiviaa | 999 | true | 2018-01-01 | 2019-01-01 | PenHouse |
    Then Verifico que el estado sea 200
    And Verifico que el cuerpo no tenga tamaño 0
    And Verifico los siguientes datos en la respuesta
      | Kevinnn   | Valdiviaa | 999 | true | 2018-01-01 | 2019-01-01 | PenHouse |



