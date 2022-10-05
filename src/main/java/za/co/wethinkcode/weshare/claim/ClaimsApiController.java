package za.co.wethinkcode.weshare.claim;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import io.javalin.http.Context;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Claim;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;

/**
 * Controller for handling API calls for Claims
 */
public class ClaimsApiController {
    
    public static final String PATH = "/claim";
    public static final String PATH2 = "/claims";
    public static final String ROOT_PATH = "/claimexpense.html";

    public static void create(Context context) {
        Map<String, Object> viewModel = new HashMap<>();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        double amount = Double.parseDouble((context.formParam("amount")));
        LocalDate date = LocalDate.parse(context.formParam("date"), df);//context.formParam("date"));
        String description = context.formParam("description");
        String paidby = context.formParam("paidby");
        String id = context.queryParam("expenseId");
        Person person = context.sessionAttribute("user");
        System.out.println(id);

        Optional<Expense> expenses = DataRepository.getInstance().getExpense(UUID.fromString(id));
        Expense expense = expenses.get();
        Optional<Claim> claims = DataRepository.INSTANCE.getClaim(UUID.fromString(id));
        Claim claim = null;

        if(!claims.isEmpty()){
            claim = claims.get();
            System.out.println(claim);
        }

        if (amount == 0.0 & date ==null & description == null& id == null & paidby == null) {
            context.redirect(ROOT_PATH);
            return;
        }

        System.out.println(DataRepository.INSTANCE.addClaim(new Claim(expense, person, amount, LocalDate.parse(context.formParam("date"), df))));
        System.out.println(amount);
        
        viewModel.put("claim", claim);
        viewModel.put("expense", expense);
        context.render("claimexpense.html", viewModel);
    }
}