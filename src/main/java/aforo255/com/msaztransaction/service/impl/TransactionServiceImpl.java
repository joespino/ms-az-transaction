package aforo255.com.msaztransaction.service.impl;

import static java.nio.charset.StandardCharsets.UTF_8;

import javax.transaction.Transactional;

import aforo255.com.msaztransaction.model.domain.Transaction;
import aforo255.com.msaztransaction.model.entity.TransactionEntity;
import aforo255.com.msaztransaction.repository.TransactionRepository;
import aforo255.com.msaztransaction.service.ITransactionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class TransactionServiceImpl implements ITransactionService {

    private static final String TOPIC_NAME = "transaction-topic";
    static final Gson GSON = new Gson();

    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private TransactionRepository _transactionRepository;

    @Override
    @Transactional
    public Transaction save(Transaction transaction) {

        var transactionEntity = _transactionRepository.save(
                mapTransactionEntity(transaction)
        );

        // Sending to Azure Service Bus
        jmsTemplate.convertAndSend(TOPIC_NAME, GSON.toJson(transactionEntity).getBytes(UTF_8));// GSON.toJson(transaction).getBytes(UTF_8)

        return mapTransaction(transactionEntity);
    }

    private TransactionEntity mapTransactionEntity(Transaction transaction) {
        return TransactionEntity.builder()
                .accountId(transaction.getAccountId())
                .type(transaction.getType())
                .amount(transaction.getAmount())
                .build();
    }

    private Transaction mapTransaction(TransactionEntity transactionEntity) {
        return Transaction.builder()
                .id(transactionEntity.getId())
                .accountId(transactionEntity.getAccountId())
                .type(transactionEntity.getType())
                .amount(transactionEntity.getAmount())
                .build();
    }

}
