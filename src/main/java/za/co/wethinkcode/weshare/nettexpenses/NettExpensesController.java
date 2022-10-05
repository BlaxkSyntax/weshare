package za.co.wethinkcode.weshare.nettexpenses;

import com.google.common.collect.ImmutableList;
import io.javalin.http.Context;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Claim;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class NettExpensesController {
    public static final String PATH = "/home";

    public static void renderHomePage(Context context){
        Map<String, Object> viewModel = new HashMap<>();
        Map<String, Object> username = context.sessionAttributeMap();

        Person userId = (Person) username.get("user");

        Person user = DataRepository.getInstance().findPerson(userId.getEmail()).get();
        //String id = context.queryParam("claimId");
        ImmutableList<Expense> expenses = DataRepository.getInstance().getExpenses(user);
        ImmutableList<Claim> claims = DataRepository.getInstance().getClaimsBy(user, true);
        ImmutableList<Claim> claimFrom = DataRepository.getInstance().findUnsettledClaimsClaimedFrom(user);

        viewModel.put("expenses", expenses);
        viewModel.put("claims", claims);
        viewModel.put("claimes", claimFrom);

        context.render("home.html", viewModel);
    }
}