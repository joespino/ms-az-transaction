package aforo255.com.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import aforo255.com.msaztransaction.model.api.TransactionRequest;
import aforo255.com.msaztransaction.model.domain.Transaction;
import aforo255.com.msaztransaction.service.ITransactionService;

import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Mock
    private ITransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    void saveDepositTest() {

        var transaction = buildTransaction();

        Mockito.when(transactionService.save(Mockito.any()))
                .thenReturn(transaction);

        var responseEntity = transactionController.postDepositEvent(buildTransactionRequest());

        var transactionResponse = responseEntity.getBody();

        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(transaction.getAccountId(), transactionResponse.getAccountId());
        Assertions.assertEquals(transaction.getAmount(), transactionResponse.getAmount());
        Assertions.assertEquals(transaction.getType(), transactionResponse.getType());
        Assertions.assertEquals(transaction.getId(), transactionResponse.getId());
    }

    private TransactionRequest buildTransactionRequest() {
        return new TransactionRequest(1, 100.00);
    }

    private Transaction buildTransaction() {
        return Transaction.builder()
                .id(1)
                .accountId(1)
                .type("deposit")
                .amount(100.00)
                .build();
    }

}