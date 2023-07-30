package ca.bjad.foldersizes.events;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Manager for registering, unregistering, and firing
 * app wide events between the components within the 
 * application. 
 *
 * @author 
 *   Ben Dougall
 */
public final class AppWideEventManager
{
   private Map<EventType, Set<AppEventListener>> eventListeners = null;
   private Map<EventType, ReentrantLock> eventLocks = null;
   
   /**
    * Singleton holder for the manager class to implement the 
    * java lazy initialization singleton pattern for thread safety
    *
    * @author 
    *   Ben Dougall.
    */
   private static class SingletonHelper
   {
      private static final AppWideEventManager instance = new AppWideEventManager();
   }
   
   /**
    * Provides the singleton instance of the AppWideEventManager.
    * 
    * @return
    *    The singleton instance of the AppWideEventManager.
    */
   public static AppWideEventManager instance()
   {
      return SingletonHelper.instance;
   }
   
   /**
    * Constructor, setting the initial values in the event listener
    * mapping adding each of the event types in the enum and a blank
    * list for each of them. 
    */
   private AppWideEventManager()
   {
      eventListeners = new HashMap<EventType, Set<AppEventListener>>(EventType.values().length);
      eventLocks = new HashMap<EventType, ReentrantLock>(EventType.values().length);
      for (EventType type : EventType.values())
      {
         eventListeners.put(type, new LinkedHashSet<>());
         eventLocks.put(type, new ReentrantLock());
      }
   }
   
   /**
    * Registers the listener to the event type passed if the listener is not  
    * registered already. 
    * 
    * @param eventType
    *    The event type to register the listener for.   
    * @param listener
    *    The listener to register
    * @return 
    *    True if the listener was registered, false if the listener was 
    *    not added due to it being previously added.
    * @throws IllegalArgumentException 
    *    Thrown if the event type or the listener are null, as they both need to 
    *    be valid values. 
    */
   public boolean registerEventListener(EventType eventType, AppEventListener listener) throws IllegalArgumentException
   {
      if (eventType == null)
      {
         throw new IllegalArgumentException("Event Type cannot be null when passed to ca.bjad.foldersizes.events.AppWideEventManager.registerEventListener");
      }
      if (listener == null)
      {
         throw new IllegalArgumentException("Listener cannot be null when passed to ca.bjad.foldersizes.events.AppWideEventManager.registerEventListener");
      } 
      
      boolean result = false;
      try
      {
         // Turn on the lock for the current event type.
         eventLocks.get(eventType).lock();

         // Attempt to add the listener, which will be blocked if its already added.
         result = eventListeners.get(eventType).add(listener);
      }
      finally
      {
         // Unlock the lock for the event type no matter what.
         eventLocks.get(eventType).unlock();
      }
      
      return result; 
   }
   
   /**
    * Unregisters the listener from the event type passed.
    * 
    * @param eventType
    *    The event type to unregister the listener for.   
    * @param listener
    *    The listener to unregister
    * @return 
    *    True if the listener was unregistered, false if the listener was 
    *    not registered to begin with.
    * @throws IllegalArgumentException 
    *    Thrown if the event type or the listener are null, as they both need to 
    *    be valid values. 
    */
   public boolean unregisterEventListener(EventType eventType, AppEventListener listener) throws IllegalArgumentException
   {
      if (eventType == null)
      {
         throw new IllegalArgumentException("Event Type cannot be null when passed to ca.bjad.foldersizes.events.AppWideEventManager.unregisterEventListener");
      }
      if (listener == null)
      {
         throw new IllegalArgumentException("Listener cannot be null when passed to ca.bjad.foldersizes.events.AppWideEventManager.unregisterEventListener");
      } 
      
      boolean result = false;
      try
      {
         // Turn on the lock for the current event type.
         eventLocks.get(eventType).lock();

         // Attempt to remove the listener.
         result = eventListeners.get(eventType).remove(listener);
      }
      finally
      {
         // Unlock the lock for the event type no matter what.
         eventLocks.get(eventType).unlock();
      }
      
      return result; 
   }
   
   /**
    * Fires all the listeners for the event type passed, sending the source object
    * and the event bean to each of the listeners.
    * 
    * @param eventType
    *    The event type to fire the listeners for.   
    * @param source
    *    The object firing the event.
    * @param eventBean 
    *    The object/bean containing the information related to the event. 
    * @throws IllegalArgumentException 
    *    Thrown if the event type or the source are null, as they both need to 
    *    be valid values. 
    */
   public void fireEvent(EventType eventType, Object source, Object eventBean) throws IllegalArgumentException
   {
      if (eventType == null)
      {
         throw new IllegalArgumentException("Event Type cannot be null when passed to ca.bjad.foldersizes.events.AppWideEventManager.fireEvent");
      }
      if (source == null)
      {
         throw new IllegalArgumentException("Source cannot be null when passed to ca.bjad.foldersizes.events.AppWideEventManager.fireEvent");
      } 
      
      try
      {
         // Turn on the lock for the current event type.
         eventLocks.get(eventType).lock();

         // Fire each of the event listeners registered for the event type. 
         eventListeners.get(eventType).forEach((AppEventListener listener) -> {
            listener.appEventOccurred(eventType, source, eventBean);
         });
      }
      finally
      {
         // Unlock the lock for the event type no matter what.
         eventLocks.get(eventType).unlock();
      }
   }
}
