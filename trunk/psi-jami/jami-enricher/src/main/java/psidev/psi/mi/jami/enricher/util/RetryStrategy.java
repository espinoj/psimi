package psidev.psi.mi.jami.enricher.util;

import psidev.psi.mi.jami.enricher.exception.EnricherException;

/**
 * Keeps track of attempts to complete a jobs and throws an exception if the maximum number of attempts is exceeded.
 * Should also be passed any exceptions which have been caught so that the contents can be interrogated.
 *
 * @author Gabriel Aldam (galdam@ebi.ac.uk)
 * @since 09/07/13
 */
public class RetryStrategy {

    private int attemptTotal;
    private int attemptCount;
    private String message;
    private boolean successfulAttempt = false;

    /**
     * Sets the strategy with the number of times the jobs should be tried.
     * If any non positive int is given, one attempt will be tried.
     * @param attempts  The number of times the jobs should be tried.
     *                  If 0 or lower is given, the jobs will be tried once.
     * @param message can be null
     */
    public RetryStrategy(int attempts , String message){
        if(attempts < 1) attempts = 1;
        this.attemptCount = attempts;
        this.attemptTotal = attempts;
        this.message = message;
    }

    /**
     * Will return true if a
     * @return
     * @throws EnricherException
     */
    public boolean retry() throws EnricherException {
        if(successfulAttempt) return false;
        if(attemptCount > 0) {
            attemptCount--;
            return true;
        }
        else{
            throw new EnricherException("A jobs could not be completed. Attempted "+attemptTotal+" times. " +
                    "Additional information:"+message);
        }
    }

    /**
     * Called when the jobs was successfully completed.
     * The next query of whether to retry will return false.
     */
    public void attemptSucceeded(){
        successfulAttempt = true;
    }

    /**
     * Exceptions should be passed here so as the contents may be interrogated.
     * Although it currently does nothing, the intention is to incorporate the contents
     * into the final exception or send to a log.
     *
     * @param e     An exception caught while attempting to do a jobs.
     */
    public void reportException(Exception e){

    }
}
