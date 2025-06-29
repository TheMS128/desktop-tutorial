package models.GetChampionsBody;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class GetChampionshipsBody {
    @JsonProperty("SportIds")
    private final List<Integer> sportIds;
    @JsonProperty("DateFrom")
    private final String dateFrom;
    @JsonProperty("DateTo")
    private final String dateTo;
}