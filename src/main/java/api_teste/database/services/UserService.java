//Importa o caminho onde a classe está do código do projeto
package api_teste.database.services;

import org.springframework.stereotype.Service;

//Importa Optional, usando para tratar calores que podem não estar presentes (evitar NullPointerException).
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//Importa a anotação para gerenciar transaçãoes no Bd (garante atomicidade na oparação de banco de dados).
import org.springframework.transaction.annotation.Transactional;

import api_teste.database.models.User;
//Importa o models .Task 
import api_teste.database.models.Task;
//Importa a interface do repositório responsável pelas operações no banco de dados relacionadas a entidade Task.
import api_teste.database.repositories.TaskRepository;
//importa a interface do repositório responsável pelas operações no banco de dados no banco de dados
import api_teste.database.repositories.UserRepository;

//Anotaçõa que indica no Spring que essa classe contem as regras de negócios da entidade User
@Service 
public class UserService {
    
    @Autowired 
    private UserRepository userRepository;

    @Autowired 
    private TaskRepository taskRepository;

    public User findById(long Id) {

        Optional<User> user = this.userRepository.findById(Id);

        return user.orElseThrow(()-> new RuntimeException(
            "User não encontrado! ID:" + Id + ",Tipo:" + User.class.getName()
        ));
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