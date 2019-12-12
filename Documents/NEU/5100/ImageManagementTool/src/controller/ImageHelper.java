package controller;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

public class ImageHelper implements ImageHelperInterface {

    //function to get a random integer values between 0 to range
    @Override
    public int getRandom(int range){
        int random = (int)(Math.random()*range);
        return random;
    }

}
