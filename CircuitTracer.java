import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;



/**
 * Search for shortest paths between start and end points on a circuit board
 * as read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to
 * a GUI according to options specified via command-line arguments.
 * 
 * @author mvail
 * @author Rich Boundji
 */
public class CircuitTracer {

	/** launch the program
	 * @param args three required arguments:
	 *  first arg: -s for stack or -q for queue
	 *  second arg: -c for console output or -g for GUI output
	 *  third arg: input file name 
	 */
	public static void main(String[] args) {
		new CircuitTracer(args); //create this with args
	}

	/** Print instructions for running CircuitTracer from the command line. */
	private void printUsage() {
		System.out.println("Usage: java CircuitTracer [-s -- use a stack for storage | -q -- use a queue for storage ] [-c --run program in console mode | -g --run program in GUI mode ] fileName "); //???
	}
	
	/** 
	 * Set up the CircuitBoard and all other components based on command
	 * line arguments.
	 * 
	 * @param args command line arguments passed through from main()
	 */
	public CircuitTracer(String[] args) {
		if (args.length != 3) {
			printUsage();
			return; //exit the constructor immediately
		}
		
		Storage<TraceState> stateStore = null;
		
		if(args[0].equals("-s") || args[0].equals("-q")) {
			if(args[0].equals("-s")) {
				stateStore  = Storage.getStackInstance();
			}
			if(args[0].equals("-q")) {
				stateStore  = Storage.getQueueInstance();
			}
		} else {
			printUsage();
			return;
		}
		
		try {
			CircuitBoard board = new CircuitBoard(args[2]);
			ArrayList<TraceState> bestPath = new ArrayList<TraceState>();
			Point statringPoint = board.getStartingPoint();
			
			if(board.isOpen((int)statringPoint.getX()+1, (int)statringPoint.getY())) {
				stateStore.store(new TraceState(board,(int)statringPoint.getX()+1, (int)statringPoint.getY()));
			}
			if(board.isOpen((int)statringPoint.getX()-1, (int)statringPoint.getY())) {
				stateStore.store(new TraceState(board,(int)statringPoint.getX()-1, (int)statringPoint.getY()));
			}
			if(board.isOpen((int)statringPoint.getX(), (int)statringPoint.getY()+1)) {
				stateStore.store(new TraceState(board,(int)statringPoint.getX(), (int)statringPoint.getY()+1));
			}
			if(board.isOpen((int)statringPoint.getX(), (int)statringPoint.getY()-1)) {
				stateStore.store(new TraceState(board,(int)statringPoint.getX(), (int)statringPoint.getY()-1));
			}
			
			while (!stateStore.isEmpty()) {
				TraceState nextTraceState = stateStore.retrieve();
				if(nextTraceState.isComplete()) {
					if(bestPath.isEmpty() || nextTraceState.pathLength() == bestPath.get(0).pathLength()) {
						bestPath.add(nextTraceState);
					} else if(nextTraceState.pathLength() < bestPath.get(0).pathLength()){
						bestPath.clear();
						bestPath.add(nextTraceState);
					}
				} else {
					if(nextTraceState.isOpen((int)nextTraceState.getRow()+1, (int)nextTraceState.getCol())) {
						stateStore.store(new TraceState(nextTraceState,(int)nextTraceState.getRow()+1, (int)nextTraceState.getCol()));
					}
					if(nextTraceState.isOpen((int)nextTraceState.getRow()-1, (int)nextTraceState.getCol())) {
						stateStore.store(new TraceState(nextTraceState,(int)nextTraceState.getRow()-1, (int)nextTraceState.getCol()));
					}
					if(nextTraceState.isOpen((int)nextTraceState.getRow(), (int)nextTraceState.getCol()+1)) {
						stateStore.store(new TraceState(nextTraceState,(int)nextTraceState.getRow(), (int)nextTraceState.getCol()+1));
					}
					if(nextTraceState.isOpen((int)nextTraceState.getRow(), (int)nextTraceState.getCol()-1)) {
						stateStore.store(new TraceState(nextTraceState,(int)nextTraceState.getRow(), (int)nextTraceState.getCol()-1));
					}
				}
			}
			
			if(args[1].equals("-c") || args[1].equals("-g")) {
				if(args[1].equals("-c")) {
					for(TraceState trace: bestPath) {
						System.out.println(trace.toString());
					}
				} 
				if(args[1].equals("-g")) {
					System.out.println("Sorry, -g GUI output option is not supported at this time.");
				} 
			} else {
				printUsage();
				return;
			}
		} 
		catch (FileNotFoundException e) {
			System.out.println(e.toString());
		} 
		catch (Exception e) {
			System.out.println(e.toString());
		}
	}
} // class CircuitTracer
