package it.digitalgarage.marketplace.commons.be.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.StoredProcedure;

// TODO: Auto-generated Javadoc
/**
 * The Class GenericStoredProcedure.
 */
public class GenericStoredProcedure extends StoredProcedure{
	
	/** The declared parameters. */
	private List<SqlParameter> declaredParameters = new ArrayList<>();
	
	/**
	 * Instantiates a new generic stored procedure.
	 *
	 * @param jdbcTemplate the jdbc template
	 * @param storeProcedure the store procedure
	 */
	public GenericStoredProcedure(JdbcTemplate jdbcTemplate,String storeProcedure) {
		super(jdbcTemplate,storeProcedure);
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.jdbc.object.RdbmsOperation#getDeclaredParameters()
	 */
	@Override
	protected List<SqlParameter> getDeclaredParameters() {
		return declaredParameters;
	}
	
	/**
	 * Sets the declared parameters.
	 *
	 * @param declaredParameters the new declared parameters
	 */
	public void setDeclaredParameters(List<SqlParameter> declaredParameters) {
		this.declaredParameters = declaredParameters;
	}

}
