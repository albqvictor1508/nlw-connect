package br.com.nlw.events.repo;

import br.com.nlw.events.model.UserModel;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserModel, Integer> {
    public UserModel findByEmail(String email);
}
