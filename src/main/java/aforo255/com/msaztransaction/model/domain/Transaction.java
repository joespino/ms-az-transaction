package aforo255.com.msaztransaction.model.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction  {
	
	private Integer id;
	private double amount ; 
	private String type ; 	
	private Integer accountId ;	
}
