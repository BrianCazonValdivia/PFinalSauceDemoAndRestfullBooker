package stepDefinitions;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import constants.BookingEndpoints;
import entities.Booking;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en_scouse.An;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import utils.Request;

import java.util.List;

public class BookingSteps {
    Response response;

    //Primer Test
    @Given("Realizo una llamada GET al endpoint de las reservas")
    public void getAllBookings() {
        response = Request.get(BookingEndpoints.Get_BookingIds);
    }

    @Then("Verifico que el estado sea {int}")
    public void verifyStatusCode(int statusCode) {
        response.then().assertThat().statusCode(statusCode);
    }

    @And("Verifico que el cuerpo no tenga tamaño {int}")
    public void verifyResponseSize(int size) {
        response.then().assertThat().body("size()", Matchers.not(size));
    }

    //Segundo Test

    Booking booking;
    @Given("Realizo una llamada GET al endpoint de las reservas con id {int}")
    public void getBookingById(int id) {
        response = Request.getById(BookingEndpoints.Get_Booking, String.valueOf(id));
    }

    @Then("El nombre del cliente es {string}")
    public void verifyCustomerFirstname(String firstname) {
        response.then().assertThat().body("firstname", Matchers.equalTo(firstname));
    }

    @And("El apellido del cliente es {string}")
    public void verifyCustomerLastname(String lastname) {
        response.then().assertThat().body("lastname", Matchers.equalTo(lastname));
    }

    @And("El precio total es {int}")
    public void verifyTotalPrice(int totalprice) {
        response.then().assertThat().body("totalprice", Matchers.equalTo(totalprice));
    }

    @And("La reserva fue pagada {string}")
    public void verifyDepositPaid(boolean depositpaid) {
        response.then().assertThat().body("depositpaid", Matchers.equalTo(depositpaid));
    }

    @And("Las fechas de la reserva son check-in {string} y check-out {string}")
    public void verifyBookingDates(String checkin, String checkout) {
        response.then().assertThat().body("bookingdates.checkin", Matchers.equalTo(checkin));
        response.then().assertThat().body("bookingdates.checkout", Matchers.equalTo(checkout));
    }

    //Test 3

    @Given("Realizo una llamada GET con id inexistente {int}")
    public void getNonExistentBookingById(int id) {
        response = Request.getById(BookingEndpoints.Get_Booking, String.valueOf(id));
    }

    @And("Verifico que el mensaje de error sea {string}")
    public void verifyErrorMessage(String message) {
        response.then().assertThat().body(Matchers.equalTo(message));
    }

    //Test 4

    @Dado("Realizo una llamada POST al endpoint de crear reserva con datos vacíos")
    public void postEmptyBooking() {
        String emptyPayload = """
        {
            "firstname" : "",
            "lastname" : "",
            "totalprice" : 0,
            "depositpaid" : true,
            "bookingdates" : {
                "checkin" : "",
                "checkout" : ""
            },
            "additionalneeds" : ""
        }
        """;

        response = Request.post(BookingEndpoints.Create_Booking, emptyPayload);
    }
    //Test 5
    @And("Realizo una llamada POST al endpoint de crear reserva con los siguientes datos")
    public void postBooking(io.cucumber.datatable.DataTable bookingInfo) throws JsonProcessingException {
        // data = {"Kevinnn", "Valdiviaa", "999", "true", "2018-01-01", "2019-01-01", "PenHouse"}
        List<String> data = bookingInfo.transpose().asList(String.class);

        // Crear el objeto Booking y asignar los valores
        Booking booking = new Booking();
        booking.setFirstname(data.get(0));
        booking.setLastname(data.get(1));
        booking.setTotalprice(Integer.parseInt(data.get(2)));
        booking.setDepositpaid(Boolean.parseBoolean(data.get(3)));

        // Asignar las fechas de check-in y check-out
        Booking.BookingDates bookingDates = new Booking.BookingDates();
        bookingDates.setCheckin(data.get(4));
        bookingDates.setCheckout(data.get(5));
        booking.setBookingdates(bookingDates);

        // Asignar necesidades adicionales
        booking.setAdditionalneeds(data.get(6));

        // Convertir el objeto Booking a JSON
        ObjectMapper mapper = new ObjectMapper();
        String bookingPayload = mapper.writeValueAsString(booking);

        // Enviar la solicitud POST
        response = Request.post(BookingEndpoints.Create_Booking, bookingPayload);
    }

    @And("Verifico los siguientes datos en la respuesta")
    public void verifyBookingData(io.cucumber.datatable.DataTable bookingInfo) throws JsonProcessingException {
        // data = {"Kevinnn", "Valdiviaa", "999", "true", "2018-01-01", "2019-01-01", "PenHouse"}
        List<String> data = bookingInfo.transpose().asList(String.class);

        // Verificar que los campos en la respuesta coincidan con los datos esperados
        response.then().assertThat().body("booking.firstname", Matchers.equalTo(data.get(0)));
        response.then().assertThat().body("booking.lastname", Matchers.equalTo(data.get(1)));
        response.then().assertThat().body("booking.totalprice", Matchers.equalTo(Integer.valueOf(data.get(2))));
        response.then().assertThat().body("booking.depositpaid", Matchers.equalTo(Boolean.valueOf(data.get(3))));
        response.then().assertThat().body("booking.bookingdates.checkin", Matchers.equalTo(data.get(4)));
        response.then().assertThat().body("booking.bookingdates.checkout", Matchers.equalTo(data.get(5)));
        response.then().assertThat().body("booking.additionalneeds", Matchers.equalTo(data.get(6)));
    }



}
