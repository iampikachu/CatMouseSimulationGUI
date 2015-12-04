public class Cat {
    
    // Constructors.
    public Cat ( ) {
	    myPosition = new Position ( );
    }
    
    public Cat (Position p) {
	    myPosition = p;
    }
    
    // An access function.
    public Position getPosition ( ) {
	    return myPosition;
    }
    
    // Move the cat around the statue:
    //	one meter toward the statue if the cat sees the mouse (or up to the statue
    //		if the cat is closer to the statue than one meter away), or 
    //	1.25 meters counterclockwise around the statue if the cat doesn't see the mouse.
    // Return true if the cat eats the mouse during the move, false otherwise.
    public boolean move (Position mousePosition) {
	// You fill this in.
        double mouse_distance = myPosition.getRadius() * Math.cos(myPosition.getAngle() - mousePosition.getAngle());
        double old_angle = myPosition.getAngle();
        double old_radius = myPosition.getRadius();
        if(mouse_distance >= 1.0 && myPosition.getRadius() > 1.0)
            if(myPosition.getRadius() < 2.0)
                myPosition.update(myPosition.getRadius() - 1.0, 0.0);
            else    
                myPosition.update(1.0, 0.0);
        else
            myPosition.update(0.0, 1.25);
        
        double angle_A = old_angle/old_radius; 
        double angle_B = mousePosition.getAngle()/mousePosition.getRadius();
        double angle_C = myPosition.getAngle()/myPosition.getRadius();

        boolean check = Math.cos(angle_B - angle_A) > Math.cos(angle_C - angle_A) && Math.cos(angle_C - angle_B) > Math.cos(angle_C - angle_A);
        return myPosition.getRadius() == 1.0 && check;        
    }
    
    private Position myPosition;
}
