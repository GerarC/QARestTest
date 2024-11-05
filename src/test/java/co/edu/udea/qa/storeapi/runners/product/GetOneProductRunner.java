package co.edu.udea.qa.storeapi.runners.product;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "src/test/resources/features/get_one_product_info.feature",
        glue = "co.edu.udea.qa.storeapi.steps.definitions",
        snippets = CucumberOptions.SnippetType.CAMELCASE
)
public class GetOneProductRunner {
}
