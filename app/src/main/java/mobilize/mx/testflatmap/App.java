package mobilize.mx.testflatmap;

import android.app.Application;
import android.content.Context;

import mobilize.mx.restcore.module.RepositoryModule;
import mobilize.mx.testflatmap.di.AppComponent;
import mobilize.mx.testflatmap.di.DaggerAppComponent;

/**
 * <pre>
 *     author: Efra Morales - emoralest@gmail.com
 *     time  : 2019/04/25
 * </pre>
 */
public class App extends Application {
    private static App app;
    private static Context context;
    private AppComponent appComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        App.context = getApplicationContext();
        appComponent = DaggerAppComponent.builder()
                .repositoryModule(new RepositoryModule(this)).build();

    }

    public AppComponent getAppComponent() {
        return appComponent;
    }

    public static App getApp() {
        return app;
    }

    public static Context getAppContext() {
        return App.context;
    }

}
