package br.com.casabemestilo.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class ConnectionFactory {
    
    private static DataSource dataSource;
    
    public Connection getConexao() throws Exception {

        try {
            
            if(dataSource == null){
                InitialContext contextoInicial = new InitialContext();
                Context contexto = (Context) contextoInicial.lookup("java:/comp/env");
                dataSource = (DataSource)contexto.lookup("SGCB");
            }
            
            return dataSource.getConnection();
            
        } catch (Exception e) {
            throw new Exception("Falha na conexao - " + e);
        }
    }
    
    public void close() throws SQLException{    	
    	dataSource.getConnection().close();		
    }
}
