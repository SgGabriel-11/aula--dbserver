//url base da API Spring Boot para buscar as tarefas do usuario de ID 1
const url = "https://localhost:8080/tasks/user/1";

//função responsavel por ocultar o icone de carregamento 
function hideLoader(){

    //busca o elemento HTML com o id 'loading' e altera o estilo de exibição para oculta-lo
    document.getElementById("loading").style.display = "none";
}

    //funçao responsavel por construir o html da tabela e preenche-lo com as tarefas 
    function show(tasks){
        
        //cria uma string contendo o cabeçalho da tabela utilizando template literals
        let tab = `
        <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Descrição</th>
                <th scope="col">Usuario</th>   
                <th scope="col">User ID</th>
            </tr>
        </thead>
        
        `;
        for(let task of tasks) {

            tab += `
            <tr>
                <td scope="row">${task.id}</td>
                <td>${task.description}</td>
                <td>${task.user.name}</td>
                <td>${task.user.id}</td>
                </tr>

            `;

        }

        document.getElementById("tasks").innerHTML = tab;

        async function getAPI(url) {

            const response = await fetch(url,{method: "GET"});

            var data = await response.json();

            if(response){
                
                hideLoader();
                        
            }
            
        }

        show(data);
    }

    
getAPI(url);
