package models.GetChampanionsBody;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class Root {
    @JsonProperty("SportIds")
    private final List<Integer> sportIds;
    private final String dateFrom;
    private final String dateTo;
}