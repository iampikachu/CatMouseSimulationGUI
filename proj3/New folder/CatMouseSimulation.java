public class CatMouseSimulation {
    
    // Run the simulation
    public static void runChase(Cat cat, Mouse mouse) {
    	int counter = 0;
    	String status = "init";
    	System.out.println(counter+ "       " + status + "      " + mouse.getPosition().toString() + "    " + cat.getPosition().toString());
    	
    	while (true) {
    		boolean eaten = cat.move(mouse.getPosition());
    		counter = counter + 1;
    		
    		if (eaten == false) {
    			mouse.move();
    			status = "running";
        		System.out.println(counter+ "       " + status + "   " + mouse.getPosition().toString() + "    " + cat.getPosition().toString());
    		} else {
    			status = "eaten";
            	System.out.println(counter+ "       " + status + "     " + mouse.getPosition().toString() + "    " + cat.getPosition().toString());
            	break;
    		}
    		
    		if (counter == 32) {
    			System.out.println("After 31 min, the cat didn't chase up the mouse and the cat got tired and wander off.");
    			break;
    		}
    	}  	
    }

    // Set up the arguments and then call runChase to run the simulation
    public static void main(String [] args) {
    	System.out.println("Welcome to the CS9G cat and mouse simulation!");
//    	System.out.println("TIME" + "    " + "STATUS" + "    " + "MOUSE" + "        " + "CAT");
    	  	
//    	Cat myCat = new Cat (new Position (3.0, 0.0));
//    	Mouse myMouse = new Mouse (new Position (1.0, 0.0));
//    	
//    	runChase (myCat, myMouse);
    	testCases();
    	
	
    }
    
    public static void testCases () {
    	System.out.println("Test Case 1:");
    	System.out.println("TIME" + "    " + "STATUS" + "    " + "MOUSE" + "        " + "CAT");
    	Cat myCat1 = new Cat (new Position (1.0, 0.19*Math.PI));
    	Mouse myMouse1 = new Mouse (new Position (1.0, 2.2*Math.PI));
    	runChase (myCat1, myMouse1);
    	
    	System.out.println("Test Case 2:");
    	System.out.println("TIME" + "    " + "STATUS" + "    " + "MOUSE" + "        " + "CAT");
    	Cat myCat2 = new Cat (new Position (3.2, 0.0));
    	Mouse myMouse2 = new Mouse (new Position (1.0, -0.32*Math.PI));
    	runChase (myCat2, myMouse2);
    	
    	System.out.println("Test Case 3:");
    	System.out.println("TIME" + "    " + "STATUS" + "    " + "MOUSE" + "        " + "CAT");
    	Cat myCat3 = new Cat (new Position (8.1, 0.0));
    	Mouse myMouse3 = new Mouse (new Position (1.0, 0.25*Math.PI));
    	runChase (myCat3, myMouse3);
    }
}