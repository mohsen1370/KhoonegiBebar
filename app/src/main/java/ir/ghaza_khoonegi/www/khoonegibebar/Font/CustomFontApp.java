package ir.ghaza_khoonegi.www.khoonegibebar.Font;

import android.app.Application;

import static ir.ghaza_khoonegi.www.khoonegibebar.Font.FontsOverride.*;


/**
 * Created by vamsi on 06-05-2017 for android custom font article
 */

public class CustomFontApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        setDefaultFont(this, "MONOSPACE", "fonts/irsans.ttf");
    }
}
