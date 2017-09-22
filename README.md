# play2-standalone-server

Simple play2 standalone server which will automatically find available ports on the host computer from a host range to starts the play2 server as well as akka actor system

# Usage

The standalone play2 server AkkaPlayServer will scan the port from "startPort" until it finds a port that is available,
it will then start the play2 server at that port, next it will then scan for the next available port to start actor system

```java
 AkkaPlayServer play2Server = new AkkaPlayServer();

int startPort = 7000;
play2Server.start(startPort);

Runtime.getRuntime().addShutdownHook(new Thread(() -> {
 logger.info("System shutdown");
}));

while (true) {
 Thread.sleep(1000);
}
```

The code aboves tries to start play2 server at port 7000, if it is available, then it will run at that port, if it is not available,
then it will scan for the next available port, say 7005, then starts at that port. Once this is done, it will then scan for the next
available port, say 7050, and starts the actor system at that port.

Now suppose,the play2 is started at port 7006, 

* To find the actor system settings, navigate http://localhost:7006/settings/actor-system.
* Or to find the actor system port, navigate to http://localhost:7006/settings/akka-port
