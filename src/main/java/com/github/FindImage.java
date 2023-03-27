package com.github;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class FindImage {

//    public static void main(String[] args) {
//        try {
//            long startTime = System.currentTimeMillis();
//            Rectangle rectangle = new Rectangle(288,348,100,100);
//            CoordBean imageForScreen = findImageForScreen(rectangle, "C:\\Ran\\word\\img\\2.jpg", new Robot());
//            System.out.println("耗时:"+(System.currentTimeMillis()-startTime));
//            if(imageForScreen!=null){
//                System.out.println("查找完毕---坐标是" + imageForScreen.getX()+ "," + imageForScreen.getY());
//            }
//        } catch (AWTException e) {
//            e.printStackTrace();
//        }
//    }


    /**
     * @param rectangle Rectangle对象，xy坐标以及宽高的定义
     * @param filePath 需要查找的图片路径
     * @param robot Robot对象(不传值会自己创建)
     */
        public static CoordBean findImageForScreen(Rectangle rectangle,String filePath,Robot robot){
            try {
                //获取屏幕宽和高
			    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                if(rectangle==null){
                    rectangle=new Rectangle(0,0,screenSize.width,screenSize.height);
                }
                if(robot==null){
                    robot = new Robot();
                }
                //全屏截图
                ImageIO.setUseCache(false);
                BufferedImage screenImg = robot.createScreenCapture(rectangle);
                OutputStream out = new FileOutputStream("C:\\Ran\\word\\img\\jieping.png");
                ImageIO.write(screenImg, "png", out);//将截到的BufferedImage写到本地
                InputStream inputStream = new FileInputStream(filePath);
                BufferedImage searchImg = ImageIO.read(inputStream);//将要查找的本地图读到BufferedImage
                //图片识别工具类
                ImageCognition ic = new ImageCognition();
                List<CoordBean> list = ic.imageSearch(screenImg, searchImg, ImageCognition.SIM_ACCURATE_VERY);
                if( list.size() > 0){
//                    for (CoordBean imgXy : list) {
//                        return imgXy;
//                    }
                    CoordBean coordBean = list.get(0);
                    coordBean.setX(coordBean.getX()+(int)rectangle.getX());
                    coordBean.setY(coordBean.getY()+(int)rectangle.getY());
                    return coordBean;
                }
//                else {
//                    System.out.println("没找到");
//                }
            }catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }
}
