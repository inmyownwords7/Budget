//package org.java.financial.utility;
//
//import org.java.financial.dto.response.TransactionResponseDTO;
//import org.java.financial.entity.Transaction;
//
//public class MapsUtils {
//    public TransactionResponseDTO mapToResponseDTO(Transaction transaction) {
//        return new TransactionResponseDTO(
//                transaction.getTransactionId(),
//                transaction.getUser().getUsername(),
//                transaction.getCategory().getCategoryName(),
//                transaction.getAmount(),
//                transaction.getType().name(),
//                transaction.getTransactionDate(),
//                transaction.getDescription()
//        );
//    }
//}
