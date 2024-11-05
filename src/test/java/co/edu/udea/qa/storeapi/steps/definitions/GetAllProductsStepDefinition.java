package co.edu.udea.qa.storeapi.steps.definitions;

import co.edu.udea.qa.storeapi.tasks.ConnectTo;
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

import static net.serenitybdd.rest.SerenityRest.lastResponse;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

public class GetAllProductsStepDefinition {
    public static final String CUSTOMER_ACTOR = "customer";
    public static final String PRODUCT_ENDPOINT = "/products";
    public static final String LIMIT_QUERY = "?limit=";

    public static final String GIVEN_STEP = "I connect to the service";

    //First scenario set
    public static final String SCENARIO_ONE_WHEN_STEP = "I get information about all available products";
    public static final String SCENARIO_ONE_THEN_STEP = "I can see a list of products with their information";

    // Second scenario set
    public static final String SCENARIO_TWO_WHEN_STEP = "I request a list of products with a limit of {int}";
    public static final String SCENARIO_TWO_THEN_STEP = "I receive a list of products with exactly {int} items";
    public static final String SCENARIO_TWO_THEN_SHOULD = "the product list size";


    Actor customer = Actor.named(CUSTOMER_ACTOR);

    @Before
    public void config() {
        OnStage.setTheStage(new OnlineCast());
        OnStage.theActorCalled(CUSTOMER_ACTOR);
    }

    @Given(GIVEN_STEP)
    public void givenStep() {
        customer.attemptsTo(ConnectTo.service());
    }

    @When(SCENARIO_ONE_WHEN_STEP)
    public void scenarioOneWhenStep() {
        customer.attemptsTo(
                Get.resource(PRODUCT_ENDPOINT).with(
                        RequestSpecification::relaxedHTTPSValidation
                )
        );
    }

    @Then(SCENARIO_ONE_THEN_STEP)
    public void scenarioOneThenStep() {
        customer.should(seeThatResponse(response ->
                response.statusCode(200)
                        .body("[0].title", Matchers.equalTo("Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops"))
                        .body("[0].price", Matchers.equalTo(109.95F))
                        .body("[0].category", Matchers.equalTo("men's clothing"))
        ));
    }

    @When(SCENARIO_TWO_WHEN_STEP)
    public void scenarioTwoWhenStep(int limit) {
        customer.attemptsTo(
                Get.resource(String.format("%s%s%s", PRODUCT_ENDPOINT, LIMIT_QUERY, limit)).with(
                        RequestSpecification::relaxedHTTPSValidation
                )
        );
    }

    @Then(SCENARIO_TWO_THEN_STEP)
    public void scenarioTwoThenStep(int limit) {
        List<Object> products = lastResponse().jsonPath().getList("$");
        customer.should(seeThat(SCENARIO_TWO_THEN_SHOULD, act -> products.size(), Matchers.equalTo(limit)));
    }
}
