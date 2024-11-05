package co.edu.udea.qa.storeapi.steps.definitions;

import co.edu.udea.qa.storeapi.tasks.ConnectTo;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;
import net.serenitybdd.screenplay.rest.interactions.Post;
import org.hamcrest.Matchers;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;

public class CreateProductStepDefinition {
    public static final String SUPPLIER_ACTOR = "supplier";
    public static final String PRODUCT_ENDPOINT = "/products";

    public static final String SCENARIO_ONE_GIVEN_STEP = "I'm connected as a supplier";

    //First scenario set
    public static final String SCENARIO_ONE_WHEN_STEP = "I create a new product with the following details";
    public static final String SCENARIO_ONE_THEN_STEP = "the product is successfully created with these details";
    public static final String SCENARIO_ONE_THEN_SHOULD = "Product creation response";


    Actor supplier = Actor.named(SUPPLIER_ACTOR);

    @Before
    public void config() {
        OnStage.setTheStage(new OnlineCast());
        OnStage.theActorCalled(SUPPLIER_ACTOR);
    }

    @Given(SCENARIO_ONE_GIVEN_STEP)
    public void givenStep() {
        supplier.attemptsTo(ConnectTo.service());
    }

    @When(SCENARIO_ONE_WHEN_STEP)
    public void scenarioOneWhenStep(@NotNull DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        Map<String, String> product = data.get(0);
        supplier.attemptsTo(
                Post.to(PRODUCT_ENDPOINT).with(request ->
                        request.relaxedHTTPSValidation()
                                .body(product)
                )
        );
    }

    @Then(SCENARIO_ONE_THEN_STEP)
    public void scenarioOneThenStep(@NotNull DataTable dataTable) {
        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        Map<String, String> product = data.get(0);
        supplier.should(seeThatResponse(SCENARIO_ONE_THEN_SHOULD,
                response ->
                    response.statusCode(200)
                            .body("title", Matchers.equalTo(product.get("title")))
                            .body("price", Matchers.equalTo(product.get("price")))
                            .body("category", Matchers.equalTo(product.get("category")))
                            .body("description", Matchers.equalTo(product.get("description")))
                            .body("image", Matchers.equalTo(product.get("image")))

        ));
    }
}
