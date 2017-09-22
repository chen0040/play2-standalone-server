package com.github.chen0040.play2.controllers;


import akka.actor.ActorRef;
import play.Logger;
import play.libs.F;
import play.mvc.Controller;
import play.mvc.Result;

import static akka.pattern.Patterns.ask;


/**
 * Created by xschen on 10/9/2016.
 */
public class CommandController extends Controller implements CommandControllerContract {

   private ActorRef commandActor;

   private static final Logger.ALogger logger = Logger.of(CommandController.class);

   public CommandController(ActorRef commandActor){
      super();
      this.commandActor = commandActor;
   }


   public F.Promise<Result> process(String msg) {
      return F.Promise.wrap(ask(commandActor, msg, 1000)).map(response -> ok(response.toString()));
   }


}
