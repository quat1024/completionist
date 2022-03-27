package agency.highlysuspect.completionist.platform;

import agency.highlysuspect.completionist.Completionist;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public class ServiceHelper {
	public static <T> T loadSingletonService(Class<T> serviceClass) {
		//Had to rewrite this to not use any Java 9 features LOL
		//I guess as a side effect of that, it does eagerly construct them all b/c that's how serviceloader worked
		
		//List<ServiceLoader.Provider<T>> providers = ServiceLoader.load(serviceClass).stream().toList();
		List<T> providers = new ArrayList<>();
		ServiceLoader.load(serviceClass).iterator().forEachRemaining(providers::add);
		
		if(providers.size() != 1) {
			String providersListMessage = providers.isEmpty() ? "None of them."
				: providers.stream().map(p -> p.getClass().getName()).collect(Collectors.joining(",", "[", "]"));
			
			//throw new IllegalStateException("There should be exactly one %s implementation on the classpath. Found: %s".formatted(serviceClass.getSimpleName(), providersListMessage));
			throw new IllegalStateException(String.format("There should be exactly one %s implementation on the classpath. Found: %s", serviceClass.getSimpleName(), providersListMessage));
		} else {
			//ServiceLoader.Provider<T> provider = providers.get(0);
			T provider = providers.get(0);
			
			//Completionist.LOGGER.info("Instantiating %s via %s".formatted(serviceClass.getSimpleName(), provider.type().getName()));
			Completionist.LOGGER.info(String.format("Instantiating %s via %s", serviceClass.getSimpleName(), provider.getClass().getName()));
			
			//return provider.get();
			return provider;
		}
	}
}
