package it.digitalgarage.marketplace.commons.async.workmanager;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;

import it.digitalgarage.marketplace.commons.logging.Logger;
import it.digitalgarage.marketplace.commons.logging.LoggerFactory;



public class ParallelExecutorService<I,O> {
	
	private static final Logger logger = LoggerFactory.getLogger(ParallelExecutorService.class);
	
	private int simultanuosTask;
	private AsyncTaskExecutor executorService;
	private ParamCallable<I, O> paramCallable;
	
	public ParallelExecutorService(AsyncTaskExecutor executorService,ParamCallable<I, O> paramCallable,int simultanuosTask) {
		this.executorService=executorService;
		this.paramCallable = paramCallable;
		this.simultanuosTask=simultanuosTask;
	}
	
	public ParallelExecutorService(ExecutorService executorService,ParamCallable<I, O> paramCallable,int simultanuosTask) {
		this(new ConcurrentTaskExecutor(executorService), paramCallable, simultanuosTask);
	}
	
	
	public List<Result<I,O>> execute(List<I> items) throws Exception{
		List<Result<I,O>> ret = new ArrayList<>();
		int blocchi = items.size() / simultanuosTask;
		int resto = items.size()   % simultanuosTask;
		logger.info("eseguo items.size()=={} a blocchi di {}. Numero blocchi {}, resto {} ",items.size(),simultanuosTask,blocchi,resto);
		for(int i=0;i<blocchi;i=i+1){
			logger.debug("inizio elabazione blocco {}",i);
			List<Result<I,Future<O>>> tmpRet = new ArrayList<>();
			for(int j=0;j<simultanuosTask;j++){
				int index = i*simultanuosTask+j;
				I input = items.get(index);
				logger.debug("elaboro {}){}",index,input);
				Future<O> output = executorService.submit(new Task<I,O>(input,this.paramCallable));
				tmpRet.add(new Result<I, Future<O>>(input, output));
			}
			result(ret, tmpRet);
			logger.debug("fine elabazione blocco {}",i);
		}
		logger.debug("inizio elabazione resto");
		List<Result<I,Future<O>>> tmpRet = new ArrayList<>();
		for(int i=items.size()-resto;i<items.size();i++){
			I input = items.get(i);
			Future<O> output = executorService.submit(new Task<I,O>(input,this.paramCallable));
			tmpRet.add(new Result<I, Future<O>>(input, output));
		}
		result(ret, tmpRet);
		logger.debug("fine elabazione resto");
		return ret;
	}


	private static <I,O> void result(List<Result<I, O>> ret, List<Result<I, Future<O>>> tmpRet) throws Exception {
		for(int k=0;k<tmpRet.size();k++){
			Result<I, Future<O>> result = tmpRet.get(k);
			ret.add(new Result<I, O>(result.getInput(), result.getOutput().get()));
		}		
	}
	
	
	public static class Task<I,O> implements Callable<O>{
		
		private I input;
		private ParamCallable<I, O> paramCallable;
		
		public Task(I input,ParamCallable<I,O> paramCallable) {
			this.input=input;
			this.paramCallable=paramCallable;
		}
		
		@Override
		public O call() throws Exception {
			return paramCallable.execute(input);
		}
	}
	
	
	public static interface ParamCallable<I,O>{
		public O execute(I input) throws Exception;
	}
	
	public static class Result<I,O>{
		private I input;
		private O output;
		
		public Result(I input,O output) {
			this.input=input;
			this.output=output;
		}
		
		public I getInput() {
			return input;
		}
		
		public O getOutput() {
			return output;
		}
		
		@Override
		public String toString() {
			StringBuilder sb = new StringBuilder();
			sb.append("input:");
			sb.append(input);
			sb.append("output:");
			sb.append(output);
			return sb.toString();
		}
	}
	
	
	public static void main(String[] args) throws Exception {
		ParallelExecutorService<Integer, Integer> p = new ParallelExecutorService<Integer, Integer>(new ConcurrentTaskExecutor(Executors.newCachedThreadPool()), new ParamCallable<Integer, Integer>() {
			
			@Override
			public Integer execute(Integer input) throws Exception {
				System.out.println(input+"^2="+input*input);
				return input*input;
			}
		}, 6);
		List<Integer> val = new ArrayList<Integer>();
		for(int i=0;i<100;i++){
			val.add(i);
		}
		
		
		List<Result<Integer, Integer>> results = p.execute(val);
		for(Result<Integer, Integer> result : results){
			System.out.println(result.getInput()+">"+result.getOutput());
		}
	}

}
