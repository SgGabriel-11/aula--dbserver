package api_teste.database.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import api_teste.database.models.Task;
import api_teste.database.models.User;
import api_teste.database.repositories.TaskRepository;


@Service 
public class TaskService {
    
    @Autowired 
    private TaskRepository taskRepository;
    @Autowired 
    private  UserService userService;
    



}
