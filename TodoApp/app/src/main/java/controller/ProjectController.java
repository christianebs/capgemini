package controller;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

/**
 *
 * @author chris
 */
public class ProjectController {
    
    public void save(Project project){
    
        String sql = "INSERT INTO projects (name"
                + "description"
                + "createdAt"
                + "updateAt) VALUES (?, ?, ?, ?)";
        
        Connection connection = null;
        PreparedStatement statement = null;
        
        try {
        //Estabelecendo a conexao com o DB
        connection = ConnectionFactory.getConnection();
        //Preparando a query
        statement = connection.prepareStatement(sql);
        //Setando os valores do statement
        statement.setString(1, project.getName());
        statement.setString(2, project.getName());
        statement.setDate(3, new Date(project.getCreatedAt().getTime()));
        statement.setDate(4, new Date(project.getUpdateAt().getTime()));
        //Executando a query
        statement.execute();
        } catch (Exception ex){
            throw new RuntimeException("Erro ao salvar a tarefa"
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }
    
    public void update(Project project){
        
        String sql = "UPDATE projects SET "
                + "name = ?"
                + "description = ?"
                + "createdAt = ?"
                + "updateAt = ?"
                + "WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;

        try {
        //Estabelecendo a conexao com o DB
        connection = ConnectionFactory.getConnection();
        //Preparando a query
        statement = connection.prepareStatement(sql);
        //Setando os valores do statement
        statement.setString(1, project.getName());
        statement.setString(2, project.getDescription());
        statement.setDate(3, new Date(project.getCreatedAt().getTime()));
        statement.setDate(4, new Date(project.getUpdateAt().getTime()));
        statement.setInt(5, project.getId());
        //Executando a query
        statement.execute();
        } catch (Exception ex){
            throw new RuntimeException("Erro ao atualizar a tarefa"
                    + ex.getMessage(), ex);
        }
    }
    
    public void removeById(int idProject){
        
        String sql = "DELETE FROM projects WHERE id = ?";
        
        Connection connection = null;
        PreparedStatement statement = null;

        try {
        //Estabelecendo a conexao com o DB
        connection = ConnectionFactory.getConnection();
        //Preparando a query
        statement = connection.prepareStatement(sql);
        //Setando os valores do statement
        statement.setInt(1, idProject);
        //Executando a query
        statement.execute();
        } catch (Exception ex){
            throw new RuntimeException("Erro ao deletar a tarefa"
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    
    }
    
    public List<Project> getAll(){
                
        String sql = "SELECT * FROM projects";
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        
        List<Project> projects = new ArrayList<>();
        
        try {
        //Estabelecendo a conexao com o DB
        connection = ConnectionFactory.getConnection();
        //Preparando a query
        statement = connection.prepareStatement(sql);
        //Valor retornado pela execução da query
        resultSet = statement.executeQuery();
        
        while(resultSet.next()){
            
            Project project = new Project();
            
            project.setId(resultSet.getInt("id"));
            project.setName(resultSet.getString("name"));
            project.setDescription(resultSet.getString("description"));
            project.setCreatedAt(resultSet.getDate("createdAt"));
            project.setUpdateAt(resultSet.getDate("updateAt"));
            
            projects.add(project);
        }
        
        } catch (Exception ex){
            throw new RuntimeException("Erro ao inserir projeto"
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultSet);
        }
       
        return projects;
    }
}
