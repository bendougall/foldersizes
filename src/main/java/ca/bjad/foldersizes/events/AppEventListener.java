package ca.bjad.foldersizes.events;

/**
 * Interface to implement in order to register for, and 
 * react to app wide events triggered by the various 
 * components in the application. 
 *
 * @author 
 *   Ben Dougall
 */
public interface AppEventListener
{
   /**
    * The method to implement to react to the various events triggered by
    * the various components within the application. 
    * 
    * @param eventType
    *    The type of event to know what happened
    * @param source
    *    The source of the event
    * @param eventBean
    *    The bean containing the data related to the event (file/directory selection, 
    *    index selected, etc...).
    */
   public void appEventOccurred(EventType eventType, Object source, Object eventBean);
}
