package model;

import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;

public class Converter {

    //single pattern converter, to make sure the converter can be instantiate once
    private static Converter converter;
    private Converter(){

    }
    public static Converter getInstance(){
        if(converter == null){
            converter = new Converter();
        }
        return converter;
    }

    //function to convert images use CustomImage object and the destinationPath to save image.
    public boolean convertImages(CustomImage ima, String destinationPath){
        boolean ifConvert = false;//field to know if convert succeed, default: false
        String sourcePath = ima.getSourcePath();//get image information from object
        int width = ima.getWidth();
        int height = ima.getHeight();
        ConvertCmd convertCmd = new ConvertCmd();
        IMOperation op = new IMOperation();
        op.addImage(sourcePath);
        op.resize(width, height);//resize image according to width and height given
        op.addImage(destinationPath);
        try {
            convertCmd.run(op);//execute convert command.
            ifConvert = true;//change value, refer to successful converted.
        } catch (Exception e) {
            e.printStackTrace();//failed.
        }
        return ifConvert;
    }
}
