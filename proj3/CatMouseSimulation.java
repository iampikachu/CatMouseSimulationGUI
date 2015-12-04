public class CatMouseSimulation {
    
    // Run the simulation
    public static void runChase(Cat cat, Mouse mouse) {
        System.out.println("TIME STATUS MOUSE CAT");
        System.out.println("0" + " init " + mouse.getPosition().toString() + " " + cat.getPosition().toString());

        int time = 1;
        while(time < 31){
            if(cat.move(mouse.getPosition())){
                System.out.println(time +  " eaten " + mouse.getPosition().toString() + " " + cat.getPosition().toString());
                return;
            }
            
            mouse.move();
            System.out.println(time + " running " + mouse.getPosition().toString() + " " + cat.getPosition().toString());   
            time++;
        }
        System.out.println(time + " escaped " + mouse.getPosition().toString() + " " + cat.getPosition().toString());    
    }

    // Set up the arguments and then call runChase to run the simulation
    public static void main(String [] args) {
        ///*
	    if(args.length != 3){
            System.out.println("Usage: java CatMouseSimulation <catRadius> <catAngle> <mouseAngle>");
            return;
        }
        double catRadius = Double.parseDouble(args[0]);
        double catAngle = Double.parseDouble(args[1]);
        double mouseAngle = Double.parseDouble(args[2]);
        
        Position cat_position = new Position(catRadius, catAngle);
        Position mouse_position = new Position(1.0, mouseAngle);

        Cat cat = new Cat(cat_position);
        Mouse mouse = new Mouse(mouse_position);

        System.out.println("Welcome to the CS9G cat and mouse simulation!");
        runChase(cat, mouse);
        //*/
        //runTests();
    }
    public static void runTests(){
        System.out.println("Running test output...\n\n");

        System.out.println("Test Case 1:");
        System.out.println("TIME " + " STATUS " + " MOUSE " + " CAT");
        Cat test_cat = new Cat(new Position(1.0, 0.610865));
        Mouse test_mouse = new Mouse(new Position(1.0, 6.9115));
        runChase(test_cat, test_mouse);
    
        System.out.println("\n\nTest Case 2:");
        System.out.println("TIME " + " STATUS " + " MOUSE " + " CAT");
        test_cat = new Cat(new Position(3.2, 0));
        test_mouse = new Mouse(new Position(1.0, -1.00530964915));
        runChase(test_cat, test_mouse);

        System.out.println("\n\nTest Case 3:");
        System.out.println("TIME " + " STATUS " + " MOUSE " + " CAT");
        test_cat = new Cat(new Position(8.1, 0));
        test_mouse = new Mouse(new Position(1.0, 0.785398));
        runChase(test_cat, test_mouse);

        System.out.println("\n\nTest Case 4:");
        System.out.println("TIME " + " STATUS " + " MOUSE " + " CAT");
        test_cat = new Cat(new Position(3.0, 0.0));
        test_mouse = new Mouse(new Position(1.0, 0.0));
        runChase(test_cat, test_mouse);

    }
}
