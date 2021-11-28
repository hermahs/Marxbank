package marxbank.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import marxbank.core.model.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

  Optional<Transaction> findById(Long id);

  Optional<List<Transaction>> findByFrom_Id(Long id);

  Optional<List<Transaction>> findByReciever_Id(Long id);

}