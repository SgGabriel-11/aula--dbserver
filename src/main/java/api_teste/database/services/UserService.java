package api_teste.database.services; //Importa o caminho onde a classe está do código do projeto

import org.springframework.stereotype.Service;
import java.util.Optional;                                       //Importa Optional, usando para tratar calores que podem não estar presentes (evitar NullPointerException).
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional; //Importa a anotação para gerenciar transaçãoes no Bd (garante atomicidade na oparação de banco de dados).
import api_teste.database.models.User;
import api_teste.database.models.Task;                           //Importa o models .Task 
import api_teste.database.repositories.TaskRepository;           //Importa a interface do repositório responsável pelas operações no banco de dados relacionadas a entidade Task.
import api_teste.database.repositories.UserRepository;           //importa a interface do repositório responsável pelas operações no banco de dados no banco de dados

//Anotaçõa que indica no Spring que essa classe contem as regras de negócios da entidade User
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TaskRepository taskRepository;

    public User findById(long Id) {

        Optional<User> user = this.userRepository.findById(Id);

        return user.orElseThrow(() -> new RuntimeException(
                "User não encontrado! ID:" + Id + ",Tipo:" + User.class.getName()));
    }

    @Transactional
    public User create(User obj) {

        obj.setId(null);

        obj = this.userRepository.save(obj);

        this.taskRepository.saveAll(obj.getTasks());

        return obj;

    }

    @Transactional
    public User update(User obj) {

        User newObj = this.findById(obj.getId());
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());

        return this.userRepository.save(newObj);

    }

}