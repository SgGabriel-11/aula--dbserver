//Pacote onde está a classe de serviço no projeto
package api_teste.database.services;

//Importa List da biblioteca padrão do Java para manipular coleções de objetos.
import java.util.List;
//Importa Optional, usando para tratar calores que podem não estar presentes (evitar NullPointerException).
import java.util.Optional;

//Importa a anotação do Spring para injeção automática de dependências
import org.springframework.beans.factory.annotation.Autowired;
//Imporrta a anotação que define essa classe com um componente de servoço gerenciado pelo Spring.
import org.springframework.stereotype.Service;
//Importa a anotação para gerenciar transaçãoes no Bd (garante atomicidade na oparação de banco de dados).
import org.springframework.transaction.annotation.Transactional;

//Importa o models .Task 
import api_teste.database.models.Task;
//Importa o models .User 
import api_teste.database.models.User;
//Importa a interface do repositório responsável pelas operações no banco de dados relacionadas a entidade Task.
import api_teste.database.repositories.TaskRepository;


//Anotação que indica para o Spring que essa classe contem as regras de negócio
@Service 
public class TaskService {

    //Injeta automaticamente a instancia do TaskRepository gerenciado pelo Spring
    @Autowired
    private TaskRepository taskRepository;

    //Injeta automaticamente a Instancia do UserService para validar o usuário
    @Autowired 
    private UserService userService;

    //Método para buscar todas as tarefas apartir do ID
    public Task findById(Long id) {
        //Executa a busca no banco, retorna um Optional contendo (ou não) a task
        Optional<Task> task = this.taskRepository.findById(id);

        // Se a tarefa existir, retorna o objeto, se estiver vazio, lança um RunTimeException
        return task.orElseThrow(() -> new RuntimeException(
            "Task não encontrada! ID:" + id + ",Tipo:" + Task.class.getName()
        ));
    }

        //Método para buscar todas as tarefas vinculadas a um usuário apartir do ID do usuário
    public List<Task> findByUserId(Long userId) {

            //Chama o UserService para garantir que o usuário existe no banco(lança exceção caso não exista)
            //executa a busca otimizada no repositório filtrando pelo ID do usuário
            List<Task> tasks = this.taskRepository.findByUserId(userId);

            return tasks; // Retorna a lista de tarefas encontradas
    }

    // Garante que a criação ocorra dentro de uma transação de banco de dados.
    @Transactional
    public Task create(Task obj) {

        // Mantém o usuário informado na tarefa.
        User user = obj.getUser();

        // Garante que o JPA realize uma inserção, e não uma atualização.
        obj.setId(null);

        obj.setUser(user);

        return this.taskRepository.save(obj);
    }

    // Garante que a atualização ocorra dentro de uma transação isolada.
    @Transactional
    public Task update(Task obj) {

        Task newObj = findById(obj.getId());

        newObj.setDescription(obj.getDescription());

        return this.taskRepository.save(newObj);    
    }

    //Método para deletar uma tarefa pelo ID
    public void delete(long id) {
        //Verifica se a tarefa existe antes de tentar deeletar
        //Procura pelo ID
        findById(id);

        try{
            //Solicita a remoção da tarefa no banco de dados pelo ID
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            //Captura execções (como violação de integridade referencial) e lança uma exceção personalizada
            throw new RuntimeException("Não foi possivel excluir pois nao ha tarefas relacionadas");
        }
    }
}