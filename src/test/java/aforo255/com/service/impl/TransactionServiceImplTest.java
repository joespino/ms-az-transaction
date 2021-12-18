package aforo255.com.service.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jms.core.JmsTemplate;

import aforo255.com.msaztransaction.model.domain.Transaction;
import aforo255.com.msaztransaction.model.entity.TransactionEntity;
import aforo255.com.msaztransaction.repository.TransactionRepository;
import aforo255.com.msaztransaction.service.impl.TransactionServiceImpl;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private JmsTemplate jmsTemplate;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void test() {

        var transactionEntity = buildTransactionEntity();

        Mockito.when(transactionRepository.save(Mockito.any()))
                .thenReturn(transactionEntity);

        jmsTemplate.convertAndSend(Mockito.anyString(), Mockito.anyString());

        var transaction = transactionService.save(buildTransaction());

        Assertions.assertEquals(transactionEntity.getAccountId(), transaction.getAccountId());
        Assertions.assertEquals(transactionEntity.getAmount(), transaction.getAmount());
        Assertions.assertEquals(transactionEntity.getType(), transaction.getType());
        Assertions.assertEquals(transactionEntity.getId(), transaction.getId());

    }

    private TransactionEntity buildTransactionEntity() {
        return TransactionEntity.builder()
                .id(1)
                .accountId(1)
                .type("deposit")
                .amount(100.00)
                .build();
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
