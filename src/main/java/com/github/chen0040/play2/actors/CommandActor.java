package com.github.chen0040.play2.actors;


import akka.actor.Props;
import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;


/**
 * Created by xschen on 10/9/2016.
 */
public class CommandActor extends UntypedActor {
   public static final String ACTOR_NAME = "CommandActor";
   private LoggingAdapter logger = Logging.getLogger(this);

   @Override public void onReceive(Object message) throws Exception {
      if(message instanceof String){
         logger.info("current address: {}", getSelf().path());
         getSender().tell(message + " - got something from server", getSelf());
      }
   }


   public static Props props() {
      return Props.create(CommandActor.class, ()-> new CommandActor());
   }

   public CommandActor(){
   }
}
