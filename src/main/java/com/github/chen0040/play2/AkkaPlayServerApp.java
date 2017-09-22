package com.github.chen0040.play2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.io.File;
import java.io.IOException;
import java.util.UUID;


/**
 * Created by chen0 on 29/5/2016.
 */
public class AkkaPlayServerApp {

   private static final Logger logger = LoggerFactory.getLogger(AkkaPlayServerApp.class);
   private static String corrid = "play2";
   private static String startupDirPath = "";



   public static void main(String[] args) throws IOException, InterruptedException {
      AkkaPlayServer producer = new AkkaPlayServer();

      logger.info("Corr ID: {}", corrid);
      int port = 7000;
      logger.info("start ZK and attempt to register, starting at port: " + port);
      producer.start(port);

      Runtime.getRuntime().addShutdownHook(new Thread(() -> {
         logger.info("System shutdown");
      }));

      while (true) {
         Thread.sleep(1000);
      }
   }
}
