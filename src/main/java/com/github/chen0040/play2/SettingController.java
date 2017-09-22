package com.github.chen0040.play2;


import akka.actor.ActorSystem;
import play.mvc.Controller;
import play.mvc.Result;


/**
 * Created by xschen on 10/9/2016.
 */
public class SettingController extends Controller {

   private ActorSystem actorSystem;
   public SettingController(ActorSystem actorSystem){
      super();
      this.actorSystem = actorSystem;
   }

   public Result settings(){
      return ok(actorSystem.settings().toString());
   }
}
