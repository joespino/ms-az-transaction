package aforo255.com.msaztransaction.model.api;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TransactionResponse {

	private Integer id;
	private double amount ;
	private String type ;
	private Integer accountId;
}
