package pl.dawidkaszuba.homebudget.model.dto.report;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import pl.dawidkaszuba.homebudget.model.db.CategoryType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class ReportFilterDto {

    private CategoryType categoryType;
    private List<Long> categoryIds;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate from;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate to;

    public List<Long> getCategoryIds() {
        return Objects.requireNonNullElseGet(this.categoryIds, ArrayList::new);
    }
}
