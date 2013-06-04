package br.com.casabemestilo.util;

import java.sql.Types;
import org.hibernate.dialect.MySQLInnoDBDialect;



public class MySQLDialect extends MySQLInnoDBDialect { 
	
	
	public MySQLDialect() { 
		super();
                //Registrar as colunas com float
		registerColumnType(Types.REAL, "number($p,$s)" ); 
                registerHibernateType(Types.REAL, "float");

                //Registrar as colunas com text
                registerColumnType(Types.LONGVARCHAR, "varchar($p)");
                registerHibernateType(Types.LONGVARCHAR, "text");
	} 
}