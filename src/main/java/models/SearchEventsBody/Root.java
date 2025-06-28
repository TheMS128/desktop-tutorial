package models.SearchEventsBody;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Root {
    private final String dateFrom;
    private final String dateTo;
    private final List<Integer> sportIds;
    private final Integer champId;
}