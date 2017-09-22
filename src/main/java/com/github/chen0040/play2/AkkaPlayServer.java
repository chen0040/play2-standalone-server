package com.github.chen0040.play2;


import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import play.Mode;
import play.server.Server;

import java.io.IOException;
import java.util.Date;
import java.util.Random;

import play.routing.RoutingDsl;
import static play.mvc.Controller.request;
import static play.mvc.Results.ok;


/**
 * Created by xschen on 1/19/16.
 */
public class AkkaPlayServer {
   private static final Logger logger = LoggerFactory.getLogger(AkkaPlayServer.class);
   private static final String actorSystemName = "Play2ActorSystem";

   private String ipAddress;

   private Server server;
   private int playPort;
   private int actorSystemPort;
   private CommandController commandController;
   private SettingController settingController;

   private ActorSystem actorSystem;

   private int registeredPort = -1;

   public AkkaPlayServer() {
      ipAddress = IpTools.getIpAddress();
   }

   public ActorSystem createActorSystem(String ipAddress, int port) {
      Config akkaConfig = ConfigFactory.load().getConfig("play2me");

      String portPath = "akka.remote.netty.tcp.port";
      akkaConfig = ConfigFactory.parseString(portPath + "=" + port).withFallback(akkaConfig);

      akkaConfig = ConfigFactory.parseString("akka.remote.netty.tcp.hostname=\"" + ipAddress + "\"").withFallback(akkaConfig);



      return ActorSystem.create(actorSystemName, akkaConfig);
   }


   public void start(int startPlayPort) throws IOException {


      Random random = new Random(new Date().getTime());

      playPort = startPlayPort;

      if(!IpTools.isPortAvailable(playPort)){
         playPort = IpTools.getNextAvailablePortWithRandomDelay(playPort, random);
      }
      actorSystemPort = IpTools.getNextAvailablePortWithRandomDelay(playPort, random);


      actorSystem = createActorSystem(ipAddress, actorSystemPort);
      ActorRef commandActor = actorSystem.actorOf(CommandActor.props(), CommandActor.ACTOR_NAME);

      commandController = new CommandController(commandActor);
      settingController = new SettingController(actorSystem);

      server = Server.forRouter(new RoutingDsl()
              .GET("/hello/:to").routeTo(to -> ok("Hello" + to))
              .GET("/actor/:msg").routeAsync(msg -> commandController.process(msg.toString()))
              .GET("/settings/actor-system").routeTo(() -> {

                 return settingController.settings();
              })
              .GET("/settings/akka-port").routeTo(() -> {
                 return ok(""+ actorSystemPort);
              })
              .POST("/json/:name").routeTo(name -> {
                  return ok(request().body().asJson().toString());
               })
              .build(), Mode.DEV, playPort);
   }





}
