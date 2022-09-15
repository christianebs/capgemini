package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author chris
 */
public class TaskController {
    
    public void save(Task task){
        
        String sql = "INSERT INTO tasks (idProject,"
                + "name,"
                + "description,"
                + "completed,"
                + "notes,"
                + "deadline,"
                + "createdAt,"
                + "updateAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
               
                Connection connection = null;
                PreparedStatement statement = null;
        
        try {
            //Estabelecendo a conexao com o DB
            connection = ConnectionFactory.getConnection();
            //Preparando a query
            statement = connection.prepareStatement(sql);
            //Setando os valores do statement
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setBoolean(4, task.isIsCompleted());
            statement.setString(5, task.getNotes());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdateAt().getTime()));
            //Executando a query
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar a tarefa "
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public void update(Task task){
        
        String sql = "UPDATE tasks SET "
                + "idProjects = ?, "
                + "name = ?, "
                + "description = ?, "
                + "notes = ?, "
                + "completed = ?, "
                + "deadline = ?, "
                + "createdAt = ?, "
                + "updateAt = ?, "
                + "WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //Estabelecendo a conexao com o DB
            connection = ConnectionFactory.getConnection();
            //Preparando a query
            statement = connection.prepareStatement(sql);
            //Setando os valores do statement
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getNotes());
            statement.setBoolean(5, task.isIsCompleted());
            statement.setDate(6, new Date(task.getDeadline().getTime()));
            statement.setDate(7, new Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new Date(task.getUpdateAt().getTime()));
            statement.setInt(9, task.getId());
            //Executando a query
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa "
                    + ex.getMessage(), ex);
        }
    }
    
    public void removeById(int taskId) throws SQLException{
        
        String sql = "DELETE FROM tasks WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
            //Estabelecendo a conexao com o DB
            connection = ConnectionFactory.getConnection();
            //Preparando a query
            statement = connection.prepareStatement(sql);
            //Setando os valores do statement
            statement.setInt(1, taskId);
            //Executando a query
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar a tarefa" 
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public List<Task> getAll(int idProject){
        String sql = "SELECT * FROM tasks WHERE idProject = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        //Lista de tarefas que será devolvida quando a chamada do método acontecer
        List<Task> tasks = new ArrayList<Task>();
        
        try {
            //Estabelecendo a conexao com o DB
            connection = ConnectionFactory.getConnection();
            //Preparando a query
            statement = connection.prepareStatement(sql);
            //Setando o valor que corresponde ao filtro de busca
            statement.setInt(1, idProject);
            //Valor retornado pela execução da query
            resultSet = statement.executeQuery();
        
            //Enquanto houverem valores a serem pecorridos no meu resultSet
        while(resultSet.next()){
            
            Task task = new Task();
            task.setId(resultSet.getInt("id"));
            task.setIdProject(resultSet.getInt("idProject"));
            task.setName(resultSet.getString("name"));
            task.setDescription(resultSet.getString("description"));
            task.setNotes(resultSet.getString("notes"));
            task.setIsCompleted(resultSet.getBoolean("completed"));
            task.setDeadline(resultSet.getDate("deadline"));
            task.setCreatedAt(resultSet.getDate("createdAt"));
            task.setUpdateAt(resultSet.getDate("updateAt"));
            
            tasks.add(task);
        }
        
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao inserir lista" 
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
        //Lista de tarefas que foi criada e 
        return tasks;
    }
}
