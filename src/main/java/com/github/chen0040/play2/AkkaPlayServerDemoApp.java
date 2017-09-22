package com.github.chen0040.play2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


/**
 * Created by chen0 on 29/5/2016.
 */
public class AkkaPlayServerDemoApp {

   private static final Logger logger = LoggerFactory.getLogger(AkkaPlayServerDemoApp.class);

   public static void main(String[] args) throws IOException, InterruptedException {
      AkkaPlayServer play2Server = new AkkaPlayServer();

      int startPort = 7000;
      play2Server.start(startPort);

      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
         logger.info("System shutdown");
      }));

      while (true) {
         Thread.sleep(1000);
      }
   }
}
