package mobilize.mx.testflatmap.di;

import javax.inject.Singleton;

import dagger.Component;
import mobilize.mx.restcore.module.RepositoryModule;
import mobilize.mx.testflatmap.ui.MainActivity;

@Component(modules = {RepositoryModule.class})
@Singleton
public interface AppComponent {
    void inject(MainActivity activity);
}
