package pl.dawidkaszuba.homebudget.service.impl;

import com.lowagie.text.Document;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.rtf.RtfWriter2;
import com.lowagie.text.rtf.style.RtfFont;
import org.springframework.stereotype.Service;
import pl.dawidkaszuba.homebudget.model.Expense;
import pl.dawidkaszuba.homebudget.model.Income;
import pl.dawidkaszuba.homebudget.model.ReportType;
import pl.dawidkaszuba.homebudget.service.ExpenseService;
import pl.dawidkaszuba.homebudget.service.IncomeService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

@Service
public class RtfReportServiceImpl {

    private final IncomeService incomeService;
    private final ExpenseService expenseService;

    public RtfReportServiceImpl(IncomeService incomeService, ExpenseService expenseService) {
        this.incomeService = incomeService;
        this.expenseService = expenseService;
    }

    public void generateReport(ReportType reportType, String userName) throws FileNotFoundException {

        final File outputFile = new File("target/" + reportType + "_" + userName + ".rtf");

        Document document = new Document(PageSize.A4);
        RtfWriter2 rtf = RtfWriter2.getInstance(document, new FileOutputStream(outputFile));
        document.open();
        document.setMargins(50, 50, 50, 50);

        document.add(new Paragraph(reportType.equals(ReportType.INCOMES_REPORT) ? "Raport wpływów" : "Raport wydatków", new RtfFont("SansSerif", 14, RtfFont.BOLD)));

        RtfFont headerFont = new RtfFont("SansSerif", 10, RtfFont.BOLD);
        RtfFont tableFont = new RtfFont("SansSerif", 9);
        int[] aWidths = {30,30,30,30};
        Table table = new Table(4);
        table.setWidth(120);
        table.setWidths(aWidths);
        table.addCell(new Phrase("data utworzenia", headerFont));
        table.addCell(new Phrase("data ostatniej edycji", headerFont));
        table.addCell(new Phrase("kategoria", headerFont));
        table.addCell(new Phrase("wartość", headerFont));

        if (reportType.equals("INCOMES_REPORT")) {

            List<Income> incomes = incomeService.getAllIncomesByUser(userName);

            incomes.forEach(income -> {
                table.addCell(new Phrase(income.getCreationTime().toString(), tableFont));
                table.addCell(new Phrase(income.getLastEditTime().toString(), tableFont));
                table.addCell(new Phrase(income.getCategory().getName(), tableFont));
                table.addCell(new Phrase(String.valueOf(income.getValue()), tableFont));
            });

            document.add(table);
        } else {
            List<Expense> expenses = expenseService.getAllExpensesByBudgetUser(userName);

            expenses.forEach(expense -> {
                table.addCell(new Phrase(expense.getCreationTime().toString(), tableFont));
                table.addCell(new Phrase(expense.getLastEditTime().toString(), tableFont));
                table.addCell(new Phrase(expense.getCategory().getName(), tableFont));
                table.addCell(new Phrase(String.valueOf(expense.getValue()), tableFont));
            });

            document.add(table);
        }

        rtf.close();
    }
}
