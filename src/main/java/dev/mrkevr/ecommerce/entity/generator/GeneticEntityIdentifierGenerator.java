package dev.mrkevr.ecommerce.entity.generator;

import java.util.Random;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

import dev.mrkevr.ecommerce.entity.GenericEntity;

public class GeneticEntityIdentifierGenerator implements IdentifierGenerator {

	private static final long serialVersionUID = 4739983790007708720L;

	@Override
	public Object generate(SharedSessionContractImplementor session, Object object) {

		Random random = new Random();
		String prefix = ((GenericEntity) object).getIdPrefix();
		String middle = String.format("%04d", random.nextInt(10000));
		String last = String.format("%04d", random.nextInt(10000));
		
		return prefix + "-" + middle + "-" + last;
	}
}
