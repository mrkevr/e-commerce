package dev.mrkevr.ecommerce.entity.generator;

import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.enhanced.SequenceStyleGenerator;
import org.hibernate.internal.util.config.ConfigurationHelper;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;

import dev.mrkevr.ecommerce.entity.GenericEntity;

/**
 * This id generator is not being used.
 * This application is currently using {@link GeneticEntityIdentifierGenerator} to generate entity ids.
 */
public class GeneticEntityIdGenerator extends SequenceStyleGenerator {

	private static final long serialVersionUID = -3832357099845937783L;

	public static final String CODE_NUMBER_SEPARATOR_PARAMETER = "codeNumberSeparator";
	public static final String CODE_NUMBER_SEPARATOR_DEFAULT = "";

	public static final String NUMBER_FORMAT_PARAMETER = "numberFormat";
	public static final String NUMBER_FORMAT_DEFAULT = "%08d";

	private String format;

	@Override
	public Object generate(SharedSessionContractImplementor session, Object object) throws HibernateException {
		return String.format(format, ((GenericEntity) object).getIdPrefix(), super.generate(session, object));
	}

	@Override
	public void configure(Type type, Properties parameters, ServiceRegistry serviceRegistry) throws MappingException {

		super.configure(type, parameters, serviceRegistry);

		String codeNumberSeparator = ConfigurationHelper.getString(
				CODE_NUMBER_SEPARATOR_PARAMETER, 
				parameters,
				CODE_NUMBER_SEPARATOR_DEFAULT);
		
		String numberFormat = ConfigurationHelper.getString(
				NUMBER_FORMAT_PARAMETER, 
				parameters, 
				NUMBER_FORMAT_DEFAULT)
				.replace("%", "%2");

		this.format = "%1$s" + codeNumberSeparator + numberFormat;
	}
}
