package za.co.wethinkcode.weshare.expense;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import io.javalin.http.Context;
import za.co.wethinkcode.weshare.app.db.DataRepository;
import za.co.wethinkcode.weshare.app.model.Expense;
import za.co.wethinkcode.weshare.app.model.Person;
import za.co.wethinkcode.weshare.nettexpenses.NettExpensesController;

/**
 * Controller for handling API calls for Expenses
 */
public class CaptureExpenseController {
    
    public static final String PATH = "/expense";
    public static final String PATH2 = "/expenses";
    public static final String ROOT_PATH = "/expenseform.html";

    public static void createExpense(Context context){
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        double amount = Double.parseDouble((context.formParam("amount")));
        LocalDate date = LocalDate.parse(context.formParam("date"), df);
        String description = context.formParam("description");
        Person person = context.sessionAttribute("user");
        if (amount == 0.0 & date ==null & description == null) {
            context.redirect(ROOT_PATH);
            return;
        }

        System.out.println(date);

        DataRepository.getInstance().addExpense(new Expense(amount, date, description, person));
        context.redirect(NettExpensesController.PATH);
    }

    public static void renderExpensePage(Context context){
        Map<String, Object> viewModel = Map.of();

        context.render("expenseform.html", viewModel);
    }
}