package br.com.casabemestilo.util;

import java.sql.Types;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.QueryException;
import org.hibernate.dialect.MySQL5InnoDBDialect;
import org.hibernate.dialect.MySQLInnoDBDialect;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.metamodel.binding.HibernateTypeDescriptor;
import org.hibernate.type.Type;

public class MySQLDialect extends MySQL5InnoDBDialect {

	public MySQLDialect() {
		super();
		// Registrar as colunas com float
		registerColumnType(Types.REAL, "number($p,$s)");
		registerHibernateType(Types.REAL, "float");

		// Registrar as colunas com text
		registerColumnType(Types.LONGVARCHAR, "varchar($p)");
		registerHibernateType(Types.LONGVARCHAR, "text");        
        
	}
}