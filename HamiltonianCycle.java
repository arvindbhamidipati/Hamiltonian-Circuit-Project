package Graphs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HamiltonianCycle<E> extends Graph<E> {
	private LinkedStack<E> path;
	private LinkedStack<E> removedEdges;
	private ArrayList<String> cityList;
	
	public HamiltonianCycle() {
		path = new LinkedStack<E>();
		removedEdges = new LinkedStack<E>();
		cityList = new ArrayList<String>();
	}

	public boolean removeEdge(E start, E end) {
		boolean result = this.remove(start, end);
		if (result) {
			removedEdges.push(end);
			removedEdges.push(start);
		}
		return result;
	} // end removeEdge

	public Pair<String, String> undoRemove() {
		if (removedEdges.isEmpty())
			return null;
		E src = removedEdges.pop();
		E dst = removedEdges.pop();
		Pair<String, String> lastRemoval = new Pair<String, String>((String)src, (String)dst);
		this.addEdge(src, dst, 0);
		return lastRemoval;
	}

	/*public void printPath() {
		while (!path.isEmpty())
			System.out.println(path.pop());
	}*/
	public String printPath(){
		String result = vertexSet.get(cityList.get(0)).getData().toString() + " \n";
		while (!path.isEmpty())
			result += path.pop() + " \n";
		return result;
	}

	public boolean calcHamiltonian() {
		unvisitVertices();
		Vertex<E> startVertex = vertexSet.get(cityList.get(0));
		return calcHamiltonianHelper(startVertex, 0);
	}

	private boolean calcHamiltonianHelper(Vertex<E> startVertex, int n) {
		//E startData = startVertex.getData();
		if (n >= vertexSet.size() && startVertex == vertexSet.get(cityList.get(0))){
			return true;
		}
		if (startVertex.isVisited())
			return false;
		startVertex.visit();
		Vertex<E> nextVertex = startVertex;
		Iterator<Map.Entry<E, Pair<Vertex<E>, Double>>> iter = nextVertex.iterator();
		while (iter.hasNext()) {
			Entry<E, Pair<Vertex<E>, Double>> nextEntry = iter.next();
			Vertex<E> neighborVertex = nextEntry.getValue().first;
			if (calcHamiltonianHelper(neighborVertex, n + 1)) {
				path.push(neighborVertex.getData());
				return true;
			}
		}
		startVertex.unvisit();
		return false;
	}

	
	public int dispCityTable() {
		Iterator<Entry<E, Vertex<E>>> iter;
		
		System.out.println("\tList of Cities: ");
		if(cityList.isEmpty()){
			iter = vertexSet.entrySet().iterator();
			while (iter.hasNext()) {
				cityList.add((String) iter.next().getValue().getData());
			}
		}
		for(int i = 0; i < cityList.size(); i++){
			String temp = cityList.get(i);
			System.out.println((i+1) + ". " + temp);
		}
		return cityList.size();
	}
	
	public ArrayList<String> getCities(){
		return cityList;
	}
	
	public void showBFT(Visitor<E> visitor)
	   {
		   unvisitVertices();
		   String startElement = cityList.get(0);
		   if(startElement == null){
			   return;
		   }
		   Vertex<E> startVertex = vertexSet.get(startElement);
		   showBFTHelper( startVertex, visitor );
	   }

	   /** Depth-first traversal from the parameter startElement */
	   public void showDFT(Visitor<E> visitor)
	   {
		   unvisitVertices();
		   String startElement = cityList.get(0);
		   if(startElement == null){
			   return;
		   }
		   Vertex<E> startVertex = vertexSet.get(startElement);
		   showDFTHelper( startVertex, visitor, 1);
	   }

	   protected void showBFTHelper(Vertex<E> startVertex,
			   Visitor<E> visitor)
	   {
		   LinkedQueue<Vertex<E>> vertexQueue = new LinkedQueue<>();
		   E startData = startVertex.getData();
		   int counter = 1;
		   System.out.print(counter + ". ");
		   startVertex.visit();		   
		   visitor.visit(startData);
		   vertexQueue.enqueue(startVertex);
		   while( !vertexQueue.isEmpty() )
		   {
			   Vertex<E> nextVertex = vertexQueue.dequeue();
			   Iterator<Map.Entry<E, Pair<Vertex<E>, Double>>> iter =
					   nextVertex.iterator(); // iterate adjacency list

			   while( iter.hasNext() )
			   {
				   Entry<E, Pair<Vertex<E>, Double>> nextEntry = iter.next();
				   Vertex<E> neighborVertex = nextEntry.getValue().first;
				   if( !neighborVertex.isVisited() )
				   {
					   counter++;
					   System.out.print(counter + ". ");
					   vertexQueue.enqueue(neighborVertex);
					   neighborVertex.visit();
					   visitor.visit(neighborVertex.getData());
				   }
			   }
		   }
	   } // end breadthFirstTraversalHelper
	   
	   public void showDFTHelper(Vertex<E> startVertex, Visitor<E> visitor, int count)
	   {
	       // YOU COMPLETE THIS (USE THE ALGORITHM GIVEN FOR LESSON 11 EXERCISE)
		   E startData = startVertex.getData();
		   System.out.print(count + ". ");	   
		   startVertex.visit();
		   visitor.visit(startData);
		   Vertex<E> nextVertex = startVertex;
		   Iterator<Map.Entry<E, Pair<Vertex<E>, Double>>> iter = nextVertex.iterator(); // iterate adjacency list
		   while( iter.hasNext() )
		   {
			   Entry<E, Pair<Vertex<E>, Double>> nextEntry = iter.next();
			   Vertex<E> neighborVertex = nextEntry.getValue().first;
			   if( !neighborVertex.isVisited() )
			   {
				   
				   showDFTHelper(neighborVertex, visitor, count+1);
			   }
		   }
	   }
	   
	   @Override
	   public void clear(){
		   vertexSet.clear();
		   while(!path.isEmpty()){
			   path.pop();
		   }
		   while(!removedEdges.isEmpty()){
			   removedEdges.pop();
		   }
		   cityList.clear();
	   }
		   
} // end HamiltonianCycle
