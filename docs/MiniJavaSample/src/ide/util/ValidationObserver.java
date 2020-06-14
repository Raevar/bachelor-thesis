package ide.util;

import edu.tum.cup2.semantics.ErrorInformation;

/**
 * Observes the process of validation.
 * @see Validator
 * @deprecated Is to be merged with a normal ParserObserver 
 ***/

@Deprecated
public interface ValidationObserver
{
	public void validationResult(ErrorInformation errorInformations);

	public void validationFailed();

	public void validationDone(Object result);
}
