package com.example.qiaoyunhao.myimages;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
//###########开始########## 第3_1步：添加必要的文件库 引用
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
//##############结束################
import java.nio.file.FileSystemLoopException;

public class MainActivity extends AppCompatActivity {

    ImageView imgView;         //第3_2步：将自己添加的控件定义一下
    Button btnNDK, btnRestore;
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");

        System.loadLibrary("imgCanny"); //第2_0步：imgCanny为上述CMakeLists.txt所使用的Add_library所定义的函数库名称
        System.loadLibrary("opencv_java3");


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //#################开始############## 第3_3步：app打开的初始画面和空间的赋值；
        // 读入图片（所读入的图片在res/drawable文件夹中，复制进去就可以）。
        btnRestore = (Button) this.findViewById(R.id.btnRestore);
        btnRestore.setOnClickListener(new ClickEvent());
        btnNDK = (Button) this.findViewById(R.id.btnNDK);
        btnNDK.setOnClickListener(new ClickEvent());
        imgView = (ImageView) this.findViewById(R.id.imageView01);
        Bitmap img = ((BitmapDrawable) getResources().getDrawable(
                R.drawable.girl)).getBitmap();  //读入图片
        imgView.setImageBitmap(img);
        //###################结束#########################
// Example of a call to a native method
        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);//这两行是native-lib.cpp中函数用的，不要和myCanny.cpp弄混。
        tv.setText(stringFromJNI());
    }
    //##################开始############第3_4步：点击事件；调用C++文件中的函数，并返回图片处理后的结果；显示结果；
    class ClickEvent implements View.OnClickListener {
        public void onClick(View v) {
             //btnRestore.setText(ImgFun());
            if (v == btnNDK) {
                long current = System.currentTimeMillis();
                Bitmap img1 = ((BitmapDrawable) getResources().getDrawable(
                        R.drawable.girl)).getBitmap();
                int w = img1.getWidth(), h = img1.getHeight();
                int[] pix = new int[w * h];
                img1.getPixels(pix, 0, w, 0, 0, w, h);
                int[] resultInt = getCannyImg(pix, w, h);
                Bitmap resultImg = Bitmap.createBitmap(w, h, Bitmap.Config.RGB_565);
                resultImg.setPixels(resultInt, 0, w, 0, 0, w, h);
                long performance = System.currentTimeMillis() - current;
                imgView.setImageBitmap(resultImg);
            } else if (v == btnRestore) {
                    Bitmap img2 = ((BitmapDrawable) getResources().getDrawable(
                            R.drawable.girl)).getBitmap();
                    imgView.setImageBitmap(img2);
                }
            }
        }
        //####################结束################//！！！至此，APP开发完成，后面进行打包即可！！！

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
    public native int[] getCannyImg(int[] a, int b, int c);//第2_1步：声明.cpp中的原型函数，并在回传类型前加上native
    //到此就可以编译程序了，并出现android界面！！！
    // 注：如果出现 链接错误导致无定义：
    // 可以尝试将MAkeLists.txt文件里的target_link_libraries改一下顺序，如将主要调用opencv的imgCanny放在前面。
    //下面步骤是界面的设计：第3_0步 见 src->main->res -> layout -> activity_main.xml文件
}
