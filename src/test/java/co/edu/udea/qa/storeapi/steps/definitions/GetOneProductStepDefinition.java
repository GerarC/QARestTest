package co.edu.udea.qa.storeapi.steps.definitions;

import co.edu.udea.qa.storeapi.tasks.ConnectTo;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.interactions.Get;
import org.hamcrest.Matchers;

import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

public class GetOneProductStepDefinition {
    public static final String CUSTOMER_ACTOR = "customer";
    public static final String PRODUCT_ENDPOINT = "/products";

    public static final String SCENARIO_ONE_GIVEN_STEP = "I'm connected as a customer";
    public static final String SCENARIO_ONE_WHEN_STEP = "I request information about product with id {int}";

    //First scenario set
    public static final String SCENARIO_ONE_THEN_STEP = "I can see information of the product";
    public static final String SCENARIO_ONE_THEN_SHOULD = "The information of the product";

    Actor customer = Actor.named(CUSTOMER_ACTOR);

    @Before
    public void config() {
        OnStage.setTheStage(new OnlineCast());
        OnStage.theActorCalled(CUSTOMER_ACTOR);
    }

    @Given(SCENARIO_ONE_GIVEN_STEP)
    public void givenStep() {
        customer.attemptsTo(ConnectTo.service());
    }

    @When(SCENARIO_ONE_WHEN_STEP)
    public void whenStep(Integer productId) {
        customer.attemptsTo(
                Get.resource(String.format("%s/%s", PRODUCT_ENDPOINT, productId)).with(
                        RequestSpecification::relaxedHTTPSValidation
                )
        );
    }

    @Then(SCENARIO_ONE_THEN_STEP)
    public void scenarioOneThenStep( DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        Map<String, String> product = data.get(0);
        customer.should(seeThatResponse(SCENARIO_ONE_THEN_SHOULD, response ->
                response.statusCode(200)
                        .body("title", Matchers.equalTo(product.get("title")))
                        .body("category", Matchers.equalTo(product.get("category")))
                        .body("description", Matchers.equalTo(product.get("description")))
                        .body("image", Matchers.equalTo(product.get("image")))
                ));
    }

}
