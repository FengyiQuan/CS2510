import tester.Tester;
import java.util.*;

//to do: add your own Deque code 

class Vertex {
	String name;
	ArrayList<Edge> outEdges;

	Vertex(String name, ArrayList<Edge> outEdges){
		this.name = name;
		this.outEdges = outEdges;
	}

	// is there a path from this vertex to the given one?
	boolean hasPathTo(Vertex dest, ArrayList<Vertex> seen) {
		for (Edge e : this.outEdges) {
			if (!seen.contains(e.to)) {
				seen.add(e.to);
				if ( e.to == dest            // can get there in just one step
						||	 e.to.hasPathTo(dest, seen)) { // can get there on a path through e.to
					return true;
				}
			}
		}
		return false;
	} 

}

class Edge {
	Vertex from;
	Vertex to;
	int weight;

	Edge(Vertex from, Vertex to, int weight) {
		this.from = from;
		this.to = to;
		this.weight = weight;
	}
}


class Graph {
	ArrayList<Vertex> allVertices;

	Graph(ArrayList<Vertex> verts) {
		this.allVertices = verts;
	}
	
	//is there a path, using depth first search
	boolean hasPathDFS(Vertex from, Vertex to) {
		return this.hasPath(from, to, new Stack<Vertex>());
	}
	
	//is there a path, using breadth first search
	boolean hasPathBFS(Vertex from, Vertex to) {
		return this.hasPath(from, to, new Queue<Vertex>());
	}
	
	//is there a path from the first given vertex to the second one
	boolean hasPath(Vertex from, Vertex to, ICollection<Vertex> worklist) {
		//Deque<Vertex> worklist = new Deque<Vertex>();
		ArrayList<Vertex> seen = new ArrayList<Vertex>();
		
		worklist.add(from);
		
		while(worklist.size() > 0) {
			Vertex next = worklist.remove();
			
			if (next.equals(to)) {
				return true;
			}
			
			else if (seen.contains(next)) {
				
			}
			
			else {
				for (Edge e : next.outEdges) {
					worklist.add(e.to);
				}
			}
			seen.add(next);
		}
		return false;
	}
}
	
interface ICollection<T> {
	//adds a T to the collection
	void add(T t);
	//remove
	T remove();
	//size
	int size();
}



class Stack<T> implements ICollection<T> {
	Deque<T> items;
	
	Stack() {
		this.items = new Deque<T>();
	}

	//add an item to the top of the stack
	public void add(T t) {
		this.items.addAtHead(t);
		
	}

	//remove an item from the top of the stack
	public T remove() {
		return this.items.removeFromHead();
	}

	//how many items in this stack?
	public int size() {
		return this.items.size();
	}
}

class Queue<T> implements ICollection<T> {
	Deque<T> items;
	
	Queue() {
		this.items = new Deque<T>();
	}

	//add an item to the end of the queue
	public void add(T t) {
		this.items.addAtTail(t);
	}

	//remove from the front of the queue
	public T remove() {
		return this.items.removeFromHead();
	}

	//how many items in this queue?
	public int size() {
		return this.items.size();
	}
	
}

class GraphUtils {
	//gets the shortest path from one given node to another
	ArrayList<Vertex> shortestPath(Vertex source, Vertex target) {
		ArrayList<Vertex> unvisited = new ArrayList<Vertex>();
		HashMap<Vertex, Integer> distances = new HashMap<Vertex, Integer>();
		HashMap<Vertex, Vertex> predecessors = new HashMap<Vertex, Vertex>();
		
		unvisited.add(source);
		distances.put(source, 0);
		
		while (unvisited.size() > 0) {
			Vertex v = unvisited.remove(0);
			for (Edge e: v.outEdges) {
				if (distances.get(e.to) == null || distances.get(e.to) > distances.get(v) + e.weight) {
					distances.put(e.to, distances.get(v) + e.weight);
					predecessors.put(e.to, v);
					unvisited.add(e.to);
				}
			}		
		}
		
		ArrayList<Vertex> path = new ArrayList<Vertex>();
		
		Vertex step = target;
		
		if (predecessors.get(step) == null) {
			return path;
		}
		path.add(step);
		while (predecessors.get(step) != null) {
			step = predecessors.get(step);
			path.add(0,step);
		}
		return path;   // to get the cost: distances.get(target);
	}	
}

class Examples {
	Vertex a;
	Vertex b;
	Vertex c;
	Vertex d;
	Vertex e;
	Vertex f;
	Edge aToB;
	Edge eToB;
	Edge cToA;
	Edge bToC;
	Edge bToD;
	Edge dToF;
	Edge cToF;
	Graph g;

	void init() {
		this.a = new Vertex("A", new ArrayList<Edge>());
		this.b = new Vertex("B", new ArrayList<Edge>());
		this.c = new Vertex("C", new ArrayList<Edge>());
		this.d = new Vertex("D", new ArrayList<Edge>());
		this.e = new Vertex("E", new ArrayList<Edge>());
		this.f = new Vertex("F", new ArrayList<Edge>());
		this.aToB = new Edge(this.a, this.b, 1);
		this.eToB = new Edge(this.e, this.b, 3);
		this.cToA = new Edge(this.c, this.a, 2);
		this.bToC = new Edge(this.b, this.c, 10);
		this.bToD = new Edge(this.b, this.d, 1);
		this.dToF = new Edge(this.d, this.f, 5);
		this.cToF = new Edge(this.c, this.f, 1);
		this.a.outEdges.add(this.aToB);
		this.b.outEdges.add(this.bToD);
		this.b.outEdges.add(this.bToC);
		this.e.outEdges.add(this.eToB);
		this.c.outEdges.add(this.cToA);
		this.c.outEdges.add(this.cToF);
		this.d.outEdges.add(this.dToF);
		this.g = new Graph(new ArrayList<Vertex>(Arrays.asList(this.a, this.b, this.c, this.d, 
				this.e, this.f)));

	}

	void testPath(Tester t) {
		this.init();
		t.checkExpect(this.a.hasPathTo(this.d, new ArrayList<Vertex>()), true);
		t.checkExpect(this.a.hasPathTo(this.e, new ArrayList<Vertex>()), false);
		t.checkExpect(this.g.hasPathDFS(this.a, this.d), true);
		t.checkExpect(this.g.hasPathDFS(this.a, this.e), false);
		t.checkExpect(this.g.hasPathBFS(this.a, this.d), true);
		t.checkExpect(this.g.hasPathBFS(this.a, this.e), false);
		t.checkExpect(new GraphUtils().shortestPath(a, f), new ArrayList<Vertex>(Arrays.asList(a, b, d, f)));
		t.checkExpect(new GraphUtils().shortestPath(a, e), new ArrayList<Vertex>());
	}

}






















































