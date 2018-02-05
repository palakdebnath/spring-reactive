package com.example.reactive.springreactive;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.context.request.async.DeferredResult;

import com.example.reactive.springreactive.model.Message;
import com.example.reactive.springreactive.model.Pubsub;
import com.example.reactive.springreactive.repository.PubsubRepository;
import com.example.reactive.springreactive.service.CompletableFutureService;

import akka.Done;
import akka.NotUsed;
import akka.actor.ActorSystem;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.OverflowStrategy;
import akka.stream.javadsl.Source;

@SpringBootApplication
public class SpringReactiveApplication {
	
	private static final Long DEFERRED_RESULT_TIMEOUT = 1000L;
	private AtomicLong id = new AtomicLong(0);
	
	@Autowired
    private ActorSystem actorSystem;

	@Autowired
    private PubsubRepository pubsubRepository;
	
	@Autowired
    private CompletableFutureService completableFutureService;

	@Bean
	CommandLineRunner init() {

		return args -> { 

			final Materializer materializer = ActorMaterializer.create(actorSystem);

			Source<Pubsub, NotUsed> source = Source.fromPublisher(pubsubRepository.findWithTailableCursorBy(
					new ArrayList<String>(Arrays.asList("display,greeting,insert".split(",")))));

			final CompletionStage<Done> done = source.buffer(1, OverflowStrategy.backpressure()).runForeach( s -> {

				System.out.println(s);

				if("display".equals(s.getType())) {

					DeferredResult<Message> deferred = new DeferredResult<>(DEFERRED_RESULT_TIMEOUT);
					CompletableFuture<Message> future = completableFutureService.getDisplay("display", id.getAndIncrement());
					future.whenComplete((result, error) -> {
						if (error != null) {
							deferred.setErrorResult(error);
						} else {
							deferred.setResult(result);
						}
					});
				} else if("greeting".equals(s.getType())) {

					DeferredResult<Message> deferred = new DeferredResult<>(DEFERRED_RESULT_TIMEOUT);
					CompletableFuture<Message> future = completableFutureService.get("greeting", id.getAndIncrement());
					future.whenComplete((result, error) -> {
						if (error != null) {
							deferred.setErrorResult(error);
						} else {
							deferred.setResult(result);
						}
					});
				}

			}, materializer);

			done.thenRun(() -> actorSystem.terminate());

		};
	}

	
	
	
	
	
	
	
	
	
	
	
	
/*	
	
	
	@Bean
	CommandLineRunner init() {
		
		return args -> { 
		ConnectionString conn = new ConnectionString("mongodb://localhost:27017");
    	MongoClient mongoClient = MongoClients.create(conn);

    	MongoCollection<Document> coll = mongoClient.getDatabase("reactive").getCollection("pubsub");

    	//Approach 2 using akka streams + actor system
    	final ActorSystem system = ActorSystem.create("QuickStart");
    	final Materializer materializer = ActorMaterializer.create(system);
    	
    	//final CompletionStage<Done> done = pubsubRepository.findWithTailableCursorBy("display").buffer(1, OverflowStrategy.backpressure());
    	
    	
    	

    	Source<Pubsub, NotUsed> source = Source.fromPublisher(pubsubRepository.findWithTailableCursorBy("display"));
    	
    	Source<Document, NotUsed> source = Source.fromPublisher(
    			coll.find(in("type", "display", "greeting", "insert")).cursorType(CursorType.Tailable));
    	
    
    	
    	final CompletionStage<Done> done = source.buffer(1, OverflowStrategy.backpressure()).runForeach( s -> {
    		
    		System.out.println(s);
    	
    		
    		if("display".equals(s.getType())) {
    		
	    		DeferredResult<Message> deferred = new DeferredResult<>(DEFERRED_RESULT_TIMEOUT);
	    		CompletableFuture<Message> future = completableFutureService.getDisplay("display", id.getAndIncrement());
	    	     future.whenComplete((result, error) -> {
	    	         if (error != null) {
	    	             deferred.setErrorResult(error);
	    	         } else {
	    	             deferred.setResult(result);
	    	         }
	    	     });
    		} else if("greeting".equals(s.getType())) {
    			
    			DeferredResult<Message> deferred = new DeferredResult<>(DEFERRED_RESULT_TIMEOUT);
	    		CompletableFuture<Message> future = completableFutureService.get("greeting", id.getAndIncrement());
	    	     future.whenComplete((result, error) -> {
	    	         if (error != null) {
	    	             deferred.setErrorResult(error);
	    	         } else {
	    	             deferred.setResult(result);
	    	         }
	    	     });
    		}
    		
    	}, materializer);
    	
    	done.thenRun(() -> system.terminate());

		};
	}
	
	*/
	

/*	
	
	
	@Bean
	CommandLineRunner init() {
		
		return args -> { 
		ConnectionString conn = new ConnectionString("mongodb://localhost:27017");
    	MongoClient mongoClient = MongoClients.create(conn);

    	MongoCollection<Document> coll = mongoClient.getDatabase("reactive").getCollection("pubsub");

    	//Approach 2 using akka streams + actor system
    	final ActorSystem system = ActorSystem.create("QuickStart");
    	final Materializer materializer = ActorMaterializer.create(system);

    	Source<Document, NotUsed> source = Source.fromPublisher(
    			// coll.find(and(eq("type", "display") )).cursorType(CursorType.Tailable));
    	
    			coll.find(in("type", "display", "greeting", "insert")).cursorType(CursorType.Tailable));
    			
    	final CompletionStage<Done> done = source.buffer(1, OverflowStrategy.backpressure()).runForeach(s -> {
    		
    		System.out.println(s.toJson());
    	
    		
    		if(s.get("type").equals("display")) {
    		
	    		DeferredResult<Message> deferred = new DeferredResult<>(DEFERRED_RESULT_TIMEOUT);
	    		CompletableFuture<Message> future = completableFutureService.getDisplay("display", id.getAndIncrement());
	    	     future.whenComplete((result, error) -> {
	    	         if (error != null) {
	    	             deferred.setErrorResult(error);
	    	         } else {
	    	             deferred.setResult(result);
	    	         }
	    	     });
    		} else if(s.get("type").equals("greeting")) {
    			
    			DeferredResult<Message> deferred = new DeferredResult<>(DEFERRED_RESULT_TIMEOUT);
	    		CompletableFuture<Message> future = completableFutureService.get("greeting", id.getAndIncrement());
	    	     future.whenComplete((result, error) -> {
	    	         if (error != null) {
	    	             deferred.setErrorResult(error);
	    	         } else {
	    	             deferred.setResult(result);
	    	         }
	    	     });
    		}
    		
    	}, materializer);
    	
    	done.thenRun(() -> system.terminate());

		};
	}
	

*/
	public static void main(String[] args) {
		SpringApplication.run(SpringReactiveApplication.class, args);
	}
}


