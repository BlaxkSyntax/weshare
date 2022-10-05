package za.co.wethinkcode.weshare.settle;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import groovy.cli.Option;
import io.javalin.http.Context;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Claim;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;
import za.co.wethinkcode.weshare.app.model.Settlement;

/**
 * Controller for handling calls from form submits for Claims
 */
public class SettlementViewController {
    public static final String HOME_PATH = "/home";
    public static final String EXPENSE_PATH = "/settle";
    public static final String ROOT_PATH = "/settleclaim.html";
    private static Optional<Claim> optional;
    public static void renderSettleClaimForm(Context context){
        Map<String, Object> viewModel = new HashMap<>();

        UUID uuid = UUID.fromString(context.queryParam("claimId"));
        optional = DataRepository.getInstance().getClaim(uuid);
        optional.ifPresent(claim -> {
            viewModel.put("email", claim.getClaimedBy().getEmail());
        });
        context.render("settleclaim.html");
    }

    public static void submitSettlement(Context context) {

        // Map<String, Object> viewModel = new HashMap<>();
        // DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        // double amount = Double.parseDouble((context.formParam("amount")));
        // LocalDate date = LocalDate.parse(context.formParam("due_"), df);
        // String description = context.formParam("description");
        // String email = context.formParam("email");
        // Person person = context.sessionAttribute("user");
        // String paidby = context.formParam("paidby");
        // String id = context.queryParam("claimId");
        
        // if (amount == 0.0 & date ==null & description == null) {
        //     context.redirect(ROOT_PATH);
        //     return;
        // }

        // System.out.println(id +email+paidby+person);
        // System.out.println(DataRepository.INSTANCE.addClaim(claim) ));
        // System.out.println(amount);

        //viewModel.put("claim", claim);
        optional.ifPresent(claim -> {
            claim.settleClaim(claim.getDueDate());
        });
        context.redirect(HOME_PATH);
    }
}