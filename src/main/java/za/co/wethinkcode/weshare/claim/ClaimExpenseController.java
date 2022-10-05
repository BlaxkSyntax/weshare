package za.co.wethinkcode.weshare.claim;

import io.javalin.http.Context;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Claim;
import za.co.wethinkcode.weshare.app.model.Expense;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Controller for handling calls from form submits for Claims
 */
public class ClaimExpenseController {

    public static final String PATH = "/claim";

    public static void renderClaimExpensePage(Context context){
        
        Map<String, Object> viewModel = new HashMap<>();
        String id = context.queryParam("expenseId");
        System.out.println(id);

        Optional<Expense> expenses = DataRepository.getInstance().getExpense(UUID.fromString(id));
        Expense expense = expenses.get();
        Optional<Claim> claims = DataRepository.INSTANCE.getClaim(UUID.fromString(id));
        Claim claim = null;

        if(!claims.isEmpty()){
            claim = claims.get();
            System.out.println(claim);
        }
        

        viewModel.put("expense", expense);
        viewModel.put("claim", claim);

        context.render("claimexpense.html", viewModel);
    }
}