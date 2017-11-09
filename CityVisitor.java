package Graphs;


public class CityVisitor implements Visitor<String>{
	@Override
	public void visit(String city){
		System.out.println(city);
	}

}
