package org.java.financial.util;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.java.financial.dto.TransactionDTO;
import org.java.financial.logging.GlobalLogger;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class StringToJson {
    public StringToJson() {
     try {
         ObjectMapper mapper = new ObjectMapper();
         TransactionDTO transaction = new TransactionDTO();
         TransactionDTO transactional = new TransactionDTO(
                 1L, 100L, 200L, new BigDecimal("49.99"),
                 "INCOME", "Freelance Payment", LocalDateTime.now()
         );
         String JSON = mapper.writeValueAsString(transaction);
         GlobalLogger.LOGGER.info(JSON);
     } catch (Exception e) {
         throw new RuntimeException(e);
     }
    }
}
