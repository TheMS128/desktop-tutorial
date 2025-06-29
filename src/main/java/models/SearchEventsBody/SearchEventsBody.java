package models.SearchEventsBody;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class SearchEventsBody {
    @JsonProperty("DateFrom")
    private final String dateFrom;
    @JsonProperty("DateTo")
    private final String dateTo;
    @JsonProperty("SportIds")
    private final List<Integer> sportIds;
    @JsonProperty("ChampId")
    private final Integer champId;
}