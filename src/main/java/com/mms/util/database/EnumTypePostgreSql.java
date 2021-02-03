package com.mms.util.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.EnumType;

public class EnumTypePostgreSql extends EnumType {
 
    /**
	 * 
	 */
	private static final long serialVersionUID = -3873883880848948240L;

	@Override
    public void nullSafeSet(PreparedStatement st, Object value, int index,
            SharedSessionContractImplementor session)
            throws HibernateException, SQLException {
        if(value == null) {
            st.setNull( index, Types.OTHER );
        }
        else {
            st.setObject( index, value.toString(), Types.OTHER );
        }
    }
 
     
}